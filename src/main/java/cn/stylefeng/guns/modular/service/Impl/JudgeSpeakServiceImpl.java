package cn.stylefeng.guns.modular.service.Impl;

import cn.stylefeng.guns.modular.entity.JudgeSpeak;
import cn.stylefeng.guns.modular.mapper.JudgeSpeakMapper;
import cn.stylefeng.guns.modular.service.JudgeSpeakService;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 审判员说话内容 服务实现类
 * </p>
 *
 * @author jinbo
 * @since 2022-07-11
 */
@Service
public class JudgeSpeakServiceImpl extends ServiceImpl<JudgeSpeakMapper, JudgeSpeak> implements JudgeSpeakService {

    @Resource
    private JudgeSpeakService judgeSpeakService;

    @Override
    public Map<String, Object> getJudgeSpeaks(String courtNumber, Map<String, Object> recordMap){
        List<JudgeSpeak> judgeSpeakList = getJudgeList(courtNumber);
        if (judgeSpeakList != null && judgeSpeakList.size() > 0) {
            for (int i = 0; i < judgeSpeakList.size(); i++) {
                JudgeSpeak judgeSpeak = judgeSpeakList.get(i);
                String module = judgeSpeak.getModule();
                String content = judgeSpeak.getContent();
                if (!ObjectUtils.isEmpty(module) && !ObjectUtils.isEmpty(content)) {
                    recordMap.put(module, content);
                }
            }
        }
        return supplyBlankSpeak(recordMap);
    }

    @Override
    public JSONObject getJudgeSpeaks(String courtNumber, JSONObject recordJson) {
        List<JudgeSpeak> judgeSpeakList = getJudgeList(courtNumber);
        if (judgeSpeakList != null || judgeSpeakList.size() > 0) {
            for (int i = 0; i < judgeSpeakList.size(); i++) {
                JudgeSpeak judgeSpeak = judgeSpeakList.get(i);
                String module = judgeSpeak.getModule();
                String content = judgeSpeak.getContent();
                if (!ObjectUtils.isEmpty(module) && !ObjectUtils.isEmpty(content)) {
                    recordJson.put(module, content);
                }
            }
        }
        return supplyJudgeSpeak(recordJson);
    }

    public List<JudgeSpeak> getJudgeList(String courtNumber){
        LambdaQueryWrapper<JudgeSpeak> judgeSpeakWrapper = new LambdaQueryWrapper<>();
        judgeSpeakWrapper.eq(JudgeSpeak::getCourtNumber, courtNumber);
        return judgeSpeakService.list(judgeSpeakWrapper);
    }

    public Map<String, Object> supplyBlankSpeak(Map<String, Object> recordMap){
        if(!recordMap.containsKey("judge_right_duty")){
            recordMap.put("judge_right_duty", "");
        }
        if(!recordMap.containsKey("judge_avoid")){
            recordMap.put("judge_avoid", "");
        }
        if(!recordMap.containsKey("judge_accuser_claim_item")){
            recordMap.put("judge_accuser_claim_item", "");
        }
        if(!recordMap.containsKey("judge_defendant_reply")){
            recordMap.put("judge_defendant_reply", "");
        }
        if(!recordMap.containsKey("judge_accuser_evidence")){
            recordMap.put("judge_accuser_evidence", "");
        }
        if(!recordMap.containsKey("judge_defendant_and_other_accuser_query")){
            recordMap.put("judge_defendant_and_other_accuser_query", "");
        }
        if(!recordMap.containsKey("judge_defendant_evidence")){
            recordMap.put("judge_defendant_evidence", "");
        }
        if(!recordMap.containsKey("judge_accuser_and_other_defendant_query")){
            recordMap.put("judge_accuser_and_other_defendant_query", "");
        }
        if(!recordMap.containsKey("judge_inquiry")){
            recordMap.put("judge_inquiry", "");
        }
        if(!recordMap.containsKey("judge_argue")){
            recordMap.put("judge_argue", "");
        }
        if(!recordMap.containsKey("judge_finalstatement")){
            recordMap.put("judge_finalstatement", "");
        }
        if(!recordMap.containsKey("judge_mediate")){
            recordMap.put("judge_mediate", "当事人是否能够调解。");
        }
        if(!recordMap.containsKey("judge_delivery")){
            recordMap.put("judge_delivery", "当事人是否同意电子送达裁判文书。");
        }
        return  recordMap;
    }

