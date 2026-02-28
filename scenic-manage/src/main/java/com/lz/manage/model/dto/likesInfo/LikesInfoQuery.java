package com.lz.manage.model.dto.likesInfo;

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
import com.lz.manage.model.domain.LikesInfo;
/**
 * 点赞信息Query对象 tb_likes_info
 *
 * @author YY
 * @date 2026-02-28
 */
@Data
public class LikesInfoQuery implements Serializable
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
     * @param likesInfoQuery 查询对象
     * @return LikesInfo
     */
    public static LikesInfo queryToObj(LikesInfoQuery likesInfoQuery) {
        if (likesInfoQuery == null) {
            return null;
        }
        LikesInfo likesInfo = new LikesInfo();
        BeanUtils.copyProperties(likesInfoQuery, likesInfo);
        return likesInfo;
    }
}
