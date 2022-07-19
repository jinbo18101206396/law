package cn.stylefeng.guns.modular.enums;

import lombok.Getter;

/**
 * @author jinbo
 * @date 2021/7/19
 */
@Getter
public enum MediateEnum {

    Y("1", "能"),
    N("2", "不能");

    private final String code;
    private final String message;

    MediateEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public static String getMessage(String code) {
        MediateEnum[] values = values();
        for (MediateEnum factLegalReleEnum : values) {
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
