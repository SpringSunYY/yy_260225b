package com.lz.manage.model.dto.evaluateInfo;

import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import com.lz.manage.model.domain.EvaluateInfo;
/**
 * 评价信息Vo对象 tb_evaluate_info
 *
 * @author YY
 * @date 2026-02-28
 */
@Data
public class EvaluateInfoEdit implements Serializable
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

    /** 评分 */
    private Long score;

    /** 评价内容 */
    private String content;

    /** 备注 */
    private String remark;

    /**
     * 对象转封装类
     *
     * @param evaluateInfoEdit 编辑对象
     * @return EvaluateInfo
     */
    public static EvaluateInfo editToObj(EvaluateInfoEdit evaluateInfoEdit) {
        if (evaluateInfoEdit == null) {
            return null;
        }
        EvaluateInfo evaluateInfo = new EvaluateInfo();
        BeanUtils.copyProperties(evaluateInfoEdit, evaluateInfo);
        return evaluateInfo;
    }
}
