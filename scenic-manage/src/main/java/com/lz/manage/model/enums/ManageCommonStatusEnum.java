package com.lz.manage.model.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * 通用状态枚举
 * 对应字典类型：manage_common_status
 */
@Getter
public enum ManageCommonStatusEnum {

    MANAGE_COMMON_STATUS_1("1", "正常"),
    MANAGE_COMMON_STATUS_2("2", "异常");

    private final String value;
    private final String label;

    ManageCommonStatusEnum(String value, String label) {
        this.value = value;
        this.label = label;
    }

    private static final Map<String, ManageCommonStatusEnum> VALUE_TO_ENUM = new HashMap<>();

    static {
        for (ManageCommonStatusEnum item : values()) {
            VALUE_TO_ENUM.put(item.value, item);
        }
    }

    public static Optional<ManageCommonStatusEnum> getEnumByValue(String value) {
        if (value == null || value.isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(VALUE_TO_ENUM.get(value));
    }
}
