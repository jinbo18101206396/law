package cn.stylefeng.guns.modular.enums;

import lombok.Getter;

/**
 * @author jinbo
 * @date 2021/7/19
 */
@Getter
public enum QueryTypeEnum {

    DEFENDANT_AND_OTHER_ACCUSER(1,"被告及其他原告"),
    ACCUSER_AND_OTHER_DEFENDANT(2, "原告及其他被告");

    private final Integer code;
    private final String message;

    QueryTypeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public static String getMessage(Integer code) {
        QueryTypeEnum[] values = values();
        for (QueryTypeEnum factLegalReleEnum : values) {
            if (factLegalReleEnum.getCode().equals(code)) {
                return factLegalReleEnum.getMessage();
            }
        }
        return null;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
