package cn.stylefeng.guns.modular.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author jinbo
 * @since 2022-06-16
 */
public class CourtInvestigate implements Serializable {

    /**
     * 原告诉讼请求项
     */
    private String accuserClaimItem;

    /**
     * 原告诉讼请求项的事实与理由
     */
    private String accuserClaimFactReason;

    /**
     * 被告答辩
     */
    private String defendantReply;

    /**
     * 原告举证
     */
    private String accuserEvidence;

    /**
     * 原告举证的事实和理由
     */
    private String accuserEvidenceFactReason;

    /**
     * 被告及其他原告质证
     */
    private String defendantAndOtherAccuserQuery;

    /**
     * 被告举证
     */
    private String defendantEvidence;

    /**
     * 被告是否举证
     */
    private String isDefendantEvidence;

    /**
     * 被告举证的事实和理由
     */
    private String defendantEvidenceFactReason;

    /**
     * 原告及其他被告质证质证
     */
    private String accuserAndOtherDefendantQuery;

    public String getAccuserClaimItem() {
        return accuserClaimItem;
    }

    public void setAccuserClaimItem(String accuserClaimItem) {
        this.accuserClaimItem = accuserClaimItem;
    }

    public String getAccuserClaimFactReason() {
        return accuserClaimFactReason;
    }

    public void setAccuserClaimFactReason(String accuserClaimFactReason) {
        this.accuserClaimFactReason = accuserClaimFactReason;
    }

    public String getDefendantReply() {
        return defendantReply;
    }

    public void setDefendantReply(String defendantReply) {
        this.defendantReply = defendantReply;
    }

    public String getAccuserEvidence() {
        return accuserEvidence;
    }

    public void setAccuserEvidence(String accuserEvidence) {
        this.accuserEvidence = accuserEvidence;
    }

    public String getAccuserEvidenceFactReason() {
        return accuserEvidenceFactReason;
    }

    public void setAccuserEvidenceFactReason(String accuserEvidenceFactReason) {
        this.accuserEvidenceFactReason = accuserEvidenceFactReason;
    }

    public String getDefendantAndOtherAccuserQuery() {
        return defendantAndOtherAccuserQuery;
    }

    public void setDefendantAndOtherAccuserQuery(String defendantAndOtherAccuserQuery) {
        this.defendantAndOtherAccuserQuery = defendantAndOtherAccuserQuery;
    }

    public String getDefendantEvidence() {
        return defendantEvidence;
    }

    public void setDefendantEvidence(String defendantEvidence) {
        this.defendantEvidence = defendantEvidence;
    }

    public String getDefendantEvidenceFactReason() {
        return defendantEvidenceFactReason;
    }

    public void setDefendantEvidenceFactReason(String defendantEvidenceFactReason) {
        this.defendantEvidenceFactReason = defendantEvidenceFactReason;
    }

    public String getAccuserAndOtherDefendantQuery() {
        return accuserAndOtherDefendantQuery;
    }

    public void setAccuserAndOtherDefendantQuery(String accuserAndOtherDefendantQuery) {
        this.accuserAndOtherDefendantQuery = accuserAndOtherDefendantQuery;
    }

    public String getIsDefendantEvidence() {
        return isDefendantEvidence;
    }

    public void setIsDefendantEvidence(String isDefendantEvidence) {
        this.isDefendantEvidence = isDefendantEvidence;
    }

    @Override
    public String toString() {
        return "CourtInvestigate{" +
                "accuserClaimItem='" + accuserClaimItem + '\'' +
                ", accuserClaimFactReason='" + accuserClaimFactReason + '\'' +
                ", defendantReply='" + defendantReply + '\'' +
                ", accuserEvidence='" + accuserEvidence + '\'' +
                ", accuserEvidenceFactReason='" + accuserEvidenceFactReason + '\'' +
                ", defendantAndOtherAccuserQuery='" + defendantAndOtherAccuserQuery + '\'' +
                ", defendantEvidence='" + defendantEvidence + '\'' +
                ", isDefendantEvidence='" + isDefendantEvidence + '\'' +
                ", defendantEvidenceFactReason='" + defendantEvidenceFactReason + '\'' +
                ", accuserAndOtherDefendantQuery='" + accuserAndOtherDefendantQuery + '\'' +
                '}';
    }
}
