// app.js
App({
  
  onLaunch() {
 
    const version = wx.getSystemInfoSync().version
    const SDKVersion = wx.getSystemInfoSync().SDKVersion
		console.log("微信版本号")
    console.log(version)
    console.log("微信基础库")
    console.log(SDKVersion)
    // 展示本地存储能力
    const logs = wx.getStorageSync('logs') || []
    logs.unshift(Date.now())
    wx.setStorageSync('logs', logs)
  
    // 登录
    wx.login({//////有bug
      
      // success (res) {
      //   console.log("????");
      //   console.log(res.code);
      //   if (res.code) {
      //     //发起网络请求
      //     wx.request({
      //       url: 'http://localhost:1234/user/wxcheckLogin',
      //       data: {
      //         loginCode: res.code,
      //       },
      //     success(ee){
      //       if (ee.data!=-1) {
      //         wx.setStorageSync('userId', ee.data);
      //         wx.setStorageSync('hasUserId', true);
      //         console.log("已在库中找到用户ID")
      //       }else{
      //         wx.setStorageSync('hasUserId', false);
      //         console.log("注意：未在在库中找到用户ID")
      //       }
           
      //     }
      //     })
      //   } else {
      //     console.log('登录失败！' + res.errMsg)
      //   }
      // },fail(res){
      //   console.log("????");
      // }
    })
  },
  globalData: {
    userInfo: null,
    navBarHeight: 20, // 导航栏高度
    menuRight: 0, // 胶囊距右方间距（方保持左、右间距一致）
    menuBotton: 0, // 胶囊距底部间距（保持底部间距一致）
    menuHeight: 0, // 胶囊高度（自定义内容可与胶囊高度保证一致）
    
  }
})