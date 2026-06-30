//pages/study/plan/plan.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    user_base_data:{mubiao:1,bookName:'四级真题词汇书',deadline_name:'四级',deadline_day:'60',already_get:'998',notyet_get:'999',percent_word:'99.9',study_max:'50',study_count:'0',res_max:'50',res_count:'0'},     
    showIndex:null,
    wordListUrl:'/pages/first/userstudy_data/us',
    plan:{recite:[10,20,30,50,70,100,150,200],review:[10,20,30,50,70,100,150,200],test_day:'1900-06-12'},
    userChange:false,
    userPlan:'',
    stauis:{"res_stu":[0,0],"lantype":'',"date":'',"userScore":"","autoSay":"","wordSize":"normal"},
//背诵计划有无更改
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad(options) {
    var that =this;
    var temp=wx.getStorageSync('user_base_data');
    var userPlanstauis=that.data.stauis;
    wx.request({
      url: 'http://localhost:1234/userplan/getUserPlan',
      data:{
        userId:temp.userId,

      },success(res){
        console.log("12312312312",res.data.deadline.length)
        var userPlan=res.data;
        console.log(userPlan);
        userPlanstauis.res_stu[0]=userPlan.plan_cite;
        userPlanstauis.res_stu[1]=userPlan.plan_re;
        userPlanstauis.lantype=userPlan.preferences.lantype;
        userPlanstauis.date=userPlan.deadline.substr(0,10);
        userPlanstauis.userScore=userPlan.userscore;
        userPlanstauis.autoSay=userPlan.preferences.autoSay;
        userPlanstauis.wordSize=userPlan.preferences.wordSize;
        //stauis:{"res_stu":[0,0],"lantype":'',"date":'',"userScore":"","autoSay":""},
        
        that.setData({
          userPlan:userPlan,
          date:userPlan.deadline,
          stauis :userPlanstauis,
        })
      }
    })
    
    that.setData({
      user_base_data: temp
  })
    console.log(this.data.user_base_data);
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady() {

  },
  toFirst(){
    wx.switchTab({
      url: '/pages/first/first',
    })
  },
  /**
   * 生命周期函数--监听页面显示
   */
  onShow() {

  },
  toBookChoice(){
    wx.navigateTo({
      url:'/pages/study/plan/book_chioces/book_c'
    })

  },
  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide() {

  },
  submit(){
    var userPlan=this.data.userPlan;
    console.log(userPlan);
    var userPlanstauis=this.data.stauis;
    if (this.data.userChange==true) {
      userPlan.plan_cite=userPlanstauis.res_stu[0];
      userPlan.plan_re=userPlanstauis.res_stu[1];
      userPlan.preferences.lantype=userPlanstauis.lantype;
      userPlan.deadline=userPlanstauis.date;
      userPlan.userscore=userPlanstauis.userScore;
      userPlan.preferences.autoSay=userPlanstauis.autoSay;
      userPlan.preferences.wordSize=userPlanstauis.wordSize;
      wx.request({
        url: 'http://localhost:1234/userplan/setUserPlan',
        contentType: "application/json;charset=UTF-8",
        data:{
          userPlan:userPlan,
        }
      })
    }
    console.log(userPlan);
  },
  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload() {
    console.log("test");
  },
  jump_link(event){
    wx.navigateTo({
        url: event.target.dataset.url,
    })
  },
  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh() {

  },
  bindDateChange: function(e) {
    var temp=this.data.stauis
    temp.date=e.detail.value
    console.log('picker发送选择改变，携带值为', e.detail.value)
    this.setData({
      stauis:temp,
      userChange:true,
    })
  },
changeScore(e){
  var temp=this.data.stauis;
  console.log(e.detail.value);
  temp.userScore=e.detail.value
  this.setData({
    stauis:temp,
    userChange:true,
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
radioChange_auturead(e) {
    var that=this;
    var temp=that.data.stauis;
    console.log('自动读音修改：', e.currentTarget.dataset.autosay=='true'?'auto on ':'auto off')
    if (e.currentTarget.dataset.autosay=='true') {
      temp.autoSay='false'
    }else{
      temp.autoSay='true' 
    }
    that.setData({
      stauis:temp,
      userChange:true
    })
  },
  radioChange(e) {
    var that=this;
    var temp=that.data.stauis;
    console.log('计划优先：', e.currentTarget.dataset.value=='r2'?'eng ':'amr')
    if (e.currentTarget.dataset.value=='r2') {
      temp.lantype='uk'
    }else{
      temp.lantype='en'
    }
    that.setData({
      stauis:temp,
      userChange:true
    })
  
    
  },
  radioChange_size(e){
    var that=this;
    var temp=that.data.stauis;
    console.log('字体大小修改：', e.currentTarget.dataset.wordsize=='normal'?'normal':'big')
    if (e.currentTarget.dataset.wordsize=='normal') {
      temp.wordSize='big'
    }else{
      temp.wordSize='normal'
    }
    that.setData({
      stauis:temp,
      userChange:true
    })
  
  },
  openPopup(e){
    var index = e.currentTarget.dataset.index;
    this.setData({
      showIndex:index
    })
  },
  //关闭弹窗
  closePopup(){
    this.setData({
      showIndex:null
    })
  },
  bindChange: function(e) {
    var tempttt=this.data.stauis
    console.log('picker发送选择改变，携带值为', e.detail.value)
    var recite_idx = this.data.plan.recite[e.detail.value[0]] ;
    var review_idx = this.data.plan.review[e.detail.value[1]];
    let temp = 'user_base_data.study_max';
    let temp2 = 'user_base_data.res_max';
    tempttt.res_stu[0]=this.data.plan.recite[e.detail.value[0]]
    tempttt.res_stu[1]=this.data.plan.review[e.detail.value[1]]
    this.setData({
      [temp]: recite_idx,
      [temp2]: review_idx,
      userChange:true,
      stauis:tempttt
    })
    console.log(this.data.user_base_data);
  },
})