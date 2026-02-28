package com.lz.manage.model.vo.scenicInfo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lz.manage.model.domain.ScenicInfo;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * 景区信息Vo对象 tb_scenic_info
 *
 * @author YY
 * @date 2026-02-28
 */
@Data
public class ScenicInfoDetailVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 状态
     */
    private String status;

    /**
     * 图片
     */
    private String image;

    /**
     * 描述
     */
    private String describe;

    /**
     * 点赞量
     */
    private Long likesNumber;

    /**
     * 评论数
     */
    private Long commentsNumber;

    /**
     * 浏览量
     */
    private Long looksNumber;

    private Boolean isLike;

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
     * @param scenicInfo ScenicInfo实体对象
     * @return ScenicInfoVo
     */
    public static ScenicInfoDetailVo objToVo(ScenicInfo scenicInfo) {
        if (scenicInfo == null) {
            return null;
        }
        ScenicInfoDetailVo scenicInfoVo = new ScenicInfoDetailVo();
        BeanUtils.copyProperties(scenicInfo, scenicInfoVo);
        return scenicInfoVo;
    }
}
