package cn.stylefeng.guns.modular.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 法庭辩论表
 * </p>
 *
 * @author jinbo
 * @since 2022-01-19
 */
@TableName("argue")
public class Argue implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "argue_id", type = IdType.ID_WORKER)
    private Long argueId;

    /**
     * 原告简称
     */
    @TableField("accuser")
    private String accuser;

    /**
     * 原告辩论
     */
    @TableField("accuser_argue")
    private String accuserArgue;

    /**
     * 被告简称
     */
    @TableField("defendant")
    private String defendant;

    /**
     * 被告辩论
     */
    @TableField("defendant_argue")
    private String defendantArgue;

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


    public Long getArgueId() {
        return argueId;
    }

    public void setArgueId(Long argueId) {
        this.argueId = argueId;
    }

    public String getAccuser() {
        return accuser;
    }

    public void setAccuser(String accuser) {
        this.accuser = accuser;
    }

    public String getAccuserArgue() {
        return accuserArgue;
    }

    public void setAccuserArgue(String accuserArgue) {
        this.accuserArgue = accuserArgue;
    }

    public String getDefendant() {
        return defendant;
    }

    public void setDefendant(String defendant) {
        this.defendant = defendant;
    }

    public String getDefendantArgue() {
        return defendantArgue;
    }

    public void setDefendantArgue(String defendantArgue) {
        this.defendantArgue = defendantArgue;
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
        return "Argue{" +
                "argueId=" + argueId +
                ", accuser=" + accuser +
                ", accuserArgue=" + accuserArgue +
                ", defendant=" + defendant +
                ", defendantArgue=" + defendantArgue +
                ", isCounterClaim=" + isCounterClaim +
                ", courtNumber=" + courtNumber +
                ", createTime=" + createTime +
                ", createUser=" + createUser +
                ", updateTime=" + updateTime +
                ", updateUser=" + updateUser +
                "}";
    }
}
