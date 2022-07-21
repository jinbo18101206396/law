package cn.stylefeng.guns.modular.enums;

import lombok.Getter;

/**
 * 被告是否举证
 * @author jinbo
 * @date 2021/7/17
 */
@Getter
public enum DefendantEvidenceEnum {

    Y("1", "是"),
    N("2", "否");

    private final String code;
    private final String message;

    DefendantEvidenceEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public static String getMessage(String code) {
        DefendantEvidenceEnum[] values = values();
        for (DefendantEvidenceEnum factLegalReleEnum : values) {
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
