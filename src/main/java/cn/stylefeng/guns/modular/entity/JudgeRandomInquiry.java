package cn.stylefeng.guns.modular.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 法官随机提问表
 * </p>
 *
 * @author jinbo
 * @since 2022-07-13
 */
@TableName("judge_random_inquiry")
public class JudgeRandomInquiry implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "judge_inquiry_id", type = IdType.ID_WORKER)
    private Long judgeInquiryId;

    /**
     * 问题
     */
    @TableField("question")
    private String question;

    /**
     * 回答人的名称
     */
    @TableField("name")
    private String name;

    /**
     * 答案
     */
    @TableField("answer")
    private String answer;

    /**
     * 问题所属模块（1-原告诉称后，2-被告答辩后，3-审判员最终陈述前）
     */
    @TableField("type")
    private String type;

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

    public Long getJudgeInquiryId() {
        return judgeInquiryId;
    }

    public void setJudgeInquiryId(Long judgeInquiryId) {
        this.judgeInquiryId = judgeInquiryId;
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

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
        return "JudgeRandomInquiry{" +
                "judgeInquiryId=" + judgeInquiryId +
                ", question='" + question + '\'' +
                ", name='" + name + '\'' +
                ", answer='" + answer + '\'' +
                ", type='" + type + '\'' +
                ", courtNumber='" + courtNumber + '\'' +
                ", delFlag='" + delFlag + '\'' +
                ", createTime=" + createTime +
                ", createUser=" + createUser +
                ", updateTime=" + updateTime +
                ", updateUser=" + updateUser +
                '}';
    }
}
