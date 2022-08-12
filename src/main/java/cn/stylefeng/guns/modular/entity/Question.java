package cn.stylefeng.guns.modular.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 问题表
 * </p>
 *
 * @author jinbo
 * @since 2022-05-31
 */
@TableName("question")
public class Question implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "question_id", type = IdType.ID_WORKER)
    private Long questionId;

    /**
     * 序号
     */
    @TableField("serial")
    private String serial;

    /**
     * 问题
     */
    @TableField("question")
    private String question;

    /**
     * 案件类型（1-买卖合同类）
     */
    @TableField("type")
    private String type;

    /**
     * 所属模块（inquiry-法庭询问，witness-证人证言）
     */
    @TableField("module")
    private String module;

    /**
     * 用户id
     */
    @TableField("user_id")
    private Long userId;

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

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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
        return "Question{" +
                "questionId=" + questionId +
                ", serial='" + serial + '\'' +
                ", question='" + question + '\'' +
                ", type='" + type + '\'' +
                ", module='" + module + '\'' +
                ", userId=" + userId +
                ", createTime=" + createTime +
                ", createUser=" + createUser +
                ", updateTime=" + updateTime +
                ", updateUser=" + updateUser +
                '}';
    }
}
