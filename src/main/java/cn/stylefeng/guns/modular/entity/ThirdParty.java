package cn.stylefeng.guns.modular.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 第三人信息表
 * </p>
 *
 * @author jinbo
 * @since 2022-07-12
 */
@TableName("third_party")
public class ThirdParty implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "third_id", type = IdType.ID_WORKER)
    private Long thirdId;

    /**
     * 第三人全称
     */
    @TableField("third_party")
    private String thirdParty;

    /**
     * 第三人简称
     */
    @TableField("third_party_short")
    private String thirdPartyShort;

    /**
     * 第三人简称
     */
    @TableField("third_party_info")
    private String thirdPartyInfo;

    /**
     * 第三人类型：1-单位，2-个人
     */
    @TableField("third_party_type")
    private String thirdPartyType;

    /**
     * 第三人地址
     */
    @TableField("third_party_address")
    private String thirdPartyAddress;

    /**
     * 法人代表
     */
    @TableField("third_party_represent")
    private String thirdPartyRepresent;

    /**
     * 法人职务
     */
    @TableField("third_party_duty")
    private String thirdPartyDuty;

    @TableField(exist = false)
    private List<String> thirdPartyAgent;

    /**
     * 案号
     */
    @TableField("court_number")
    private String courtNumber;

    /**
     * 删除标记
     */
    @TableField("del_flag")
    private String delFlag;

    /**
     * 是否听清诉讼权力和义务
     */
    @TableField("third_party_right_duty")
    private String thirdPartyRightDuty;

    /**
     * 对审判员和书记员是否申请回避
     */
    @TableField("third_party_avoid")
    private String thirdPartyAvoid;

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
     * 是否有证据补充
     */
    @TableField(exist = false)
    private String isSupplyEvidence;

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

    public Long getThirdId() {
        return thirdId;
    }

    public void setThirdId(Long thirdId) {
        this.thirdId = thirdId;
    }

    public String getThirdParty() {
        return thirdParty;
    }

    public void setThirdParty(String thirdParty) {
        this.thirdParty = thirdParty;
    }

    public String getThirdPartyShort() {
        return thirdPartyShort;
    }

    public void setThirdPartyShort(String thirdPartyShort) {
        this.thirdPartyShort = thirdPartyShort;
    }

    public String getThirdPartyInfo() {
        return thirdPartyInfo;
    }

    public void setThirdPartyInfo(String thirdPartyInfo) {
        this.thirdPartyInfo = thirdPartyInfo;
    }

    public String getThirdPartyType() {
        return thirdPartyType;
    }

    public void setThirdPartyType(String thirdPartyType) {
        this.thirdPartyType = thirdPartyType;
    }

    public String getThirdPartyAddress() {
        return thirdPartyAddress;
    }

    public void setThirdPartyAddress(String thirdPartyAddress) {
        this.thirdPartyAddress = thirdPartyAddress;
    }

    public String getThirdPartyRepresent() {
        return thirdPartyRepresent;
    }

    public void setThirdPartyRepresent(String thirdPartyRepresent) {
        this.thirdPartyRepresent = thirdPartyRepresent;
    }

    public String getThirdPartyDuty() {
        return thirdPartyDuty;
    }

    public void setThirdPartyDuty(String thirdPartyDuty) {
        this.thirdPartyDuty = thirdPartyDuty;
    }

    public List<String> getThirdPartyAgent() {
        return thirdPartyAgent;
    }

    public void setThirdPartyAgent(List<String> thirdPartyAgent) {
        this.thirdPartyAgent = thirdPartyAgent;
    }

    public String getCourtNumber() {
        return courtNumber;
    }

    public void setCourtNumber(String courtNumber) {
        this.courtNumber = courtNumber;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getThirdPartyRightDuty() {
        return thirdPartyRightDuty;
    }

    public void setThirdPartyRightDuty(String thirdPartyRightDuty) {
        this.thirdPartyRightDuty = thirdPartyRightDuty;
    }

    public String getThirdPartyAvoid() {
        return thirdPartyAvoid;
    }

    public void setThirdPartyAvoid(String thirdPartyAvoid) {
        this.thirdPartyAvoid = thirdPartyAvoid;
    }

    public String getIsMediate() {
        return isMediate;
    }

    public void setIsMediate(String isMediate) {
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

    public String getIsDelivery() {
        return isDelivery;
    }

    public void setIsDelivery(String isDelivery) {
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

    public String getIsSupplyEvidence() {
        return isSupplyEvidence;
    }

    public void setIsSupplyEvidence(String isSupplyEvidence) {
        this.isSupplyEvidence = isSupplyEvidence;
    }

    @Override
    public String toString() {
        return "ThirdParty{" +
                "thirdId=" + thirdId +
                ", thirdParty='" + thirdParty + '\'' +
                ", thirdPartyShort='" + thirdPartyShort + '\'' +
                ", thirdPartyInfo='" + thirdPartyInfo + '\'' +
                ", thirdPartyType='" + thirdPartyType + '\'' +
                ", thirdPartyAddress='" + thirdPartyAddress + '\'' +
                ", thirdPartyRepresent='" + thirdPartyRepresent + '\'' +
                ", thirdPartyDuty='" + thirdPartyDuty + '\'' +
                ", thirdPartyAgent='" + thirdPartyAgent + '\'' +
                ", courtNumber='" + courtNumber + '\'' +
                ", delFlag='" + delFlag + '\'' +
                ", thirdPartyRightDuty='" + thirdPartyRightDuty + '\'' +
                ", thirdPartyAvoid='" + thirdPartyAvoid + '\'' +
                ", isMediate='" + isMediate + '\'' +
                ", mediatePlan='" + mediatePlan + '\'' +
                ", timeLimit='" + timeLimit + '\'' +
                ", isDelivery='" + isDelivery + '\'' +
                ", email='" + email + '\'' +
                ", finalStatement='" + finalStatement + '\'' +
                ", createTime=" + createTime +
                ", createUser=" + createUser +
                ", updateTime=" + updateTime +
                ", updateUser=" + updateUser +
                '}';
    }
}
