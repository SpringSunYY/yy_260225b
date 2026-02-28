package com.lz.manage.service;

import java.util.List;
import com.lz.manage.model.domain.LooksInfo;
import com.lz.manage.model.vo.looksInfo.LooksInfoVo;
import com.lz.manage.model.dto.looksInfo.LooksInfoQuery;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
/**
 * 浏览信息Service接口
 * 
 * @author YY
 * @date 2026-02-28
 */
public interface ILooksInfoService extends IService<LooksInfo>
{
    //region mybatis代码
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
     * 批量删除浏览信息
     * 
     * @param ids 需要删除的浏览信息主键集合
     * @return 结果
     */
    public int deleteLooksInfoByIds(Long[] ids);

    /**
     * 删除浏览信息信息
     * 
     * @param id 浏览信息主键
     * @return 结果
     */
    public int deleteLooksInfoById(Long id);
    //endregion
    /**
     * 获取查询条件
     *
     * @param looksInfoQuery 查询条件对象
     * @return 查询条件
     */
    QueryWrapper<LooksInfo> getQueryWrapper(LooksInfoQuery looksInfoQuery);

    /**
     * 转换vo
     *
     * @param looksInfoList LooksInfo集合
     * @return LooksInfoVO集合
     */
    List<LooksInfoVo> convertVoList(List<LooksInfo> looksInfoList);
}
