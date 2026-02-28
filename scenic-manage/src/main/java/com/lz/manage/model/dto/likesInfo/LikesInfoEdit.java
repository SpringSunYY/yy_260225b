package com.lz.manage.model.dto.likesInfo;

import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import com.lz.manage.model.domain.LikesInfo;
/**
 * 点赞信息Vo对象 tb_likes_info
 *
 * @author YY
 * @date 2026-02-28
 */
@Data
public class LikesInfoEdit implements Serializable
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
     * @param likesInfoEdit 编辑对象
     * @return LikesInfo
     */
    public static LikesInfo editToObj(LikesInfoEdit likesInfoEdit) {
        if (likesInfoEdit == null) {
            return null;
        }
        LikesInfo likesInfo = new LikesInfo();
        BeanUtils.copyProperties(likesInfoEdit, likesInfo);
        return likesInfo;
    }
}
