import request from '@/utils/request'

export default {
  pageQuery(current, limit, data) {
    return request({
      url: `http://localhost:8686/record/pageQuery/${current}/${limit}`,
      method: 'post',
      data: data
    })
  },
  deleteById(id) {
    return request({
      url: `http://localhost:8686/record/deleteById/${id}`,
      method: 'delete'
    })
  },
  save(data) {
    return request({
      url: `http://localhost:8686/record/install`,
      method: 'post',
      data: data
    })
  },

  update(data) {
    return request({
      url: `http://localhost:8686/record/update`,
      method: 'put',
      data: data
    })
  },

  getAll() {
    return request({
      url: `http://localhost:8686/student/getTypeAll`,
      method: 'get'
    })
  }

}
