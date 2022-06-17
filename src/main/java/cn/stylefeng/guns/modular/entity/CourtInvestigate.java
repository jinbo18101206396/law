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
     * 被告质证
     */
    private String defendantQuery;


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

    public String getDefendantQuery() {
        return defendantQuery;
    }

    public void setDefendantQuery(String defendantQuery) {
        this.defendantQuery = defendantQuery;
    }

    @Override
    public String toString() {
        return "CourtInvestigate{" +
                "accuserClaimItem='" + accuserClaimItem + '\'' +
                ", accuserClaimFactReason='" + accuserClaimFactReason + '\'' +
                ", defendantReply='" + defendantReply + '\'' +
                ", accuserEvidence='" + accuserEvidence + '\'' +
                ", accuserEvidenceFactReason='" + accuserEvidenceFactReason + '\'' +
                ", defendantQuery='" + defendantQuery + '\'' +
                '}';
    }
}
