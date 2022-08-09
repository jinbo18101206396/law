package cn.stylefeng.guns.modular.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

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
     * 原告诉讼请求项（变更后）
     */
    private String accuserClaimItemAfterChange;

    /**
     * 原告诉讼请求项的事实与理由（变更后）
     */
    private String accuserClaimFactReasonAfterChange;

    /**
     * 被告答辩
     */
    private String defendantReply;

    /**
     * 原告举证（物证）
     */
    private String accuserEvidence;

    /**
     * 原告举证（物证 & 人证）
     */
    private List<Proof> accuserEvidenceList;

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
     * 被告及第三人举证（物证 & 人证）
     */
    private List<Proof> defendantAndThirdEvidenceList;

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

    public String getAccuserClaimItemAfterChange() {
        return accuserClaimItemAfterChange;
    }

    public void setAccuserClaimItemAfterChange(String accuserClaimItemAfterChange) {
        this.accuserClaimItemAfterChange = accuserClaimItemAfterChange;
    }

    public String getAccuserClaimFactReasonAfterChange() {
        return accuserClaimFactReasonAfterChange;
    }

    public void setAccuserClaimFactReasonAfterChange(String accuserClaimFactReasonAfterChange) {
        this.accuserClaimFactReasonAfterChange = accuserClaimFactReasonAfterChange;
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

    public List<Proof> getAccuserEvidenceList() {
        return accuserEvidenceList;
    }

    public void setAccuserEvidenceList(List<Proof> accuserEvidenceList) {
        this.accuserEvidenceList = accuserEvidenceList;
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

    public String getIsDefendantEvidence() {
        return isDefendantEvidence;
    }

    public void setIsDefendantEvidence(String isDefendantEvidence) {
        this.isDefendantEvidence = isDefendantEvidence;
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

    public List<Proof> getDefendantAndThirdEvidenceList() {
        return defendantAndThirdEvidenceList;
    }

    public void setDefendantAndThirdEvidenceList(List<Proof> defendantAndThirdEvidenceList) {
        this.defendantAndThirdEvidenceList = defendantAndThirdEvidenceList;
    }

    @Override
    public String toString() {
        return "CourtInvestigate{" +
                "accuserClaimItem='" + accuserClaimItem + '\'' +
                ", accuserClaimFactReason='" + accuserClaimFactReason + '\'' +
                ", accuserClaimItemAfterChange='" + accuserClaimItemAfterChange + '\'' +
                ", accuserClaimFactReasonAfterChange='" + accuserClaimFactReasonAfterChange + '\'' +
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
