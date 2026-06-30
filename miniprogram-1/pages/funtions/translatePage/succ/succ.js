// pages/funtions/translatePage/succ/succ.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    trans:'',
    content:'',
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad(options) {

  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady() {
    var content=wx.getStorageSync('content');
    var trams=wx.getStorageSync('trans');
    this.setData({
      trans:trams,
      content:content
    })
    wx.removeStorageSync('content');
    wx.removeStorageSync('trans');
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
  next(){
wx.redirectTo({
  url: '/pages/funtions/translatePage/trans_page',
})
  },
  back(){
wx.switchTab({
  url: '/pages/first/first',
})
  }
})