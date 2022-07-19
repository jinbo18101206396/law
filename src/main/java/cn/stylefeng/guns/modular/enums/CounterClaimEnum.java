package cn.stylefeng.guns.modular.enums;

import lombok.Getter;

/**
 * @author jinbo
 * @date 2021/7/17
 */
@Getter
public enum CounterClaimEnum {

    COUNTER_CLAIM("1", "反诉"),
    NOT_COUNTER_CLAIM("2", "不反诉");

    private final String code;
    private final String message;

    CounterClaimEnum(String code, String message) {
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
