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
public class LooksInfoInsert implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 景区 */
    private Long scenicId;

    /** 备注 */
    private String remark;

    /**
     * 对象转封装类
     *
     * @param looksInfoInsert 插入对象
     * @return LooksInfoInsert
     */
    public static LooksInfo insertToObj(LooksInfoInsert looksInfoInsert) {
        if (looksInfoInsert == null) {
            return null;
        }
        LooksInfo looksInfo = new LooksInfo();
        BeanUtils.copyProperties(looksInfoInsert, looksInfo);
        return looksInfo;
    }
}
