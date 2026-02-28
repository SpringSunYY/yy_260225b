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
public class ScenicInfoEdit implements Serializable
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

    /** 排序 */
    private Long sortNum;

    /** 备注 */
    private String remark;

    /**
     * 对象转封装类
     *
     * @param scenicInfoEdit 编辑对象
     * @return ScenicInfo
     */
    public static ScenicInfo editToObj(ScenicInfoEdit scenicInfoEdit) {
        if (scenicInfoEdit == null) {
            return null;
        }
        ScenicInfo scenicInfo = new ScenicInfo();
        BeanUtils.copyProperties(scenicInfoEdit, scenicInfo);
        return scenicInfo;
    }
}
