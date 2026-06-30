// pages/first/sreach/sreach.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    hideScroll:false,
    showWord:false,
    lanType:["uk","en"],//uk==英国 ，en美国
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad(options) {

  },

  sreach(e){
    var that=this;
    if (e.detail.value.length>0) {
      wx.request({
        url: 'http://localhost:1234/Word/obscureSreachWord',
        data:{
          word:e.detail.value,
          limit:5
        },success(res)
        {
          console.log(res.data);
          console.log(res.data.length);
          that.setData({
            hideScroll:true,
            bindSource:res.data,
            showWord:true,
          })
        }
      })
    }else{
      that.setData({
        showWord:false,
        hideScroll:false,
      })
    }
   

  },
  confirmsreach(){
console.log("hihihhihihihhihi")

  },
  
  audio(e){
    const innerAudioContext= wx.createInnerAudioContext();
    var  wordSoundUrl='https://fanyi.baidu.com/gettts?lan='+this.data.lanType[0]+'&text='+
    e.currentTarget.dataset.word+'&spd=3&source=web';

    innerAudioContext.autoplay = true;
    innerAudioContext.src = wordSoundUrl  //音频路径
    innerAudioContext.play();
    innerAudioContext.onPlay(() => {
      console.log('开始播放')
    });
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

  }
})