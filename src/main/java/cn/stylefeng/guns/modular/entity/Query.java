package cn.stylefeng.guns.modular.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 质证表
 * </p>
 *
 * @author jinbo
 * @since 2022-01-19
 */
@TableName("query")
public class Query implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "query_id", type = IdType.ID_WORKER)
    private Long queryId;

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
     * 真实性
     */
    @TableField("facticity")
    private Boolean facticity;

    /**
     * 合法性
     */
    @TableField("legality")
    private Boolean legality;

    /**
     * 关联性
     */
    @TableField("relevance")
    private Boolean relevance;

    /**
     * 理由
     */
    @TableField("reason")
    private String reason;

    /**
     * 是否反诉
     */
    @TableField("is_counter_claim")
    private String isCounterClaim;

    /**
     * 质证类型：1-被告质证，2-原告及其他被告质证
     */
    @TableField("query_type")
    private Integer queryType;

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


    public Long getQueryId() {
        return queryId;
    }

    public void setQueryId(Long queryId) {
        this.queryId = queryId;
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

    public Boolean getFacticity() {
        return facticity;
    }

    public void setFacticity(Boolean facticity) {
        this.facticity = facticity;
    }

    public Boolean getLegality() {
        return legality;
    }

    public void setLegality(Boolean legality) {
        this.legality = legality;
    }

    public Boolean getRelevance() {
        return relevance;
    }

    public void setRelevance(Boolean relevance) {
        this.relevance = relevance;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getIsCounterClaim() {
        return isCounterClaim;
    }

    public void setIsCounterClaim(String isCounterClaim) {
        this.isCounterClaim = isCounterClaim;
    }

    public Integer getQueryType() {
        return queryType;
    }

    public void setQueryType(Integer queryType) {
        this.queryType = queryType;
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
        return "Query{" +
                "queryId=" + queryId +
                ", name=" + name +
                ", evidence=" + evidence +
                ", facticity=" + facticity +
                ", legality=" + legality +
                ", relevance=" + relevance +
                ", reason=" + reason +
                ", isCounterClaim=" + isCounterClaim +
                ", queryType=" + queryType +
                ", courtNumber=" + courtNumber +
                ", createTime=" + createTime +
                ", createUser=" + createUser +
                ", updateTime=" + updateTime +
                ", updateUser=" + updateUser +
                "}";
    }
}
