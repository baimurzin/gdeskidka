import request from '@/utils/request'
// import qs from 'qs'

export function createShop(shopInfo) {
  return request({
    url: '/shop',
    method: 'post',
    data: shopInfo
  })
}

export function getShops() {
  return request({
    url: '/shop',
    method: 'get'
  })
}
