package com.lz.manage.service.impl;

import java.util.*;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;
import javax.validation.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.lz.common.utils.StringUtils;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lz.common.utils.DateUtils;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lz.manage.mapper.LikesInfoMapper;
import com.lz.manage.model.domain.LikesInfo;
import com.lz.manage.service.ILikesInfoService;
import com.lz.manage.model.dto.likesInfo.LikesInfoQuery;
import com.lz.manage.model.vo.likesInfo.LikesInfoVo;

/**
 * 点赞信息Service业务层处理
 *
 * @author YY
 * @date 2026-02-28
 */
@Service
public class LikesInfoServiceImpl extends ServiceImpl<LikesInfoMapper, LikesInfo> implements ILikesInfoService
{

    @Resource
    private LikesInfoMapper likesInfoMapper;

    //region mybatis代码
    /**
     * 查询点赞信息
     *
     * @param id 点赞信息主键
     * @return 点赞信息
     */
    @Override
    public LikesInfo selectLikesInfoById(Long id)
    {
        return likesInfoMapper.selectLikesInfoById(id);
    }

    /**
     * 查询点赞信息列表
     *
     * @param likesInfo 点赞信息
     * @return 点赞信息
     */
    @Override
    public List<LikesInfo> selectLikesInfoList(LikesInfo likesInfo)
    {
        return likesInfoMapper.selectLikesInfoList(likesInfo);
    }

    /**
     * 新增点赞信息
     *
     * @param likesInfo 点赞信息
     * @return 结果
     */
    @Override
    public int insertLikesInfo(LikesInfo likesInfo)
    {
        likesInfo.setCreateTime(DateUtils.getNowDate());
        return likesInfoMapper.insertLikesInfo(likesInfo);
    }

    /**
     * 修改点赞信息
     *
     * @param likesInfo 点赞信息
     * @return 结果
     */
    @Override
    public int updateLikesInfo(LikesInfo likesInfo)
    {
        likesInfo.setUpdateTime(DateUtils.getNowDate());
        return likesInfoMapper.updateLikesInfo(likesInfo);
    }

    /**
     * 批量删除点赞信息
     *
     * @param ids 需要删除的点赞信息主键
     * @return 结果
     */
    @Override
    public int deleteLikesInfoByIds(Long[] ids)
    {
        return likesInfoMapper.deleteLikesInfoByIds(ids);
    }

    /**
     * 删除点赞信息信息
     *
     * @param id 点赞信息主键
     * @return 结果
     */
    @Override
    public int deleteLikesInfoById(Long id)
    {
        return likesInfoMapper.deleteLikesInfoById(id);
    }
    //endregion
    @Override
    public QueryWrapper<LikesInfo> getQueryWrapper(LikesInfoQuery likesInfoQuery){
        QueryWrapper<LikesInfo> queryWrapper = new QueryWrapper<>();
        //如果不使用params可以删除
        Map<String, Object> params = likesInfoQuery.getParams();
        if (StringUtils.isNull(params)) {
            params = new HashMap<>();
        }
        Long id = likesInfoQuery.getId();
        queryWrapper.eq( StringUtils.isNotNull(id),"id",id);

        Long scenicId = likesInfoQuery.getScenicId();
        queryWrapper.eq( StringUtils.isNotNull(scenicId),"scenic_id",scenicId);

        Date createTime = likesInfoQuery.getCreateTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginCreateTime"))&&StringUtils.isNotNull(params.get("endCreateTime")),"create_time",params.get("beginCreateTime"),params.get("endCreateTime"));

        return queryWrapper;
    }

    @Override
    public List<LikesInfoVo> convertVoList(List<LikesInfo> likesInfoList) {
        if (StringUtils.isEmpty(likesInfoList)) {
            return Collections.emptyList();
        }
        return likesInfoList.stream().map(LikesInfoVo::objToVo).collect(Collectors.toList());
    }

}
