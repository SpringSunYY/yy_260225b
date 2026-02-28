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
import com.lz.manage.mapper.LooksInfoMapper;
import com.lz.manage.model.domain.LooksInfo;
import com.lz.manage.service.ILooksInfoService;
import com.lz.manage.model.dto.looksInfo.LooksInfoQuery;
import com.lz.manage.model.vo.looksInfo.LooksInfoVo;

/**
 * 浏览信息Service业务层处理
 *
 * @author YY
 * @date 2026-02-28
 */
@Service
public class LooksInfoServiceImpl extends ServiceImpl<LooksInfoMapper, LooksInfo> implements ILooksInfoService
{

    @Resource
    private LooksInfoMapper looksInfoMapper;

    //region mybatis代码
    /**
     * 查询浏览信息
     *
     * @param id 浏览信息主键
     * @return 浏览信息
     */
    @Override
    public LooksInfo selectLooksInfoById(Long id)
    {
        return looksInfoMapper.selectLooksInfoById(id);
    }

    /**
     * 查询浏览信息列表
     *
     * @param looksInfo 浏览信息
     * @return 浏览信息
     */
    @Override
    public List<LooksInfo> selectLooksInfoList(LooksInfo looksInfo)
    {
        return looksInfoMapper.selectLooksInfoList(looksInfo);
    }

    /**
     * 新增浏览信息
     *
     * @param looksInfo 浏览信息
     * @return 结果
     */
    @Override
    public int insertLooksInfo(LooksInfo looksInfo)
    {
        looksInfo.setCreateTime(DateUtils.getNowDate());
        return looksInfoMapper.insertLooksInfo(looksInfo);
    }

    /**
     * 修改浏览信息
     *
     * @param looksInfo 浏览信息
     * @return 结果
     */
    @Override
    public int updateLooksInfo(LooksInfo looksInfo)
    {
        looksInfo.setUpdateTime(DateUtils.getNowDate());
        return looksInfoMapper.updateLooksInfo(looksInfo);
    }

    /**
     * 批量删除浏览信息
     *
     * @param ids 需要删除的浏览信息主键
     * @return 结果
     */
    @Override
    public int deleteLooksInfoByIds(Long[] ids)
    {
        return looksInfoMapper.deleteLooksInfoByIds(ids);
    }

    /**
     * 删除浏览信息信息
     *
     * @param id 浏览信息主键
     * @return 结果
     */
    @Override
    public int deleteLooksInfoById(Long id)
    {
        return looksInfoMapper.deleteLooksInfoById(id);
    }
    //endregion
    @Override
    public QueryWrapper<LooksInfo> getQueryWrapper(LooksInfoQuery looksInfoQuery){
        QueryWrapper<LooksInfo> queryWrapper = new QueryWrapper<>();
        //如果不使用params可以删除
        Map<String, Object> params = looksInfoQuery.getParams();
        if (StringUtils.isNull(params)) {
            params = new HashMap<>();
        }
        Long id = looksInfoQuery.getId();
        queryWrapper.eq( StringUtils.isNotNull(id),"id",id);

        Long scenicId = looksInfoQuery.getScenicId();
        queryWrapper.eq( StringUtils.isNotNull(scenicId),"scenic_id",scenicId);

        Date createTime = looksInfoQuery.getCreateTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginCreateTime"))&&StringUtils.isNotNull(params.get("endCreateTime")),"create_time",params.get("beginCreateTime"),params.get("endCreateTime"));

        return queryWrapper;
    }

    @Override
    public List<LooksInfoVo> convertVoList(List<LooksInfo> looksInfoList) {
        if (StringUtils.isEmpty(looksInfoList)) {
            return Collections.emptyList();
        }
        return looksInfoList.stream().map(LooksInfoVo::objToVo).collect(Collectors.toList());
    }

}
