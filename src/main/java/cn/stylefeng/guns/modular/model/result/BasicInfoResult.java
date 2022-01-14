package cn.stylefeng.guns.modular.model.result;

import lombok.Data;
import java.util.Date;
import java.io.Serializable;

/**
 * <p>
 * 基本信息表
 * </p>
 *
 * @author 金波
 * @date 2022/01/14 15:07
 */
@Data
public class BasicInfoResult implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 主键
     */
    private Long basicId;

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
     * 案件状态
     */
    private int status;

    /**
     * 删除标记：Y-已删除，N-未删除
     */
    private String delFlag;

    /**
     * 创建人
     */
    private Long createUser;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新人
     */
    private Long updateUser;

    /**
     * 更新时间
     */
    private Date updateTime;

}
