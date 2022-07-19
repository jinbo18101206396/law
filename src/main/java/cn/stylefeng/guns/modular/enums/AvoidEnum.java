package cn.stylefeng.guns.modular.enums;

import lombok.Getter;

/**
 * @author jinbo
 * @date 2021/7/17
 */
@Getter
public enum AvoidEnum {

    Y("1", "申请回避"),
    N("2", "不申请回避");

    private final String code;
    private final String message;

    AvoidEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public static String getMessage(String code) {
        AvoidEnum[] values = values();
        for (AvoidEnum factLegalReleEnum : values) {
            if (factLegalReleEnum.getCode().equals(code)) {
                return factLegalReleEnum.getMessage();
            }
        }
        return null;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
