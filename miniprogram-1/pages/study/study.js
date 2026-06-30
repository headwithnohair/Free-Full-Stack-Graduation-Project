// pages/study/study.js
const innerAudioContext = wx.createInnerAudioContext();
Page({

  /**
   * 页面的初始数据
   */
  data: {
    many: 10,//已知要学个数
    word:['one','two','3','4','5','6','7','8','9','10'],
    plan_url : '/pages/study/plan/plan',
    userdata_url: '/pages/first/userstudy_data/us',
    study_url: '/pages/study/study',
    mine_url: '/pages/mine/mine',
    talk_url: '/pages/talk/talk',
    wordDetail_url:'/pages/study/wordDetail/wordDetail',
    test:'',
    this_time_word:'',
    now_Word:'',
    word:'',
    count:{'cite':0,'view':0},
    lanType:["uk","en"],//uk==英国 ，en美国
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad(options) {
//  console.log(null !=wx.getStorageSync('studypage')?'done':'error');
  var that =this;
  var word;
  var count=wx.getStorageSync('count');
  that.data.test =wx.getStorageSync('studypage');
  if(count=="")
  { 
    count={'cite':0,'view':0};
    wx.setStorageSync('count', count);
    
  }
  {
  that.data.now_Word=wx.getStorageSync("new_Word_Data");
  console.log(that.data.now_Word)
  if (that.data.test[2].statius ==1 ) {//背诵模式
    console.log("背诵");
    var temp = {word:that.data.now_Word[0][count.cite].word_name,
      pernance:that.data.now_Word[0][count.cite].word_say,
      statius : that.data.test[2].statius,tttt:count.cite
    };
    word=that.data.now_Word[0][count.cite];
  }else{//复习模式
    console.log("复习")
    console.log(that.data.test[1].tttt)
    var temp = {word:that.data.now_Word[1][count.view].word_name,
      pernance:that.data.now_Word[1][count.view].word_say,
      statius : that.data.test[2].statius,
      tttt:count.view
    };
    word=that.data.now_Word[1][count.view];
  }
  console.log("在这里");
  console.log(word);
   that.setData({
     word:word,
    test :wx.getStorageSync('studypage'),
    now_Word:wx.getStorageSync('new_Word_Data'),
    this_time_word : temp,
    count:count
  }
  ) 
}

  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady() {
    var that=this;
    var  wordSoundUrl='https://fanyi.baidu.com/gettts?lan='+that.data.lanType[0]+'&text='+
    that.data.word.word_name+'&spd=3&source=web';

    innerAudioContext.autoplay = true;
    innerAudioContext.src = wordSoundUrl  //音频路径
    innerAudioContext.onPlay(() => {
      console.log('开始播放')
    });
  },
  back(){
    wx.switchTab({
      url: '/pages/first/first',
    })
  },
  audio(){

    var  wordSoundUrl='https://fanyi.baidu.com/gettts?lan='+this.data.lanType[0]+'&text='+
    this.data.word.word_name+'&spd=3&source=web';

    innerAudioContext.autoplay = true;
    innerAudioContext.src = wordSoundUrl  //音频路径
    innerAudioContext.play();
    innerAudioContext.onPlay(() => {
      console.log('开始播放')
    });

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

  jump_link(event){
    var that =this;
    var userData=wx.getStorageSync('user_base_data');
    wx.setStorageSync('word_Detail', that.data.word);
    console.log(that.data.word);
wx.request({
  url: 'http://localhost:1234/RRC/record',
  data: {
    userId : userData.userId,
    wordId : that.data.word.word_id,
    bookId : userData.bookId,
    rf_judge: event.target.dataset.sta,
    judge:that.data.test[2].statius,
    ///test
  },
  success(res){

  }
})
    wx.redirectTo({
        url: event.target.dataset.url,
    })
  
  }
})