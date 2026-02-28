package com.lz.manage.model.dto.looksInfo;

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
import com.lz.manage.model.domain.LooksInfo;
/**
 * 浏览信息Query对象 tb_looks_info
 *
 * @author YY
 * @date 2026-02-28
 */
@Data
public class LooksInfoQuery implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    private Long id;

    /** 景区 */
    private Long scenicId;

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
     * @param looksInfoQuery 查询对象
     * @return LooksInfo
     */
    public static LooksInfo queryToObj(LooksInfoQuery looksInfoQuery) {
        if (looksInfoQuery == null) {
            return null;
        }
        LooksInfo looksInfo = new LooksInfo();
        BeanUtils.copyProperties(looksInfoQuery, looksInfo);
        return looksInfo;
    }
}
