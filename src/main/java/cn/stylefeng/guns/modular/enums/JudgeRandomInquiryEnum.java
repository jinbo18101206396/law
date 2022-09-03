package cn.stylefeng.guns.modular.enums;

import lombok.Getter;

/**
 * @author jinbo
 * @date 2021/7/17
 */
@Getter
public enum JudgeRandomInquiryEnum {
    AFTER_ACCUSER_CLAIM("1", "原告诉陈后"),
    AFTER_DEFENDANT_REPLY("2", "被告答辩后"),
    BEFORE_SUMMARIZE("3", "审判员最终陈述前"),
    BEFORE_THIRD_STATE("4", "第三人述称前");

    private final String code;
    private final String message;

    JudgeRandomInquiryEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public static String getMessage(String code) {
        JudgeRandomInquiryEnum[] values = values();
        for (JudgeRandomInquiryEnum factLegalReleEnum : values) {
            if (factLegalReleEnum.getCode().equals(code)) {
                return factLegalReleEnum.getMessage();
            }
        }
        return null;
    }
    
    public static String getCode(String message){
        JudgeRandomInquiryEnum[] values = values();
        for (JudgeRandomInquiryEnum factLegalReleEnum : values) {
            if (factLegalReleEnum.getMessage().equals(message)) {
                return factLegalReleEnum.getCode();
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
