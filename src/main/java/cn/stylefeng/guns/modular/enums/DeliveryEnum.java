package cn.stylefeng.guns.modular.enums;

import lombok.Getter;

/**
 * @author jinbo
 * @date 2021/7/19
 */
@Getter
public enum DeliveryEnum {

    Y("1", "同意"),
    N("2", "不同意");

    private final String code;
    private final String message;

    DeliveryEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public static String getMessage(String code) {
        DeliveryEnum[] values = values();
        for (DeliveryEnum factLegalReleEnum : values) {
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
