package com.lz.manage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lz.common.core.domain.entity.SysUser;
import com.lz.common.utils.DateUtils;
import com.lz.common.utils.SecurityUtils;
import com.lz.common.utils.StringUtils;
import com.lz.manage.mapper.LikesInfoMapper;
import com.lz.manage.model.domain.LikesInfo;
import com.lz.manage.model.domain.ScenicInfo;
import com.lz.manage.model.dto.likesInfo.LikesInfoQuery;
import com.lz.manage.model.vo.likesInfo.LikesInfoVo;
import com.lz.manage.service.ILikesInfoService;
import com.lz.manage.service.IScenicInfoService;
import com.lz.system.service.ISysUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 点赞信息Service业务层处理
 *
 * @author YY
 * @date 2026-02-28
 */
@Service
public class LikesInfoServiceImpl extends ServiceImpl<LikesInfoMapper, LikesInfo> implements ILikesInfoService {

    @Resource
    private LikesInfoMapper likesInfoMapper;

    @Resource
    private IScenicInfoService scenicInfoService;

    @Resource
    private ISysUserService sysUserService;

    //region mybatis代码

    /**
     * 查询点赞信息
     *
     * @param id 点赞信息主键
     * @return 点赞信息
     */
    @Override
    public LikesInfo selectLikesInfoById(Long id) {
        return likesInfoMapper.selectLikesInfoById(id);
    }

    /**
     * 查询点赞信息列表
     *
     * @param likesInfo 点赞信息
     * @return 点赞信息
     */
    @Override
    public List<LikesInfo> selectLikesInfoList(LikesInfo likesInfo) {
        List<LikesInfo> likesInfos = likesInfoMapper.selectLikesInfoList(likesInfo);
        for (LikesInfo info : likesInfos) {
            SysUser sysUser = sysUserService.selectUserById(info.getUserId());
            if (StringUtils.isNotNull(sysUser)) {
                info.setUserName(sysUser.getUserName());
            }
            ScenicInfo scenicInfo = scenicInfoService.selectScenicInfoById(info.getScenicId());
            if (StringUtils.isNotNull(scenicInfo)) {
                info.setScenicName(scenicInfo.getName());
            }
        }
        return likesInfos;
    }

    /**
     * 新增点赞信息
     *
     * @param likesInfo 点赞信息
     * @return 结果
     */
    @Override
    public int insertLikesInfo(LikesInfo likesInfo) {
        //查询是否有景点
        ScenicInfo scenicInfo = scenicInfoService.selectScenicInfoById(likesInfo.getScenicId());
        if (StringUtils.isNull(scenicInfo)) {
            return 1;
        }
        //首先查询是否有点赞
        LambdaQueryWrapper<LikesInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(LikesInfo::getScenicId, likesInfo.getScenicId());
        Long userId = SecurityUtils.getUserId();
        queryWrapper.eq(LikesInfo::getUserId, userId);
        List<LikesInfo> list = this.list(queryWrapper);
        if (StringUtils.isNotEmpty(list)) {
            //有点赞表示删除点赞
            this.remove(queryWrapper);
        } else {
            likesInfo.setCreateTime(DateUtils.getNowDate());
            likesInfo.setUpdateTime(DateUtils.getNowDate());
            likesInfo.setUserId(userId);
            likesInfoMapper.insertLikesInfo(likesInfo);
        }
        LambdaQueryWrapper<LikesInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(LikesInfo::getScenicId, likesInfo.getScenicId());
        long count = this.count(wrapper);
        scenicInfo.setLikesNumber(count);
        return scenicInfoService.updateScenicInfo(scenicInfo);
    }

    /**
     * 修改点赞信息
     *
     * @param likesInfo 点赞信息
     * @return 结果
     */
    @Override
    public int updateLikesInfo(LikesInfo likesInfo) {
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
    public int deleteLikesInfoByIds(Long[] ids) {
        return likesInfoMapper.deleteLikesInfoByIds(ids);
    }

    /**
     * 删除点赞信息信息
     *
     * @param id 点赞信息主键
     * @return 结果
     */
    @Override
    public int deleteLikesInfoById(Long id) {
        return likesInfoMapper.deleteLikesInfoById(id);
    }

    //endregion
    @Override
    public QueryWrapper<LikesInfo> getQueryWrapper(LikesInfoQuery likesInfoQuery) {
        QueryWrapper<LikesInfo> queryWrapper = new QueryWrapper<>();
        //如果不使用params可以删除
        Map<String, Object> params = likesInfoQuery.getParams();
        if (StringUtils.isNull(params)) {
            params = new HashMap<>();
        }
        Long id = likesInfoQuery.getId();
        queryWrapper.eq(StringUtils.isNotNull(id), "id", id);

        Long scenicId = likesInfoQuery.getScenicId();
        queryWrapper.eq(StringUtils.isNotNull(scenicId), "scenic_id", scenicId);

        Date createTime = likesInfoQuery.getCreateTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginCreateTime")) && StringUtils.isNotNull(params.get("endCreateTime")), "create_time", params.get("beginCreateTime"), params.get("endCreateTime"));

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
