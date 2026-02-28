package com.lz.manage.model.vo.likesInfo;

import java.io.Serializable;
import java.util.Date;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import com.lz.common.annotation.Excel;
import org.springframework.beans.BeanUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lz.manage.model.domain.LikesInfo;
/**
 * 点赞信息Vo对象 tb_likes_info
 *
 * @author YY
 * @date 2026-02-28
 */
@Data
public class LikesInfoVo implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    private Long id;

    /** 景区 */
    private Long scenicId;

    /** 备注 */
    private String remark;

    /** 创建人 */
    private Long userId;

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
     * @param likesInfo LikesInfo实体对象
     * @return LikesInfoVo
     */
    public static LikesInfoVo objToVo(LikesInfo likesInfo) {
        if (likesInfo == null) {
            return null;
        }
        LikesInfoVo likesInfoVo = new LikesInfoVo();
        BeanUtils.copyProperties(likesInfo, likesInfoVo);
        return likesInfoVo;
    }
}
