package cn.stylefeng.guns.modular.model.result;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 基本信息表
 * </p>
 *
 * @author jinbo
 * @since 2022-01-19
 */
@Data
public class BasicInfoResult implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 主键
     */
    private Long basicId;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 立案时间
     */
    private Date filingTime;

    /**
     * 开庭时间
     */
    private Date courtTime;

    /**
     * 开庭地点
     */
    private String courtPlace;

    /**
     * 审判长
     */
    private String chiefJudge;

    /**
     * 审判员
     */
    private String judge;

    /**
     * 陪审员
     */
    private String juror;

    /**
     * 人民陪审员
     */
    private String peopleJuror;

    /**
     * 书记员
     */
    private String courtClerk;

    /**
     * 案号
     */
    private String courtNumber;

    /**
     * 案由
     */
    private String courtCause;

    /**
     * 状态：1-在审，2-已结案
     */
    private Integer status;

    /**
     * 删除标记
     */
    private String delFlag;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建人
     */
    private Long createUser;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 更新人
     */
    private Long updateUser;

}
