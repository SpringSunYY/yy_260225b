package com.lz.manage.model.vo.looksInfo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lz.manage.model.domain.LooksInfo;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * 浏览信息Vo对象 tb_looks_info
 *
 * @author YY
 * @date 2026-02-28
 */
@Data
public class LooksInfoVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private Long id;

    /**
     * 景区
     */
    private Long scenicId;
    private String scenicName;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建人
     */
    private Long userId;
    private String userName;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;

    /**
     * 更新人
     */
    private String updateBy;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date updateTime;


    /**
     * 对象转封装类
     *
     * @param looksInfo LooksInfo实体对象
     * @return LooksInfoVo
     */
    public static LooksInfoVo objToVo(LooksInfo looksInfo) {
        if (looksInfo == null) {
            return null;
        }
        LooksInfoVo looksInfoVo = new LooksInfoVo();
        BeanUtils.copyProperties(looksInfo, looksInfoVo);
        return looksInfoVo;
    }
}
