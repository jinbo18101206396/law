package cn.stylefeng.guns.modular.service.Impl;

import cn.stylefeng.guns.modular.entity.Proof;
import cn.stylefeng.guns.modular.entity.WitnessTestimony;
import cn.stylefeng.guns.modular.mapper.ProofMapper;
import cn.stylefeng.guns.modular.service.AccuserService;
import cn.stylefeng.guns.modular.service.ProofService;
import cn.stylefeng.guns.modular.service.WitnessTestimonyService;
import cn.stylefeng.roses.kernel.rule.enums.YesOrNotEnum;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;

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
    @Resource
    private WitnessTestimonyService witnessTestimonyService;

    /**
     * 原告举证
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
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
        if (recordJsonObject.containsKey("courtInvestigate")) {
            JSONObject courtInvestigateObject = recordJsonObject.getJSONObject("courtInvestigate");
            if (courtInvestigateObject.containsKey("accuser_evidence") && courtInvestigateObject.containsKey("accuser_is_witness") && courtInvestigateObject.containsKey("accuser_evidence_witness")) {
                String accuserIsWitness = courtInvestigateObject.getString("accuser_is_witness");
                //物证
                List<Proof> proofs = getProofs(courtNumber);
                if(proofs != null && proofs.size() > 0){
                    proofService.delete(courtNumber);
                }
                JSONArray accuserEvidenceArray = courtInvestigateObject.getJSONArray("accuser_evidence");
                saveProof(courtNumber,accuserIsWitness, counterClaim, "原告", accuserEvidenceArray);
                //人证
                List<WitnessTestimony> witnessProofs = getWitnessProofs(courtNumber);
                if(witnessProofs != null && witnessProofs.size() > 0){
                    witnessTestimonyService.delete(courtNumber);
                }
                JSONArray accuserWitnessEvidenceArray = courtInvestigateObject.getJSONArray("accuser_evidence_witness");
                saveWitnessProof(courtNumber,accuserIsWitness,"原告","原告",accuserWitnessEvidenceArray);
            }
        }
    }

    /**
     * 被告及第三人举证
     */
    @Override
    public void saveDefendantEvidence(String courtNumber, String counterClaim, JSONObject recordJsonObject) {
        if (recordJsonObject.containsKey("courtInvestigate")) {
            JSONObject courtInvestigateObject = recordJsonObject.getJSONObject("courtInvestigate");
            if (courtInvestigateObject.containsKey("defendant_and_third_evidence")) {
                String defendantIsWitness = courtInvestigateObject.getString("defendant_is_witness");
                //物证
                JSONArray defendantAndThirdEvidenceArray = courtInvestigateObject.getJSONArray("defendant_and_third_evidence");
                saveProof(courtNumber,defendantIsWitness, counterClaim, "被告及第三人", defendantAndThirdEvidenceArray);
                //人证
                JSONArray defendantAndThirdEvidenceWitnessArray = courtInvestigateObject.getJSONArray("defendant_and_third_evidence_witness");
                saveWitnessProof(courtNumber,defendantIsWitness,"被告及第三人","被告及第三人",defendantAndThirdEvidenceWitnessArray);
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
                saveProof(courtNumber,"", counterClaim, "反诉原告", counterClaimAccuserEvidenceArray);
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
                saveProof(courtNumber, "",counterClaim, "反诉被告", counterClaimDefendantEvidenceArray);
            }
        }
    }

    /**
     * 举证-物证
     */
    public void saveProof(String courtNumber,String witness, String counterClaim, String type, JSONArray evidenceArray) {
        for (int i = 0; i < evidenceArray.size(); i++) {
            JSONObject evidenceObject = evidenceArray.getJSONObject(i);
            String name = "";
            if (evidenceObject.containsKey("name")) {
                name = evidenceObject.getString("name");
            }
            String serial = evidenceObject.getString("serial");
            String evidence = evidenceObject.getString("evidence");
            String evidenceType = evidenceObject.getString("evidence_type");
            String content = evidenceObject.getString("content");
            if (!ObjectUtils.isEmpty(evidence) && !ObjectUtils.isEmpty(evidenceType) && !ObjectUtils.isEmpty(content)) {
                Proof proof = new Proof();
                proof.setType(type);
                proof.setName(name);
                proof.setSerial(serial);
                proof.setEvidence(evidence);
                proof.setEvidenceType(evidenceType);
                proof.setContent(content);
                proof.setIsWitness(witness);
                proof.setIsCounterClaim(counterClaim);
                proof.setCourtNumber(courtNumber);
                this.save(proof);
            }
        }
    }

    /**
     * 举证-人证
     * 将人证中的"证据编号"、"证据类型"、"证据名称"、"证明事项"存入Proof表中
     * 将人证中的"证据名称"、"证人姓名"、"证人类型"、"提问者"、"问题"、"回答者"、"答案"存入WitnessTestimony表中
     * Proof和WitnessTestimony表中的人证部分通过"证据名称"进行关联
     */
    public void saveWitnessProof(String courtNumber,String witness,String name,String type, JSONArray witnessArray) {
        for (int i = 0; i < witnessArray.size(); i++) {
            JSONObject witnessObject = witnessArray.getJSONObject(i);
            String serial = witnessObject.getString("serial");
            String evidenceType = witnessObject.getString("evidence_type");
            String evidence = witnessObject.getString("evidence");
            String content = witnessObject.getString("content");
            String witnessName = witnessObject.getString("witness_name");
            String witnessType = witnessObject.getString("witness_type");

            Proof proof = new Proof();
            //举证人的名称
            proof.setName(name);
            //举证人的类型（原告，被告，第三人）
            proof.setType(type);
            proof.setSerial(serial);
            proof.setEvidenceType(evidenceType);
            proof.setEvidence(evidence);
            proof.setContent(content);
            proof.setIsWitness(witness);
            proofService.save(proof);

            JSONArray witnessTestimonyArray = witnessObject.getJSONArray("witness_testimony");
            for(int j=0;j<witnessTestimonyArray.size();j++){
                JSONObject witnessTestimonyObject = witnessTestimonyArray.getJSONObject(j);
                String quizzer = witnessTestimonyObject.getString("quizzer");
                String question = witnessTestimonyObject.getString("question");
                String responder = witnessTestimonyObject.getString("responder");
                String answer = witnessTestimonyObject.getString("answer");

                WitnessTestimony witnessTestimony = new WitnessTestimony();
                witnessTestimony.setCourtNumber(courtNumber);
                witnessTestimony.setEvidence(evidence);
                witnessTestimony.setName(witnessName);
                witnessTestimony.setType(witnessType);
                witnessTestimony.setQuizzer(quizzer);
                witnessTestimony.setQuestion(question);
                witnessTestimony.setResponder(responder);
                witnessTestimony.setAnswer(answer);
                witnessTestimonyService.saveOrUpdate(witnessTestimony);
            }
        }
    }

    public List<Proof> getProofs(String courtNumber) {
        LambdaQueryWrapper<Proof> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Proof::getCourtNumber, courtNumber);
        lambdaQueryWrapper.eq(Proof::getDelFlag, YesOrNotEnum.N.getCode());
        return proofService.list(lambdaQueryWrapper);
    }

    public List<WitnessTestimony> getWitnessProofs(String courtNumber) {
        LambdaQueryWrapper<WitnessTestimony> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(WitnessTestimony::getCourtNumber, courtNumber);
        lambdaQueryWrapper.eq(WitnessTestimony::getDelFlag, YesOrNotEnum.N.getCode());
        return witnessTestimonyService.list(lambdaQueryWrapper);
    }

    @Override
    public Boolean deleteProofInfo(String courtNumber) {
        LambdaUpdateWrapper<Proof> proofWrapper = new LambdaUpdateWrapper<>();
        proofWrapper.set(Proof::getDelFlag, YesOrNotEnum.Y.getCode()).eq(Proof::getCourtNumber, courtNumber);
        return proofService.update(proofWrapper);
    }

    @Override
    public void delete(String courtNumber) {
        LambdaQueryWrapper<Proof> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Proof::getCourtNumber, courtNumber);
        lambdaQueryWrapper.eq(Proof::getDelFlag, YesOrNotEnum.N.getCode());
        baseMapper.delete(lambdaQueryWrapper);
    }
}
