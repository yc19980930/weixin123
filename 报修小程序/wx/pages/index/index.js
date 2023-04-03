
var call = require("../../utils/request")
var help = require("../../utils/help")
var token = '';
Page({

  data: {
    tabList: [{
      name: '当前未处理',
      status: '0'
    }, {
      name: '当前已处理',
      status: '1'
    }],
    floorList: [], // 存储所有设备信息
    applyData: [], // 申请记录
    status:'0',// 处理状态
    equipment:null,// 设备名称
  },


  /**
   * 最先执行
   */
  onLoad: function () {
    // 获取登录状态
    this.getInit();
    // 查询申请数据
    this.getApplyData();

    // 查询所有设备
    this.getEquipmentAll();
  },

  /**
   * 获取登录用户信息
   */
  getInit(){
    token = wx.getStorageSync("token")
     // 用户信息不存在跳转登录页面
     if(token == null || token == undefined || token == ''){
      // 跳转到登录页面
      wx.reLaunch({
        url: '/pages/login/index'
     })
  }
  },
  /**
   * 获取所有设备
   */
  getEquipmentAll(){
    call.getData('wxRecord/getEquipmentAll' , this.onSuccessEquipmentAll, this.onFailEquipmentAll);
  },
  onSuccessEquipmentAll(res) {
    if(res.code == 20000){
      let  floorListto = ['全部'];
      for (let i = 0; i < res.data.row.length; i++) {
        floorListto[i+1] = res.data.row[i].name;
      }
      this.setData({
        floorList :floorListto
      })
    }
  },
  onFailEquipmentAll() {
    help.show("网络请求失败");
  },

  /* 选择处理状态 */
  selectStatus(e) {
    this.setData({
      status : e.detail.status
    })
    this.getApplyData();
  },

  /* 选择栋数 */
  selectFloor(e) {
    // 查询所有
    if (e.detail.value === '全部') {
      this.setData({
        applyData: []
      })
      this.setData({
        equipment :null
      })
     
    } else {
      this.setData({
        equipment :e.detail.value
      })
    }
    this.getApplyData();
  },

  /* 获取申报数据 */
  async getApplyData() {
    call.request(`wxRecord/getByStudentIdRecord`,{
      studentId: token,
      status: this.data.status,
      equipmentName:  this.data.equipment
    } , this.onSuccessInit, this.onFailEquipmentAll);
  },
  onSuccessInit(res) {
    console.log(res)
    if(res.code == 20000){
      this.setData({
        applyData: res.data.row
      })
    }
  },

  /* 跳转申报页 */
  toPublish() {
    wx.navigateTo({
      url: '../publish/publish'
    })
  },
  /* 退出登录 */
  toReset(){
    wx.removeStorageSync('token')
    help.show("退出登录成功");
    setTimeout(function () {
      // 跳转到登录页面
      wx.reLaunch({
        url: '/pages/login/index'
    })
    }, 2000)
   
  },

  /* 宿舍管理员 */
  toAdmin() {
      wx.navigateTo({
        url: '../admin/admin'
      })
  },

  /* 查看申报表详情 */
  navDetail(e) {
    wx.navigateTo({
      url: '../detail/detail?id=' + e.currentTarget.dataset.id
    })

  }

})