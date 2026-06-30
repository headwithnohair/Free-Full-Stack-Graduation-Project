// pages/funtions/listenPage/lis_page.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    word:{name:"question"},
    lili:[],
    lanType:["uk","en"],//uk==英国 ，en美国
    wordSoundUrl:'',
    showSayDetail:false,
    showTranDetail:false,
    trueValue:'',
    isFocus:false,
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad(options) {
    var that=this;
    var clip;
    var test;
    var user_base_data=wx.getStorageSync('user_base_data');
    var wordSoundUrl;
    console.log(user_base_data.userId);
    wx.request({
      url: 'http://localhost:1234/RRC/oneReviewWord',
      data:{
        userId:user_base_data.userId,
        bookId:user_base_data.bookId
      },
      success(res){
        clip=res.data;
        test=clip.word_name.split("");
        wordSoundUrl='https://fanyi.baidu.com/gettts?lan='+that.data.lanType[0]+'&text='+clip.word_name+'&spd=3&source=web';
        console.log(clip)
        that.setData({
          wordArray: test,
          wordSoundUrl:wordSoundUrl,
          word:clip,
          wordlength:clip.word_name.length,
          user_base_data:user_base_data,
        })

        const innerAudioContext = wx.createInnerAudioContext();
        innerAudioContext.autoplay = true;
        innerAudioContext.src = that.data.wordSoundUrl  //音频路径
        innerAudioContext.onPlay(() => {
          console.log('开始播放')
        });
      }

    })
    


  },
  setValue:function(e){
   // console.log(e.detail.value);
    var that = this;
    that.setData({ trueValue: e.detail.value });
  },
  onFocus:function(e){
    var that = this;
    console.log("isFocus : true" )
    that.setData({isFocus:true});
  },
  bindsubmit(e){//用户确认提交单词
    var word=this.data.wordArray
    var judgeW=1;//0为错误,1正确
    for( const w in word)
    { 
      if (`${word[w]}`!=e.detail.value[w]) {
        judgeW=2
        console.log(e.detail.value)
        break;
      }
    } 
    if(judgeW==1){
      console.log("单词正确！！！！");
      wx.showToast({
        title: '单词正确',
        icon:'success',
        duration: 0.5*1000
      })
    }else{
      console.log("单词错误！！！！");
      wx.showToast({
        title: '单词错误',
        icon:'error',
        duration: 0.5*1000
      })
    }
    wx.request({
      url: 'http://localhost:1234/RRC/listenRecoord',
      data:{
        userId:this.data.user_base_data.userId,
        bookId:this.data.user_base_data.bookId,
        wordId:this.data.word.word_id,
        rf_judge:judgeW,
      },
  
    })
    this.setData({
      showSayDetail:true,
      showTranDetail:true,
      trueValue:word
  })
    setTimeout(()=>
      { wx.redirectTo({
        url: '/pages/funtions/listenPage/lis_page',
      })
     

      }, 2*1000)
   
  },
  audioPlay() {
    var that=this
    const innerAudioContext = wx.createInnerAudioContext()
    innerAudioContext.autoplay = true
    innerAudioContext.src = that.data.wordSoundUrl 
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
  changeShow(){
    var that=this;
    var temp=true;
    if (that.data.showTranDetail) {
      temp=false
    }
    this.setData({
      showTranDetail:temp
    })
  },

})