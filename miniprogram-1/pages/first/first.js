// pages/first/first.js
const app = getApp();
Page({

  /**
   * 页面的初始数据
   */
  data: {
    show: false,
    barHeight: 20, //  顶部状态栏高度
    navBarHeight: 30, // 顶部高度
    gridList : ['新词','复习','未学'],
    gg : ['10','0','10'],
    mokuai :['听力','翻译','发帖'],
    urlll:['/pages/funtions/listenPage/lis_page','/pages/funtions/translatePage/trans_page','/pages/funtions/postPage/post_page'],
    navBarHeight : app.globalData.navBarHeight,
    menuRight: app.globalData.menuRight,
    menuBotton : app.globalData.menuRight,
    menuHeight : app.globalData.menuRight,
    plan_url : '/pages/study/plan/plan',
    userdata_url: '/pages/first/userstudy_data/us',
    study_url: '/pages/study/study',
    mine_url: '/pages/mine/mine',
    talk_url: '/pages/talk/talk',
    sreach_url:'/pages/first/sreach/sreach',
    studypage : [
      {data:'study_data' ,many:10,tttt:0},
      {data:'reStuday_data' ,many:10,tttt:0},
      {statius: 1}
    ],
    user_base_data:{mubiao:0,bookName:'六级真题词汇书',deadline_name:'六级',deadline_day:'60',already_get:'250',notyet_get:'1678',percent_word:'60.80',study_max:'10',study_count:'0',res_max:'10',res_count:'0'},//1
    new_Word_Data:"",
    statius:1,//进行中//2为结束
    urrr: '/pages/study/finsh/finsh'
  }, 
  
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad(options) {
    var sta=1;
    var that=this;
    var test='studypage[0].many';
    var test2='studypage[1].many';
    var test4='studypage[0].tttt';
    var test5='studypage[1].tttt';
    var test3='studypage[2].statius';
    if(wx.getStorageSync('statius')==null){
      console.log("状态为空")
      wx.setStorageSync('statius', that.data.statius);
    }
    var count=wx.getStorageSync('count');
    if(count=="")
    { 
      count={'cite':0,'view':0};
      wx.setStorageSync('count', count);
      wx.setStorageSync('nowChange',false);
    }
    let userId ;
    if (wx.getStorageSync('nowChange')==false) {
      
    
    wx.login({
    
      success (res) {
        console.log(res.code);
        request('http://localhost:1234/user/wxcheckLogin', 
        {loginCode: res.code},'GET')
        .then((ee)=>{
              userId =  JSON.parse(JSON.stringify(ee.data));
                wx.setStorageSync('userId', ee.data);
                wx.setStorageSync('hasUserId', true);
                console.log("已在库中找到用户ID")
            wx.request({
              url: 'http://localhost:1234/userplan/getUserBaseData',
              data:{
                userId :userId //
              }, 
                   success(res){
                  
                    if(res.data!=null)
                    {
                      that.setData({
                        user_base_data:res.data, 
                        gg : [(res.data.study_count),
                          res.data.res_count,res.data.study_max-res.data.study_count],
                         [test] : res.data.study_max,//
                         [test2] : res.data.res_max,//
                         [test4]: res.data.study_count,
                         [test5]:res.data.res_count
                      })
                      wx.setStorageSync("user_base_data", res.data);
                      console.log("已获取用户基础信息",that.data.user_base_data);
                     
                    }
                    else{
                      console.log("null!!!!!",res);
                    }
                  },
                  fail(res){
                    console.log("wht",res);
                  }
            })
             return userId;
        }).then((userId)=>{
          wx.request({
          url: 'http://localhost:1234/userplan/getDayWord',
          data: {
              userId: userId
          },
        success(res){
          if(res.data!=null)
        {  
          var recite = new Array();
          var review = new Array();
          var cc =new Array();
          console.log("!!!!",res.data);
          for (const noRecite of res.data.noRecite) {recite.push(noRecite);}
          for (const noReview of res.data.noReview) {review.push(noReview);}
          cc.push(recite);
          cc.push(review);
          console.log(cc);
          if(res.data.noRecite.length==0){
            sta=2
            console.log("已执行")
          }

          that.setData({
            new_Word_Data: cc,
            [test] : res.data.noRecite.length,
            [test2] : res.data.noReview.length,
            [test3] :sta,
          })
          console.log(that.data.studypage,sta)
          wx.setStorageSync("studypage", that.data.studypage);
          wx.setStorageSync("new_Word_Data", that.data.new_Word_Data);
        }
        else{
          console.log("失败，getDayWord为空");
          
        }
        } ,     fail(res){
          console.log("wht",res);
        
          wx.showToast({
            title: '网络问题，请稍后重试',
            icon: 'success',
            duration: 2000//持续的时间
          })
        }
        })});
      }
    })
  }else{
    wx.request({
      url: 'http://localhost:1234/userplan/getUserBaseData',
      data:{
        userId :wx.getStorageSync('userId') //
      }, 
           success(res){
            if(res.data!=null)
            {
              that.setData({
                user_base_data:res.data, 
                gg : [(res.data.study_count),
                  res.data.res_count,res.data.study_max-res.data.study_count],
                 [test] : res.data.study_max,//
                 [test2] : res.data.res_max,//
                 [test4]: res.data.study_count,
                 [test5]:res.data.res_count
              })
              wx.setStorageSync("user_base_data", res.data);
              console.log("已获取用户基础信息",that.data.user_base_data);
             
            }
            else{
              console.log("null!!!!!",res);
            }
            wx.request({
              url: 'http://localhost:1234/userplan/getDayWord',
              data: {
                  userId: wx.getStorageSync('userId') //
              },
            success(res){
              if(res.data!=null)
            {  
              var recite = new Array();
              var review = new Array();
              var cc =new Array();
              console.log("!!!!",res.data);
              for (const noRecite of res.data.noRecite) {recite.push(noRecite);}
              for (const noReview of res.data.noReview) {review.push(noReview);}
              cc.push(recite);
              cc.push(review);
              console.log(cc);
              if(res.data.noRecite.length==0){
                sta=2
                console.log("已执行")
              }
        
              that.setData({
                new_Word_Data: cc,
                [test] : res.data.noRecite.length,
                [test2] : res.data.noReview.length,
                [test3] :sta,
              })
              console.log(that.data.studypage,sta)
              wx.setStorageSync("studypage", that.data.studypage);
              wx.setStorageSync("new_Word_Data", that.data.new_Word_Data);
            }
            else{
              console.log("失败，getDayWord为空");  
            }}})
          }
    })

  }
    var now =getNowFormatDate();
    var eng;
    var chi;
    wx.request({
      url: 'https://dict.youdao.com/infoline?mode=publish&date=' + now + '&update=auto&apiversion=5.0',
      success(res){
        console.log(res.data);
        for(let i in res.data) {
          eng=res.data[i][0].title;
          chi=res.data[i][0].summary;
        break
        }
        that.setData({
          eng:eng,
          chi:chi
        })
      }
    })

  },  
  
  // 页面滚动监听
  onPageScroll(e) {
      if (e.scrollTop > 5) {
          this.setData({
              show: true
          })
      } else {
          this.setData({
              show:  false
          })
      }
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
    if (wx.getStorageSync('nowChange')==true) {
      
      this.onLoad();
      wx.setStorageSync('nowChange', false);
    }

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

  wx.stopPullDownRefresh()
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
  serch_first(){
    
  },
  
  function_1() {
    const that = this;
    // 获取系统信息
    const systemInfo = wx.getSystemInfoSync();
    // 胶囊按钮位置
    const menuButtonInfo = wx.getMenuButtonBoundingClientRect();
    // 导航栏高度
    that.globalData.navBarHeight = systemInfo.statusBarHeight + 44;
    that.globalData.menuRight = systemInfo.screenWidth - menuButtonInfo.right;
    that.globalData.menuBotton = menuButtonInfo.top - systemInfo.statusBarHeight;
    that.globalData.menuHeight = menuButtonInfo.height;
},
jump_link(event){
  wx.navigateTo({
      url: event.target.dataset.url,
  })
},
jump_link2(event){
  
  var that=this;
  var count=wx.getStorageSync('count');
  var userId= wx.getStorageSync('userId');
  wx.request({
    url: 'http://localhost:1234/DBC/statius',
    data:{
      userId: userId
    },
    success(res){
      console.log(that.data.studypage[1].many);
      console.log(res.data);
      console.log(count.view);
      if(count.view>=that.data.studypage[1].many&&res.data=="startWrongList"){
        that.setData({
           staaa:1,
          urrr:'/pages/study/wrongList/wrongList'
        })
      }else if (count.view<that.data.studypage[1].many&&res.data=="startWrongList") {
        that.setData({
          staaa:1,
         urrr:'/pages/study/study'
       })
      }
      else if(res.data=="keep"){
        that.setData({
          staaa:1,
          urrr:'/pages/study/study'
        })
      }else{//complete
        that.setData({
          staaa:2,
          urrr:'/pages/study/finsh/finsh'
        })
      }
      wx.navigateTo({
        url: that.data.urrr
    })
    }
    
  })
 
},
sreach(e){
  


}
})
//获取当前日期函数
function getNowFormatDate() {
  let date = new Date(),
    year = date.getFullYear(), //获取完整的年份(4位)
    month = date.getMonth() + 1, //获取当前月份(0-11,0代表1月)
    strDate = date.getDate() // 获取当前日(1-31)
  if (month < 10) month = `0${month}` // 如果月份是个位数，在前面补0
  if (strDate < 10) strDate = `0${strDate}` // 如果日是个位数，在前面补0
 
  return `${year}-${month}-${strDate}`
}

function request(url, data = {}, method ) {
  return new Promise((resolve, reject) => {
    wx.request({
      url: url,
      data: data,
      method: method,
      success: resolve,
      fail: reject
    },
    );
  });
}