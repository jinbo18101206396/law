package cn.stylefeng.guns.modular.service.Impl;

import cn.stylefeng.guns.modular.entity.Proof;
import cn.stylefeng.guns.modular.mapper.ProofMapper;
import cn.stylefeng.guns.modular.service.AccuserService;
import cn.stylefeng.guns.modular.service.ProofService;
import cn.stylefeng.roses.kernel.rule.enums.YesOrNotEnum;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;

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

    @Resource
    private AccuserService accuserService;
    @Resource
    private ProofService proofService;

    /**
     * 原告举证
     */
    @Override
    public void saveAccuserEvidence(String courtNumber, String counterClaim, JSONObject recordJsonObject) {
        //拼接所有原告的姓名(原告是一个整体)
        JSONArray accuserInfoArray = recordJsonObject.getJSONArray("accuserInfo");
        StringBuffer accuserName = new StringBuffer();
        if (!ObjectUtils.isEmpty(accuserInfoArray)) {
            for (int i = 0; i < accuserInfoArray.size(); i++) {
                JSONObject accuserInfoObject = accuserInfoArray.getJSONObject(i);
                String accuserShort = accuserInfoObject.getString("accuser_short");
                accuserName.append(accuserShort);
            }
        }
        //法庭调查
        if (recordJsonObject.containsKey("courtInvestigate")) {
            JSONObject courtInvestigateObject = recordJsonObject.getJSONObject("courtInvestigate");
            //原告举证
            if (courtInvestigateObject.containsKey("accuser_evidence")) {
                JSONArray accuserEvidenceArray = courtInvestigateObject.getJSONArray("accuser_evidence");
                saveProof(courtNumber, counterClaim, "原告", accuserEvidenceArray);
            }
        }
    }

    /**
     * 被告举证
     */
    @Override
    public void saveDefendantEvidence(String courtNumber, String counterClaim, JSONObject recordJsonObject) {
        if (recordJsonObject.containsKey("courtInvestigate")) {
            JSONObject courtInvestigateObject = recordJsonObject.getJSONObject("courtInvestigate");
            //被告举证
            if (courtInvestigateObject.containsKey("defendant_evidence")) {
                JSONArray defendantEvidenceArray = courtInvestigateObject.getJSONArray("defendant_evidence");
                saveProof(courtNumber, counterClaim, "被告", defendantEvidenceArray);
            }
        }
    }

    public void saveProof(String courtNumber, String counterClaim, String type, JSONArray evidenceArray) {
        for (int i = 0; i < evidenceArray.size(); i++) {
            JSONObject evidenceObject = evidenceArray.getJSONObject(i);
            String name = "";
            if(evidenceObject.containsKey("name")){
                name = evidenceObject.getString("name");
            }
            String serial = evidenceObject.getString("serial");
            String evidence = evidenceObject.getString("evidence");
            String evidenceType = evidenceObject.getString("evidence_type");
            String content = evidenceObject.getString("content");
            if (!ObjectUtils.isEmpty(evidence) && !ObjectUtils.isEmpty(evidenceType)) {
                Proof proof = new Proof();
                proof.setType(type);
                proof.setName(name);
                proof.setSerial(serial);
                proof.setEvidence(evidence);
                proof.setEvidenceType(evidenceType);
                proof.setContent(content);
                proof.setIsCounterClaim(counterClaim);
                proof.setCourtNumber(courtNumber);
                this.save(proof);
            }
        }
    }

    /**
     * 反诉原告举证
     */
    @Override
    public void saveCounterClaimAccuserEvidence(String courtNumber, String counterClaim, JSONObject recordJsonObject) {
        if (recordJsonObject.containsKey("courtInvestigate")) {
            JSONObject courtInvestigateObject = recordJsonObject.getJSONObject("courtInvestigate");
            //反诉原告举证
            if (courtInvestigateObject.containsKey("counterclaim_accuser_evidence")) {
                JSONArray counterClaimAccuserEvidenceArray = courtInvestigateObject.getJSONArray("counterclaim_accuser_evidence");
                for (int i = 0; i < counterClaimAccuserEvidenceArray.size(); i++) {
                    JSONObject counterClaimAccuserEvidenceObject = counterClaimAccuserEvidenceArray.getJSONObject(i);
                    String serial = counterClaimAccuserEvidenceObject.getString("serial");
                    String evidence = counterClaimAccuserEvidenceObject.getString("evidence");
                    String evidenceType = counterClaimAccuserEvidenceObject.getString("evidence_type");
                    String content = counterClaimAccuserEvidenceObject.getString("content");
                    if (!ObjectUtils.isEmpty(evidence) && !ObjectUtils.isEmpty(evidenceType)) {
                        Proof proof = new Proof();
                        proof.setName("反诉原告");
                        proof.setType("反诉原告");
                        proof.setSerial(serial);
                        proof.setEvidence(evidence);
                        proof.setEvidenceType(evidenceType);
                        proof.setContent(content);
                        proof.setIsCounterClaim(counterClaim);
                        proof.setCourtNumber(courtNumber);
                        this.save(proof);
                    }
                }
            }
        }
    }

    /**
     * 反诉被告举证
     */
    @Override
    public void saveCounterClaimDefendantEvidence(String courtNumber, String counterClaim, JSONObject recordJsonObject) {
        if (recordJsonObject.containsKey("courtInvestigate")) {
            JSONObject courtInvestigateObject = recordJsonObject.getJSONObject("courtInvestigate");
            //反诉被告举证
            if (courtInvestigateObject.containsKey("counterclaim_defendant_evidence")) {
                JSONArray counterClaimDefendantEvidenceArray = courtInvestigateObject.getJSONArray("counterclaim_defendant_evidence");
                for (int i = 0; i < counterClaimDefendantEvidenceArray.size(); i++) {
                    JSONObject counterClaimDefendantEvidenceObject = counterClaimDefendantEvidenceArray.getJSONObject(i);
                    String serial = counterClaimDefendantEvidenceObject.getString("serial");
                    String evidence = counterClaimDefendantEvidenceObject.getString("evidence");
                    String evidenceType = counterClaimDefendantEvidenceObject.getString("evidence_type");
                    String content = counterClaimDefendantEvidenceObject.getString("content");
                    if (!ObjectUtils.isEmpty(evidence) && !ObjectUtils.isEmpty(evidenceType)) {
                        Proof proof = new Proof();
                        proof.setType("反诉被告");
                        proof.setSerial(serial);
                        proof.setEvidence(evidence);
                        proof.setEvidenceType(evidenceType);
                        proof.setContent(content);
                        proof.setIsCounterClaim(counterClaim);
                        proof.setCourtNumber(courtNumber);
                        this.save(proof);
                    }
                }
            }
        }
    }

    @Override
    public Boolean deleteProofInfo(String courtNumber) {
        LambdaUpdateWrapper<Proof> proofWrapper = new LambdaUpdateWrapper<>();
        proofWrapper.set(Proof::getDelFlag, YesOrNotEnum.Y.getCode()).eq(Proof::getCourtNumber, courtNumber);
        return proofService.update(proofWrapper);
    }

}
