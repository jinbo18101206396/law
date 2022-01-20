package cn.stylefeng.guns.modular.entity;

import com.baomidou.mybatisplus.annotation.*;

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
@TableName("defendant")
public class Defendant implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "defendant_id", type = IdType.ID_WORKER)
    private Long defendantId;

    /**
     * 被告全称
     */
    @TableField("defendant")
    private String defendant;

    /**
     * 被告简称
     */
    @TableField("defendant_short")
    private String defendantShort;

    /**
     * 被告类型：1-单位，2-个人
     */
    @TableField("defendant_type")
    private Integer defendantType;

    /**
     * 被告地址
     */
    @TableField("defendant_address")
    private String defendantAddress;

    /**
     * 法人代表
     */
    @TableField("defendant_represent")
    private String defendantRepresent;

    /**
     * 法人职务
     */
    @TableField("defendant_duty")
    private String defendantDuty;

    /**
     * 案号
     */
    @TableField("court_number")
    private String courtNumber;

    /**
     * 是否听清诉讼权力和义务
     */
    @TableField("defendant_right_duty")
    private String defendantRightDuty;

    /**
     * 对审判员和书记员是否申请回避
     */
    @TableField("defendant_avoid")
    private String defendantAvoid;

    /**
     * 是否能够调解
     */
    @TableField("is_mediate")
    private Boolean isMediate;

    /**
     * 调解方案
     */
    @TableField("mediate_plan")
    private String mediatePlan;

    /**
     * 庭外和解时限
     */
    @TableField("time_limit")
    private String timeLimit;

    /**
     * 是否同意电子裁判文书送达
     */
    @TableField("is_delivery")
    private Boolean isDelivery;

    /**
     * 电子邮件地址
     */
    @TableField("email")
    private String email;

    /**
     * 最后陈述意见
     */
    @TableField("final_statement")
    private String finalStatement;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 创建人
     */
    @TableField(value = "create_user", fill = FieldFill.INSERT)
    private Long createUser;

    /**
     * 更新时间
     */
    @TableField(value = "update_time", fill = FieldFill.UPDATE)
    private Date updateTime;

    /**
     * 更新人
     */
    @TableField(value = "update_user", fill = FieldFill.UPDATE)
    private Long updateUser;


    public Long getDefendantId() {
        return defendantId;
    }

    public void setDefendantId(Long defendantId) {
        this.defendantId = defendantId;
    }

    public String getDefendant() {
        return defendant;
    }

    public void setDefendant(String defendant) {
        this.defendant = defendant;
    }

    public String getDefendantShort() {
        return defendantShort;
    }

    public void setDefendantShort(String defendantShort) {
        this.defendantShort = defendantShort;
    }

    public Integer getDefendantType() {
        return defendantType;
    }

    public void setDefendantType(Integer defendantType) {
        this.defendantType = defendantType;
    }

    public String getDefendantAddress() {
        return defendantAddress;
    }

    public void setDefendantAddress(String defendantAddress) {
        this.defendantAddress = defendantAddress;
    }

    public String getDefendantRepresent() {
        return defendantRepresent;
    }

    public void setDefendantRepresent(String defendantRepresent) {
        this.defendantRepresent = defendantRepresent;
    }

    public String getDefendantDuty() {
        return defendantDuty;
    }

    public void setDefendantDuty(String defendantDuty) {
        this.defendantDuty = defendantDuty;
    }

    public String getCourtNumber() {
        return courtNumber;
    }

    public void setCourtNumber(String courtNumber) {
        this.courtNumber = courtNumber;
    }

    public String getDefendantRightDuty() {
        return defendantRightDuty;
    }

    public void setDefendantRightDuty(String defendantRightDuty) {
        this.defendantRightDuty = defendantRightDuty;
    }

    public String getDefendantAvoid() {
        return defendantAvoid;
    }

    public void setDefendantAvoid(String defendantAvoid) {
        this.defendantAvoid = defendantAvoid;
    }

    public Boolean getMediate() {
        return isMediate;
    }

    public void setMediate(Boolean isMediate) {
        this.isMediate = isMediate;
    }

    public String getMediatePlan() {
        return mediatePlan;
    }

    public void setMediatePlan(String mediatePlan) {
        this.mediatePlan = mediatePlan;
    }

    public String getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(String timeLimit) {
        this.timeLimit = timeLimit;
    }

    public Boolean getDelivery() {
        return isDelivery;
    }

    public void setDelivery(Boolean isDelivery) {
        this.isDelivery = isDelivery;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFinalStatement() {
        return finalStatement;
    }

    public void setFinalStatement(String finalStatement) {
        this.finalStatement = finalStatement;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Long getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(Long updateUser) {
        this.updateUser = updateUser;
    }

    @Override
    public String toString() {
        return "Defendant{" +
                "defendantId=" + defendantId +
                ", defendant=" + defendant +
                ", defendantShort=" + defendantShort +
                ", defendantType=" + defendantType +
                ", defendantAddress=" + defendantAddress +
                ", defendantRepresent=" + defendantRepresent +
                ", defendantDuty=" + defendantDuty +
                ", courtNumber=" + courtNumber +
                ", defendantRightDuty=" + defendantRightDuty +
                ", defendantAvoid=" + defendantAvoid +
                ", isMediate=" + isMediate +
                ", mediatePlan=" + mediatePlan +
                ", timeLimit=" + timeLimit +
                ", isDelivery=" + isDelivery +
                ", email=" + email +
                ", finalStatement=" + finalStatement +
                ", createTime=" + createTime +
                ", createUser=" + createUser +
                ", updateTime=" + updateTime +
                ", updateUser=" + updateUser +
                "}";
    }
}
