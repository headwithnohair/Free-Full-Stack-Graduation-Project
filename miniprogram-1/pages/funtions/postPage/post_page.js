// pages/funtions/postPage/post_page.js
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
  choose(e){
    var that=this;
    wx.chooseMedia({
      mediaType:['image'],
      sourceType:'album',
      count:9,
      success(res){
        console.log(res.tempFiles[0].tempFilePath)
        console.log(res.tempFiles[0].size)
        console.log(res.tempFiles.length);
        that.setData({
          img:res.tempFiles
        })
      }
    }
    )
  },
  back(){
    wx.switchTab({
      url: '/pages/talk/talk',
    })
  },
post(e){
  var tt;
  var that=this;
  const query = wx.createSelectorQuery();
    query.select('#tit').fields({properties:['value']})
    query.select('#cc').fields({properties:['value']})
    query.exec(res=>{
      if (res[0].value.length==0||res[1].value.length==0) {
        wx.showToast({
          title: '请输入文字！',
          icon:"none"
        })
      }else{
        wx.request({
          url: 'http://localhost:1234/post/postReciver',
          data:{
            userId:wx.getStorageSync('userId'),
            title:res[0].value,
            content:res[1].value,
          },success(post){
            wx.showToast({
              title: '发布成功！',
              icon:"none"
            })
            console.log(post.data)
            console.log(that.data.img!=undefined)
            if (that.data.img!=undefined&&that.data.img.length>0) {
              var index=0;
              that.data.img.forEach((v,i)=>{
                console.log(v.tempFilePath)
                wx.uploadFile({
                  filePath:v.tempFilePath ,
                  name: 'multipartFile',
                  url: 'http://localhost:1234/post/postPicReciver',
                  formData:{
                    postId:post.data,
                    index:i
                  },success(res){
                    
                      // wx.switchTab({
                      //   url: '/pages',
                      // })
                  },fail(res){
                  console.log(res)}
                })             
              })
            }else{
              console.log("用户没有上传图片")
            }
          }
        })
      }
    })

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