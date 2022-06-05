package cn.stylefeng.guns.modular.service.Impl;

import cn.stylefeng.guns.modular.entity.Accuser;
import cn.stylefeng.guns.modular.entity.Proof;
import cn.stylefeng.guns.modular.entity.Query;
import cn.stylefeng.guns.modular.mapper.ProofMapper;
import cn.stylefeng.guns.modular.service.AccuserService;
import cn.stylefeng.guns.modular.service.ProofService;
import cn.stylefeng.roses.kernel.rule.enums.YesOrNotEnum;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
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
        //拼接所有原告的姓名
        JSONArray accuserInfoArray = recordJsonObject.getJSONArray("accuserInfo");
        StringBuffer accuserName = new StringBuffer();
        if(!ObjectUtils.isEmpty(accuserInfoArray)){
            for (int i = 0; i < accuserInfoArray.size(); i++) {
                JSONObject accuserInfoObject = accuserInfoArray.getJSONObject(i);
                String accuserShort = accuserInfoObject.get("accuser_short").toString();
                accuserName.append(accuserShort);
            }
        }
        //法庭调查
        if(recordJsonObject.containsKey("courtInvestigate")){
            JSONObject courtInvestigateObject = recordJsonObject.getJSONObject("courtInvestigate");
            //原告举证
            if(courtInvestigateObject.containsKey("accuser_evidence")){
                JSONArray accuserEvidenceArray = courtInvestigateObject.getJSONArray("accuser_evidence");
                for (int i = 0; i < accuserEvidenceArray.size(); i++) {
                    JSONObject accuserEvidenceObject = accuserEvidenceArray.getJSONObject(i);
                    Proof proof = new Proof();
                    proof.setName(accuserName.toString());
                    proof.setType("原告");
                    proof.setSerial(accuserEvidenceObject.get("serial").toString());
                    proof.setEvidence(accuserEvidenceObject.get("evidence").toString());
                    proof.setEvidenceType(accuserEvidenceObject.get("evidence_type").toString());
                    proof.setContent(accuserEvidenceObject.get("content").toString());
                    proof.setIsCounterClaim(counterClaim);
                    proof.setCourtNumber(courtNumber);
                    this.save(proof);
                }
            }
        }
    }

    /**
     * 被告举证
     */
    @Override
    public void saveDefendantEvidence(String courtNumber, String counterClaim, JSONObject recordJsonObject) {
        if(recordJsonObject.containsKey("courtInvestigate")){
            JSONObject courtInvestigateObject = recordJsonObject.getJSONObject("courtInvestigate");
            //被告举证
            if(courtInvestigateObject.containsKey("defendant_evidence")){
                JSONArray defendantEvidenceArray = courtInvestigateObject.getJSONArray("defendant_evidence");
                for (int i = 0; i < defendantEvidenceArray.size(); i++) {
                    JSONObject defendantEvidenceObject = defendantEvidenceArray.getJSONObject(i);
                    Proof proof = new Proof();
                    proof.setType("被告");
                    proof.setSerial(defendantEvidenceObject.get("serial").toString());
                    proof.setEvidence(defendantEvidenceObject.get("evidence").toString());
                    proof.setEvidenceType(defendantEvidenceObject.get("evidence_type").toString());
                    proof.setContent(defendantEvidenceObject.get("content").toString());
                    proof.setIsCounterClaim(counterClaim);
                    proof.setCourtNumber(courtNumber);
                    this.save(proof);
                }
            }
        }
    }

    /**
     * 反诉原告举证
     */
    @Override
    public void saveCounterClaimAccuserEvidence(String courtNumber, String counterClaim, JSONObject recordJsonObject) {
        if(recordJsonObject.containsKey("courtInvestigate")){
            JSONObject courtInvestigateObject = recordJsonObject.getJSONObject("courtInvestigate");
            //反诉原告举证
            if(courtInvestigateObject.containsKey("counterclaim_accuser_evidence")){
                JSONArray counterClaimAccuserEvidenceArray = courtInvestigateObject.getJSONArray("counterclaim_accuser_evidence");
                for (int i = 0; i < counterClaimAccuserEvidenceArray.size(); i++) {
                    JSONObject counterClaimAccuserEvidenceObject = counterClaimAccuserEvidenceArray.getJSONObject(i);
                    Proof proof = new Proof();
                    proof.setName("反诉原告");
                    proof.setType("反诉原告");
                    proof.setSerial(counterClaimAccuserEvidenceObject.get("serial").toString());
                    proof.setEvidence(counterClaimAccuserEvidenceObject.get("evidence").toString());
                    proof.setEvidenceType(counterClaimAccuserEvidenceObject.get("evidence_type").toString());
                    proof.setContent(counterClaimAccuserEvidenceObject.get("content").toString());
                    proof.setIsCounterClaim(counterClaim);
                    proof.setCourtNumber(courtNumber);
                    this.save(proof);
                }
            }
        }
    }

    /**
     * 反诉被告举证
     */
    @Override
    public void saveCounterClaimDefendantEvidence(String courtNumber, String counterClaim, JSONObject recordJsonObject) {
        if(recordJsonObject.containsKey("courtInvestigate")){
            JSONObject courtInvestigateObject = recordJsonObject.getJSONObject("courtInvestigate");
            //反诉被告举证
            if(courtInvestigateObject.containsKey("counterclaim_defendant_evidence")){
                JSONArray counterClaimDefendantEvidenceArray = courtInvestigateObject.getJSONArray("counterclaim_defendant_evidence");
                for (int i = 0; i < counterClaimDefendantEvidenceArray.size(); i++) {
                    JSONObject counterClaimDefendantEvidenceObject = counterClaimDefendantEvidenceArray.getJSONObject(i);
                    Proof proof = new Proof();
                    proof.setType("反诉被告");
                    proof.setSerial(counterClaimDefendantEvidenceObject.get("serial").toString());
                    proof.setEvidence(counterClaimDefendantEvidenceObject.get("evidence").toString());
                    proof.setEvidenceType(counterClaimDefendantEvidenceObject.get("evidence_type").toString());
                    proof.setContent(counterClaimDefendantEvidenceObject.get("content").toString());
                    proof.setIsCounterClaim(counterClaim);
                    proof.setCourtNumber(courtNumber);
                    this.save(proof);
                }
            }
        }
    }

    @Override
    public Boolean deleteProofInfo(String courtNumber) {
        LambdaUpdateWrapper<Proof> proofWrapper = new LambdaUpdateWrapper<>();
        proofWrapper.set(Proof::getDelFlag, YesOrNotEnum.Y.getCode()).eq(Proof::getCourtNumber,courtNumber);
        return proofService.update(proofWrapper);
    }

}
