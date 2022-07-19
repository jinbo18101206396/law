package cn.stylefeng.guns.modular.enums;

import lombok.Getter;

/**
 * @author jinbo
 * @date 2021/7/17
 */
@Getter
public enum AccuserDefendantTypeEnum {

    DEPARTMENT("1", "单位"),
    PERSON("2", "个人");

    private final String code;
    private final String message;

    AccuserDefendantTypeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
