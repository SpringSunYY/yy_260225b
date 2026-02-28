package com.lz.manage.model.vo.evaluateInfo;

import java.io.Serializable;
import java.util.Date;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import com.lz.common.annotation.Excel;
import org.springframework.beans.BeanUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lz.manage.model.domain.EvaluateInfo;
/**
 * 评价信息Vo对象 tb_evaluate_info
 *
 * @author YY
 * @date 2026-02-28
 */
@Data
public class EvaluateInfoVo implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    private Long id;

    /** 景区 */
    private Long scenicId;
    private String scenicName;

    /** 状态 */
    private String status;

    /** 标题 */
    private String title;

    /** 评分 */
    private Long score;

    /** 评价内容 */
    private String content;

    /** 备注 */
    private String remark;

    /** 创建人 */
    private Long userId;
    private String userName;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;

    /** 更新人 */
    private String updateBy;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date updateTime;


     /**
     * 对象转封装类
     *
     * @param evaluateInfo EvaluateInfo实体对象
     * @return EvaluateInfoVo
     */
    public static EvaluateInfoVo objToVo(EvaluateInfo evaluateInfo) {
        if (evaluateInfo == null) {
            return null;
        }
        EvaluateInfoVo evaluateInfoVo = new EvaluateInfoVo();
        BeanUtils.copyProperties(evaluateInfo, evaluateInfoVo);
        return evaluateInfoVo;
    }
}
