package com.lz.manage.mapper;

import java.util.List;
import com.lz.manage.model.domain.LooksInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 浏览信息Mapper接口
 * 
 * @author YY
 * @date 2026-02-28
 */
public interface LooksInfoMapper extends BaseMapper<LooksInfo>
{
    /**
     * 查询浏览信息
     * 
     * @param id 浏览信息主键
     * @return 浏览信息
     */
    public LooksInfo selectLooksInfoById(Long id);

    /**
     * 查询浏览信息列表
     * 
     * @param looksInfo 浏览信息
     * @return 浏览信息集合
     */
    public List<LooksInfo> selectLooksInfoList(LooksInfo looksInfo);

    /**
     * 新增浏览信息
     * 
     * @param looksInfo 浏览信息
     * @return 结果
     */
    public int insertLooksInfo(LooksInfo looksInfo);

    /**
     * 修改浏览信息
     * 
     * @param looksInfo 浏览信息
     * @return 结果
     */
    public int updateLooksInfo(LooksInfo looksInfo);

    /**
     * 删除浏览信息
     * 
     * @param id 浏览信息主键
     * @return 结果
     */
    public int deleteLooksInfoById(Long id);

    /**
     * 批量删除浏览信息
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteLooksInfoByIds(Long[] ids);
}
