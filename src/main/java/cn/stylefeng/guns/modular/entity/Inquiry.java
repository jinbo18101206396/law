package cn.stylefeng.guns.modular.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 法庭询问表
 * </p>
 *
 * @author jinbo
 * @since 2022-01-19
 */
@TableName("inquiry")
public class Inquiry implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "inquiry_id", type = IdType.ID_WORKER)
    private Long inquiryId;

    /**
     * 问题
     */
    @TableField("question")
    private String question;

    /**
     * 原告简称
     */
    @TableField("accuser")
    private String accuser;

    /**
     * 原告回答
     */
    @TableField("accuser_answer")
    private String accuserAnswer;

    /**
     * 被告简称
     */
    @TableField("defendant")
    private String defendant;

    /**
     * 被告回答
     */
    @TableField("defendant_answer")
    private String defendantAnswer;

    /**
     * 是否反诉
     */
    @TableField("is_counter_claim")
    private Boolean isCounterClaim;

    /**
     * 案号
     */
    @TableField("court_number")
    private String courtNumber;

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


    public Long getInquiryId() {
        return inquiryId;
    }

    public void setInquiryId(Long inquiryId) {
        this.inquiryId = inquiryId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAccuser() {
        return accuser;
    }

    public void setAccuser(String accuser) {
        this.accuser = accuser;
    }

    public String getAccuserAnswer() {
        return accuserAnswer;
    }

    public void setAccuserAnswer(String accuserAnswer) {
        this.accuserAnswer = accuserAnswer;
    }

    public String getDefendant() {
        return defendant;
    }

    public void setDefendant(String defendant) {
        this.defendant = defendant;
    }

    public String getDefendantAnswer() {
        return defendantAnswer;
    }

    public void setDefendantAnswer(String defendantAnswer) {
        this.defendantAnswer = defendantAnswer;
    }

    public Boolean getCounterClaim() {
        return isCounterClaim;
    }

    public void setCounterClaim(Boolean isCounterClaim) {
        this.isCounterClaim = isCounterClaim;
    }

    public String getCourtNumber() {
        return courtNumber;
    }

    public void setCourtNumber(String courtNumber) {
        this.courtNumber = courtNumber;
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
        return "Inquiry{" +
                "inquiryId=" + inquiryId +
                ", question=" + question +
                ", accuser=" + accuser +
                ", accuserAnswer=" + accuserAnswer +
                ", defendant=" + defendant +
                ", defendantAnswer=" + defendantAnswer +
                ", isCounterClaim=" + isCounterClaim +
                ", courtNumber=" + courtNumber +
                ", createTime=" + createTime +
                ", createUser=" + createUser +
                ", updateTime=" + updateTime +
                ", updateUser=" + updateUser +
                "}";
    }
}
