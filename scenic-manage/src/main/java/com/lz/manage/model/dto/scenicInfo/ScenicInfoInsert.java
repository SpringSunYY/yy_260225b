package com.lz.manage.model.dto.scenicInfo;

import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import com.lz.manage.model.domain.ScenicInfo;
/**
 * 景区信息Vo对象 tb_scenic_info
 *
 * @author YY
 * @date 2026-02-28
 */
@Data
public class ScenicInfoInsert implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 名称 */
    private String name;

    /** 状态 */
    private String status;

    /** 图片 */
    private String image;

    /** 描述 */
    private String describe;

    /** 排序 */
    private Long sortNum;

    /** 备注 */
    private String remark;

    /**
     * 对象转封装类
     *
     * @param scenicInfoInsert 插入对象
     * @return ScenicInfoInsert
     */
    public static ScenicInfo insertToObj(ScenicInfoInsert scenicInfoInsert) {
        if (scenicInfoInsert == null) {
            return null;
        }
        ScenicInfo scenicInfo = new ScenicInfo();
        BeanUtils.copyProperties(scenicInfoInsert, scenicInfo);
        return scenicInfo;
    }
}
