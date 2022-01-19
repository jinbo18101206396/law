package cn.stylefeng.guns.modular.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

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

    private static final long serialVersionUID=1L;

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
        return "Allege{" +
        "allegeId=" + allegeId +
        ", name=" + name +
        ", claimItem=" + claimItem +
        ", factReason=" + factReason +
        ", isCounterClaim=" + isCounterClaim +
        ", courtNumber=" + courtNumber +
        ", createTime=" + createTime +
        ", createUser=" + createUser +
        ", updateTime=" + updateTime +
        ", updateUser=" + updateUser +
        "}";
    }
}
