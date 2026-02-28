import request from '@/utils/request'

// 查询评价信息列表
export function listEvaluateInfo(query) {
  return request({
    url: '/manage/evaluateInfo/list',
    method: 'get',
    params: query
  })
}
export function listEvaluateInfoByScenic(query) {
  return request({
    url: '/manage/evaluateInfo/list/scenic',
    method: 'get',
    params: query
  })
}

// 查询评价信息详细
export function getEvaluateInfo(id) {
  return request({
    url: '/manage/evaluateInfo/' + id,
    method: 'get'
  })
}

// 新增评价信息
export function addEvaluateInfo(data) {
  return request({
    url: '/manage/evaluateInfo',
    method: 'post',
    data: data
  })
}

// 修改评价信息
export function updateEvaluateInfo(data) {
  return request({
    url: '/manage/evaluateInfo',
    method: 'put',
    data: data
  })
}

// 删除评价信息
export function delEvaluateInfo(id) {
  return request({
    url: '/manage/evaluateInfo/' + id,
    method: 'delete'
  })
}
