import request from '@/utils/request'

function doGet(params) {
  return request({
    url: params.url,
    method: 'get',
    params: params.data
  })
}

function doPost(params) {
  return request({
    url: params.url,
    method: 'post',
    data: params.data
  })
}

function doPut(params) {
  return request({
    url: params.url,
    method: 'put',
    data: params.data
  })
}

function doDelete(params) {
  return request({
    url: params.url,
    method: 'delete',
    params: params.data
  })
}

export default {
  post: doPost,
  get: doGet
}
