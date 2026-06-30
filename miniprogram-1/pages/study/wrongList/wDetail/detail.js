// pages/study/wrongList/wDetail/detail.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    wrongListUrl: '/pages/study/wrongList/wrongList',
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad(options) {
    var tt=wx.getStorageSync('wrongWord');
    var that=this;
    that.setData({
      test:tt
    })
  },

  jump_link(e){
    wx.navigateTo({
      url: e.target.dataset.url,
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