package cn.stylefeng.guns.modular.enums;

import lombok.Getter;

/**
 * @author jinbo
 * @date 2021/7/17
 */
@Getter
public enum RightDutyEnum {

    Y("1", "听清楚了"),
    N("2", "没听清楚");

    private final String code;
    private final String message;

    RightDutyEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public static String getMessage(String code) {
        RightDutyEnum[] values = values();
        for (RightDutyEnum factLegalReleEnum : values) {
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
