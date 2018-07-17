import request from '@/utils/request'
import qs from 'qs'

export function login(username, password) {
  return request({
    headers: { 'content-type': 'application/x-www-form-urlencoded' },
    url: '/login',
    method: 'post',
    data: qs.stringify({
      username,
      password
    })
  })
}

export function getInfo(token) {
  return request({
    url: '/user/info',
    method: 'post',
    params: { token }
  })
}

export function logout() {
  return request({
    url: '/user/logout',
    method: 'post'
  })
}
