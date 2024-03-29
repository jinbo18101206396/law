package cn.stylefeng.guns.modular.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 诉称表
 * </p>
 *
 * @author jinbo
 * @since 2022-01-19
 */
@TableName("allege")
public class Allege implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "allege_id", type = IdType.ID_WORKER)
    private Long allegeId;

    /**
     * 原告或反诉原告简称
     */
    @TableField("name")
    private String name;

    /**
     * 诉讼人的类型（原告、反诉原告）
     */
    @TableField("type")
    private String type;

    /**
     * 诉讼请求项
     */
    @TableField("claim_item")
    private String claimItem;

    /**
     * 事实与理由
     */
    @TableField("fact_reason")
    private String factReason;

    /**
     * 是否变更诉讼请求
     */
    @TableField("is_change_claim_item")
    private String isChangeClaimItem;

    /**
     * 变更后的诉讼请求项
     */
    @TableField("claim_item_after_change")
    private String claimItemAfterChange;

    /**
     * 变更后的事实与理由
     */
    @TableField("fact_reason_after_change")
    private String factReasonAfterChange;

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

    public Long getAllegeId() {
        return allegeId;
    }

    public void setAllegeId(Long allegeId) {
        this.allegeId = allegeId;
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

    public String getClaimItem() {
        return claimItem;
    }

    public void setClaimItem(String claimItem) {
        this.claimItem = claimItem;
    }

    public String getFactReason() {
        return factReason;
    }

    public void setFactReason(String factReason) {
        this.factReason = factReason;
    }

    public String getIsChangeClaimItem() {
        return isChangeClaimItem;
    }

    public void setIsChangeClaimItem(String isChangeClaimItem) {
        this.isChangeClaimItem = isChangeClaimItem;
    }

    public String getClaimItemAfterChange() {
        return claimItemAfterChange;
    }

    public void setClaimItemAfterChange(String claimItemAfterChange) {
        this.claimItemAfterChange = claimItemAfterChange;
    }

    public String getFactReasonAfterChange() {
        return factReasonAfterChange;
    }

    public void setFactReasonAfterChange(String factReasonAfterChange) {
        this.factReasonAfterChange = factReasonAfterChange;
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

    @Override
    public String toString() {
        return "Allege{" +
                "allegeId=" + allegeId +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", claimItem='" + claimItem + '\'' +
                ", factReason='" + factReason + '\'' +
                ", isChangeClaimItem='" + isChangeClaimItem + '\'' +
                ", claimItemAfterChange='" + claimItemAfterChange + '\'' +
                ", factReasonAfterChange='" + factReasonAfterChange + '\'' +
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
