// pages/study/plan/book_chioces/book_c.js
Page({

  /**
   * 页面的初始数据
   */
  data: {

  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad(options) {
    var that=this;
    wx.request({
      url: 'http://localhost:1234/userplan/getBookList',
      data:{
        number:5,
      },success(res){
        console.log(res.data);
        that.setData({
          bookList:res.data

        })
      }
    })
  },
  changeBook(e){
    console.log(e.currentTarget.dataset.bookid);
    var userId=wx.getStorageSync('userId');
  
    if (userId!=undefined) {
      console.log("更换计划书本", userId);
      wx.request({
        url: 'http://localhost:1234/userplan/changeUserBook',
        data:{
          userId:userId,
          bookId:e.currentTarget.dataset.bookid
        }
      })
    }else{
      wx.showToast({
        title: '未登录',
        icon:'error'
      })
    }
  }
  ,
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