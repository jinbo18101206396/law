package cn.stylefeng.guns.modular.model.result;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 被告信息表
 * </p>
 *
 * @author jinbo
 * @since 2022-01-19
 */
@Data
public class DefendantResult implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 主键
     */
    private Long defendantId;

    /**
     * 被告全称
     */
    private String defendant;

    /**
     * 被告简称
     */
    private String defendantShort;

    /**
     * 被告类型：1-单位，2-个人
     */
    private Integer defendantType;

    /**
     * 被告地址
     */
    private String defendantAddress;

    /**
     * 法人代表
     */
    private String defendantRepresent;

    /**
     * 法人职务
     */
    private String defendantDuty;

    /**
     * 案号
     */
    private String courtNumber;

    /**
     * 是否听清诉讼权力和义务
     */
    private String defendantRightDuty;

    /**
     * 对审判员和书记员是否申请回避
     */
    private String defendantAvoid;

    /**
     * 是否能够调解
     */
    private Boolean isMediate;

    /**
     * 调解方案
     */
    private String mediatePlan;

    /**
     * 庭外和解时限
     */
    private String timeLimit;

    /**
     * 是否同意电子裁判文书送达
     */
    private Boolean isDelivery;

    /**
     * 电子邮件地址
     */
    private String email;

    /**
     * 最后陈述意见
     */
    private String finalStatement;

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
