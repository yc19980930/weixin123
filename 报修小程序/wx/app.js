App({
  onLaunch: function () {
    if (!wx.cloud) {
      console.error('请使用 2.2.3 或以上的基础库以使用云能力')
    } else {
      wx.cloud.init({
        env: 'dorm-8svqc',
        traceUser: true
      })
    }
  },
  globalData: {
    baseURL: "http://127.0.0.1:8686/"
  }
})
