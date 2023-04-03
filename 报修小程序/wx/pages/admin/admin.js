// pages/guanlijintie/guanlijintie.js
var call = require("../../utils/request.js")
var help = require("../../utils/help.js")
Page({

    /**
     * 页面的初始数据
     */
    data: {

        // 存放数据
        list:[]

    },

    /**
   * 最先执行
   */
  onLoad:function(){
    this.initData();
  },

  
  //1: 加载数据
  initData: function () {
    wx.showLoading({
      title: '数据加载中...'
    });
    call.getData('wxRecord/getAdmin', this.onSuccess, this.onFail);
  },
  onSuccess(res) {
    this.setData({
      list: res.data.data
    })
    wx.hideLoading();
  },
  onFail() {
    wx.hideLoading();
    help.show("网络请求超时,请稍后再试")
  },
})
