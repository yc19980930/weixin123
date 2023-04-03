var call = require("../../utils/request")
var help = require("../../utils/help")
var token = '';
Page({

  data: {
    pickerList: [], // 存储所有设备
    name:'', // 申请人
    address:'',// 维修地址
    recordDesc:'', // 维修描述
    recordDesc:'', // 维修描述
    level:'0', // 维修级别
    equipment:'', // 设备名称
  },

  onLoad: function() {
     // 查询所有设备
     this.getEquipmentAll();
     this.getInit();
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
      let floorListto = [];
      for (let i = 0; i < res.data.row.length; i++) {
        floorListto[i] = res.data.row[i].name;
      }
      this.setData({
        pickerList :floorListto
      })
    }
  },
  onFailEquipmentAll() {
    help.show("网络请求失败");
  },
  /* 申报人 */
  setName(e) {
    this.setData({
      name: e.detail.value
    })
  },

  /* 维修地址 */
  setAddress(e) {
    this.setData({
      address: e.detail.value
    })
  },

  /* 选择设备 */
  selectEquipment(e) {
    this.setData({
      equipment: this.data.pickerList[Number(e.detail.value)]
    })
  },

  /* 联系电话 */
  setPhone(e) {
    this.setData({
      phone: e.detail.value
    })
  },

  /* 申报描述 */
  setRecordDesc(e) {
    this.setData({
      recordDesc: e.detail.value
    })
  },

  /* 选择维修级别 */
  selectLevel(e) {
    this.setData({
      level: e.detail
    })
  },

  clickLevel(e) {
    this.setData({
      level: e.currentTarget.dataset.level
    })
  },

  /* 提交申报表 */
  inApplyData() {
    if (this.validate()) {
      wx.showLoading({
        title: '正在提交...',
        mask: true
      })

      call.request('wxRecord/insertRepRecord' , {
          name: this.data.name,
          equipmentId: this.data.equipment,
          address: this.data.address,
          phone: this.data.phone.trim(),
          recordDesc: this.data.recordDesc,
          level: this.data.level,
          studentId: token
      }, this.onSuccessSubmit, this.onFailSubmit);
    }
  },
  onSuccessSubmit(res) {
    if(res.code == 20000){
      // 关闭加载框
      wx.hideLoading();
      help.okShow("提交成功");
      setTimeout(function () {
        // 跳转到首页
        wx.reLaunch({
          url: '../index/index',
        })
      }, 2000)
    }else{
      help.show("提交失败!");
    }
  },

  onFailSubmit() {
    help.show("网络请求失败");
  },

  /* 申报表单验证 */
  validate() {
    let cp = /[1][3,4,5,7,8][0-9]{9}$/;
    if (this.data.name === '' || !this.data.name) {
      wx.showToast({
        title: '请填写申报人',
        icon: 'error',
        duration: 500
      })
      return false;
    }
    if (this.data.equipment === '' || !this.data.equipment) {
      wx.showToast({
        title: '请选择维修设备',
        icon: 'error',
        duration: 500
      })
      return false;
    }
    if (this.data.address === '' || !this.data.address) {
      wx.showToast({
        title: '请填写维修地址',
        icon: 'error',
        duration: 500
      })
      return false;
    }
    if (this.data.phone === '' || !this.data.phone) {
      wx.showToast({
        title: '请填写手机号码',
        icon: 'error',
        duration: 500
      })
      return false;
    }
    if (!cp.test(this.data.phone)) {
      wx.showToast({
        title: '请填写正确手机号',
        icon: 'error',
        duration: 500
      })
      return false;
    }
    if (this.data.recordDesc === '' || !this.data.recordDesc) {
      wx.showToast({
        title: '请说明维修设备情况',
        icon: 'error',
        duration: 500
      })
      return false;
    }
    return true;
  }

})