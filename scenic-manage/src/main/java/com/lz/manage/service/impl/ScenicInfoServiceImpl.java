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
import com.lz.manage.mapper.ScenicInfoMapper;
import com.lz.manage.model.domain.ScenicInfo;
import com.lz.manage.service.IScenicInfoService;
import com.lz.manage.model.dto.scenicInfo.ScenicInfoQuery;
import com.lz.manage.model.vo.scenicInfo.ScenicInfoVo;

/**
 * 景区信息Service业务层处理
 *
 * @author YY
 * @date 2026-02-28
 */
@Service
public class ScenicInfoServiceImpl extends ServiceImpl<ScenicInfoMapper, ScenicInfo> implements IScenicInfoService
{

    @Resource
    private ScenicInfoMapper scenicInfoMapper;

    //region mybatis代码
    /**
     * 查询景区信息
     *
     * @param id 景区信息主键
     * @return 景区信息
     */
    @Override
    public ScenicInfo selectScenicInfoById(Long id)
    {
        return scenicInfoMapper.selectScenicInfoById(id);
    }

    /**
     * 查询景区信息列表
     *
     * @param scenicInfo 景区信息
     * @return 景区信息
     */
    @Override
    public List<ScenicInfo> selectScenicInfoList(ScenicInfo scenicInfo)
    {
        return scenicInfoMapper.selectScenicInfoList(scenicInfo);
    }

    /**
     * 新增景区信息
     *
     * @param scenicInfo 景区信息
     * @return 结果
     */
    @Override
    public int insertScenicInfo(ScenicInfo scenicInfo)
    {
        scenicInfo.setCreateTime(DateUtils.getNowDate());
        return scenicInfoMapper.insertScenicInfo(scenicInfo);
    }

    /**
     * 修改景区信息
     *
     * @param scenicInfo 景区信息
     * @return 结果
     */
    @Override
    public int updateScenicInfo(ScenicInfo scenicInfo)
    {
        scenicInfo.setUpdateTime(DateUtils.getNowDate());
        return scenicInfoMapper.updateScenicInfo(scenicInfo);
    }

    /**
     * 批量删除景区信息
     *
     * @param ids 需要删除的景区信息主键
     * @return 结果
     */
    @Override
    public int deleteScenicInfoByIds(Long[] ids)
    {
        return scenicInfoMapper.deleteScenicInfoByIds(ids);
    }

    /**
     * 删除景区信息信息
     *
     * @param id 景区信息主键
     * @return 结果
     */
    @Override
    public int deleteScenicInfoById(Long id)
    {
        return scenicInfoMapper.deleteScenicInfoById(id);
    }
    //endregion
    @Override
    public QueryWrapper<ScenicInfo> getQueryWrapper(ScenicInfoQuery scenicInfoQuery){
        QueryWrapper<ScenicInfo> queryWrapper = new QueryWrapper<>();
        //如果不使用params可以删除
        Map<String, Object> params = scenicInfoQuery.getParams();
        if (StringUtils.isNull(params)) {
            params = new HashMap<>();
        }
        Long id = scenicInfoQuery.getId();
        queryWrapper.eq( StringUtils.isNotNull(id),"id",id);

        String name = scenicInfoQuery.getName();
        queryWrapper.like(StringUtils.isNotEmpty(name) ,"name",name);

        String status = scenicInfoQuery.getStatus();
        queryWrapper.eq(StringUtils.isNotEmpty(status) ,"status",status);

        String describe = scenicInfoQuery.getDescribe();
        queryWrapper.eq(StringUtils.isNotEmpty(describe) ,"describe",describe);

        Date createTime = scenicInfoQuery.getCreateTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginCreateTime"))&&StringUtils.isNotNull(params.get("endCreateTime")),"create_time",params.get("beginCreateTime"),params.get("endCreateTime"));

        return queryWrapper;
    }

    @Override
    public List<ScenicInfoVo> convertVoList(List<ScenicInfo> scenicInfoList) {
        if (StringUtils.isEmpty(scenicInfoList)) {
            return Collections.emptyList();
        }
        return scenicInfoList.stream().map(ScenicInfoVo::objToVo).collect(Collectors.toList());
    }

}
