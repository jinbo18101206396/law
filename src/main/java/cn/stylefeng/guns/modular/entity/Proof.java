package cn.stylefeng.guns.modular.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

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
     * 举证人的简称
     */
    @TableField("name")
    private String name;

    /**
     * 举证人的类型（原告/被告/反诉原告/反诉被告）
     */
    @TableField("type")
    private String type;

    /**
     * 证据编号
     */
    @TableField("serial")
    private String serial;

    /**
     * 证据编号和名称
     */
    @TableField(exist = false)
    private String serialAndName;

    /**
     * 证据名称
     */
    @TableField("evidence")
    private String evidence;

    /**
     * 证据类型
     */
    @TableField("evidence_type")
    private String evidenceType;

    /**
     * 证明事项
     */
    @TableField("content")
    private String content;

    /**
     * 事实理由
     */
    @TableField("fact_reason")
    private String factReason;

    /**
     * 是否有人证
     */
    @TableField("is_witness")
    private String isWitness;

    /**
     * 人证问答组
     */
    @TableField(exist = false)
    private List<WitnessTestimony> witnessProofs;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getEvidence() {
        return evidence;
    }

    public void setEvidence(String evidence) {
        this.evidence = evidence;
    }

    public String getEvidenceType() {
        return evidenceType;
    }

    public void setEvidenceType(String evidenceType) {
        this.evidenceType = evidenceType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFactReason() {
        return factReason;
    }

    public void setFactReason(String factReason) {
        this.factReason = factReason;
    }

    public String getIsWitness() {
        return isWitness;
    }

    public void setIsWitness(String isWitness) {
        this.isWitness = isWitness;
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

    public List<WitnessTestimony> getWitnessProofs() {
        return witnessProofs;
    }

    public void setWitnessProofs(List<WitnessTestimony> witnessProofs) {
        this.witnessProofs = witnessProofs;
    }

    public String getSerialAndName() {
        return serialAndName;
    }

    public void setSerialAndName(String serialAndName) {
        this.serialAndName = serialAndName;
    }

    @Override
    public String toString() {
        return "Proof{" +
                "proofId=" + proofId +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", serial='" + serial + '\'' +
                ", evidence='" + evidence + '\'' +
                ", evidenceType='" + evidenceType + '\'' +
                ", content='" + content + '\'' +
                ", factReason='" + factReason + '\'' +
                ", isWitness='" + isWitness + '\'' +
                ", isCounterClaim='" + isCounterClaim + '\'' +
                ", courtNumber='" + courtNumber + '\'' +
                ", delFlag='" + delFlag + '\'' +
                ", createTime=" + createTime +
                ", createUser=" + createUser +
                ", updateTime=" + updateTime +
                ", updateUser=" + updateUser +
                '}';
    }
}
