package cn.stylefeng.guns.modular.service.Impl;

import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.modular.entity.*;
import cn.stylefeng.guns.modular.mapper.BasicInfoMapper;
import cn.stylefeng.guns.modular.model.request.BasicInfoRequest;
import cn.stylefeng.guns.modular.service.*;
import cn.stylefeng.roses.kernel.auth.api.context.LoginContext;
import cn.stylefeng.roses.kernel.db.api.factory.PageFactory;
import cn.stylefeng.roses.kernel.db.api.factory.PageResultFactory;
import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.rule.enums.YesOrNotEnum;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 基本信息服务实现类
 *
 * @author 金波
 * @date 2022/01/14 15:07
 */

@Slf4j
@Service
public class BasicInfoServiceImpl extends ServiceImpl<BasicInfoMapper, BasicInfo> implements BasicInfoService {

    @Resource
    private BasicInfoService basicInfoService;
    @Resource
    private AccuserService accuserService;
    @Resource
    private DefendantService defendantService;
    @Resource
    private ProofService proofService;
    @Resource
    private QueryService queryService;
    @Resource
    private ReplyService replyService;
    @Resource
    private AllegeService allegeService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveBasicInfo(String courtNumber, JSONObject recordJsonObject) {
        BasicInfo basicInfo = new BasicInfo();

        //当前用户
        Long userId = LoginContext.me().getLoginUser().getUserId();
        basicInfo.setUserId(userId);

        //基本信息
        JSONObject basicInfoObject = JSONObject.parseObject(recordJsonObject.getString("basicInfo"));
        //立案时间
        if(basicInfoObject.containsKey("filing_time")){
            basicInfo.setFilingTime(basicInfoObject.get("filing_time").toString());
        }
        //开庭时间
        if(basicInfoObject.containsKey("court_time")){
            basicInfo.setCourtTime(basicInfoObject.get("court_time").toString());
        }
        //开庭地点
        if(basicInfoObject.containsKey("court_place")){
            basicInfo.setCourtPlace(basicInfoObject.get("court_place").toString());
        }
        //审判长（可多位，用逗号分隔）
        if(basicInfoObject.containsKey("chief_judge")){
            String chiefJudge = "";
            JSONArray chiefJudgeArray = basicInfoObject.getJSONArray("chief_judge");
            for (int i = 0; i < chiefJudgeArray.size(); i++) {
                chiefJudge += chiefJudgeArray.getJSONObject(i).getString("name") + ",";
            }
            basicInfo.setChiefJudge(chiefJudge.substring(0, chiefJudge.length() - 1));
        }
        //审判员（可多位，用逗号分隔）
        if(basicInfoObject.containsKey("judge")){
            String judge = "";
            JSONArray judgeArray = basicInfoObject.getJSONArray("judge");
            for (int i = 0; i < judgeArray.size(); i++) {
                judge += judgeArray.getJSONObject(i).getString("name") + ",";
            }
            basicInfo.setJudge(judge.substring(0, judge.length() - 1));
        }
        //陪审员（可多位，用逗号分隔）
        if(basicInfoObject.containsKey("juror")){
            String juror = "";
            JSONArray jurorArray = basicInfoObject.getJSONArray("juror");
            for (int i = 0; i < jurorArray.size(); i++) {
                juror += jurorArray.getJSONObject(i).getString("name") + ",";
            }
            basicInfo.setJuror(juror.substring(0, juror.length() - 1));
        }
        //人民陪审员（可多位，用逗号分隔）
        if(basicInfoObject.containsKey("people_juror")){
            String peopleJuror = "";
            JSONArray peopleJurorArray = basicInfoObject.getJSONArray("people_juror");
            for (int i = 0; i < peopleJurorArray.size(); i++) {
                peopleJuror += peopleJurorArray.getJSONObject(i).getString("name") + ",";
            }
            basicInfo.setPeopleJuror(peopleJuror.substring(0, peopleJuror.length() - 1));
        }
        //书记员
        if(basicInfoObject.containsKey("court_clerk")){
            basicInfo.setCourtClerk(basicInfoObject.get("court_clerk").toString());
        }
        //案由
        if(basicInfoObject.containsKey("court_cause")){
            basicInfo.setCourtCause(basicInfoObject.get("court_cause").toString());
        }
        basicInfo.setCourtNumber(courtNumber);
        this.save(basicInfo);
    }

