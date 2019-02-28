import request from '@/utils/request'
import qs from 'qs'

export function loginByUsername(username, password) {
  const data = {
    username,
    password
  }

  return request({
    headers: { 'content-type': 'application/x-www-form-urlencoded' },
    url: '/login',
    method: 'post',
    data: qs.stringify(data)
  })
}

export function logout() {
  return request({
    url: '/login/logout',
    method: 'post'
  })
}

export function getUserInfo(token) {
  return request({
    headers: { 'X-Auth-Token': token },
    url: '/user/info',
    method: 'post'
  })
}

