package com.lz.manage.service;

import java.util.List;
import com.lz.manage.model.domain.EvaluateInfo;
import com.lz.manage.model.vo.evaluateInfo.EvaluateInfoVo;
import com.lz.manage.model.dto.evaluateInfo.EvaluateInfoQuery;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
/**
 * 评价信息Service接口
 * 
 * @author YY
 * @date 2026-02-28
 */
public interface IEvaluateInfoService extends IService<EvaluateInfo>
{
    //region mybatis代码
    /**
     * 查询评价信息
     * 
     * @param id 评价信息主键
     * @return 评价信息
     */
    public EvaluateInfo selectEvaluateInfoById(Long id);

    /**
     * 查询评价信息列表
     * 
     * @param evaluateInfo 评价信息
     * @return 评价信息集合
     */
    public List<EvaluateInfo> selectEvaluateInfoList(EvaluateInfo evaluateInfo);

    /**
     * 新增评价信息
     * 
     * @param evaluateInfo 评价信息
     * @return 结果
     */
    public int insertEvaluateInfo(EvaluateInfo evaluateInfo);

    /**
     * 修改评价信息
     * 
     * @param evaluateInfo 评价信息
     * @return 结果
     */
    public int updateEvaluateInfo(EvaluateInfo evaluateInfo);

    /**
     * 批量删除评价信息
     * 
     * @param ids 需要删除的评价信息主键集合
     * @return 结果
     */
    public int deleteEvaluateInfoByIds(Long[] ids);

    /**
     * 删除评价信息信息
     * 
     * @param id 评价信息主键
     * @return 结果
     */
    public int deleteEvaluateInfoById(Long id);
    //endregion
    /**
     * 获取查询条件
     *
     * @param evaluateInfoQuery 查询条件对象
     * @return 查询条件
     */
    QueryWrapper<EvaluateInfo> getQueryWrapper(EvaluateInfoQuery evaluateInfoQuery);

    /**
     * 转换vo
     *
     * @param evaluateInfoList EvaluateInfo集合
     * @return EvaluateInfoVO集合
     */
    List<EvaluateInfoVo> convertVoList(List<EvaluateInfo> evaluateInfoList);
}
