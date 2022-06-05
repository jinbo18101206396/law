package cn.stylefeng.guns.modular.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 原告信息表
 * </p>
 *
 * @author jinbo
 * @since 2022-01-19
 */
@TableName("accuser")
public class Accuser implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "accuser_id", type = IdType.ID_WORKER)
    private Long accuserId;

    /**
     * 原告全称
     */
    @TableField("accuser")
    private String accuser;

    /**
     * 原告简称
     */
    @TableField("accuser_short")
    private String accuserShort;

    /**
     * 原告类型：1-单位，2-个人
     */
    @TableField("accuser_type")
    private String accuserType;

    /**
     * 原告地址
     */
    @TableField("accuser_address")
    private String accuserAddress;

    /**
     * 法人代表
     */
    @TableField("accuser_represent")
    private String accuserRepresent;

    /**
     * 法人职务
     */
    @TableField("accuser_duty")
    private String accuserDuty;

    /**
     * 案号
     */
    @TableField("court_number")
    private String courtNumber;

    /**
     * 是否听清诉讼权力和义务
     */
    @TableField("accuser_right_duty")
    private String accuserRightDuty;

    /**
     * 对审判员和书记员是否申请回避
     */
    @TableField("accuser_avoid")
    private String accuserAvoid;

    /**
     * 是否能够调解
     */
    @TableField("is_mediate")
    private String isMediate;
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
    private String isDelivery;
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
     * 删除标记
     */
    @TableField("del_flag")
    private String delFlag;

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

    public String getIsMediate() {
        return isMediate;
    }

    public void setIsMediate(String isMediate) {
        this.isMediate = isMediate;
    }

    public String getIsDelivery() {
        return isDelivery;
    }

    public void setIsDelivery(String isDelivery) {
        this.isDelivery = isDelivery;
    }

    public Long getAccuserId() {
        return accuserId;
    }

    public void setAccuserId(Long accuserId) {
        this.accuserId = accuserId;
    }

    public String getAccuser() {
        return accuser;
    }

    public void setAccuser(String accuser) {
        this.accuser = accuser;
    }

    public String getAccuserShort() {
        return accuserShort;
    }

    public void setAccuserShort(String accuserShort) {
        this.accuserShort = accuserShort;
    }

    public String getAccuserType() {
        return accuserType;
    }

    public void setAccuserType(String accuserType) {
        this.accuserType = accuserType;
    }

    public String getAccuserAddress() {
        return accuserAddress;
    }

    public void setAccuserAddress(String accuserAddress) {
        this.accuserAddress = accuserAddress;
    }

    public String getAccuserRepresent() {
        return accuserRepresent;
    }

    public void setAccuserRepresent(String accuserRepresent) {
        this.accuserRepresent = accuserRepresent;
    }

    public String getAccuserDuty() {
        return accuserDuty;
    }

    public void setAccuserDuty(String accuserDuty) {
        this.accuserDuty = accuserDuty;
    }

    public String getCourtNumber() {
        return courtNumber;
    }

    public void setCourtNumber(String courtNumber) {
        this.courtNumber = courtNumber;
    }

    public String getAccuserRightDuty() {
        return accuserRightDuty;
    }

    public void setAccuserRightDuty(String accuserRightDuty) {
        this.accuserRightDuty = accuserRightDuty;
    }

    public String getAccuserAvoid() {
        return accuserAvoid;
    }

    public void setAccuserAvoid(String accuserAvoid) {
        this.accuserAvoid = accuserAvoid;
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

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    @Override
    public String toString() {
        return "Accuser{" +
                "accuserId=" + accuserId +
                ", accuser=" + accuser +
                ", accuserShort=" + accuserShort +
                ", accuserType=" + accuserType +
                ", accuserAddress=" + accuserAddress +
                ", accuserRepresent=" + accuserRepresent +
                ", accuserDuty=" + accuserDuty +
                ", courtNumber=" + courtNumber +
                ", accuserRightDuty=" + accuserRightDuty +
                ", accuserAvoid=" + accuserAvoid +
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
