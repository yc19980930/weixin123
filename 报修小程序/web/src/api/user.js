import request from '@/utils/request'

export function login(data) {
  return request({
    url: 'http://localhost:8686/login',
    method: 'post',
    data
  })
}

// export function login(data) {
//   return request({
//     url: '/vue-admin-template/user/login',
//     method: 'post',
//     data
//   })
// }

export function getInfo(token) {
  return request({
    url: '/vue-admin-template/user/info',
    method: 'get',
    params: { token }
  })
}

export function logout() {
  return request({
    url: '/vue-admin-template/user/logout',
    method: 'post'
  })
}

export function pageQuery(current, limit, data) {
  return request({
    url: `http://localhost:5880/user/pageQuery/${current}/${limit}`,
    method: 'post',
    data: data
  })
}
export function deleteById(id) {
  return request({
    url: `http://localhost:5880/user/delete/${id}`,
    method: 'delete'
  })
}
export function saveTeacher(data){
  return request({
    url: `http://localhost:5880/user/saveAndUpdate`,
    method: 'post',
    data: data
  })
}
