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
     * 辩论人的姓名
     */
    @TableField("name")
    private String name;

    /**
     * 辩论人的类型（原告，被告，反诉原告，反诉被告）
     */
    @TableField("type")
    private String type;

    /**
     * 辩论意见
     */
    @TableField("argue_content")
    private String argueContent;

    /**
     * 是否反诉(1-反诉，2-不反诉)
     */
    @TableField("is_counter_claim")
    private String isCounterClaim;

    /**
     * 案号
     */
    @TableField("court_number")
    private String courtNumber;

    /**
     * 原告辩论
     */
    @TableField(exist = false)
    private String accuserArgue;

    /**
     * 被告辩论
     */
    @TableField(exist = false)
    private String defendantArgue;

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

    public Long getArgueId() {
        return argueId;
    }

    public void setArgueId(Long argueId) {
        this.argueId = argueId;
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

    public String getArgueContent() {
        return argueContent;
    }

    public void setArgueContent(String argueContent) {
        this.argueContent = argueContent;
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

    public String getAccuserArgue() {
        return accuserArgue;
    }

    public void setAccuserArgue(String accuserArgue) {
        this.accuserArgue = accuserArgue;
    }

    public String getDefendantArgue() {
        return defendantArgue;
    }

    public void setDefendantArgue(String defendantArgue) {
        this.defendantArgue = defendantArgue;
    }

    @Override
    public String toString() {
        return "Argue{" +
                "argueId=" + argueId +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", argueContent='" + argueContent + '\'' +
                ", isCounterClaim=" + isCounterClaim +
                ", courtNumber='" + courtNumber + '\'' +
                ", createTime=" + createTime +
                ", createUser=" + createUser +
                ", updateTime=" + updateTime +
                ", updateUser=" + updateUser +
                '}';
    }
}
