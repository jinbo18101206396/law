package cn.stylefeng.guns.modular.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 证人证言表
 * </p>
 *
 * @author jinbo
 * @since 2022-08-03
 */
@TableName("witness_testimony")
public class WitnessTestimony implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "witness_id", type = IdType.ID_WORKER)
    private Long proofId;

    /**
     * 证据名称
     */
    @TableField("evidence")
    private String evidence;

    /**
     * 证人姓名
     */
    @TableField("name")
    private String name;

    /**
     * 证人类型（证人，鉴定人，勘验人）
     */
    @TableField("type")
    private String type;

    /**
     * 提问者（审判员，原告，被告，第三人）
     */
    @TableField("quizzer")
    private String quizzer;

    /**
     * 问题
     */
    @TableField("question")
    private String question;

    /**
     * 回答者（证人，鉴定人，勘验人，原告，被告，第三人）
     */
    @TableField("responder")
    private String responder;

    /**
     * 答案
     */
    @TableField("answer")
    private String answer;

    /**
     * 删除标记
     */
    @TableField("del_flag")
    private String delFlag;

    /**
     * 答案
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

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getProofId() {
        return proofId;
    }

    public void setProofId(Long proofId) {
        this.proofId = proofId;
    }

    public String getEvidence() {
        return evidence;
    }

    public void setEvidence(String evidence) {
        this.evidence = evidence;
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

    public String getQuizzer() {
        return quizzer;
    }

    public void setQuizzer(String quizzer) {
        this.quizzer = quizzer;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getResponder() {
        return responder;
    }

    public void setResponder(String responder) {
        this.responder = responder;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
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

    public String getCourtNumber() {
        return courtNumber;
    }

    public void setCourtNumber(String courtNumber) {
        this.courtNumber = courtNumber;
    }

    @Override
    public String toString() {
        return "WitnessTestimony{" +
                "proofId=" + proofId +
                ", evidence='" + evidence + '\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", quizzer='" + quizzer + '\'' +
                ", question='" + question + '\'' +
                ", responder='" + responder + '\'' +
                ", answer='" + answer + '\'' +
                ", delFlag='" + delFlag + '\'' +
                ", courtNumber='" + courtNumber + '\'' +
                ", createTime=" + createTime +
                ", createUser=" + createUser +
                ", updateTime=" + updateTime +
                ", updateUser=" + updateUser +
                '}';
    }
}
