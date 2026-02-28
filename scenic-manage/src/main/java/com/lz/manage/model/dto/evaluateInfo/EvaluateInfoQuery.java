package com.lz.manage.model.dto.evaluateInfo;

import java.util.Map;
import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.beans.BeanUtils;
import com.baomidou.mybatisplus.annotation.TableField;
import com.lz.manage.model.domain.EvaluateInfo;
/**
 * 评价信息Query对象 tb_evaluate_info
 *
 * @author YY
 * @date 2026-02-28
 */
@Data
public class EvaluateInfoQuery implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    private Long id;

    /** 景区 */
    private Long scenicId;

    /** 状态 */
    private String status;

    /** 标题 */
    private String title;

    /** 创建人 */
    private Long userId;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;

    /** 请求参数 */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @TableField(exist = false)
    private Map<String, Object> params;

    /**
     * 对象转封装类
     *
     * @param evaluateInfoQuery 查询对象
     * @return EvaluateInfo
     */
    public static EvaluateInfo queryToObj(EvaluateInfoQuery evaluateInfoQuery) {
        if (evaluateInfoQuery == null) {
            return null;
        }
        EvaluateInfo evaluateInfo = new EvaluateInfo();
        BeanUtils.copyProperties(evaluateInfoQuery, evaluateInfo);
        return evaluateInfo;
    }
}
