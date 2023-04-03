import request from '@/utils/request'

export default {
  pageQuery(current, limit, data) {
    return request({
      url: `http://localhost:8686/admin/pageQuery/${current}/${limit}`,
      method: 'post',
      data: data
    })
  },
  deleteById(id) {
    return request({
      url: `http://localhost:8686/admin/deleteById/${id}`,
      method: 'delete'
    })
  },
  save(data) {
    return request({
      url: `http://localhost:8686/admin/install`,
      method: 'post',
      data: data
    })
  },

  update(data) {
    return request({
      url: `http://localhost:8686/admin/update`,
      method: 'put',
      data: data
    })
  },

  getEquipmentIdByAdminId(id) {
    return request({
      url: `http://localhost:8686/equipmentAdmin/getByAdminId/${id}`,
      method: 'get'
    })
  },

  /**
   * 更新管理员关联设备
   * @param id
   */
  updateAdminEquipment(id, data) {
    return request({
      url: `http://localhost:8686/equipmentAdmin/updateAdminEquipment/${id}`,
      method: 'put',
      data: data
    })
  }

}
