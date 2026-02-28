package com.lz.manage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lz.common.annotation.DataScope;
import com.lz.common.core.domain.entity.SysUser;
import com.lz.common.utils.DateUtils;
import com.lz.common.utils.SecurityUtils;
import com.lz.common.utils.StringUtils;
import com.lz.manage.mapper.LooksInfoMapper;
import com.lz.manage.mapper.ScenicInfoMapper;
import com.lz.manage.model.domain.LooksInfo;
import com.lz.manage.model.domain.ScenicInfo;
import com.lz.manage.model.dto.looksInfo.LooksInfoQuery;
import com.lz.manage.model.vo.looksInfo.LooksInfoVo;
import com.lz.manage.service.ILooksInfoService;
import com.lz.system.service.ISysUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 浏览信息Service业务层处理
 *
 * @author YY
 * @date 2026-02-28
 */
@Service
public class LooksInfoServiceImpl extends ServiceImpl<LooksInfoMapper, LooksInfo> implements ILooksInfoService {

    @Resource
    private LooksInfoMapper looksInfoMapper;

    @Resource
    private ScenicInfoMapper scenicInfoMapper;

    @Resource
    private ISysUserService sysUserService;

    //region mybatis代码

    /**
     * 查询浏览信息
     *
     * @param id 浏览信息主键
     * @return 浏览信息
     */
    @Override
    public LooksInfo selectLooksInfoById(Long id) {
        return looksInfoMapper.selectLooksInfoById(id);
    }

    /**
     * 查询浏览信息列表
     *
     * @param looksInfo 浏览信息
     * @return 浏览信息
     */
    @DataScope(deptAlias = "tb_looks_info", userAlias = "tb_looks_info")
    @Override
    public List<LooksInfo> selectLooksInfoList(LooksInfo looksInfo) {
        List<LooksInfo> looksInfos = looksInfoMapper.selectLooksInfoList(looksInfo);
        for (LooksInfo info : looksInfos) {
            SysUser sysUser = sysUserService.selectUserById(info.getUserId());
            if (StringUtils.isNotNull(sysUser)) {
                info.setUserName(sysUser.getUserName());
            }
            ScenicInfo scenicInfo = scenicInfoMapper.selectScenicInfoById(info.getScenicId());
            if (StringUtils.isNotNull(scenicInfo)) {
                info.setScenicName(scenicInfo.getName());
            }
        }
        return looksInfos;
    }

    /**
     * 新增浏览信息
     *
     * @param looksInfo 浏览信息
     * @return 结果
     */
    @Override
    public int insertLooksInfo(LooksInfo looksInfo) {
        looksInfo.setUserId(SecurityUtils.getUserId());
        looksInfo.setUpdateBy(SecurityUtils.getUsername());
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
    public int updateLooksInfo(LooksInfo looksInfo) {
        looksInfo.setUpdateBy(SecurityUtils.getUsername());
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
    public int deleteLooksInfoByIds(Long[] ids) {
        return looksInfoMapper.deleteLooksInfoByIds(ids);
    }

    /**
     * 删除浏览信息信息
     *
     * @param id 浏览信息主键
     * @return 结果
     */
    @Override
    public int deleteLooksInfoById(Long id) {
        return looksInfoMapper.deleteLooksInfoById(id);
    }

    //endregion
    @Override
    public QueryWrapper<LooksInfo> getQueryWrapper(LooksInfoQuery looksInfoQuery) {
        QueryWrapper<LooksInfo> queryWrapper = new QueryWrapper<>();
        //如果不使用params可以删除
        Map<String, Object> params = looksInfoQuery.getParams();
        if (StringUtils.isNull(params)) {
            params = new HashMap<>();
        }
        Long id = looksInfoQuery.getId();
        queryWrapper.eq(StringUtils.isNotNull(id), "id", id);

        Long scenicId = looksInfoQuery.getScenicId();
        queryWrapper.eq(StringUtils.isNotNull(scenicId), "scenic_id", scenicId);

        Date createTime = looksInfoQuery.getCreateTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginCreateTime")) && StringUtils.isNotNull(params.get("endCreateTime")), "create_time", params.get("beginCreateTime"), params.get("endCreateTime"));

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
