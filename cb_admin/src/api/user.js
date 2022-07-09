import request from '@/utils/request'

export function login(data) {
  return request.post(`/admin/user/login`, data)
  // return request({
  //   url: '/admin/user/login',
  //   method: 'post',
  //   data
  // })
}

export function getInfo(token) {
  return request.post(`/admin/user/info`, {})
  // return request({
  //   url: '/admin/user/info',
  //   method: 'post'
  // })
}

export function logout() {
  return request.post(`/admin/user/logout`, {})
  // return request({
  //   url: '/admin/user/logout',
  //   method: 'post'
  // })
}

export function getUserList(params) {
  return request.post(`/admin/user/findPage/${params.current}/${params.size}`, params)
  // return request({
  //   url: `/admin/user/findPage/${current}/${size}`,
  //   method: 'post',
  //   params
  // })
}

