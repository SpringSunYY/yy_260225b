package com.lz.manage.service;

import java.util.List;
import com.lz.manage.model.domain.ScenicInfo;
import com.lz.manage.model.vo.scenicInfo.ScenicInfoDetailVo;
import com.lz.manage.model.vo.scenicInfo.ScenicInfoVo;
import com.lz.manage.model.dto.scenicInfo.ScenicInfoQuery;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
/**
 * 景区信息Service接口
 *
 * @author YY
 * @date 2026-02-28
 */
public interface IScenicInfoService extends IService<ScenicInfo>
{
    //region mybatis代码
    /**
     * 查询景区信息
     *
     * @param id 景区信息主键
     * @return 景区信息
     */
    public ScenicInfo selectScenicInfoById(Long id);

    /**
     * 查询景区信息详细
     *
     * @param id 景区信息主键
     * @return 景区信息
     */
    ScenicInfoDetailVo selectScenicInfoDetailById(Long id);

    /**
     * 查询景区信息列表
     *
     * @param scenicInfo 景区信息
     * @return 景区信息集合
     */
    public List<ScenicInfo> selectScenicInfoList(ScenicInfo scenicInfo);

    /**
     * 新增景区信息
     *
     * @param scenicInfo 景区信息
     * @return 结果
     */
    public int insertScenicInfo(ScenicInfo scenicInfo);

    /**
     * 修改景区信息
     *
     * @param scenicInfo 景区信息
     * @return 结果
     */
    public int updateScenicInfo(ScenicInfo scenicInfo);

    /**
     * 批量删除景区信息
     *
     * @param ids 需要删除的景区信息主键集合
     * @return 结果
     */
    public int deleteScenicInfoByIds(Long[] ids);

    /**
     * 删除景区信息信息
     *
     * @param id 景区信息主键
     * @return 结果
     */
    public int deleteScenicInfoById(Long id);
    //endregion
    /**
     * 获取查询条件
     *
     * @param scenicInfoQuery 查询条件对象
     * @return 查询条件
     */
    QueryWrapper<ScenicInfo> getQueryWrapper(ScenicInfoQuery scenicInfoQuery);

    /**
     * 转换vo
     *
     * @param scenicInfoList ScenicInfo集合
     * @return ScenicInfoVO集合
     */
    List<ScenicInfoVo> convertVoList(List<ScenicInfo> scenicInfoList);
}
