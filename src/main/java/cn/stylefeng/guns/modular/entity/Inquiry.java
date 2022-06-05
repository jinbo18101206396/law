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
     * 回答人的姓名
     */
    @TableField("name")
    private String name;

    /**
     * 回答人的类型（原告、被告、反诉原告、反诉被告）
     */
    @TableField("type")
    private String type;

    /**
     * 答案
     */
    @TableField("answer")
    private String answer;

    /**
     * 是否反诉
     */
    @TableField("is_counter_claim")
    private String isCounterClaim;

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


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getIsCounterClaim() {
        return isCounterClaim;
    }

    public void setIsCounterClaim(String isCounterClaim) {
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

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    @Override
    public String toString() {
        return "Inquiry{" +
                "inquiryId=" + inquiryId +
                ", question='" + question + '\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", answer='" + answer + '\'' +
                ", isCounterClaim=" + isCounterClaim +
                ", courtNumber='" + courtNumber + '\'' +
                ", createTime=" + createTime +
                ", createUser=" + createUser +
                ", updateTime=" + updateTime +
                ", updateUser=" + updateUser +
                '}';
    }
}
