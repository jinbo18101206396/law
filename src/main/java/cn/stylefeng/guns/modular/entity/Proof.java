package cn.stylefeng.guns.modular.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 举证表
 * </p>
 *
 * @author jinbo
 * @since 2022-01-19
 */
@TableName("proof")
public class Proof implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "proof_id", type = IdType.ID_WORKER)
    private Long proofId;

    /**
     * 原告/被告/反诉原告/反诉被告简称
     */
    @TableField("name")
    private String name;

    /**
     * 证据名称
     */
    @TableField("evidence")
    private String evidence;

    /**
     * 证明事项
     */
    @TableField("content")
    private String content;

    /**
     * 是否反诉
     */
    @TableField("is_counter_claim")
    private Boolean isCounterClaim;

    /**
     * 举证类型：1-原告，2-被告
     */
    @TableField("proof_type")
    private Integer proofType;

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


    public Long getProofId() {
        return proofId;
    }

    public void setProofId(Long proofId) {
        this.proofId = proofId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEvidence() {
        return evidence;
    }

    public void setEvidence(String evidence) {
        this.evidence = evidence;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean getCounterClaim() {
        return isCounterClaim;
    }

    public void setCounterClaim(Boolean isCounterClaim) {
        this.isCounterClaim = isCounterClaim;
    }

    public Integer getProofType() {
        return proofType;
    }

    public void setProofType(Integer proofType) {
        this.proofType = proofType;
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
        return "Proof{" +
                "proofId=" + proofId +
                ", name=" + name +
                ", evidence=" + evidence +
                ", content=" + content +
                ", isCounterClaim=" + isCounterClaim +
                ", proofType=" + proofType +
                ", courtNumber=" + courtNumber +
                ", createTime=" + createTime +
                ", createUser=" + createUser +
                ", updateTime=" + updateTime +
                ", updateUser=" + updateUser +
                "}";
    }
}
