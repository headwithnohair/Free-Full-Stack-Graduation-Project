// pages/study/wrongList/wrongList.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    wordDetail_url:'/pages/study/wrongList/wDetail/detail',
    lanType:["uk","en"],//uk==英国 ，en美国
    this_time_word:'',
    wordSoundUrl:'',
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad(options) {
    var that=this;
    wx.request({
      url: 'http://localhost:1234/DBC/wrongList',
      data :{
        userId:wx.getStorageSync('userId'),

      },
      success(res){
        if (res.data!=null) {
          var word=res.data;
          var wordSoundUrl='https://fanyi.baidu.com/gettts?lan='+that.data.lanType[0]+'&text='+
          word.word_name+'&spd=3&source=web';
          console.log(word);
          wx.setStorageSync('wrongWord', word);
          const innerAudioContext = wx.createInnerAudioContext();
          innerAudioContext.autoplay = true;
          innerAudioContext.src = wordSoundUrl  //音频路径
          innerAudioContext.onPlay(() => {
            console.log('开始播放')
          });
          that.setData({
            word_id:word.word_id,
            this_time_word: word,
            wordSoundUrl:wordSoundUrl
          })
        }else{
          wx.redirectTo({
            url:'/pages/study/finsh/finsh'
        })

        }
       
        
      }
      
    })
  },

  jump_link(event){
    var that=this;
    var user_base_data=wx.getStorageSync('user_base_data');
    console.log(this.data.word_id);
    wx.redirectTo({
      url: event.target.dataset.url,
  })
  console.log(user_base_data)
  wx.request({
    url: 'http://localhost:1234/RRC/record',
    data: {
      userId : user_base_data.userId,
      wordId : that.data.word_id,
      bookId : user_base_data.bookId,
      rf_judge: event.target.dataset.sta,
      judge: 2,
      ///test
    }, 
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
audio(){
  var that=this;
 const innerAudioContext = wx.createInnerAudioContext();
      innerAudioContext.autoplay = true;
      innerAudioContext.src =that.data. wordSoundUrl  //音频路径
      innerAudioContext.onPlay(() => {
        console.log('开始播放')
      });

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

  }
})