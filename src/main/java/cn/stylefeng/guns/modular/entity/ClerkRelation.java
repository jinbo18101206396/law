package cn.stylefeng.guns.modular.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 书记员、审判员、开庭地点对应关系表
 * </p>
 *
 * @author jinbo
 * @since 2022-05-31
 */
@TableName("clerk_relation")
public class ClerkRelation implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "relation_id", type = IdType.ID_WORKER)
    private Long relationId;

    /**
     * 书记员
     */
    @TableField("court_clerk")
    private String courtClerk;

    /**
     * 审判员
     */
    @TableField("judge")
    private String judge;

    /**
     * 开庭地点
     */
    @TableField("court_place")
    private String courtPlace;

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

    public Long getRelationId() {
        return relationId;
    }

    public void setRelationId(Long relationId) {
        this.relationId = relationId;
    }

    public String getCourtClerk() {
        return courtClerk;
    }

    public void setCourtClerk(String courtClerk) {
        this.courtClerk = courtClerk;
    }

    public String getJudge() {
        return judge;
    }

    public void setJudge(String judge) {
        this.judge = judge;
    }

    public String getCourtPlace() {
        return courtPlace;
    }

    public void setCourtPlace(String courtPlace) {
        this.courtPlace = courtPlace;
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
        return "ClerkRelation{" +
                "relationId=" + relationId +
                ", courtClerk='" + courtClerk + '\'' +
                ", judge='" + judge + '\'' +
                ", courtPlace='" + courtPlace + '\'' +
                ", createTime=" + createTime +
                ", createUser=" + createUser +
                ", updateTime=" + updateTime +
                ", updateUser=" + updateUser +
                '}';
    }
}
