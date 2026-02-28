package com.lz.manage.mapper;

import java.util.List;
import com.lz.manage.model.domain.LikesInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 点赞信息Mapper接口
 * 
 * @author YY
 * @date 2026-02-28
 */
public interface LikesInfoMapper extends BaseMapper<LikesInfo>
{
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
     * 删除点赞信息
     * 
     * @param id 点赞信息主键
     * @return 结果
     */
    public int deleteLikesInfoById(Long id);

    /**
     * 批量删除点赞信息
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteLikesInfoByIds(Long[] ids);
}
