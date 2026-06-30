// pages/funtions/translatePage/trans_page.js

const app = getApp();
//引入插件：微信同声传译
const plugin = requirePlugin('WechatSI');
//获取全局唯一的语音识别管理器recordRecoManager
const manager = plugin.getRecordRecognitionManager();
Page({

  /**
   * 页面的初始数据
   */
  data: {
    test:"",//含符号的不可点击？
    trans:"",
    listA:[],
    selectId:-1,
    showDetail:false,
    postionLeft:0,
    postiontop:0,
    wordname:"",
    lanType:["uk","en"],//uk==英国 ，en美国
    longmusic:false,
    tranMaxSize:300,
    tranNowSize:0,
    content:'',
  },

  /**
   * 生命周期函数--监听页面加载
   */
  

  onLoad(options) {
    var user_base_data =wx.getStorageSync('user_base_data');
    var that=this;
    wx.request({
      url: 'http://localhost:1234/Trans/normalonetran',
      data:{
        userId:user_base_data.userId,
        bookId:user_base_data.bookId,
      },
      success(res){
        if(res.data!=null){
        console.log(res.data);
        var listA=res.data.content.split(" ");
        that.setData({
          test:res.data.content,
          listA:listA,
          trans:res.data.trans,
          transId:res.data.transId,
          user_base_data:user_base_data
        })}else{
          wx.showToast({
            title: '你翻译完啦！',
            icon: 'none',
          })
        }
      }
    })
    
    this.innerAudioContext = wx.createInnerAudioContext();
    this.innerAudioContext.onError(function (res) {
      //console.log(res);
      
      wx.showToast({
        title: '语音播放失败',
        icon: 'none',
      })
    }) 
  

  },
  wordYun (e) {
    var that = this;
    var content = this.data.test;
    plugin.textToSpeech({
      lang: "en_US",
      tts: true,
      content: content,
      success: function (res) {
        console.log(res);
        console.log("succ tts", res.filename);
        that.setData({
          src: res.filename
        })
        that.yuyinPlay();
      },
      fail: function (res) {
        console.log("fail tts", res)
      }
    })
  },
  yuyinPlay (e) {
    if (this.data.src == '') {
      console.log("暂无语音");
      return;
    }
    this.innerAudioContext.src = this.data.src //设置音频地址
    this.innerAudioContext.play(); //播放音频
    this.setData({
      longmusic:true //开始播放
    })
  },
  toFirst(){
    wx.switchTab({
      url: '/pages/first/first',
    })
  },
  pause(){
    var tt;
    if (this.innerAudioContext.src=='') {
      this.wordYun();
      tt=true
    }else{
      tt=true
    if(this.data.longmusic){
      this.innerAudioContext.pause();
      tt=false
    }
    else{
      if (this.innerAudioContext.src!='') {
        this.innerAudioContext.play();     
      }
      
    }}
    //小bug：点击够快有几率报错
    this.setData({
      longmusic:tt //开始播放
    })
  },


  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady() {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow() {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide() {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload() {
    this.innerAudioContext.destroy();
  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh() {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom() {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage() {

  },
  userCheck(e){
    var that=this; 
    var check=e.target.dataset.value;
    if(check.search(/[\.\,]$/g)!=-1)
    {//对结尾含有.与，的单词做处理
      check = check.substring(0, check.length - 1);
    }
    that.test(e,check);

  },
  canceltap(){
    this.setData({
      showDetail:false,
    })
  },
  test(e,check){
    var that=this;
    const query = wx.createSelectorQuery();
    var showDetail=true;
   //小程序BUG不加延时算出来的高度部分机型不准确//不是onLoad（）等官方加载函数就没问题
   //只有在带有view的标签元素才能id选择
    setTimeout(() => { 
      query.select('#'+'location'+String(e.target.dataset.id)).boundingClientRect(res=>{
        if(check.search(/\'/g)!=-1)
        {
          showDetail=false;
        }else{
          var q2=wx.createSelectorQuery();
          q2.select('#mask').boundingClientRect(cc=>{
            that.setData({
              masktop:cc.top,
              maskbottom:cc.bottom
            })
          }).exec();

          wx.request({
            url: 'http://dict.youdao.com/suggest?num=1&doctype=json',
            data:{
              q:check,
            },success(res){
              that.setData({
                wordDetail:res.data.data.entries[0].explain,
                wordword:res.data.data.entries[0].entry
              })
            }
          })
        }
        that.setData({
          postionLeft: (res.right-res.left)/2.0+res.left,
          postiontop: res.bottom,
          wordname:check,
          selectId:e.target.dataset.id,
          showDetail:showDetail,
        })
      }).exec();
     }, 0);

  },
  audioPlay(e){
    var that=this
    const innerAudioContext = wx.createInnerAudioContext()
    innerAudioContext.autoplay = true
    innerAudioContext.src = 'https://fanyi.baidu.com/gettts?lan='+that.data.lanType[0]+'&text='+that.data.wordname+'&spd=3&source=web'
  },
  check(e){
    var content=e.detail.value;
    var size=(e.detail.value).length;
    this.setData({
      content:content,
      tranNowSize:size
    })
  },
  upload(e){
    var that=this;
    if(that.data.content.length!=0){
    wx.setStorageSync('trans', that.data.trans);
    wx.setStorageSync('content', that.data.content);
    wx.request({
      url: 'http://localhost:1234/Trans/setAnswerRecord',
      data:{
        userId:that.data.user_base_data.userId,
        transId:that.data.transId,
        u_trans:that.data.content,
      },
success(res){
  wx.redirectTo({
    url: '/pages/funtions/translatePage/succ/succ',
  })
}
    })
  
  }else{
    wx.showToast({
      title: '未输入翻译！',
      icon: 'error',
      duration: 1000//持续的时间
    })
  }
  }
})