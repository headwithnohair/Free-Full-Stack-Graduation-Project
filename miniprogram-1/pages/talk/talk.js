// pages/talk/talk.js
Page({
  /**
   * 页面的初始数据
   */
  data: {

    tt:false,

  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad(options) {
    var that=this;

    wx.request({
      url: 'http://localhost:1234/post/test',
      success(res){
          console.log(res.data);
          console.log(res.data[0].post.content);
          that.setData({
            postList:res.data,
          })
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
    var that=this;
    wx.request({
      url: 'http://localhost:1234/post/refreshPost',
      data: {
        postId:that.data.postList[0].post.postid,
        number:3
      },success(res){
        if (res.data.length==0) {
          console.log("no more")
        }

        console.log(res.data)
        that.setData({
          postList:res.data.concat(that.data.postList),
        })
      }
    })
    wx.showToast({
      title: '刷新成功',
      icon:"none"
    })
      wx.stopPullDownRefresh()
      this.setData({
        tt:true
      })
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
    wx.navigateTo({
        
        url: event.currentTarget.dataset.url,
      
  
    })
  
  },
  test(e){
    wx.navigateTo({
      url: '/pages/talk/detail_page/dpage',
      success:(res)=>{
        console.log("发送帖子详细事件");
				// 发送一个事件
				res.eventChannel.emit('detail_page',{ detail: e.currentTarget.dataset.url})
			}
    })

  },
  
})