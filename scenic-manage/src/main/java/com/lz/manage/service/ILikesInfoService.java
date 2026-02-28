package com.lz.manage.service;

import java.util.List;
import com.lz.manage.model.domain.LikesInfo;
import com.lz.manage.model.vo.likesInfo.LikesInfoVo;
import com.lz.manage.model.dto.likesInfo.LikesInfoQuery;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
/**
 * 点赞信息Service接口
 * 
 * @author YY
 * @date 2026-02-28
 */
public interface ILikesInfoService extends IService<LikesInfo>
{
    //region mybatis代码
    /**
     * 查询点赞信息
     * 
     * @param id 点赞信息主键
     * @return 点赞信息
     */
    public LikesInfo selectLikesInfoById(Long id);

    /**
     * 查询点赞信息列表
     * 
     * @param likesInfo 点赞信息
     * @return 点赞信息集合
     */
    public List<LikesInfo> selectLikesInfoList(LikesInfo likesInfo);

    /**
     * 新增点赞信息
     * 
     * @param likesInfo 点赞信息
     * @return 结果
     */
    public int insertLikesInfo(LikesInfo likesInfo);

    /**
     * 修改点赞信息
     * 
     * @param likesInfo 点赞信息
     * @return 结果
     */
    public int updateLikesInfo(LikesInfo likesInfo);

    /**
     * 批量删除点赞信息
     * 
     * @param ids 需要删除的点赞信息主键集合
     * @return 结果
     */
    public int deleteLikesInfoByIds(Long[] ids);

    /**
     * 删除点赞信息信息
     * 
     * @param id 点赞信息主键
     * @return 结果
     */
    public int deleteLikesInfoById(Long id);
    //endregion
    /**
     * 获取查询条件
     *
     * @param likesInfoQuery 查询条件对象
     * @return 查询条件
     */
    QueryWrapper<LikesInfo> getQueryWrapper(LikesInfoQuery likesInfoQuery);

    /**
     * 转换vo
     *
     * @param likesInfoList LikesInfo集合
     * @return LikesInfoVO集合
     */
    List<LikesInfoVo> convertVoList(List<LikesInfo> likesInfoList);
}
