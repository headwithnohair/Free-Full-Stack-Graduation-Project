// pages/study/finsh/finsh.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    first_url:'/pages/first/first'
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad(options) {
    wx.request({
      url: 'http://localhost:1234/DBC/test',
      data:{
        userId: 1
      },
      success(res){
        if (res.data=="startWrongList") {
          wx.navigateTo({
            url: '/pages/study/wrongList/wrongList',
          })
        }
     
        }
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

  },

jump_link(event){
    wx.switchTab({
      url: event.target.dataset.url,
    })
  
  }
})