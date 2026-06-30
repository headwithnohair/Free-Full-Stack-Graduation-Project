// pages/study/wordDetail/wordDetail.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    study_url: '/pages/study/study',
    test:'',
    urrr:'',
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad(options) {
    var word=wx.getStorageSync('word_Detail')
   console.log(null !=word?'done':'error');
   
    var that =this;
    that.setData({
      test : word
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
  var statius=1;
  var wc=wx.getStorageSync('studypage');
  console.log(wc);
  var count=wx.getStorageSync('count');
  console.log(count.cite);
  var that=this;
  if (wc[2].statius ==1&&parseInt(wc[0].many)>count.cite+1 ) {//背诵为1，复习为2
    count.cite++;
    console.log(count.cite);
  }else{//fuxi
    console.log('复习模式，启动！')
    if (wc[2].statius!=2) {
      wc[2].statius=2;
      count.cite++;
    }else{
      count.view++;
    }
  }
  wx.setStorageSync('count', count);
  wx.setStorageSync('studypage', wc);
    wx.request({
      url: 'http://localhost:1234/DBC/statius',
      data:{
        userId: 8//userid
      },
      success(res){
        console.log("QQQQQQQQQ",count.view,wc[1].many)   
        if(count.view>=wc[1].many&&res.data=="startWrongList"){
          that.setData({
            statius:1,
            urrr:'/pages/study/wrongList/wrongList'
          })
        }else if (count.view<wc[1].many&&res.data=="startWrongList") {
          that.setData({
            statius:1,
            urrr:'/pages/study/study'
          })
        }
        else if(res.data=="keep"){
          that.setData({
            statius:1,
            urrr:'/pages/study/study'
          })
        }else{//complete
          that.setData({
            statius:2,
            urrr:'/pages/study/finsh/finsh'
          })
        }


        wx.setStorageSync('statius', statius);
        wx.redirectTo({
          url: that.data.urrr,
      })
      }
    })
   
  }
})