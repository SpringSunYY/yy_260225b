package com.lz.manage.model.dto.looksInfo;

import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import com.lz.manage.model.domain.LooksInfo;
/**
 * 浏览信息Vo对象 tb_looks_info
 *
 * @author YY
 * @date 2026-02-28
 */
@Data
public class LooksInfoEdit implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    private Long id;

    /** 景区 */
    private Long scenicId;

    /** 备注 */
    private String remark;

    /**
     * 对象转封装类
     *
     * @param looksInfoEdit 编辑对象
     * @return LooksInfo
     */
    public static LooksInfo editToObj(LooksInfoEdit looksInfoEdit) {
        if (looksInfoEdit == null) {
            return null;
        }
        LooksInfo looksInfo = new LooksInfo();
        BeanUtils.copyProperties(looksInfoEdit, looksInfo);
        return looksInfo;
    }
}