    @Override
    public JSONObject getBasicInfoObject(String courtNumber) {
        //笔录基本信息
        LambdaQueryWrapper<BasicInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BasicInfo::getCourtNumber, courtNumber);
        BasicInfo basicInfo = this.getOne(queryWrapper);

        JSONObject basicInfoObject = new JSONObject();
        basicInfoObject.put("filing_time", basicInfo.getFilingTime());
        basicInfoObject.put("court_time", basicInfo.getCourtTime());
        basicInfoObject.put("court_place", basicInfo.getCourtPlace());

        //审判长
        JSONArray chiefJudgeArray = new JSONArray();
        String chiefJudge = basicInfo.getChiefJudge();
        String[] chiefJudges = chiefJudge.split(",");
        for (int i = 0; i < chiefJudges.length; i++) {
            JSONObject chiefJudgeObject = new JSONObject();
            chiefJudgeObject.put("name", chiefJudges[i]);
            chiefJudgeArray.add(chiefJudgeObject);
        }
        basicInfoObject.put("chief_judge", chiefJudgeArray);

        //审判员
        JSONArray judgeArray = new JSONArray();
        String judge = basicInfo.getJudge();
        String[] judges = judge.split(",");
        for (int i = 0; i < judges.length; i++) {
            JSONObject judgeObject = new JSONObject();
            judgeObject.put("name", judges[i]);
            judgeArray.add(judgeObject);
        }
        basicInfoObject.put("judge", judgeArray);

        //陪审员
        JSONArray jurorArray = new JSONArray();
        String juror = basicInfo.getJuror();
        String[] jurors = juror.split(",");
        for (int i = 0; i < jurors.length; i++) {
            JSONObject jurorObject = new JSONObject();
            jurorObject.put("name", jurors[i]);
            jurorArray.add(jurorObject);
        }
        basicInfoObject.put("juror", jurorArray);

