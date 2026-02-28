package com.lz.manage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lz.common.annotation.DataScope;
import com.lz.common.core.domain.entity.SysUser;
import com.lz.common.utils.DateUtils;
import com.lz.common.utils.SecurityUtils;
import com.lz.common.utils.StringUtils;
import com.lz.common.utils.ThrowUtils;
import com.lz.manage.mapper.EvaluateInfoMapper;
import com.lz.manage.model.domain.EvaluateInfo;
import com.lz.manage.model.domain.ScenicInfo;
import com.lz.manage.model.dto.evaluateInfo.EvaluateInfoQuery;
import com.lz.manage.model.enums.ManageCommonStatusEnum;
import com.lz.manage.model.vo.evaluateInfo.EvaluateInfoVo;
import com.lz.manage.service.IEvaluateInfoService;
import com.lz.manage.service.IScenicInfoService;
import com.lz.system.service.ISysUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 评价信息Service业务层处理
 *
 * @author YY
 * @date 2026-02-28
 */
@Service
public class EvaluateInfoServiceImpl extends ServiceImpl<EvaluateInfoMapper, EvaluateInfo> implements IEvaluateInfoService {

    @Resource
    private EvaluateInfoMapper evaluateInfoMapper;

    @Resource
    private IScenicInfoService scenicInfoService;

    @Resource
    private ISysUserService sysUserService;

    //region mybatis代码

    /**
     * 查询评价信息
     *
     * @param id 评价信息主键
     * @return 评价信息
     */
    @Override
    public EvaluateInfo selectEvaluateInfoById(Long id) {
        return evaluateInfoMapper.selectEvaluateInfoById(id);
    }

    /**
     * 查询评价信息列表
     *
     * @param evaluateInfo 评价信息
     * @return 评价信息
     */
    @DataScope(deptAlias = "tb_evaluate_info", userAlias = "tb_evaluate_info")
    @Override
    public List<EvaluateInfo> selectEvaluateInfoList(EvaluateInfo evaluateInfo) {
        return getEvaluateInfos(evaluateInfo);
    }

    private List<EvaluateInfo> getEvaluateInfos(EvaluateInfo evaluateInfo) {
        List<EvaluateInfo> evaluateInfos = evaluateInfoMapper.selectEvaluateInfoList(evaluateInfo);
        for (EvaluateInfo info : evaluateInfos) {
            SysUser sysUser = sysUserService.selectUserById(info.getUserId());
            if (StringUtils.isNotNull(sysUser)) {
                info.setUserName(sysUser.getUserName());
            }
            ScenicInfo scenicInfo = scenicInfoService.selectScenicInfoById(info.getScenicId());
            if (StringUtils.isNotNull(scenicInfo)) {
                info.setScenicName(scenicInfo.getName());
            }
        }
        return evaluateInfos;
    }

    @Override
    public List<EvaluateInfo> selectEvaluateInfoListByScenic(EvaluateInfo evaluateInfo) {
        evaluateInfo.setStatus(ManageCommonStatusEnum.MANAGE_COMMON_STATUS_1.getValue());
        return getEvaluateInfos(evaluateInfo);
    }

    /**
     * 新增评价信息
     *
     * @param evaluateInfo 评价信息
     * @return 结果
     */
    @Override
    public int insertEvaluateInfo(EvaluateInfo evaluateInfo) {
        //查询是否有景点
        ScenicInfo scenicInfo = scenicInfoService.selectScenicInfoById(evaluateInfo.getScenicId());
        ThrowUtils.throwIf(StringUtils.isNull(scenicInfo), "没有该景点");
        ThrowUtils.throwIf(!scenicInfo.getStatus().equals(ManageCommonStatusEnum.MANAGE_COMMON_STATUS_1.getValue()),
                "景点当前已关闭");
        evaluateInfo.setStatus(ManageCommonStatusEnum.MANAGE_COMMON_STATUS_1.getValue());
        evaluateInfo.setUserId(SecurityUtils.getUserId());
        evaluateInfo.setCreateTime(DateUtils.getNowDate());
        evaluateInfoMapper.insertEvaluateInfo(evaluateInfo);

        //更新评论数
        long count = this.count(new LambdaQueryWrapper<EvaluateInfo>()
                .eq(EvaluateInfo::getScenicId, evaluateInfo.getScenicId()));
        scenicInfo.setCommentsNumber(count);
        return scenicInfoService.updateScenicInfo(scenicInfo);
    }

    /**
     * 修改评价信息
     *
     * @param evaluateInfo 评价信息
     * @return 结果
     */
    @Override
    public int updateEvaluateInfo(EvaluateInfo evaluateInfo) {
        evaluateInfo.setUpdateBy(SecurityUtils.getUsername());
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
    public int deleteEvaluateInfoByIds(Long[] ids) {
        return evaluateInfoMapper.deleteEvaluateInfoByIds(ids);
    }

    /**
     * 删除评价信息信息
     *
     * @param id 评价信息主键
     * @return 结果
     */
    @Override
    public int deleteEvaluateInfoById(Long id) {
        return evaluateInfoMapper.deleteEvaluateInfoById(id);
    }

    //endregion
    @Override
    public QueryWrapper<EvaluateInfo> getQueryWrapper(EvaluateInfoQuery evaluateInfoQuery) {
        QueryWrapper<EvaluateInfo> queryWrapper = new QueryWrapper<>();
        //如果不使用params可以删除
        Map<String, Object> params = evaluateInfoQuery.getParams();
        if (StringUtils.isNull(params)) {
            params = new HashMap<>();
        }
        Long id = evaluateInfoQuery.getId();
        queryWrapper.eq(StringUtils.isNotNull(id), "id", id);

        Long scenicId = evaluateInfoQuery.getScenicId();
        queryWrapper.eq(StringUtils.isNotNull(scenicId), "scenic_id", scenicId);

        String status = evaluateInfoQuery.getStatus();
        queryWrapper.eq(StringUtils.isNotEmpty(status), "status", status);

        String title = evaluateInfoQuery.getTitle();
        queryWrapper.like(StringUtils.isNotEmpty(title), "title", title);

        Long userId = evaluateInfoQuery.getUserId();
        queryWrapper.eq(StringUtils.isNotNull(userId), "user_id", userId);

        Date createTime = evaluateInfoQuery.getCreateTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginCreateTime")) && StringUtils.isNotNull(params.get("endCreateTime")), "create_time", params.get("beginCreateTime"), params.get("endCreateTime"));

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
