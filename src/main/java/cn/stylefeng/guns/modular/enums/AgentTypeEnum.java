package cn.stylefeng.guns.modular.enums;

import lombok.Getter;

/**
 * @author jinbo
 * @date 2021/7/17
 */
@Getter
public enum AgentTypeEnum {

    ACCUSER("1", "原告代理"),
    DEFENDANT("2", "被告代理"),
    THIRD("3", "第三人代理");

    private final String code;
    private final String message;

    AgentTypeEnum(String code, String message) {
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
