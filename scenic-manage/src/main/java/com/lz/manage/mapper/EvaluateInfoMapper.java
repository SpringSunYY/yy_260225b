package com.lz.manage.mapper;

import java.util.List;
import com.lz.manage.model.domain.EvaluateInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 评价信息Mapper接口
 * 
 * @author YY
 * @date 2026-02-28
 */
public interface EvaluateInfoMapper extends BaseMapper<EvaluateInfo>
{
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
     * 删除评价信息
     * 
     * @param id 评价信息主键
     * @return 结果
     */
    public int deleteEvaluateInfoById(Long id);

    /**
     * 批量删除评价信息
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteEvaluateInfoByIds(Long[] ids);
}
