package com.lz.manage.mapper;

import java.util.List;
import com.lz.manage.model.domain.ScenicInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 景区信息Mapper接口
 * 
 * @author YY
 * @date 2026-02-28
 */
public interface ScenicInfoMapper extends BaseMapper<ScenicInfo>
{
    /**
     * 查询景区信息
     * 
     * @param id 景区信息主键
     * @return 景区信息
     */
    public ScenicInfo selectScenicInfoById(Long id);

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
     * 删除景区信息
     * 
     * @param id 景区信息主键
     * @return 结果
     */
    public int deleteScenicInfoById(Long id);

    /**
     * 批量删除景区信息
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteScenicInfoByIds(Long[] ids);
}
