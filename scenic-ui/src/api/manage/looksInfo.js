import request from '@/utils/request'

// 查询浏览信息列表
export function listLooksInfo(query) {
  return request({
    url: '/manage/looksInfo/list',
    method: 'get',
    params: query
  })
}

// 查询浏览信息详细
export function getLooksInfo(id) {
  return request({
    url: '/manage/looksInfo/' + id,
    method: 'get'
  })
}

// 新增浏览信息
export function addLooksInfo(data) {
  return request({
    url: '/manage/looksInfo',
    method: 'post',
    data: data
  })
}

// 修改浏览信息
export function updateLooksInfo(data) {
  return request({
    url: '/manage/looksInfo',
    method: 'put',
    data: data
  })
}

// 删除浏览信息
export function delLooksInfo(id) {
  return request({
    url: '/manage/looksInfo/' + id,
    method: 'delete'
  })
}
