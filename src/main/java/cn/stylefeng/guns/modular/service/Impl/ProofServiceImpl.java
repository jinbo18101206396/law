package cn.stylefeng.guns.modular.service.Impl;

import cn.stylefeng.guns.modular.entity.Proof;
import cn.stylefeng.guns.modular.mapper.ProofMapper;
import cn.stylefeng.guns.modular.service.ProofService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 举证表 服务实现类
 * </p>
 *
 * @author jinbo
 * @since 2022-01-19
 */
@Service
public class ProofServiceImpl extends ServiceImpl<ProofMapper, Proof> implements ProofService {


    @Override
    public void saveAccuserEvidence(String courtNumber, String counterClaim, JSONObject recordJsonObject) {
        //法庭调查
        JSONObject courtInvestigateObject = recordJsonObject.getJSONObject("courtInvestigate");
        JSONArray accuserInfoArray = recordJsonObject.getJSONArray("accuserInfo");

        //拼接所有原告的姓名
        StringBuffer accuserName = new StringBuffer();
        for (int i = 0; i < accuserInfoArray.size(); i++) {
            JSONObject accuserInfoObject = accuserInfoArray.getJSONObject(i);
            String accuserShort = accuserInfoObject.get("accuser_short").toString();
            accuserName.append(accuserShort);
        }
        //原告举证
        JSONArray accuserEvidenceArray = courtInvestigateObject.getJSONArray("accuser_evidence");
        String accuserEvidenceFactReason = courtInvestigateObject.get("accuser_evidence_fact_reason").toString();

        for (int i = 0; i < accuserEvidenceArray.size(); i++) {
            JSONObject accuserEvidenceObject = accuserEvidenceArray.getJSONObject(i);
            String evidence = accuserEvidenceObject.get("evidence").toString();
            String content = accuserEvidenceObject.get("content").toString();

            Proof proof = new Proof();
            proof.setName(accuserName.toString());
            proof.setType("原告");
            proof.setEvidence(evidence);
            proof.setContent(content);
            proof.setFactReason(accuserEvidenceFactReason);
            proof.setIsCounterClaim(counterClaim);
            proof.setCourtNumber(courtNumber);
            this.save(proof);
        }
    }

    @Override
    public void saveDefendantEvidence(String courtNumber, String counterClaim, JSONObject recordJsonObject) {
        JSONObject courtInvestigateObject = recordJsonObject.getJSONObject("courtInvestigate");
        //被告举证
        JSONArray defendantEvidenceArray = courtInvestigateObject.getJSONArray("defendant_evidence");
        String defendantEvidenceFactReason = courtInvestigateObject.get("defendant_evidence_fact_reason").toString();

        for (int i = 0; i < defendantEvidenceArray.size(); i++) {
            JSONObject defendantEvidenceObject = defendantEvidenceArray.getJSONObject(i);
            String evidence = defendantEvidenceObject.get("evidence").toString();
            String content = defendantEvidenceObject.get("content").toString();

            Proof proof = new Proof();
            proof.setName("");
            proof.setType("被告");
            proof.setEvidence(evidence);
            proof.setContent(content);
            proof.setFactReason(defendantEvidenceFactReason);
            proof.setIsCounterClaim(counterClaim);
            proof.setCourtNumber(courtNumber);
            this.save(proof);
        }
    }

    @Override
    public void saveCounterClaimAccuserEvidence(String courtNumber, String counterClaim, JSONObject recordJsonObject) {
        JSONObject courtInvestigateObject = recordJsonObject.getJSONObject("courtInvestigate");
        //反诉原告举证
        JSONArray counterClaimAccuserEvidenceArray = courtInvestigateObject.getJSONArray("counterclaim_accuser_evidence");
        String counterClaimAccuserEvidenceFactReason = courtInvestigateObject.get("counterclaim_accuser_evidence_fact_reason").toString();

        for (int i = 0; i < counterClaimAccuserEvidenceArray.size(); i++) {
            JSONObject counterClaimAccuserEvidenceObject = counterClaimAccuserEvidenceArray.getJSONObject(i);
            String evidence = counterClaimAccuserEvidenceObject.get("evidence").toString();
            String content = counterClaimAccuserEvidenceObject.get("content").toString();

            Proof proof = new Proof();
            proof.setName("反诉原告");
            proof.setType("反诉原告");
            proof.setEvidence(evidence);
            proof.setContent(content);
            proof.setFactReason(counterClaimAccuserEvidenceFactReason);
            proof.setIsCounterClaim(counterClaim);
            proof.setCourtNumber(courtNumber);
            this.save(proof);
        }
    }


    @Override
    public void saveCounterClaimDefendantEvidence(String courtNumber, String counterClaim, JSONObject recordJsonObject) {
        JSONObject courtInvestigateObject = recordJsonObject.getJSONObject("courtInvestigate");
        //反诉被告举证
        JSONArray counterClaimDefendantEvidenceArray = courtInvestigateObject.getJSONArray("counterclaim_defendant_evidence");
        String counterClaimDefendantEvidenceFactReason = courtInvestigateObject.get("counterclaim_defendant_evidence_fact_reason").toString();

        for (int i = 0; i < counterClaimDefendantEvidenceArray.size(); i++) {
            JSONObject counterClaimDefendantEvidenceObject = counterClaimDefendantEvidenceArray.getJSONObject(i);
            String evidence = counterClaimDefendantEvidenceObject.get("evidence").toString();
            String content = counterClaimDefendantEvidenceObject.get("content").toString();

            Proof proof = new Proof();
            proof.setName("");
            proof.setType("反诉被告");
            proof.setEvidence(evidence);
            proof.setContent(content);
            proof.setFactReason(counterClaimDefendantEvidenceFactReason);
            proof.setIsCounterClaim(counterClaim);
            proof.setCourtNumber(courtNumber);
            this.save(proof);
        }
    }
}
