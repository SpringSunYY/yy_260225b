import request from '@/utils/request'

// 查询点赞信息列表
export function listLikesInfo(query) {
  return request({
    url: '/manage/likesInfo/list',
    method: 'get',
    params: query
  })
}

// 查询点赞信息详细
export function getLikesInfo(id) {
  return request({
    url: '/manage/likesInfo/' + id,
    method: 'get'
  })
}

// 新增点赞信息
export function addLikesInfo(data) {
  return request({
    url: '/manage/likesInfo',
    method: 'post',
    data: data
  })
}

// 修改点赞信息
export function updateLikesInfo(data) {
  return request({
    url: '/manage/likesInfo',
    method: 'put',
    data: data
  })
}

// 删除点赞信息
export function delLikesInfo(id) {
  return request({
    url: '/manage/likesInfo/' + id,
    method: 'delete'
  })
}