    public JSONObject supplyJudgeSpeak(JSONObject recordJson){
        if(!recordJson.containsKey("judge_right_duty")){
            recordJson.put("judge_right_duty", "审判员：依据《中华人民共和国民事诉讼法》的规定，当事人在法庭上享有下列权利：1.原告有权承认、变更、放弃自己的诉讼请求，被告有权反驳原告的诉讼请求或提起反诉；2.当事人有权申请回避；3.当事人有权举证；4.当事人有权辩论、有权请求法庭调解,当事人在享有上述权利的同时，负有以下义务：1.当事人有依法行使诉讼权利的义务；2.当事人有听从法庭指挥、遵守法庭纪律的义务；3.当事人有如实陈述事实、如实举证的义务。上述诉讼权利和义务双方是否听清？");
        }
        if(!recordJson.containsKey("judge_avoid")){
            recordJson.put("judge_avoid", "审判员：当事人对审判员和书记是否申请回避？");
        }
        if(!recordJson.containsKey("judge_accuser_claim_item")){
            recordJson.put("judge_accuser_claim_item", "下面进行法庭调查，原告陈述诉讼请求和事实理由。");
        }
        if(!recordJson.containsKey("judge_defendant_reply")){
            recordJson.put("judge_defendant_reply", "对于原告的诉讼请求及事实理由，被告进行答辩。");
        }
        if(!recordJson.containsKey("judge_accuser_evidence")){
            recordJson.put("judge_accuser_evidence", "下面进行举证质证，首先原告进行举证。");
        }
        if(!recordJson.containsKey("judge_defendant_and_other_accuser_query")){
            recordJson.put("judge_defendant_and_other_accuser_query", "被告及其他当事人对原告提交的证据进行质证。");
        }
        if(!recordJson.containsKey("judge_defendant_evidence")){
            recordJson.put("judge_defendant_evidence", "被告进行举证。");
        }
        if(!recordJson.containsKey("judge_accuser_and_other_defendant_query")){
            recordJson.put("judge_accuser_and_other_defendant_query", "原告及其他当事人对被告提交的证据进行质证。");
        }
        if(!recordJson.containsKey("judge_inquiry")){
            recordJson.put("judge_inquiry", "举证质证结束，下面进入法庭询问。");
        }
        if(!recordJson.containsKey("judge_argue")){
            recordJson.put("judge_argue", "法庭询问结束，下面进行法庭辩论，首先原告发表辩论意见。");
        }
        if(!recordJson.containsKey("judge_finalstatement")){
            recordJson.put("judge_finalstatement", "双方发表最后陈述意见。");
        }
        if(!recordJson.containsKey("judge_mediate")){
            recordJson.put("judge_mediate", "当事人是否能够调解。");
        }
        if(!recordJson.containsKey("judge_delivery")){
            recordJson.put("judge_delivery", "当事人是否同意电子送达裁判文书。");
        }
        return  recordJson;
    }

