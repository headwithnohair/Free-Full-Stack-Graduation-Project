const defaultAvatarUrl = 'https://mmbiz.qpic.cn/mmbiz/icTdbqWNOwNRna42FI242Lcia07jQodd2FJGIYQfG0LAJGFxM4FbnQP6yfMxBgJ0F3YRqJCJ1aPAK2dQagdusBZg/0'

Page({
  /**
   * 页面的初始数据
   */
  data: {userInfo:'',hasUserInfo:'',
    nickname:'',
    showIndex:null,
    avatarUrl: defaultAvatarUrl,
    showModalStatus: false,
      show: false,
      barHeight: 20, //  顶部状态栏高度
      navBarHeight: 66, // 顶部高度
      height:0,
      tabList: [{
              id: '1',
              src: '/img/wenhao.jpg',
              title: '设置'
          },
          {
              id: '2',
              src: '/img/wenhao.jpg',
              title: '听力'
          },
          {
              id: '3',
              src: '/img/wenhao.jpg',
              title: '翻译'
          },
          {
              id: '4',
              src: '/img/wenhao.jpg',
              title: '发帖'
          }
      ],
      hasUserInfo: false,
      userInfo :'',
      noWechat:false,
      wxopenid:false
  },
  
  onLoad(){
    var that=this;

    console.log(wx.getStorageSync('hasUserId'));
    console.log(wx.getStorageSync('userId'));
    if (wx.getStorageSync('noChange')==undefined) {
      wx.setStorageSync('noChange', false);
    }
  //  if (wx.getStorageSync('noChange')==false){}
    wx.login({
      success (res) {
        console.log(res.code);
        if (res.code) {}}})
        console.log(wx.getStorageSync('userId'));  
  if(wx.getStorageSync('hasUserId'))
  {
    wx.request({
      url: 'http://localhost:1234/user/wxGetUserInfo',
      data:{
        userId:wx.getStorageSync('userId')
      },
      success(res){
        var signtime=res.data.signtime.substr(0,10);
        var wxopenid=false
        console.log(res.data);
        console.log(res.data.userid);
        if (res.data.wx_openid!=undefined) {
          wxopenid=true
        }
        that.setData({//设置用户登录数据
          username:res.data.nickname,
          avatarUrl:"http://localhost:1234/user/gaveIcon?userId="+res.data.userid+"",
          user:res.data,
          hasUserInfo:true,
          signtime:signtime,
          wxopenid:wxopenid,
          postCount:res.data.postcount
        })
      }
    })
  }


  },
  formsubmit(e){
    var that=this;
    var nickname='user.nickname';
    var usericon='user.usericon';
    var tempFilePaths=this.data.avatarUrl;
    if(e.detail.value.nickname==''){
      wx.showToast({
        title: '用户名不能为空',
        icon: 'none'
    })
    }
    else{
      this.setData({
          username:e.detail.value.nickname,
          showIndex:null,
          hasUserInfo: true,
         [nickname] : e.detail.value.nickname,
         [usericon]: that.data.avatarUrl
      })
      
      wx.showToast({
        title: '设置成功！',
        icon: 'none'
    })
    console.log(JSON.stringify(this.data.user));
    wx.request({
      url: 'http://localhost:1234/user/wxChangeUserInfo',
      header: {
        'content-type': 'application/json;charset=utf-8'},
      data:JSON.stringify(this.data.user) 
    ,
      method: 'POST',
      success(){
  
        wx.uploadFile({
          url: 'http://localhost:1234/user/getUserIcon', 
          filePath: tempFilePaths,
          name: 'multipartFile',
          formData:{
            userId:that.data.user.userid
          },
          success (res){
            const data = res.data
            //do something
          }
        })
      }
    })
    
    }
    
  },
  bindchooseavatar(e) {
    const  { avatarUrl }  = e.detail ;
    console.log(e.detail);
    this.setData({
      avatarUrl,
      hasUserInfo: true,
    })
    wx.login({
      success (res) {
        console.log(res.code);
        if (res.code) {}}})
    console.log(this.data.avatarUrl);
  },


  // 通知
  shareClick() {
      wx.navigateTo({
          url: '/pages/mine/notice/notice',
      })
  },
  // 二级菜单监听
  tabClick(e) {
      var info = e.currentTarget.dataset.item;
      wx.showToast({
          title: info.title,
          icon: 'none'
      })
      switch (info.id) {
          case '1':
              wx.navigateTo({
                  url: '/pages/study/plan/plan',
              })
              break;
          case '2':
              wx.navigateTo({
                  url: '/pages/funtions/listenPage/lis_page',
              })
              break;
          case '3':
              wx.navigateTo({
                  url: '/pages/funtions/translatePage/trans_page',
              })
              break;
          default:
              wx.navigateTo({
                  url: '/pages/funtions/postPage/post_page',
              })
              break;
      }
  },

  // 匿名反馈
  feedbackClick() {

      wx.navigateTo({
          url: '/pages/mine/tousu/tousu',
      })
  },
  // 关于我们
  aboutClick() {

      wx.navigateTo({
          url: '/pages/mine/aboutUs/aboutUs',
      })
  },
  // 页面滚动监听
  onPageScroll(e) {
      if (e.scrollTop > 60) {
          this.setData({
              show: true
          })
      } else {
          this.setData({
              show: false
          })
      }
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady() {
      var that = this;
      // 胶囊信息
      var menu = wx.getMenuButtonBoundingClientRect();
      var that = this;
      // 动态获取屏幕高度
      
      wx.getSystemInfo({
          success(res) {
              that.setData({
                  barHeight: res.statusBarHeight,
                  navBarHeight: menu.top + menu.height,
                  height: res.windowHeight
              })
          }
      })
  },
  onShow() {
  
  },
  openPopup(e){//微信快捷登录
    var index = e.currentTarget.dataset.index;
    const SDKVersion = wx.getSystemInfoSync().SDKVersion
    let that=this;
    console.log(SDKVersion );
    if(that.compareVersion(SDKVersion, '2.21.0') <= 0){//不支持 昵称头像填写能力
    wx.getUserProfile({//
      desc: '用户登录',//获取用户的信息
      success:(res)=> {//用户成功授权
      console.log("成功",res)
      wx.showToast({
        title: '成功登录',
        icon: 'success',
        duration: 2000//持续的时间
      })
      this.setData({
        userInfo: res.userInfo,
        hasUserInfo: true,
        nickName:res.userInfo.nickName, //获取昵称
        touxian:res.userInfo.avatarUrl,  //获取头像url
          })
      },
     fail:res=>{
      wx.showModal({
        title: '提示',
        content: '当前微信版本过低，部分功能将会无法正常使用，请升级到最新微信版本后重试。'
      })
       console.log("失败",res)
     }
  })
    }else{
      console.log("????")
      this.setData({
        showIndex:index,
      })

    }
 
  },
  tappp(e){
  },
  changeWayLogin(e){
    var te=true;
    if (this.data.noWechat==true) {
      te=false;
    }
  this.setData({
    noWechat:te
  })

  },
  userlogin(e){
    var that=this;
    const query = wx.createSelectorQuery();
    setTimeout(() => { 
      query.select('#name').fields({properties:['value']})
      query.select('#password').fields({properties:['value']})
      query.exec(result=>{
        console.log("账号密码为：   ",result)
        if (result[0].value.length==0||result[1].value.length==0) {
          wx.showToast({
            title: '请输入账号或密码！',
            icon:"none"
          })
        }else{
          wx.request({
            url: 'http://localhost:1234/user/login',
            data:{
              username:result[0].value,
              password:result[1].value
            },success(res){
              if (res.data!=-1) {
                wx.showToast({
                  title: '登录成功',
                  icon:"none"
                })
                wx.setStorageSync('nowChange', true);
                wx.setStorageSync('userId', res.data);
                console.log(res.data);
                
                that.onLoad();
              }
            }
          })
        }

      })},0)
    
  },


  //关闭弹窗
  closePopup(){
    this.setData({
      showIndex:null
    })
  },
 compareVersion(v1, v2) {
  v1 = v1.split('.')
  v2 = v2.split('.')
  var len = Math.max(v1.length, v2.length)
  while (v1.length < len) {
    v1.push('0')
  }
  while (v2.length < len) {
    v2.push('0')
  }
  for (var i = 0; i < len; i++) {
    var num1 = parseInt(v1[i])
    var num2 = parseInt(v2[i])
    if (num1 > num2) {
      return 1
    }else if (num1 < num2) {
      return -1
    }
  }
  return 0
 }
})
