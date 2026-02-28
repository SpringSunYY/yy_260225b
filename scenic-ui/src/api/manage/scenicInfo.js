import request from '@/utils/request'

// 查询景区信息列表
export function listScenicInfo(query) {
  return request({
    url: '/manage/scenicInfo/list',
    method: 'get',
    params: query
  })
}

// 查询景区信息详细
export function getScenicInfo(id) {
  return request({
    url: '/manage/scenicInfo/' + id,
    method: 'get'
  })
}

// 新增景区信息
export function addScenicInfo(data) {
  return request({
    url: '/manage/scenicInfo',
    method: 'post',
    data: data
  })
}

// 修改景区信息
export function updateScenicInfo(data) {
  return request({
    url: '/manage/scenicInfo',
    method: 'put',
    data: data
  })
}

// 删除景区信息
export function delScenicInfo(id) {
  return request({
    url: '/manage/scenicInfo/' + id,
    method: 'delete'
  })
}
