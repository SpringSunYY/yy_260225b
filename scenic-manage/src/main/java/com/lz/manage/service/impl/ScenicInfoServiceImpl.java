package com.lz.manage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lz.common.core.domain.entity.SysUser;
import com.lz.common.utils.DateUtils;
import com.lz.common.utils.SecurityUtils;
import com.lz.common.utils.StringUtils;
import com.lz.manage.mapper.LikesInfoMapper;
import com.lz.manage.mapper.LooksInfoMapper;
import com.lz.manage.mapper.ScenicInfoMapper;
import com.lz.manage.model.domain.LikesInfo;
import com.lz.manage.model.domain.LooksInfo;
import com.lz.manage.model.domain.ScenicInfo;
import com.lz.manage.model.dto.scenicInfo.ScenicInfoQuery;
import com.lz.manage.model.vo.scenicInfo.ScenicInfoDetailVo;
import com.lz.manage.model.vo.scenicInfo.ScenicInfoVo;
import com.lz.manage.service.IScenicInfoService;
import com.lz.system.service.ISysUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 景区信息Service业务层处理
 *
 * @author YY
 * @date 2026-02-28
 */
@Service
public class ScenicInfoServiceImpl extends ServiceImpl<ScenicInfoMapper, ScenicInfo> implements IScenicInfoService {

    @Resource
    private ScenicInfoMapper scenicInfoMapper;

    @Resource
    private ISysUserService sysUserService;

    @Resource
    private LikesInfoMapper likesInfoMapper;

    @Resource
    private LooksInfoMapper looksInfoMapper;

    //region mybatis代码

    /**
     * 查询景区信息
     *
     * @param id 景区信息主键
     * @return 景区信息
     */
    @Override
    public ScenicInfo selectScenicInfoById(Long id) {
        return scenicInfoMapper.selectScenicInfoById(id);
    }

    @Override
    public ScenicInfoDetailVo selectScenicInfoDetailById(Long id) {
        ScenicInfo scenicInfo = scenicInfoMapper.selectScenicInfoById(id);
        ScenicInfoDetailVo scenicInfoDetailVo = ScenicInfoDetailVo.objToVo(scenicInfo);
        LikesInfo likesInfo = new LikesInfo();
        likesInfo.setScenicId(id);
        Long userId = SecurityUtils.getUserId();
        likesInfo.setUserId(userId);
        //判断是否已点赞
        List<LikesInfo> likesInfos = likesInfoMapper.selectLikesInfoList(likesInfo);
        if (StringUtils.isNotEmpty(likesInfos)) {
            scenicInfoDetailVo.setIsLike(true);
        } else {
            scenicInfoDetailVo.setIsLike(false);
        }
        //判断是否有浏览记录
        LooksInfo looksInfo = new LooksInfo();
        looksInfo.setScenicId(id);
        looksInfo.setUserId(userId);
        List<LooksInfo> looksInfos = looksInfoMapper.selectLooksInfoList(looksInfo);
        Date nowDate = DateUtils.getNowDate();
        //如果有就更新浏览的更新时间，没有插入
        if (StringUtils.isNotEmpty(looksInfos)) {
            LooksInfo looks = looksInfos.get(0);
            looks.setUpdateTime(nowDate);
            looksInfoMapper.updateLooksInfo(looks);
        } else {
            looksInfo.setCreateTime(nowDate);
            looksInfo.setUpdateTime(nowDate);
            looksInfoMapper.insertLooksInfo(looksInfo);
            scenicInfo.setLooksNumber(scenicInfo.getLooksNumber() + 1);
            scenicInfoMapper.updateScenicInfo(scenicInfo);
        }
        return scenicInfoDetailVo;
    }

    /**
     * 查询景区信息列表
     *
     * @param scenicInfo 景区信息
     * @return 景区信息
     */
    @Override
    public List<ScenicInfo> selectScenicInfoList(ScenicInfo scenicInfo) {
        List<ScenicInfo> scenicInfos = scenicInfoMapper.selectScenicInfoList(scenicInfo);
        for (ScenicInfo info : scenicInfos) {
            SysUser sysUser = sysUserService.selectUserById(info.getUserId());
            if (StringUtils.isNotNull(sysUser)) {
                info.setUserName(sysUser.getUserName());
            }
        }
        return scenicInfos;
    }

    /**
     * 新增景区信息
     *
     * @param scenicInfo 景区信息
     * @return 结果
     */
    @Override
    public int insertScenicInfo(ScenicInfo scenicInfo) {
        scenicInfo.setUserId(SecurityUtils.getUserId());
        Date nowDate = DateUtils.getNowDate();
        scenicInfo.setCreateTime(nowDate);
        scenicInfo.setLikesNumber(0L);
        scenicInfo.setLooksNumber(0L);
        scenicInfo.setCommentsNumber(0L);
        return scenicInfoMapper.insertScenicInfo(scenicInfo);
    }

    /**
     * 修改景区信息
     *
     * @param scenicInfo 景区信息
     * @return 结果
     */
    @Override
    public int updateScenicInfo(ScenicInfo scenicInfo) {
        scenicInfo.setUpdateBy(SecurityUtils.getUsername());
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
    public int deleteScenicInfoByIds(Long[] ids) {
        return scenicInfoMapper.deleteScenicInfoByIds(ids);
    }

    /**
     * 删除景区信息信息
     *
     * @param id 景区信息主键
     * @return 结果
     */
    @Override
    public int deleteScenicInfoById(Long id) {
        return scenicInfoMapper.deleteScenicInfoById(id);
    }

    //endregion
    @Override
    public QueryWrapper<ScenicInfo> getQueryWrapper(ScenicInfoQuery scenicInfoQuery) {
        QueryWrapper<ScenicInfo> queryWrapper = new QueryWrapper<>();
        //如果不使用params可以删除
        Map<String, Object> params = scenicInfoQuery.getParams();
        if (StringUtils.isNull(params)) {
            params = new HashMap<>();
        }
        Long id = scenicInfoQuery.getId();
        queryWrapper.eq(StringUtils.isNotNull(id), "id", id);

        String name = scenicInfoQuery.getName();
        queryWrapper.like(StringUtils.isNotEmpty(name), "name", name);

        String status = scenicInfoQuery.getStatus();
        queryWrapper.eq(StringUtils.isNotEmpty(status), "status", status);

        String describe = scenicInfoQuery.getDescribe();
        queryWrapper.eq(StringUtils.isNotEmpty(describe), "describe", describe);

        Date createTime = scenicInfoQuery.getCreateTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginCreateTime")) && StringUtils.isNotNull(params.get("endCreateTime")), "create_time", params.get("beginCreateTime"), params.get("endCreateTime"));

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
