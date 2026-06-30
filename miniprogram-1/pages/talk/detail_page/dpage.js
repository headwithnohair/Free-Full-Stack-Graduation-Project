// pages/talk/detail_page/dpage.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
detail:'',
time:true,
imgList:['http://localhost:1234/post/givePic?src=target/classes/static/images/333.jpg'],
header:'http://localhost:1234/post/givePic?src=',//接口地址
ninePicMode:false,//九图模式
testt:0,
reply:{},
getReply:true,
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad(options) {
    let that =this;
    const eventChannel = this.getOpenerEventChannel();
    var picMode=false;//模式关闭
    var userSystemInfo=wx.getSystemInfoSync();
    console.log(userSystemInfo.windowWidth)
    eventChannel.on('detail_page', data => {
      if (data.detail.post.picture) {
      console.log(data.detail.post.picture.pic.length);
      console.log(data.detail.post.picture.pic);
      if (data.detail.post.picture.pic.length>0) {
        picMode=true;
      }
      for(const link in data.detail.post.picture.pic)
      {
        data.detail.post.picture.pic[link]=this.data.header+data.detail.post.picture.pic[link]; 
      }
    }

    wx.request({
      url: 'http://localhost:1234/post/getReply',
      data:{
        postId:data.detail.post.postid,
        number:5,

      },success(res)
      { var getReply=true;
        var lee=res.data.length;
        if (lee<5) {
          getReply=false;
        }
        that.setData({
          reply:res.data,
          replylenth:res.data.length,
          getReply:getReply
        })
   
      }
    })
    this.setData({
      detail: data.detail,
      time : false,
      ninePicMode:picMode
    })
    });
   //获取用户信息
   //编排文章内容
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady() {
  },
  // 页面滚动监听
  onPageScroll(e) {
       this.setData({
        testt:e.scrollTop

       })
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
    var that=this;
    var getReply=this.data.getReply;
    var replylenth=this.data.replylenth;
    console.log("是否需要更新：",this.data.getReply);
    if (getReply==true) {    
      wx.request({
        url: 'http://localhost:1234/post/pulldownReply',
        data:{
          postId:this.data.detail.post.postid,
          number:5,
          head:this.data.replylenth,
        },success(res){
         var cc= that.data.reply.concat(res.data);
          if (res.data.length<5) {
            getReply=false;

          }
          that.setData({
            getReply:getReply,
            reply:cc,
            replylenth:cc.length
          })
          console.log("更新成功")
        },fail(res){
          wx.showToast({
            title: '网络错误',
            icon:'error'
          })
        }
      })

    }

  },
  replyPost(e){
    var that=this;
    const query = wx.createSelectorQuery();
    setTimeout(() => { 
      query.select('#post').fields({
        properties:['value']
      },res=>{
        var postId=this.data.detail.post.postid;
        var userId=wx.getStorageSync('userId');
        if (res.value!="") {
          wx.request({
            url: 'http://localhost:1234/post/uploadReply',
            data:{
              postId:postId,
              userId:userId,
              reply:res.value,
            },success(){
              wx.showToast({
                title: '回复成功！',
                icon:'none'
              })
              that.onLoad();
            }
          })
        }
        

      }).exec()},0);
  },
  /**
   * 用户点击右上角分享
   */
  onShareAppMessage() {

  },
  pictureDetail(e){
    const test =new Array();
    test.push(e.currentTarget.dataset.url)
    wx.previewImage({           
      showmenu:true,
      urls: test,
      current: e.currentTarget.dataset.url, // 当前显示图片的http链接
    })
  }
})