    @Override
    public Boolean saveJudgeSpeaks(String courtNumber, String courtCause, JSONObject recordJsonObject) {
        List<JudgeSpeak> judgeSpeakList = new ArrayList<>();
        if (recordJsonObject.containsKey("judge_right_duty")) {
            String judgeRightDuty = recordJsonObject.getString("judge_right_duty");
            judgeSpeakList.add(judgeSpeak(courtNumber, courtCause, "judge_right_duty", judgeRightDuty));
        }
        if (recordJsonObject.containsKey("judge_avoid")) {
            String judgeAvoid = recordJsonObject.getString("judge_avoid");
            judgeSpeakList.add(judgeSpeak(courtNumber, courtCause, "judge_avoid", judgeAvoid));
        }
        if (recordJsonObject.containsKey("judge_accuser_claim_item")) {
            String judgeAccuserClaimItem = recordJsonObject.getString("judge_accuser_claim_item");
            judgeSpeakList.add(judgeSpeak(courtNumber, courtCause, "judge_accuser_claim_item", judgeAccuserClaimItem));
        }
        if (recordJsonObject.containsKey("judge_defendant_reply")) {
            String judgeDefendantReply = recordJsonObject.getString("judge_defendant_reply");
            judgeSpeakList.add(judgeSpeak(courtNumber, courtCause, "judge_defendant_reply", judgeDefendantReply));
        }
        if (recordJsonObject.containsKey("judge_accuser_evidence")) {
            String judgeAccuserEvidence = recordJsonObject.getString("judge_accuser_evidence");
            judgeSpeakList.add(judgeSpeak(courtNumber, courtCause, "judge_accuser_evidence", judgeAccuserEvidence));
        }
        if (recordJsonObject.containsKey("judge_defendant_and_other_accuser_query")) {
            String judgeDefendantAndOtherAccuserQuery = recordJsonObject.getString("judge_defendant_and_other_accuser_query");
            judgeSpeakList.add(judgeSpeak(courtNumber, courtCause, "judge_defendant_and_other_accuser_query", judgeDefendantAndOtherAccuserQuery));
        }
        if (recordJsonObject.containsKey("judge_defendant_evidence")) {
            String judgeDefendantEvidence = recordJsonObject.getString("judge_defendant_evidence");
            judgeSpeakList.add(judgeSpeak(courtNumber, courtCause, "judge_defendant_evidence", judgeDefendantEvidence));
        }
        if (recordJsonObject.containsKey("judge_accuser_and_other_defendant_query")) {
            String judgeAccuserAndOtherDefendantQuery = recordJsonObject.getString("judge_accuser_and_other_defendant_query");
            judgeSpeakList.add(judgeSpeak(courtNumber, courtCause, "judge_accuser_and_other_defendant_query", judgeAccuserAndOtherDefendantQuery));
        }
        if (recordJsonObject.containsKey("judge_inquiry")) {
            String judgeInquiry = recordJsonObject.getString("judge_inquiry");
            judgeSpeakList.add(judgeSpeak(courtNumber, courtCause, "judge_inquiry", judgeInquiry));
        }
        if (recordJsonObject.containsKey("judge_argue")) {
            String judgeArgue = recordJsonObject.getString("judge_argue");
            judgeSpeakList.add(judgeSpeak(courtNumber, courtCause, "judge_argue", judgeArgue));
        }
        if (recordJsonObject.containsKey("judge_finalstatement")) {
            String judgeFinalstatement = recordJsonObject.getString("judge_finalstatement");
            judgeSpeakList.add(judgeSpeak(courtNumber, courtCause, "judge_finalstatement", judgeFinalstatement));
        }
        if (recordJsonObject.containsKey("judge_mediate")) {
            String judgeMediate = recordJsonObject.getString("judge_mediate");
            judgeSpeakList.add(judgeSpeak(courtNumber, courtCause, "judge_mediate", judgeMediate));
        }
        if (recordJsonObject.containsKey("judge_delivery")) {
            String judgeDelivery = recordJsonObject.getString("judge_delivery");
            judgeSpeakList.add(judgeSpeak(courtNumber, courtCause, "judge_delivery", judgeDelivery));
        }
        if (judgeSpeakList != null && judgeSpeakList.size() > 0) {
            List<JudgeSpeak> judgeSpeaks = getJudgeSpeaks(courtNumber);
            if(judgeSpeaks != null && judgeSpeaks.size() > 0){
                judgeSpeakService.delete(courtNumber);
            }
            return judgeSpeakService.saveOrUpdateBatch(judgeSpeakList);
        }
        return null;
    }

    public List<JudgeSpeak> getJudgeSpeaks(String courtNumber) {
        LambdaQueryWrapper<JudgeSpeak> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(JudgeSpeak::getCourtNumber, courtNumber);
        return judgeSpeakService.list(lambdaQueryWrapper);
    }

    @Override
    public void delete(String courtNumber) {
        LambdaQueryWrapper<JudgeSpeak> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(JudgeSpeak::getCourtNumber, courtNumber);
        baseMapper.delete(lambdaQueryWrapper);
    }

    public JudgeSpeak judgeSpeak(String courtNumber, String courtCause, String module, String content) {
        JudgeSpeak judgeSpeak = new JudgeSpeak();
        judgeSpeak.setCourtNumber(courtNumber);
        judgeSpeak.setCourtCause(courtCause);
        judgeSpeak.setModule(module);
        judgeSpeak.setContent(content);
        return judgeSpeak;
    }
}
