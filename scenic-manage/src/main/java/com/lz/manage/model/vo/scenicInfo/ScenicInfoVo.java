package com.lz.manage.model.vo.scenicInfo;

import java.io.Serializable;
import java.util.Date;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import com.lz.common.annotation.Excel;
import org.springframework.beans.BeanUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lz.manage.model.domain.ScenicInfo;
/**
 * 景区信息Vo对象 tb_scenic_info
 *
 * @author YY
 * @date 2026-02-28
 */
@Data
public class ScenicInfoVo implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    private Long id;

    /** 名称 */
    private String name;

    /** 状态 */
    private String status;

    /** 图片 */
    private String image;

    /** 描述 */
    private String describe;

    /** 点赞量 */
    private Long likesNumber;

    /** 评论数 */
    private Long commentsNumber;

    /** 浏览量 */
    private Long looksNumber;

    /** 排序 */
    private Long sortNum;

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
     * @param scenicInfo ScenicInfo实体对象
     * @return ScenicInfoVo
     */
    public static ScenicInfoVo objToVo(ScenicInfo scenicInfo) {
        if (scenicInfo == null) {
            return null;
        }
        ScenicInfoVo scenicInfoVo = new ScenicInfoVo();
        BeanUtils.copyProperties(scenicInfo, scenicInfoVo);
        return scenicInfoVo;
    }
}
