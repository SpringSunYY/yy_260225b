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
import com.lz.manage.mapper.EvaluateInfoMapper;
import com.lz.manage.model.domain.EvaluateInfo;
import com.lz.manage.service.IEvaluateInfoService;
import com.lz.manage.model.dto.evaluateInfo.EvaluateInfoQuery;
import com.lz.manage.model.vo.evaluateInfo.EvaluateInfoVo;

/**
 * 评价信息Service业务层处理
 *
 * @author YY
 * @date 2026-02-28
 */
@Service
public class EvaluateInfoServiceImpl extends ServiceImpl<EvaluateInfoMapper, EvaluateInfo> implements IEvaluateInfoService
{

    @Resource
    private EvaluateInfoMapper evaluateInfoMapper;

    //region mybatis代码
    /**
     * 查询评价信息
     *
     * @param id 评价信息主键
     * @return 评价信息
     */
    @Override
    public EvaluateInfo selectEvaluateInfoById(Long id)
    {
        return evaluateInfoMapper.selectEvaluateInfoById(id);
    }

    /**
     * 查询评价信息列表
     *
     * @param evaluateInfo 评价信息
     * @return 评价信息
     */
    @Override
    public List<EvaluateInfo> selectEvaluateInfoList(EvaluateInfo evaluateInfo)
    {
        return evaluateInfoMapper.selectEvaluateInfoList(evaluateInfo);
    }

    /**
     * 新增评价信息
     *
     * @param evaluateInfo 评价信息
     * @return 结果
     */
    @Override
    public int insertEvaluateInfo(EvaluateInfo evaluateInfo)
    {
        evaluateInfo.setCreateTime(DateUtils.getNowDate());
        return evaluateInfoMapper.insertEvaluateInfo(evaluateInfo);
    }

    /**
     * 修改评价信息
     *
     * @param evaluateInfo 评价信息
     * @return 结果
     */
    @Override
    public int updateEvaluateInfo(EvaluateInfo evaluateInfo)
    {
        evaluateInfo.setUpdateTime(DateUtils.getNowDate());
        return evaluateInfoMapper.updateEvaluateInfo(evaluateInfo);
    }

    /**
     * 批量删除评价信息
     *
     * @param ids 需要删除的评价信息主键
     * @return 结果
     */
    @Override
    public int deleteEvaluateInfoByIds(Long[] ids)
    {
        return evaluateInfoMapper.deleteEvaluateInfoByIds(ids);
    }

    /**
     * 删除评价信息信息
     *
     * @param id 评价信息主键
     * @return 结果
     */
    @Override
    public int deleteEvaluateInfoById(Long id)
    {
        return evaluateInfoMapper.deleteEvaluateInfoById(id);
    }
    //endregion
    @Override
    public QueryWrapper<EvaluateInfo> getQueryWrapper(EvaluateInfoQuery evaluateInfoQuery){
        QueryWrapper<EvaluateInfo> queryWrapper = new QueryWrapper<>();
        //如果不使用params可以删除
        Map<String, Object> params = evaluateInfoQuery.getParams();
        if (StringUtils.isNull(params)) {
            params = new HashMap<>();
        }
        Long id = evaluateInfoQuery.getId();
        queryWrapper.eq( StringUtils.isNotNull(id),"id",id);

        Long scenicId = evaluateInfoQuery.getScenicId();
        queryWrapper.eq( StringUtils.isNotNull(scenicId),"scenic_id",scenicId);

        String status = evaluateInfoQuery.getStatus();
        queryWrapper.eq(StringUtils.isNotEmpty(status) ,"status",status);

        String title = evaluateInfoQuery.getTitle();
        queryWrapper.like(StringUtils.isNotEmpty(title) ,"title",title);

        Long userId = evaluateInfoQuery.getUserId();
        queryWrapper.eq( StringUtils.isNotNull(userId),"user_id",userId);

        Date createTime = evaluateInfoQuery.getCreateTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginCreateTime"))&&StringUtils.isNotNull(params.get("endCreateTime")),"create_time",params.get("beginCreateTime"),params.get("endCreateTime"));

        return queryWrapper;
    }

    @Override
    public List<EvaluateInfoVo> convertVoList(List<EvaluateInfo> evaluateInfoList) {
        if (StringUtils.isEmpty(evaluateInfoList)) {
            return Collections.emptyList();
        }
        return evaluateInfoList.stream().map(EvaluateInfoVo::objToVo).collect(Collectors.toList());
    }

}