        //人民陪审员
        JSONArray peopleJurorArray = new JSONArray();
        String peopleJuror = basicInfo.getPeopleJuror();
        String[] peopleJurors = peopleJuror.split(",");
        for (int i = 0; i < peopleJurors.length; i++) {
            JSONObject peopleJurorObject = new JSONObject();
            peopleJurorObject.put("name", peopleJurors[i]);
            peopleJurorArray.add(peopleJurorObject);
        }
        basicInfoObject.put("people_juror", peopleJurorArray);
        basicInfoObject.put("court_clerk", basicInfo.getCourtClerk());
        basicInfoObject.put("court_cause", basicInfo.getCourtCause());
        return basicInfoObject;
    }

    @Override
    public List<BasicInfo> getBasicInfoList(String courtNumber) {
        LambdaQueryWrapper<BasicInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BasicInfo::getCourtNumber, courtNumber);
        List<BasicInfo> basicInfos = basicInfoService.list(queryWrapper);
        return basicInfos;
    }

    //权利告知
    @Override
    public JSONObject getRightInfoObject(String courtNumber) {
        JSONObject rightInfoObject = new JSONObject();
        rightInfoObject.put("judge_right_duty", "审判员：依据《中华人民共和国民事诉讼法》的规定，当事人在法庭上享有下列权利：1.原告有权承认、变更、放弃自己的诉讼请求，被告有权反驳原告的诉讼请求或提起反诉；2.当事人有权申请回避；3.当事人有权举证；4.当事人有权辩论、有权请求法庭调解,当事人在享有上述权利的同时，负有以下义务：1.当事人有依法行使诉讼权利的义务；2.当事人有听从法庭指挥、遵守法庭纪律的义务；3.当事人有如实陈述事实、如实举证的义务。上述诉讼权利和义务双方是否听清？");
        rightInfoObject.put("judge_avoid", "审判员：当事人对审判员和书记是否申请回避？");

        //原告
        JSONArray accuserRightDutyArray = new JSONArray();
        LambdaQueryWrapper<Accuser> accuserQueryWrapper = new LambdaQueryWrapper<>();
        accuserQueryWrapper.eq(Accuser::getCourtNumber, courtNumber);
        List<Accuser> accusers = accuserService.list(accuserQueryWrapper);
        for (int i = 0; i < accusers.size(); i++) {
            Accuser accuser = accusers.get(i);
            String accuserName = accuser.getAccuser();
            String accuserRightDuty = accuser.getAccuserRightDuty();
            String accuserAvoid = accuser.getAccuserAvoid();
            JSONObject accuserRightDutyObject = new JSONObject();
            accuserRightDutyObject.put("accuser", accuserName);
            accuserRightDutyObject.put("right_duty", accuserRightDuty);
            accuserRightDutyObject.put("avoid", accuserAvoid);
            accuserRightDutyArray.add(accuserRightDutyObject);
        }
        rightInfoObject.put("accuser_right_duty", accuserRightDutyArray);

        //被告
        JSONArray defendantRightDutyArray = new JSONArray();
        LambdaQueryWrapper<Defendant> defendentQueryWrapper = new LambdaQueryWrapper<>();
        defendentQueryWrapper.eq(Defendant::getCourtNumber, courtNumber);
        List<Defendant> defendants = defendantService.list(defendentQueryWrapper);
        for (int i = 0; i < defendants.size(); i++) {
            Defendant defendant = defendants.get(i);
            String defendantName = defendant.getDefendant();
            String defendantRightDuty = defendant.getDefendantRightDuty();
            String defendantAvoid = defendant.getDefendantAvoid();
            JSONObject defendantRightDutyObject = new JSONObject();
            defendantRightDutyObject.put("defendant", defendantName);
            defendantRightDutyObject.put("right_duty", defendantRightDuty);
            defendantRightDutyObject.put("avoid", defendantAvoid);
            defendantRightDutyArray.add(defendantRightDutyObject);
        }
        rightInfoObject.put("defendant_right_duty", defendantRightDutyArray);
        return rightInfoObject;
    }


    //电子判决文书送达
    @Override
    public JSONArray getDiliveryInfoArray(String courtNumber) {
        JSONArray diliveryInfoArray = new JSONArray();
        //原告
        LambdaQueryWrapper<Accuser> accuserQueryWrapper = new LambdaQueryWrapper<>();
        accuserQueryWrapper.eq(Accuser::getCourtNumber, courtNumber);
        List<Accuser> accusers = accuserService.list(accuserQueryWrapper);
        for (int i = 0; i < accusers.size(); i++) {
            Accuser accuser = accusers.get(i);
            String accuserShortName = accuser.getAccuserShort();
            String isDelivery = accuser.getIsDelivery();
            String email = accuser.getEmail();

            JSONObject accuserDeliveryObject = new JSONObject();
            accuserDeliveryObject.put("name", accuserShortName + "（原告）");
            accuserDeliveryObject.put("is_delivery", isDelivery);
            accuserDeliveryObject.put("email", email);
            diliveryInfoArray.add(accuserDeliveryObject);
        }
        //被告
        LambdaQueryWrapper<Defendant> defendantQueryWrapper = new LambdaQueryWrapper<>();
        defendantQueryWrapper.eq(Defendant::getCourtNumber, courtNumber);
        List<Defendant> defendants = defendantService.list(defendantQueryWrapper);
        for (int i = 0; i < defendants.size(); i++) {
            Defendant defendant = defendants.get(i);
            String defendantShortName = defendant.getDefendantShort();
            String isDelivery = defendant.getIsDelivery();
            String email = defendant.getEmail();

            JSONObject defendantDeliveryObject = new JSONObject();
            defendantDeliveryObject.put("name", defendantShortName + "（被告）");
            defendantDeliveryObject.put("is_delivery", isDelivery);
            defendantDeliveryObject.put("email", email);
            diliveryInfoArray.add(defendantDeliveryObject);
        }
        return diliveryInfoArray;
    }

    //是否能够调解
    @Override
    public JSONObject getMediateInfoObject(String courtNumber) {
        JSONObject mediateInfoObject = new JSONObject();
        //原告
        LambdaQueryWrapper<Accuser> accuserQueryWrapper = new LambdaQueryWrapper<>();
        accuserQueryWrapper.eq(Accuser::getCourtNumber, courtNumber);
        List<Accuser> accusers = accuserService.list(accuserQueryWrapper);
        JSONArray mediateAccuserArray = new JSONArray();
        for (int i = 0; i < accusers.size(); i++) {
            Accuser accuser = accusers.get(i);
            String accuserShortName = accuser.getAccuserShort();
            String isMediate = accuser.getIsMediate();
            String mediatePlan = accuser.getMediatePlan();
            String timeLimit = accuser.getTimeLimit();

            JSONObject mediateAccuserObject = new JSONObject();
            mediateAccuserObject.put("accuser", accuserShortName);
            mediateAccuserObject.put("is_mediate", isMediate);
            mediateAccuserObject.put("mediate_plan", mediatePlan);
            mediateAccuserObject.put("time_limit", timeLimit);
            mediateAccuserArray.add(mediateAccuserObject);
        }
        mediateInfoObject.put("mediate_accuser", mediateAccuserArray);

        //被告
        LambdaQueryWrapper<Defendant> defendantQueryWrapper = new LambdaQueryWrapper<>();
        defendantQueryWrapper.eq(Defendant::getCourtNumber, courtNumber);
        List<Defendant> defendants = defendantService.list(defendantQueryWrapper);
        JSONArray mediateDefendantArray = new JSONArray();
        for (int i = 0; i < defendants.size(); i++) {
            Defendant defendant = defendants.get(i);
            String defendantShortName = defendant.getDefendantShort();
            String isMediate = defendant.getIsMediate();
            String mediatePlan = defendant.getMediatePlan();

            JSONObject mediateDefendantObject = new JSONObject();
            mediateDefendantObject.put("defendant", defendantShortName);
            mediateDefendantObject.put("is_mediate", isMediate);
            mediateDefendantObject.put("mediate_plan", mediatePlan);
            mediateDefendantArray.add(mediateDefendantObject);
        }
        mediateInfoObject.put("mediate_defendant", mediateDefendantArray);

        return mediateInfoObject;
    }

    @Override
    public JSONArray getFinalStatementInfoArray(String courtNumber) {
        JSONArray finalStatementInfoArray = new JSONArray();

        //原告
        LambdaQueryWrapper<Accuser> accuserQueryWrapper = new LambdaQueryWrapper<>();
        accuserQueryWrapper.eq(Accuser::getCourtNumber, courtNumber);
        List<Accuser> accusers = accuserService.list(accuserQueryWrapper);
        for (int i = 0; i < accusers.size(); i++) {
            Accuser accuser = accusers.get(i);
            String accuserShortName = accuser.getAccuserShort();
            String finalStatement = accuser.getFinalStatement();

            JSONObject accuserFinalStatementInfoObject = new JSONObject();
            accuserFinalStatementInfoObject.put("name", accuserShortName + "（原告）");
            accuserFinalStatementInfoObject.put("final_statement", finalStatement);
            finalStatementInfoArray.add(accuserFinalStatementInfoObject);
        }

        //被告
        LambdaQueryWrapper<Defendant> defendantQueryWrapper = new LambdaQueryWrapper<>();
        defendantQueryWrapper.eq(Defendant::getCourtNumber, courtNumber);
        List<Defendant> defendants = defendantService.list(defendantQueryWrapper);
        for (int i = 0; i < defendants.size(); i++) {
            Defendant defendant = defendants.get(i);
            String defendantShortName = defendant.getDefendantShort();
            String finalStatement = defendant.getFinalStatement();

            JSONObject defendantFinalStatementInfoObject = new JSONObject();
            defendantFinalStatementInfoObject.put("name", defendantShortName + "（被告）");
            defendantFinalStatementInfoObject.put("final_statement", finalStatement);
            finalStatementInfoArray.add(defendantFinalStatementInfoObject);
        }
        return finalStatementInfoArray;
    }

    //法庭调查
    @Override
    public JSONObject getCourtInvestigateObject(String courtNumber) {
        JSONObject courtInvestigateObject = new JSONObject();
        //诉称内容
        courtInvesAllege(courtNumber, courtInvestigateObject);
        //答辩内容
        courtInvesReply(courtNumber, courtInvestigateObject);
        //举证内容
        courtInvesProof(courtNumber, courtInvestigateObject);
        //质证内容
        courtInvesQuery(courtNumber, courtInvestigateObject);
        return courtInvestigateObject;
    }

    //法庭调查-诉称内容
    public void courtInvesAllege(String courtNumber, JSONObject courtInvestigateObject) {
        LambdaQueryWrapper<Allege> allegeQueryWrapper = new LambdaQueryWrapper<>();
        allegeQueryWrapper.eq(Allege::getCourtNumber, courtNumber);
        List<Allege> alleges = allegeService.list(allegeQueryWrapper);

        for (int i = 0; i < alleges.size(); i++) {
            Allege allege = alleges.get(i);
            String type = allege.getType();
            String claimItem = allege.getClaimItem();
            String factReason = allege.getFactReason();
            if (type != "" && "原告".equals(type)) {
                //原告的诉讼请求项
                courtInvestigateObject.put("accuser_claim_item", claimItem);
                //原告的事实和理由
                courtInvestigateObject.put("accuser_claim_fact_reason", factReason);
            } else if (type != "" && "反诉原告".equals(type)) {
                //反诉原告的诉讼请求项
                courtInvestigateObject.put("counterclaim_accuser_claim_item", factReason);
                //反诉原告的事实和理由
                courtInvestigateObject.put("counterclaim_accuser_fact_reason", factReason);
            }
            //是否反诉
            courtInvestigateObject.put("is_counterClaim", allege.getIsCounterClaim());
        }
    }

    //法庭调查-答辩内容
    public void courtInvesReply(String courtNumber, JSONObject courtInvestigateObject) {
        LambdaQueryWrapper<Reply> replyQueryWrapper = new LambdaQueryWrapper<>();
        replyQueryWrapper.eq(Reply::getCourtNumber, courtNumber);
        List<Reply> replies = replyService.list(replyQueryWrapper);
        JSONArray defendantReplyArray = new JSONArray();
        JSONArray counterClaimDefendantReplyArray = new JSONArray();
        for (int i = 0; i < replies.size(); i++) {
            Reply reply = replies.get(i);
            String name = reply.getName();
            String type = reply.getType();
            String content = reply.getContent();

            JSONObject replyObject = new JSONObject();
            replyObject.put("name", name);
            replyObject.put("content", content);

            if ("被告".equals(type)) {
                defendantReplyArray.add(replyObject);
            } else if ("反诉被告".equals(type)) {
                counterClaimDefendantReplyArray.add(replyObject);
                courtInvestigateObject.put("counterclaim_defendant_today_is_reply", "1");
            }
        }
        courtInvestigateObject.put("defendant_reply", defendantReplyArray);
        courtInvestigateObject.put("counterclaim_defendant_reply", counterClaimDefendantReplyArray);
    }

    //法庭调查-举证内容
    public void courtInvesProof(String courtNumber, JSONObject courtInvestigateObject) {
        LambdaQueryWrapper<Proof> proofQueryWrapper = new LambdaQueryWrapper<>();
        proofQueryWrapper.eq(Proof::getCourtNumber, courtNumber);
        List<Proof> proofs = proofService.list(proofQueryWrapper);
        JSONArray accuserEvidenceArray = new JSONArray();
        JSONArray defendantEvidenceArray = new JSONArray();
        JSONArray counterClaimAccuserEvidenceArray = new JSONArray();
        JSONArray counterClaimDefendantEvidenceArray = new JSONArray();
        for (int i = 0; i < proofs.size(); i++) {
            Proof proof = proofs.get(i);
            String type = proof.getType();
            String evidence = proof.getEvidence();
            String content = proof.getContent();
            String factReason = proof.getFactReason();

            JSONObject evidenceObject = new JSONObject();
            evidenceObject.put("evidence", evidence);
            evidenceObject.put("content", content);

            if ("原告".equals(type)) {
                accuserEvidenceArray.add(evidenceObject);
                if (!courtInvestigateObject.containsKey("accuser_evidence_fact_reason")) {
                    courtInvestigateObject.put("accuser_evidence_fact_reason", factReason);
                }
            } else if ("被告".equals(type)) {
                defendantEvidenceArray.add(evidenceObject);
                if (!courtInvestigateObject.containsKey("defendant_evidence_fact_reason")) {
                    courtInvestigateObject.put("defendant_evidence_fact_reason", factReason);
                }
            } else if ("反诉原告".equals(type)) {
                counterClaimAccuserEvidenceArray.add(evidenceObject);
                if (!courtInvestigateObject.containsKey("counterclaim_accuser_evidence_fact_reason")) {
                    courtInvestigateObject.put("counterclaim_accuser_evidence_fact_reason", factReason);
                }
            } else if ("反诉被告".equals(type)) {
                counterClaimDefendantEvidenceArray.add(evidenceObject);
                if (!courtInvestigateObject.containsKey("counterclaim_defendant_evidence_fact_reason")) {
                    courtInvestigateObject.put("counterclaim_defendant_evidence_fact_reason", factReason);
                }
            }
        }
        courtInvestigateObject.put("accuser_evidence", accuserEvidenceArray);
        courtInvestigateObject.put("defendant_evidence", defendantEvidenceArray);
        courtInvestigateObject.put("counterclaim_accuser_evidence", counterClaimAccuserEvidenceArray);
        courtInvestigateObject.put("counterclaim_defendant_evidence", counterClaimDefendantEvidenceArray);
    }


    //法庭调查-质证内容
    public void courtInvesQuery(String courtNumber, JSONObject courtInvestigateObject) {
        LambdaQueryWrapper<Query> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Query::getCourtNumber, courtNumber);
        List<Query> queries = queryService.list(queryWrapper);

        //所有原告
        LambdaQueryWrapper<Accuser> accuserQueryWrapper = new LambdaQueryWrapper<>();
        accuserQueryWrapper.eq(Accuser::getCourtNumber, courtNumber);
        List<Accuser> accusers = accuserService.list(accuserQueryWrapper);
        List<String> accuserShortNames = new ArrayList<>();
        for (int i = 0; i < accusers.size(); i++) {
            Accuser accuser = accusers.get(i);
            String accuserShortName = accuser.getAccuserShort();
            accuserShortNames.add(accuserShortName);
        }

        //所有被告
        LambdaQueryWrapper<Defendant> defendantQueryWrapper = new LambdaQueryWrapper<>();
        defendantQueryWrapper.eq(Defendant::getCourtNumber, courtNumber);
        List<Defendant> defendants = defendantService.list(defendantQueryWrapper);
        List<String> defendantShortNames = new ArrayList<>();
        for (int i = 0; i < defendants.size(); i++) {
            Defendant defendant = defendants.get(i);
            String defendantShortName = defendant.getDefendantShort();
            defendantShortNames.add(defendantShortName);
        }

        JSONArray accuserQueryArray = new JSONArray();
        JSONArray defendantQueryArray = new JSONArray();
        JSONArray otherDefendantQueryArray = new JSONArray();
        JSONArray counterClaimAccuserQueryArray = new JSONArray();
        JSONArray counterClaimDefendantQueryArray = new JSONArray();
        JSONArray otherCounterClaimDefendantQueryArray = new JSONArray();

        for (int i = 0; i < queries.size(); i++) {
            Query query = queries.get(i);
            String name = query.getName();
            String evidence = query.getEvidence();
            Boolean facticity = query.getFacticity();
            Boolean legality = query.getLegality();
            Boolean relevance = query.getRelevance();
            String reason = query.getReason();
            String queryType = query.getQueryType().toString();

            JSONObject queryObject = new JSONObject();
            queryObject.put("evidence", evidence);
            queryObject.put("facticity", facticity);
            queryObject.put("legality", legality);
            queryObject.put("relevance", relevance);

            if ("1".equals(queryType)) {
                //被告质证
                queryObject.put("defendant", name);
                queryObject.put("defendant_query_fact_reason", reason);
                defendantQueryArray.add(queryObject);
            } else if ("2".equals(queryType)) {
                //原告质证及其他被告质证
                if (accuserShortNames.contains(name)) {
                    queryObject.put("accuser", name);
                    queryObject.put("accuser_query_fact_reason", reason);
                    accuserQueryArray.add(queryObject);
                }
                if (defendantShortNames.contains(name)) {
                    queryObject.put("defendant", name);
                    queryObject.put("other_defendant_query_fact_reason", reason);
                    otherDefendantQueryArray.add(queryObject);
                }
            } else if ("3".equals(queryType)) {
                //反诉被告质证
                queryObject.put("counterclaim_defendant", name);
                queryObject.put("counterclaim_defendant_query_fact_reason", reason);
                counterClaimDefendantQueryArray.add(queryObject);
            } else if ("4".equals(queryType)) {
                //反诉原告及其他反诉被告质证
                if (defendantShortNames.contains(name)) {
                    queryObject.put("counterclaim_accuser", name);
                    queryObject.put("counterclaim_accuser_query_fact_reason", reason);
                    counterClaimAccuserQueryArray.add(queryObject);
                }
                if (accuserShortNames.contains(name)) {
                    queryObject.put("other_counterclaim_defendant", name);
                    queryObject.put("other_counterclaim_defendant_query_fact_reason", reason);
                    otherCounterClaimDefendantQueryArray.add(queryObject);
                }
            }
        }
        courtInvestigateObject.put("accuser_query", accuserQueryArray);
        courtInvestigateObject.put("defendant_query", defendantQueryArray);
        courtInvestigateObject.put("other_defendant_query", otherDefendantQueryArray);
        courtInvestigateObject.put("counterclaim_accuser_query", counterClaimAccuserQueryArray);
        courtInvestigateObject.put("counterclaim_defendant_query", counterClaimDefendantQueryArray);
        courtInvestigateObject.put("other_counterclaim_defendant_query", otherCounterClaimDefendantQueryArray);
    }


    @Override
    public PageResult<BasicInfo> findPage(BasicInfoRequest basicInfoRequest) {
        LambdaQueryWrapper<BasicInfo> wrapper = this.createWrapper(basicInfoRequest);
        Page<BasicInfo> page = this.page(PageFactory.defaultPage(), wrapper);
        return PageResultFactory.createPageResult(page);
    }

    /**
     * 实体构建 QueryWrapper
     *
     * @author 金波
     * @date 2022/01/14 15:07
     */
    private LambdaQueryWrapper<BasicInfo> createWrapper(BasicInfoRequest basicInfoRequest) {
        LambdaQueryWrapper<BasicInfo> queryWrapper = new LambdaQueryWrapper<>();

        boolean superAdminFlag = LoginContext.me().getSuperAdminFlag();
        if(!superAdminFlag){
            //非超级管理员只能看到自己的笔录
            Long userId = LoginContext.me().getLoginUser().getUserId();
            queryWrapper.eq(ObjectUtil.isNotEmpty(userId), BasicInfo::getUserId, userId);
        }
        Long basicId = basicInfoRequest.getBasicId();
        String judge = basicInfoRequest.getJudge();
        String courtNumber = basicInfoRequest.getCourtNumber();
        String courtCause = basicInfoRequest.getCourtCause();

        queryWrapper.eq(ObjectUtil.isNotEmpty(basicId), BasicInfo::getBasicId, basicId);
        queryWrapper.like(ObjectUtil.isNotEmpty(judge), BasicInfo::getJudge, judge);
        queryWrapper.eq(ObjectUtil.isNotEmpty(courtNumber), BasicInfo::getCourtNumber, courtNumber);
        queryWrapper.like(ObjectUtil.isNotEmpty(courtCause), BasicInfo::getCourtCause, courtCause);

        // 查询未删除状态的
        queryWrapper.eq(BasicInfo::getDelFlag, YesOrNotEnum.N.getCode());

        return queryWrapper;
    }
}

