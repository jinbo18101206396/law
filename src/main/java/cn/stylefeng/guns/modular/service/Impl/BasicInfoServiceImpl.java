package cn.stylefeng.guns.modular.service.Impl;

import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.modular.entity.*;
import cn.stylefeng.guns.modular.enums.FactLegalReleEnum;
import cn.stylefeng.guns.modular.mapper.BasicInfoMapper;
import cn.stylefeng.guns.modular.model.request.BasicInfoRequest;
import cn.stylefeng.guns.modular.service.*;
import cn.stylefeng.roses.kernel.auth.api.context.LoginContext;
import cn.stylefeng.roses.kernel.auth.api.pojo.login.LoginUser;
import cn.stylefeng.roses.kernel.cache.api.CacheOperatorApi;
import cn.stylefeng.roses.kernel.db.api.factory.PageFactory;
import cn.stylefeng.roses.kernel.db.api.factory.PageResultFactory;
import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.rule.enums.YesOrNotEnum;
import cn.stylefeng.roses.kernel.system.api.pojo.user.SysUserDTO;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

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
    private ThirdPartyService thirdPartyService;
    @Resource
    private ThirdPartyStateService thirdPartyStateService;
    @Resource
    private ProofService proofService;
    @Resource
    private WitnessTestimonyService witnessTestimonyService;
    @Resource
    private QueryService queryService;
    @Resource
    private ReplyService replyService;
    @Resource
    private AllegeService allegeService;
    @Resource
    private AgentService agentService;
    @Resource
    private StateService stateService;
    @Resource
    private ArgueService argueService;
    @Resource
    private InquiryService inquiryService;
    @Resource
    private JudgeRandomInquiryService judgeRandomInquiryService;
    @Resource
    private CacheOperatorApi<SysUserDTO> sysUserCacheOperatorApi;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveBasicInfo(String courtNumber, JSONObject recordJsonObject) {
        List<BasicInfo> basicInfoList = getBasicInfoList(courtNumber);
        if (basicInfoList != null || basicInfoList.size() > 0) {
            basicInfoService.delete(courtNumber);
        }

        BasicInfo basicInfo = new BasicInfo();
        //当前用户
        Long userId = LoginContext.me().getLoginUser().getUserId();
        basicInfo.setUserId(userId);
        //基本信息
        String basicInfoJsonStr = recordJsonObject.getString("basicInfo");
        JSONObject basicInfoObject = JSONObject.parseObject(basicInfoJsonStr);
        //立案时间
        if (basicInfoObject.containsKey("filing_time")) {
            basicInfo.setFilingTime(basicInfoObject.getString("filing_time"));
        }
        //开庭时间
        if (basicInfoObject.containsKey("court_time")) {
            basicInfo.setCourtTime(basicInfoObject.getString("court_time"));
        }
        //开庭地点
        if (basicInfoObject.containsKey("court_place")) {
            basicInfo.setCourtPlace(basicInfoObject.getString("court_place"));
        }
        //审判长（可多位，用逗号分隔）
        if (basicInfoObject.containsKey("chief_judge")) {
            String chiefJudge = "";
            JSONArray chiefJudgeArray = basicInfoObject.getJSONArray("chief_judge");
            for (int i = 0; i < chiefJudgeArray.size(); i++) {
                chiefJudge += chiefJudgeArray.getJSONObject(i).getString("name") + ",";
            }
            basicInfo.setChiefJudge(chiefJudge.substring(0, chiefJudge.length() - 1));
        }
        //审判员（可多位，用逗号分隔）
        if (basicInfoObject.containsKey("judge")) {
            String judge = "";
            JSONArray judgeArray = basicInfoObject.getJSONArray("judge");
            for (int i = 0; i < judgeArray.size(); i++) {
                judge += judgeArray.getJSONObject(i).getString("name") + ",";
            }
            basicInfo.setJudge(judge.substring(0, judge.length() - 1));
        }
        //陪审员（可多位，用逗号分隔）
        if (basicInfoObject.containsKey("juror")) {
            String juror = "";
            JSONArray jurorArray = basicInfoObject.getJSONArray("juror");
            for (int i = 0; i < jurorArray.size(); i++) {
                juror += jurorArray.getJSONObject(i).getString("name") + ",";
            }
            basicInfo.setJuror(juror.substring(0, juror.length() - 1));
        }
        //人民陪审员（可多位，用逗号分隔）
        if (basicInfoObject.containsKey("people_juror")) {
            String peopleJuror = "";
            JSONArray peopleJurorArray = basicInfoObject.getJSONArray("people_juror");
            for (int i = 0; i < peopleJurorArray.size(); i++) {
                peopleJuror += peopleJurorArray.getJSONObject(i).getString("name") + ",";
            }
            basicInfo.setPeopleJuror(peopleJuror.substring(0, peopleJuror.length() - 1));
        }
        //书记员
        if (basicInfoObject.containsKey("court_clerk")) {
            basicInfo.setCourtClerk(basicInfoObject.getString("court_clerk"));
        }
        //案由
        if (basicInfoObject.containsKey("court_cause")) {
            basicInfo.setCourtCause(basicInfoObject.getString("court_cause"));
        }
        if (recordJsonObject.containsKey("courtInvestigate")) {
            JSONObject courtInvestigateObject = recordJsonObject.getJSONObject("courtInvestigate");
            //被告是否举证
            if (courtInvestigateObject.containsKey("is_defendant_evidence")) {
                String isDefendantEvidence = courtInvestigateObject.getString("is_defendant_evidence");
                basicInfo.setIsDefendantEvidence(isDefendantEvidence);
            }
            //反诉被告今日是否答辩
            if (courtInvestigateObject.containsKey("counterclaim_defendant_today_is_reply")) {
                String counterClaimDefendantTodayIsReply = courtInvestigateObject.getString("counterclaim_defendant_today_is_reply");
                basicInfo.setCounterClaimDefendantTodayIsReply(counterClaimDefendantTodayIsReply);
            }
        }
        //原被告都统一且法院最终确认的调解方案
        if (recordJsonObject.containsKey("mediateInfo")) {
            JSONObject mediateInfoObject = recordJsonObject.getJSONObject("mediateInfo");
            String finalMediatePlan = "";
            if (mediateInfoObject.containsKey("final_mediate_plan")) {
                finalMediatePlan = mediateInfoObject.getString("final_mediate_plan");
            }
            basicInfo.setFinalMediatePlan(finalMediatePlan);
        }
        //审判员最终总结
        if (recordJsonObject.containsKey("summarize")) {
            String summarize = recordJsonObject.getString("summarize");
            basicInfo.setSummarize(summarize);
        }
        basicInfo.setCourtNumber(courtNumber);
        this.save(basicInfo);
    }

    /**
     * 获取笔录基本信息
     */
    @Override
    public JSONObject getBasicInfoObject(String courtNumber) {
        //笔录基本信息
        JSONObject basicInfoObject = new JSONObject();
        BasicInfo basicInfo = this.getBasicInfo(courtNumber);
        if (!ObjectUtils.isEmpty(basicInfo)) {
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
        }
        return basicInfoObject;
    }

    @Override
    public BasicInfo getBasicInfo(String courtNumber) {
        LambdaQueryWrapper<BasicInfo> basicInfoQueryWrapper = new LambdaQueryWrapper<>();
        basicInfoQueryWrapper.eq(BasicInfo::getCourtNumber, courtNumber);
        basicInfoQueryWrapper.eq(BasicInfo::getDelFlag, YesOrNotEnum.N.getCode());
        return basicInfoService.getOne(basicInfoQueryWrapper);
    }

    @Override
    public Boolean deleteBasicInfo(String courtNumber) {
        LambdaUpdateWrapper<BasicInfo> basicUpdateWrapper = new LambdaUpdateWrapper<>();
        basicUpdateWrapper.set(BasicInfo::getDelFlag, YesOrNotEnum.Y.getCode()).eq(BasicInfo::getCourtNumber, courtNumber);
        return basicInfoService.update(basicUpdateWrapper);
    }

    @Override
    public void delete(String courtNumber) {
        LambdaQueryWrapper<BasicInfo> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(BasicInfo::getCourtNumber, courtNumber);
        lambdaQueryWrapper.eq(BasicInfo::getDelFlag, YesOrNotEnum.N.getCode());
        baseMapper.delete(lambdaQueryWrapper);
    }

    @Override
    public List<BasicInfo> getBasicInfoList(String courtNumber) {
        LambdaQueryWrapper<BasicInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BasicInfo::getCourtNumber, courtNumber);
        queryWrapper.eq(BasicInfo::getDelFlag, YesOrNotEnum.N.getCode());
        return basicInfoService.list(queryWrapper);
    }

    public List<Accuser> getAccusers(String courtNumber) {
        LambdaQueryWrapper<Accuser> accuserQueryWrapper = new LambdaQueryWrapper<>();
        accuserQueryWrapper.eq(Accuser::getCourtNumber, courtNumber);
        accuserQueryWrapper.eq(Accuser::getDelFlag, YesOrNotEnum.N.getCode());
        return accuserService.list(accuserQueryWrapper);
    }

    public List<Defendant> getDefendants(String courtNumber) {
        LambdaQueryWrapper<Defendant> defendentQueryWrapper = new LambdaQueryWrapper<>();
        defendentQueryWrapper.eq(Defendant::getCourtNumber, courtNumber);
        defendentQueryWrapper.eq(Defendant::getDelFlag, YesOrNotEnum.N.getCode());
        return defendantService.list(defendentQueryWrapper);
    }

    public List<ThirdParty> getThirdParties(String courtNumber) {
        LambdaQueryWrapper<ThirdParty> thirdPartyQueryWrapper = new LambdaQueryWrapper<>();
        thirdPartyQueryWrapper.eq(ThirdParty::getCourtNumber, courtNumber);
        thirdPartyQueryWrapper.eq(ThirdParty::getDelFlag, YesOrNotEnum.N.getCode());
        return thirdPartyService.list(thirdPartyQueryWrapper);
    }


    public JSONObject blankAccuserRightDutyObject() {
        JSONObject accuserRightDutyObject = new JSONObject();
        accuserRightDutyObject.put("accuser", "");
        accuserRightDutyObject.put("right_duty", "");
        accuserRightDutyObject.put("avoid", "");
        return accuserRightDutyObject;
    }

    public JSONObject blankDefendantRightDutyObject() {
        JSONObject defendantRightDutyObject = new JSONObject();
        defendantRightDutyObject.put("defendant", "");
        defendantRightDutyObject.put("right_duty", "");
        defendantRightDutyObject.put("avoid", "");
        return defendantRightDutyObject;
    }

    public JSONObject blankThirdPartyRightDutyObject() {
        JSONObject thirdPartyRightDutyObject = new JSONObject();
        thirdPartyRightDutyObject.put("third_party", "");
        thirdPartyRightDutyObject.put("right_duty", "");
        thirdPartyRightDutyObject.put("avoid", "");
        return thirdPartyRightDutyObject;
    }

    /**
     * 权利告知
     */
    @Override
    public JSONObject getRightInfoObject(String courtNumber) {
        JSONObject rightInfoObject = new JSONObject();
        rightInfoObject.put("judge_right_duty", "审判员：依据《中华人民共和国民事诉讼法》的规定，当事人在法庭上享有下列权利：1.原告有权承认、变更、放弃自己的诉讼请求，被告有权反驳原告的诉讼请求或提起反诉；2.当事人有权申请回避；3.当事人有权举证；4.当事人有权辩论、有权请求法庭调解,当事人在享有上述权利的同时，负有以下义务：1.当事人有依法行使诉讼权利的义务；2.当事人有听从法庭指挥、遵守法庭纪律的义务；3.当事人有如实陈述事实、如实举证的义务。上述诉讼权利和义务双方是否听清？");
        rightInfoObject.put("judge_avoid", "审判员：当事人对审判员和书记是否申请回避？");
        //原告
        JSONArray accuserRightDutyArray = new JSONArray();
        List<Accuser> accusers = getAccusers(courtNumber);
        if (null == accusers || accusers.size() == 0) {
            accuserRightDutyArray.add(blankAccuserRightDutyObject());
        } else {
            for (int i = 0; i < accusers.size(); i++) {
                Accuser accuser = accusers.get(i);
                JSONObject accuserRightDutyObject = new JSONObject();
                String accuserShortName = accuser.getAccuserShort();
                String accuserRightDuty = accuser.getAccuserRightDuty();
                String accuserAvoid = accuser.getAccuserAvoid();

                if (!ObjectUtils.isEmpty(accuserShortName) && !ObjectUtils.isEmpty(accuserRightDuty) && !ObjectUtils.isEmpty(accuserAvoid)) {
                    accuserRightDutyObject.put("accuser", accuserShortName);
                    accuserRightDutyObject.put("right_duty", accuserRightDuty);
                    accuserRightDutyObject.put("avoid", accuserAvoid);
                    accuserRightDutyArray.add(accuserRightDutyObject);
                }
            }
        }
        if (accuserRightDutyArray == null || accuserRightDutyArray.size() <= 0) {
            accuserRightDutyArray.add(blankAccuserRightDutyObject());
        }
        rightInfoObject.put("accuser_right_duty", accuserRightDutyArray);
        //被告
        JSONArray defendantRightDutyArray = new JSONArray();
        List<Defendant> defendants = getDefendants(courtNumber);
        if (null == defendants || defendants.size() == 0) {
            defendantRightDutyArray.add(blankDefendantRightDutyObject());
        } else {
            for (int i = 0; i < defendants.size(); i++) {
                Defendant defendant = defendants.get(i);
                JSONObject defendantRightDutyObject = new JSONObject();
                String defendantShortName = defendant.getDefendantShort();
                String defendantRightDuty = defendant.getDefendantRightDuty();
                String defendantAvoid = defendant.getDefendantAvoid();
                if (!ObjectUtils.isEmpty(defendantShortName) && !ObjectUtils.isEmpty(defendantRightDuty) && !ObjectUtils.isEmpty(defendantAvoid)) {
                    defendantRightDutyObject.put("defendant", defendantShortName);
                    defendantRightDutyObject.put("right_duty", defendantRightDuty);
                    defendantRightDutyObject.put("avoid", defendantAvoid);
                    defendantRightDutyArray.add(defendantRightDutyObject);
                }
            }
        }
        if (defendantRightDutyArray == null || defendantRightDutyArray.size() <= 0) {
            defendantRightDutyArray.add(blankDefendantRightDutyObject());
        }
        rightInfoObject.put("defendant_right_duty", defendantRightDutyArray);
        //第三人
        JSONArray thirdRightDutyArray = new JSONArray();
        List<ThirdParty> thirdParties = getThirdParties(courtNumber);
        if (null == thirdParties || thirdParties.size() <= 0) {
            thirdRightDutyArray.add(blankThirdPartyRightDutyObject());
        } else {
            for (int i = 0; i < thirdParties.size(); i++) {
                ThirdParty thirdParty = thirdParties.get(i);
                JSONObject thirdRightDutyObject = new JSONObject();
                String thirdPartyShortName = thirdParty.getThirdPartyShort();
                String thirdPartyRightDuty = thirdParty.getThirdPartyRightDuty();
                String thirdPartyAvoid = thirdParty.getThirdPartyAvoid();
                if (!ObjectUtils.isEmpty(thirdPartyShortName) && !ObjectUtils.isEmpty(thirdPartyRightDuty) && !ObjectUtils.isEmpty(thirdPartyAvoid)) {
                    thirdRightDutyObject.put("third_party", thirdPartyShortName);
                    thirdRightDutyObject.put("right_duty", thirdPartyRightDuty);
                    thirdRightDutyObject.put("avoid", thirdPartyAvoid);
                    thirdRightDutyArray.add(thirdRightDutyObject);
                }
            }
        }
        if (thirdRightDutyArray == null || thirdRightDutyArray.size() <= 0) {
            thirdRightDutyArray.add(blankThirdPartyRightDutyObject());
        }
        rightInfoObject.put("third_party_right_duty", thirdRightDutyArray);
        return rightInfoObject;
    }

    /**
     * 电子判决文书送达
     */
    @Override
    public JSONArray getDiliveryInfoArray(String courtNumber) {
        JSONArray diliveryInfoArray = new JSONArray();
        //原告
        List<Accuser> accusers = getAccusers(courtNumber);
        if (null == accusers || accusers.size() == 0) {
            diliveryInfoArray.add(blankDelivery());
        } else {
            for (int i = 0; i < accusers.size(); i++) {
                JSONObject accuserDeliveryObject = new JSONObject();
                Accuser accuser = accusers.get(i);
                String accuserShortName = accuser.getAccuserShort();
                String isDelivery = accuser.getIsDelivery();
                if (ObjectUtils.isEmpty(isDelivery)) {
                    isDelivery = "1";
                }
                accuserDeliveryObject.put("name", accuserShortName + "（原告）");
                accuserDeliveryObject.put("is_delivery", isDelivery);
                accuserDeliveryObject.put("email", accuser.getEmail());
                diliveryInfoArray.add(accuserDeliveryObject);
            }
        }
        //被告
        List<Defendant> defendants = getDefendants(courtNumber);
        if (null == defendants || defendants.size() == 0) {
            diliveryInfoArray.add(blankDelivery());
        } else {
            for (int i = 0; i < defendants.size(); i++) {
                JSONObject defendantDeliveryObject = new JSONObject();
                Defendant defendant = defendants.get(i);
                String defendantShortName = defendant.getDefendantShort();
                String isDelivery = defendant.getIsDelivery();
                if (ObjectUtils.isEmpty(isDelivery)) {
                    isDelivery = "1";
                }
                defendantDeliveryObject.put("name", defendantShortName + "（被告）");
                defendantDeliveryObject.put("is_delivery", isDelivery);
                defendantDeliveryObject.put("email", defendant.getEmail());
                diliveryInfoArray.add(defendantDeliveryObject);
            }
        }

        //第三人
        List<ThirdParty> thirdParties = getThirdParties(courtNumber);
        if (null == thirdParties || thirdParties.size() <= 0) {
            diliveryInfoArray.add(blankDelivery());
        } else {
            for (int i = 0; i < thirdParties.size(); i++) {
                JSONObject thirdPartyDeliveryObject = new JSONObject();
                ThirdParty thirdParty = thirdParties.get(i);
                String thirdPartyShort = thirdParty.getThirdPartyShort();
                String isDelivery = thirdParty.getIsDelivery();
                if (ObjectUtils.isEmpty(isDelivery)) {
                    isDelivery = "1";
                }
                thirdPartyDeliveryObject.put("name", thirdPartyShort + "（第三人）");
                thirdPartyDeliveryObject.put("is_delivery", isDelivery);
                thirdPartyDeliveryObject.put("email", thirdParty.getEmail());
                diliveryInfoArray.add(thirdPartyDeliveryObject);
            }
        }
        return diliveryInfoArray;
    }

    public JSONObject blankDelivery() {
        JSONObject deliveryObject = new JSONObject();
        deliveryObject.put("name", "");
        deliveryObject.put("is_delivery", FactLegalReleEnum.AGREE.getCode());
        deliveryObject.put("email", "");
        return deliveryObject;
    }

    /**
     * 是否能够调解
     */
    @Override
    public JSONObject getMediateInfoObject(String courtNumber) {
        JSONObject mediateInfoObject = new JSONObject();
        //最终调解方案
        LambdaQueryWrapper<BasicInfo> basicInfoQueryWrapper = new LambdaQueryWrapper<>();
        basicInfoQueryWrapper.eq(BasicInfo::getCourtNumber, courtNumber);
        basicInfoQueryWrapper.eq(BasicInfo::getDelFlag, YesOrNotEnum.N.getCode());
        BasicInfo basicInfo = basicInfoService.getOne(basicInfoQueryWrapper);

        String finalMediatePlan = "";
        if (!ObjectUtils.isEmpty(basicInfo)) {
            finalMediatePlan = basicInfo.getFinalMediatePlan();
        }
        mediateInfoObject.put("final_mediate_plan", finalMediatePlan);

        //原告
        LambdaQueryWrapper<Accuser> accuserQueryWrapper = new LambdaQueryWrapper<>();
        accuserQueryWrapper.eq(Accuser::getCourtNumber, courtNumber);
        accuserQueryWrapper.eq(Accuser::getDelFlag, YesOrNotEnum.N.getCode());
        List<Accuser> accusers = accuserService.list(accuserQueryWrapper);
        JSONArray mediateAccuserArray = new JSONArray();
        for (int i = 0; i < accusers.size(); i++) {
            Accuser accuser = accusers.get(i);
            JSONObject mediateAccuserObject = new JSONObject();
            String accuserShort = accuser.getAccuserShort();
            String isMediate = accuser.getIsMediate();
            String timeLimit = accuser.getTimeLimit();
            String mediatePlan = accuser.getMediatePlan();
            mediateAccuserObject.put("accuser", accuserShort + "（原告）");
            if (ObjectUtils.isEmpty(isMediate)) {
                isMediate = "1";
            }
            mediateAccuserObject.put("is_mediate", isMediate);
            mediateAccuserObject.put("mediate_plan", mediatePlan);
            mediateAccuserObject.put("time_limit", timeLimit);
            mediateAccuserArray.add(mediateAccuserObject);
        }
        if (mediateAccuserArray == null || mediateAccuserArray.size() <= 0) {
            JSONObject mediateAccuserObject = new JSONObject();
            mediateAccuserObject.put("accuser", "");
            mediateAccuserObject.put("is_mediate", "1");
            mediateAccuserObject.put("mediate_plan", "");
            mediateAccuserObject.put("time_limit", "");
            mediateAccuserArray.add(mediateAccuserObject);
        }
        mediateInfoObject.put("mediate_accuser", mediateAccuserArray);

        //被告
        LambdaQueryWrapper<Defendant> defendantQueryWrapper = new LambdaQueryWrapper<>();
        defendantQueryWrapper.eq(Defendant::getCourtNumber, courtNumber);
        defendantQueryWrapper.eq(Defendant::getDelFlag, YesOrNotEnum.N.getCode());
        List<Defendant> defendants = defendantService.list(defendantQueryWrapper);
        JSONArray mediateDefendantArray = new JSONArray();
        for (int i = 0; i < defendants.size(); i++) {
            Defendant defendant = defendants.get(i);
            JSONObject mediateDefendantObject = new JSONObject();
            String defendantShort = defendant.getDefendantShort();
            String isMediate = defendant.getIsMediate();
            String mediatePlan = defendant.getMediatePlan();
            mediateDefendantObject.put("defendant", defendantShort + "（被告）");
            if (ObjectUtils.isEmpty(isMediate)) {
                isMediate = "1";
            }
            mediateDefendantObject.put("is_mediate", isMediate);
            mediateDefendantObject.put("mediate_plan", mediatePlan);
            mediateDefendantArray.add(mediateDefendantObject);
        }
        if (mediateDefendantArray == null || mediateDefendantArray.size() <= 0) {
            JSONObject mediateDefendantObject = new JSONObject();
            mediateDefendantObject.put("defendant", "");
            mediateDefendantObject.put("is_mediate", "1");
            mediateDefendantObject.put("mediate_plan", "");
            mediateDefendantArray.add(mediateDefendantObject);
        }
        mediateInfoObject.put("mediate_defendant", mediateDefendantArray);

        //第三人
        LambdaQueryWrapper<ThirdParty> thirdPartyQueryWrapper = new LambdaQueryWrapper<>();
        thirdPartyQueryWrapper.eq(ThirdParty::getCourtNumber, courtNumber);
        thirdPartyQueryWrapper.eq(ThirdParty::getDelFlag, YesOrNotEnum.N.getCode());
        List<ThirdParty> thirdParties = thirdPartyService.list(thirdPartyQueryWrapper);
        JSONArray mediateThirdPartyArray = new JSONArray();
        for (int i = 0; i < thirdParties.size(); i++) {
            ThirdParty thirdParty = thirdParties.get(i);
            JSONObject mediateThirdPartyObject = new JSONObject();
            String thirdPartyShort = thirdParty.getThirdPartyShort();
            String isMediate = thirdParty.getIsMediate();
            String mediatePlan = thirdParty.getMediatePlan();
            mediateThirdPartyObject.put("third_party", thirdPartyShort + "（第三人）");
            if (ObjectUtils.isEmpty(isMediate)) {
                isMediate = "1";
            }
            mediateThirdPartyObject.put("is_mediate", isMediate);
            mediateThirdPartyObject.put("mediate_plan", mediatePlan);
            mediateThirdPartyArray.add(mediateThirdPartyObject);
        }
        if (mediateThirdPartyArray == null || mediateThirdPartyArray.size() <= 0) {
            JSONObject mediateThirdPartyObject = new JSONObject();
            mediateThirdPartyObject.put("third_party", "");
            mediateThirdPartyObject.put("is_mediate", "1");
            mediateThirdPartyObject.put("mediate_plan", "");
            mediateThirdPartyArray.add(mediateThirdPartyObject);
        }
        mediateInfoObject.put("mediate_third_party", mediateThirdPartyArray);
        return mediateInfoObject;
    }

    /**
     * 最后陈述意见
     */
    @Override
    public JSONArray getFinalStatementInfoArray(String courtNumber) {
        JSONArray finalStatementInfoArray = new JSONArray();
        //原告
        LambdaQueryWrapper<Accuser> accuserQueryWrapper = new LambdaQueryWrapper<>();
        accuserQueryWrapper.eq(Accuser::getCourtNumber, courtNumber);
        accuserQueryWrapper.eq(Accuser::getDelFlag, YesOrNotEnum.N.getCode());
        List<Accuser> accusers = accuserService.list(accuserQueryWrapper);
        //若原告为空
        if (null == accusers || accusers.size() == 0) {
            JSONObject accuserFinalStatementInfoObject = new JSONObject();
            accuserFinalStatementInfoObject.put("name", "");
            accuserFinalStatementInfoObject.put("final_statement", "");
            finalStatementInfoArray.add(accuserFinalStatementInfoObject);
        } else {
            for (int i = 0; i < accusers.size(); i++) {
                Accuser accuser = accusers.get(i);
                String accuserShortName = accuser.getAccuserShort();
                String finalStatement = accuser.getFinalStatement();
                JSONObject accuserFinalStatementInfoObject = new JSONObject();
                accuserFinalStatementInfoObject.put("name", accuserShortName + "（原告）");
                accuserFinalStatementInfoObject.put("final_statement", finalStatement);
                finalStatementInfoArray.add(accuserFinalStatementInfoObject);
            }
        }

        //被告
        LambdaQueryWrapper<Defendant> defendantQueryWrapper = new LambdaQueryWrapper<>();
        defendantQueryWrapper.eq(Defendant::getCourtNumber, courtNumber);
        defendantQueryWrapper.eq(Defendant::getDelFlag, YesOrNotEnum.N.getCode());
        List<Defendant> defendants = defendantService.list(defendantQueryWrapper);
        if (null == defendants || defendants.size() == 0) {
            JSONObject defendantFinalStatementInfoObject = new JSONObject();
            defendantFinalStatementInfoObject.put("name", "");
            defendantFinalStatementInfoObject.put("final_statement", "");
            finalStatementInfoArray.add(defendantFinalStatementInfoObject);
        } else {
            for (int j = 0; j < defendants.size(); j++) {
                Defendant defendant = defendants.get(j);
                String defendantShortName = defendant.getDefendantShort();
                String finalStatement = defendant.getFinalStatement();
                JSONObject defendantFinalStatementInfoObject = new JSONObject();
                defendantFinalStatementInfoObject.put("name", defendantShortName + "（被告）");
                defendantFinalStatementInfoObject.put("final_statement", finalStatement);
                finalStatementInfoArray.add(defendantFinalStatementInfoObject);
            }
        }

        //第三人
        LambdaQueryWrapper<ThirdParty> thirdPartyQueryWrapper = new LambdaQueryWrapper<>();
        thirdPartyQueryWrapper.eq(ThirdParty::getCourtNumber, courtNumber);
        thirdPartyQueryWrapper.eq(ThirdParty::getDelFlag, YesOrNotEnum.N.getCode());
        List<ThirdParty> thirdParties = thirdPartyService.list(thirdPartyQueryWrapper);
        if (null == thirdParties || thirdParties.size() <= 0) {
            JSONObject thirdPartyFinalStatementInfoObject = new JSONObject();
            thirdPartyFinalStatementInfoObject.put("name", "");
            thirdPartyFinalStatementInfoObject.put("final_statement", "");
            finalStatementInfoArray.add(thirdPartyFinalStatementInfoObject);
        } else {
            for (int j = 0; j < thirdParties.size(); j++) {
                ThirdParty thirdParty = thirdParties.get(j);
                String thirdPartyShortName = thirdParty.getThirdPartyShort();
                String finalStatement = thirdParty.getFinalStatement();
                JSONObject thirdPartyFinalStatementInfoObject = new JSONObject();
                thirdPartyFinalStatementInfoObject.put("name", thirdPartyShortName + "（第三人）");
                thirdPartyFinalStatementInfoObject.put("final_statement", finalStatement);
                finalStatementInfoArray.add(thirdPartyFinalStatementInfoObject);
            }
        }
        return finalStatementInfoArray;
    }

    /**
     * 审判员最终总结
     */
    @Override
    public String getSummarize(String courtNumber) {
        LambdaQueryWrapper<BasicInfo> basicInfoQueryWrapper = new LambdaQueryWrapper<>();
        basicInfoQueryWrapper.eq(BasicInfo::getCourtNumber, courtNumber);
        basicInfoQueryWrapper.eq(BasicInfo::getDelFlag, YesOrNotEnum.N.getCode());
        BasicInfo basicInfo = basicInfoService.getOne(basicInfoQueryWrapper);
        String summarize = "";
        if (!ObjectUtils.isEmpty(basicInfo)) {
            summarize = basicInfo.getSummarize();
        }
        return summarize;
    }

    /**
     * 法庭调查
     */
    @Override
    public JSONObject getCourtInvestigateObject(String courtNumber) {
        JSONObject courtInvestigateObject = new JSONObject();
        //诉称内容
        courtInvesAllege(courtNumber, courtInvestigateObject);
        //法官随机提问(原告诉称后，被告答辩后)
        judgeRandomInquiry(courtNumber, courtInvestigateObject);
        //答辩内容
        courtInvesReply(courtNumber, courtInvestigateObject);
        //第三人述称
        thirdPartyState(courtNumber, courtInvestigateObject);
        //举证内容
        courtInvesProof(courtNumber, courtInvestigateObject);
        //质证内容
        courtInvesQuery(courtNumber, courtInvestigateObject);
        return courtInvestigateObject;
    }

    /**
     * 第三人述称
     */
    public void thirdPartyState(String courtNumber, JSONObject courtInvestigateObject) {
        JSONArray stateArray = new JSONArray();
        LambdaQueryWrapper<ThirdPartyState> stateQueryWrapper = new LambdaQueryWrapper<>();
        stateQueryWrapper.eq(ThirdPartyState::getCourtNumber, courtNumber);
        stateQueryWrapper.eq(ThirdPartyState::getDelFlag, YesOrNotEnum.N.getCode());
        List<ThirdPartyState> thirdPartyStateList = thirdPartyStateService.list(stateQueryWrapper);
        if (thirdPartyStateList != null && thirdPartyStateList.size() > 0) {
            for (ThirdPartyState thirdPartyState : thirdPartyStateList) {
                String name = thirdPartyState.getName();
                String state = thirdPartyState.getState();
                JSONObject stateObject = new JSONObject();
                stateObject.put("name", name);
                stateObject.put("state", state);
                stateArray.add(stateObject);
            }
        } else {
            JSONObject stateObject = new JSONObject();
            stateObject.put("name", "");
            stateObject.put("state", "");
            stateArray.add(stateObject);
        }
        courtInvestigateObject.put("third_party_state", stateArray);
    }

    /**
     * 被告答辩
     */
    @Override
    public List<Reply> getDefendantReply(String courtNumber) {
        List<Reply> defendantReplyList = new ArrayList<>();
        JSONObject courtInvestigateObject = this.getCourtInvestigateObject(courtNumber);
        JSONArray defendantReplyArray = courtInvestigateObject.getJSONArray("defendant_reply");
        for (int i = 0; i < defendantReplyArray.size(); i++) {
            JSONObject defendantReplyObject = defendantReplyArray.getJSONObject(i);
            String name = defendantReplyObject.getString("name");
            String content = defendantReplyObject.getString("content");
            if (!ObjectUtils.isEmpty(name) && !ObjectUtils.isEmpty(content)) {
                Reply reply = new Reply();
                reply.setName(name);
                reply.setContent(content);
                defendantReplyList.add(reply);
            }
        }
        return defendantReplyList;
    }

    /**
     * 第三人述称
     */
    @Override
    public List<ThirdPartyState> getThirdPartyState(String courtNumber) {
        List<ThirdPartyState> thirdPartyStateList = new ArrayList<>();
        JSONObject courtInvestigateObject = this.getCourtInvestigateObject(courtNumber);
        JSONArray thirdPartyStateArray = courtInvestigateObject.getJSONArray("third_party_state");
        for (int i = 0; i < thirdPartyStateArray.size(); i++) {
            JSONObject thirdPartyStateObject = thirdPartyStateArray.getJSONObject(i);
            String name = thirdPartyStateObject.getString("name");
            String state = thirdPartyStateObject.getString("state");
            if (!ObjectUtils.isEmpty(name) && !ObjectUtils.isEmpty(state)) {
                ThirdPartyState thirdPartyState = new ThirdPartyState();
                thirdPartyState.setName(name);
                thirdPartyState.setState(state);
                thirdPartyStateList.add(thirdPartyState);
            }
        }
        return thirdPartyStateList;
    }

    /**
     * 被告及其他原告质证
     */
    @Override
    public List<Query> getDefendantAndOtherAccuserQuery(String courtNumber) {
        JSONObject courtInvestigateObject = this.getCourtInvestigateObject(courtNumber);
        JSONArray defendantQueryArray = courtInvestigateObject.getJSONArray("defendant_and_other_accuser_query");
        return getQueryList(defendantQueryArray);
    }

    /**
     * 原告及其他被告质证
     */
    @Override
    public List<Query> getAccuserAndOtherDefendantQuery(String courtNumber) {
        JSONObject courtInvestigateObject = this.getCourtInvestigateObject(courtNumber);
        JSONArray accuserQueryArray = courtInvestigateObject.getJSONArray("accuser_and_other_defendant_query");
        return getQueryList(accuserQueryArray);
    }

    private List<Query> getQueryList(JSONArray queryArray) {
        List<Query> queryList = new ArrayList();
        if (queryArray != null && queryArray.size() > 0) {
            for (int i = 0; i < queryArray.size(); i++) {
                JSONObject queryObject = queryArray.getJSONObject(i);
                String name = queryObject.getString("name");
                String evidence = queryObject.getString("evidence");
                String facticity = queryObject.getString("facticity");
                String legality = queryObject.getString("legality");
                String relevance = queryObject.getString("relevance");
                String factReason = queryObject.getString("fact_reason");
                if (!ObjectUtils.isEmpty(name) && !ObjectUtils.isEmpty(evidence)) {
                    if (name.contains("**")) {
                        name = name.replace("**", "、");
                    }
                    String evidenceNumber = "";
                    //证据一:材料证明**证据二:承诺书
                    if (evidence.contains("**")) {
                        String[] evidences = evidence.split("[**]");
                        for (int k = 0; k < evidences.length; k++) {
                            String evidenceStr = evidences[k];
                            if (!ObjectUtils.isEmpty(evidenceStr)) {
                                String evidenceNum = evidenceStr.split(":")[0];
                                evidenceNumber += evidenceNum + "、";
                            }
                        }
                    } else if (evidence.contains("：")) {
                        String evidenceNum = evidence.split("：")[0];
                        evidenceNumber += evidenceNum;
                    } else if (evidence.contains(":")) {
                        String evidenceNum = evidence.split(":")[0];
                        evidenceNumber += evidenceNum;
                    }
                    Query query = new Query();
                    query.setName(name);
                    if (!ObjectUtils.isEmpty(evidenceNumber) && evidenceNumber.contains("、")) {
                        evidenceNumber = evidenceNumber.substring(0, evidenceNumber.length() - 1);
                    }
                    query.setEvidence(evidenceNumber);
                    query.setFacticity(FactLegalReleEnum.getMessage(facticity));
                    query.setLegality(FactLegalReleEnum.getMessage(legality));
                    query.setRelevance(FactLegalReleEnum.getMessage(relevance));
                    query.setReason(factReason);
                    queryList.add(query);
                }
            }
        }
        return queryList;
    }

    //物证
    private String getEvidenceContent(JSONArray evidenceArray) {
        String evidenceContent = "";
        if (evidenceArray != null && evidenceArray.size() > 0) {
            for (int i = 0; i < evidenceArray.size(); i++) {
                JSONObject evidenceObject = evidenceArray.getJSONObject(i);
                String serial = evidenceObject.getString("serial");
                String evidenceType = evidenceObject.getString("evidence_type");
                String evidence = evidenceObject.getString("evidence");
                String content = evidenceObject.getString("content");
                if (ObjectUtils.isEmpty(evidence) || ObjectUtils.isEmpty(evidenceType) || ObjectUtils.isEmpty(content)) {
                    continue;
                }
                evidenceContent += "证据" + serial + "." + evidence + "（" + evidenceType + "），" + content + "。";
            }
        }
        return evidenceContent;
    }

    private List<Proof> getEvidenceContent(String courtNumber,String type){
        List<Proof> proofs = getProofs(courtNumber,type);
        List<WitnessTestimony> witnessProofs = new ArrayList<>();
        for (int i = 0; i < proofs.size(); i++) {
            Proof proof = proofs.get(i);
            String isWitness = proof.getIsWitness();
            if (!ObjectUtils.isEmpty(isWitness) && "1".equals(isWitness)) {
                witnessProofs = getWitnessProofs(courtNumber, proof.getEvidence());
            }
            proof.setWitnessProofs(witnessProofs);
        }
        return proofs;
    }

    private String getQueryContent(JSONArray queryArray) {
        String queryContent = "";
        if (queryArray != null && queryArray.size() > 0) {
            for (int i = 0; i < queryArray.size(); i++) {
                JSONObject queryObject = queryArray.getJSONObject(i);
                String name = queryObject.getString("name");
                String factReason = queryObject.getString("fact_reason");
                String evidence = queryObject.getString("evidence");
                if (!ObjectUtils.isEmpty(name) && !ObjectUtils.isEmpty(evidence)) {
                    if (name.contains("**")) {
                        name = name.replace("**", "、");
                    }
                    if (evidence.contains("**")) {
                        evidence = evidence.replace("**", "、");
                    }
                    queryContent += name + "质证（" + evidence + "）；事实和理由：" + factReason;
                }
            }
        }
        return queryContent;
    }

    @Override
    public CourtInvestigate getCourtInvestigateInfo(String courtNumber) {
        CourtInvestigate courtInvestigate = new CourtInvestigate();
        JSONObject courtInvestigateObject = this.getCourtInvestigateObject(courtNumber);
        //原告诉讼请求
        String accuserClaimItem = courtInvestigateObject.getString("accuser_claim_item");
        String accuserClaimFactReason = courtInvestigateObject.getString("accuser_claim_fact_reason");
        String accuserClaimItemAfterChange = courtInvestigateObject.getString("accuser_claim_item_after_change");
        courtInvestigate.setAccuserClaimItem(accuserClaimItem);
        courtInvestigate.setAccuserClaimFactReason(accuserClaimFactReason);
        courtInvestigate.setAccuserClaimItemAfterChange(accuserClaimItemAfterChange);
        //原告举证（物证/人证）
        List<Proof> accuserEvidenceList = getEvidenceContent(courtNumber,"原告");
        courtInvestigate.setAccuserEvidenceList(accuserEvidenceList);
        //被告及其他原告质证
        JSONArray defendantQueryArray = courtInvestigateObject.getJSONArray("defendant_and_other_accuser_query");
        String defendantAndOtherAccuserQuery = getQueryContent(defendantQueryArray);
        courtInvestigate.setDefendantAndOtherAccuserQuery(defendantAndOtherAccuserQuery);
        //被告及第三人举证
        List<Proof> defendantAndThirdEvidenceList = getEvidenceContent(courtNumber, "被告及第三人");
        courtInvestigate.setDefendantAndThirdEvidenceList(defendantAndThirdEvidenceList);
        BasicInfo basicInfo = getBasicInfo(courtNumber);
        courtInvestigate.setIsDefendantEvidence(basicInfo.getIsDefendantEvidence());
        //原告及其他被告质证
        JSONArray accuserQueryArray = courtInvestigateObject.getJSONArray("accuser_and_other_defendant_query");
        String accuserAndOtherDefendantQuery = getQueryContent(accuserQueryArray);
        courtInvestigate.setAccuserAndOtherDefendantQuery(accuserAndOtherDefendantQuery);
        return courtInvestigate;
    }

    /**
     * 法庭调查-诉称内容
     */
    public void courtInvesAllege(String courtNumber, JSONObject courtInvestigateObject) {
        LambdaQueryWrapper<Allege> allegeQueryWrapper = new LambdaQueryWrapper<>();
        allegeQueryWrapper.eq(Allege::getCourtNumber, courtNumber);
        allegeQueryWrapper.eq(Allege::getDelFlag, YesOrNotEnum.N.getCode());
        List<Allege> alleges = allegeService.list(allegeQueryWrapper);
        //若诉称内容为空
        if (null == alleges || alleges.size() <= 0) {
            courtInvestigateObject.put("accuser_claim_item", "");
            courtInvestigateObject.put("accuser_claim_fact_reason", "");
            courtInvestigateObject.put("counterclaim_accuser_claim_item", "");
            courtInvestigateObject.put("counterclaim_accuser_fact_reason", "");
            courtInvestigateObject.put("is_counterclaim", "2");
            courtInvestigateObject.put("is_change_claim_item", "2");
            courtInvestigateObject.put("accuser_claim_item_after_change", "");
            courtInvestigateObject.put("accuser_claim_fact_reason_after_change", "");
        } else {
            for (int i = 0; i < alleges.size(); i++) {
                Allege allege = alleges.get(i);
                String type = allege.getType();
                String claimItem = allege.getClaimItem();
                String factReason = allege.getFactReason();
                String isCounterClaim = allege.getIsCounterClaim();

                String isChangeClaimItem = allege.getIsChangeClaimItem();
                String claimItemAfterChange = allege.getClaimItemAfterChange();
                String factReasonAfterChange = allege.getFactReasonAfterChange();

                if (!ObjectUtils.isEmpty(type) && "原告".equals(type)) {
                    courtInvestigateObject.put("accuser_claim_item", claimItem);
                    courtInvestigateObject.put("accuser_claim_fact_reason", factReason);
                    courtInvestigateObject.put("is_counterclaim", isCounterClaim);
                    courtInvestigateObject.put("is_change_claim_item", isChangeClaimItem);
                    courtInvestigateObject.put("accuser_claim_item_after_change", claimItemAfterChange);
                    courtInvestigateObject.put("accuser_claim_fact_reason_after_change", factReasonAfterChange);
                } else if (type != "" && "反诉原告".equals(type)) {
                    courtInvestigateObject.put("counterclaim_accuser_claim_item", claimItem);
                    courtInvestigateObject.put("counterclaim_accuser_fact_reason", factReason);
                }
            }
        }
    }

    /**
     * 法庭调查-法官随机提问
     */
    public void judgeRandomInquiry(String courtNumber, JSONObject courtInvestigateObject) {
        JSONObject judgeRandomInquiry = judgeRandomInquiryService.getJudgeRandomInquiry(courtNumber);
        courtInvestigateObject.put("judge_inquiry_after_accuser_claim", judgeRandomInquiry.getJSONArray("judge_inquiry_after_accuser_claim"));
        courtInvestigateObject.put("judge_inquiry_after_defendant_reply", judgeRandomInquiry.getJSONArray("judge_inquiry_after_defendant_reply"));
    }

    /**
     * 法庭调查-答辩内容
     */
    public void courtInvesReply(String courtNumber, JSONObject courtInvestigateObject) {
        JSONArray defendantReplyArray = new JSONArray();
        JSONArray counterClaimDefendantReplyArray = new JSONArray();

        BasicInfo basicInfo = getBasicInfo(courtNumber);
        String counterClaimDefendantTodayIsReply = basicInfo.getCounterClaimDefendantTodayIsReply();
        courtInvestigateObject.put("counterclaim_defendant_today_is_reply", counterClaimDefendantTodayIsReply);

        List<Reply> replies = getReplies(courtNumber);
        for (int i = 0; i < replies.size(); i++) {
            Reply reply = replies.get(i);
            String name = reply.getName();
            String content = reply.getContent();
            String type = reply.getType();
            if ("被告".equals(type)) {
                JSONObject defendantReplyObject = new JSONObject();
                defendantReplyObject.put("name", name);
                defendantReplyObject.put("content", content);
                defendantReplyArray.add(defendantReplyObject);
            } else if ("反诉被告".equals(type)) {
                JSONObject counterClaimReplyObject = new JSONObject();
                counterClaimReplyObject.put("name", name);
                counterClaimReplyObject.put("content", content);
                counterClaimDefendantReplyArray.add(counterClaimReplyObject);
            }
        }
        if (defendantReplyArray == null || defendantReplyArray.size() <= 0) {
            defendantReplyArray.add(blankReply());
        }
        courtInvestigateObject.put("defendant_reply", defendantReplyArray);

        if (counterClaimDefendantReplyArray == null || counterClaimDefendantReplyArray.size() <= 0) {
            counterClaimDefendantReplyArray.add(blankReply());
        }
        courtInvestigateObject.put("counterclaim_defendant_reply", counterClaimDefendantReplyArray);
    }

    public JSONObject blankReply() {
        JSONObject replyObject = new JSONObject();
        replyObject.put("name", "");
        replyObject.put("content", "");
        return replyObject;
    }

    public List<Reply> getReplies(String courtNumber) {
        LambdaQueryWrapper<Reply> replyQueryWrapper = new LambdaQueryWrapper<>();
        replyQueryWrapper.eq(Reply::getCourtNumber, courtNumber);
        replyQueryWrapper.eq(Reply::getDelFlag, YesOrNotEnum.N.getCode());
        return replyService.list(replyQueryWrapper);
    }

    /**
     * 物证
     */
    public List<Proof> getProofs(String courtNumber,String type) {
        LambdaQueryWrapper<Proof> proofQueryWrapper = new LambdaQueryWrapper<>();
        proofQueryWrapper.eq(Proof::getCourtNumber, courtNumber);
        if(!ObjectUtils.isEmpty(type)){
            proofQueryWrapper.eq(Proof::getType,type);
        }
        proofQueryWrapper.eq(Proof::getDelFlag, YesOrNotEnum.N.getCode());
        return proofService.list(proofQueryWrapper);
    }

    /**
     * 人证
     */
    public List<WitnessTestimony> getWitnessProofs(String courtNumber, String evidence) {
        LambdaQueryWrapper<WitnessTestimony> witnessQueryWrapper = new LambdaQueryWrapper<>();
        witnessQueryWrapper.eq(WitnessTestimony::getEvidence, evidence);
        witnessQueryWrapper.eq(WitnessTestimony::getCourtNumber, courtNumber);
        witnessQueryWrapper.eq(WitnessTestimony::getDelFlag, YesOrNotEnum.N.getCode());
        return witnessTestimonyService.list(witnessQueryWrapper);
    }

    public JSONObject blankEvidence() {
        JSONObject evidenceObject = new JSONObject();
        evidenceObject.put("name", "");
        evidenceObject.put("serial", "1");
        evidenceObject.put("evidence", "");
        evidenceObject.put("evidence_type", "");
        evidenceObject.put("content", "");
        return evidenceObject;
    }

    public JSONObject blankWitnessEvidence() {
        JSONObject witnessEvidenceObject = new JSONObject();
        witnessEvidenceObject.put("serial", "1");
        witnessEvidenceObject.put("evidence_type", "");
        witnessEvidenceObject.put("evidence", "");
        witnessEvidenceObject.put("witness_name", "");
        witnessEvidenceObject.put("witness_type", "");

        JSONArray witnessTestimonyArray = new JSONArray();
        JSONObject witnessTestimonyObject = new JSONObject();
        witnessTestimonyObject.put("quizzer", "");
        witnessTestimonyObject.put("question", "");
        witnessTestimonyObject.put("responder", "");
        witnessTestimonyObject.put("answer", "");
        witnessTestimonyArray.add(witnessTestimonyObject);

        witnessEvidenceObject.put("witness_testimony", witnessTestimonyArray);
        return witnessEvidenceObject;
    }

    /**
     * 法庭调查-举证内容
     */
    public void courtInvesProof(String courtNumber, JSONObject courtInvestigateObject) {
        JSONArray accuserEvidenceArray = new JSONArray();
        JSONArray defendantEvidenceArray = new JSONArray();
        JSONArray counterClaimAccuserEvidenceArray = new JSONArray();
        JSONArray counterClaimDefendantEvidenceArray = new JSONArray();
        JSONArray accuserWitnessEvidenceArray = new JSONArray();
        JSONArray defendantAndThirdWitnessEvidenceArray = new JSONArray();

        List<Proof> proofs = getProofs(courtNumber,"");
        for (int i = 0; i < proofs.size(); i++) {
            Proof proof = proofs.get(i);
            String isWitness = proof.getIsWitness();
            String name = proof.getName();
            String serial = proof.getSerial();
            String evidence = proof.getEvidence();
            String evidenceType = proof.getEvidenceType();
            String content = proof.getContent();
            String type = proof.getType();
            //人证
            if (!ObjectUtils.isEmpty(isWitness) && "1".equals(isWitness)) {
                JSONArray witnessTestimonyArray = new JSONArray();
                List<WitnessTestimony> witnessProofs = getWitnessProofs(courtNumber, evidence);
                String witnessName = "";
                String witnessType = "";
                for (int j = 0; j < witnessProofs.size(); j++) {
                    WitnessTestimony witnessTestimony = witnessProofs.get(j);
                    witnessName = witnessTestimony.getName();
                    witnessType = witnessTestimony.getType();

                    JSONObject questionAndAnswerObject = new JSONObject();
                    questionAndAnswerObject.put("quizzer", witnessTestimony.getQuizzer());
                    questionAndAnswerObject.put("question", witnessTestimony.getQuestion());
                    questionAndAnswerObject.put("responder", witnessTestimony.getResponder());
                    questionAndAnswerObject.put("answer", witnessTestimony.getAnswer());

                    witnessTestimonyArray.add(questionAndAnswerObject);
                }
                JSONObject witnessEvidenceObject = new JSONObject();
                witnessEvidenceObject.put("serial", serial);
                witnessEvidenceObject.put("evidence_type", evidenceType);
                witnessEvidenceObject.put("evidence", evidence);
                witnessEvidenceObject.put("content", content);
                witnessEvidenceObject.put("witness_name", witnessName);
                witnessEvidenceObject.put("witness_type", witnessType);
                witnessEvidenceObject.put("witness_testimony", witnessTestimonyArray);
                if ("原告".equals(type)) {
                    accuserWitnessEvidenceArray.add(witnessEvidenceObject);
                } else if ("被告及第三人".equals(type)) {
                    defendantAndThirdWitnessEvidenceArray.add(witnessEvidenceObject);
                }
            } else {
                //物证
                JSONObject evidenceObject = new JSONObject();
                evidenceObject.put("name", name);
                evidenceObject.put("serial", serial);
                evidenceObject.put("evidence", evidence);
                evidenceObject.put("evidence_type", evidenceType);
                evidenceObject.put("content", content);
                if ("原告".equals(type)) {
                    accuserEvidenceArray.add(evidenceObject);
                } else if ("被告及第三人".equals(type)) {
                    defendantEvidenceArray.add(evidenceObject);
                } else if ("反诉原告".equals(type)) {
                    counterClaimAccuserEvidenceArray.add(evidenceObject);
                } else if ("反诉被告".equals(type)) {
                    counterClaimDefendantEvidenceArray.add(evidenceObject);
                }
            }
        }
        //原告举证（物证）
        if (accuserEvidenceArray == null || accuserEvidenceArray.size() <= 0) {
            accuserEvidenceArray.add(blankEvidence());
        }
        courtInvestigateObject.put("accuser_evidence", accuserEvidenceArray);

        //原告举证（人证）
        if (accuserWitnessEvidenceArray == null || accuserWitnessEvidenceArray.size() <= 0) {
            accuserWitnessEvidenceArray.add(blankWitnessEvidence());
            courtInvestigateObject.put("accuser_is_witness", "2");
        } else {
            courtInvestigateObject.put("accuser_is_witness", "1");
        }
        courtInvestigateObject.put("accuser_evidence_witness", accuserWitnessEvidenceArray);

        //被告及第三人举证（物证）
        if (defendantEvidenceArray == null || defendantEvidenceArray.size() <= 0) {
            defendantEvidenceArray.add(blankEvidence());
        }
        courtInvestigateObject.put("defendant_and_third_evidence", defendantEvidenceArray);

        //被告及第三人举证（人证）
        if (defendantAndThirdWitnessEvidenceArray == null || defendantAndThirdWitnessEvidenceArray.size() <= 0) {
            defendantAndThirdWitnessEvidenceArray.add(blankWitnessEvidence());
            courtInvestigateObject.put("defendant_is_witness", "2");
        } else {
            courtInvestigateObject.put("defendant_is_witness", "1");
        }
        courtInvestigateObject.put("defendant_and_third_evidence_witness", defendantAndThirdWitnessEvidenceArray);

        if (counterClaimAccuserEvidenceArray == null || counterClaimAccuserEvidenceArray.size() <= 0) {
            counterClaimAccuserEvidenceArray.add(blankEvidence());
        }
        courtInvestigateObject.put("counterclaim_accuser_evidence", counterClaimAccuserEvidenceArray);

        if (counterClaimDefendantEvidenceArray == null || counterClaimDefendantEvidenceArray.size() <= 0) {
            counterClaimDefendantEvidenceArray.add(blankEvidence());
        }
        courtInvestigateObject.put("counterclaim_defendant_evidence", counterClaimDefendantEvidenceArray);
    }

    /**
     * 法庭调查-质证内容
     */
    public void courtInvesQuery(String courtNumber, JSONObject courtInvestigateObject) {
        JSONArray defendantAndOtherAccuserQueryArray = new JSONArray();
        JSONArray accuserAndOtherDefendantQueryArray = new JSONArray();

        List<Query> queries = getQueries(courtNumber);
        for (int i = 0; i < queries.size(); i++) {
            Query query = queries.get(i);
            String name = query.getName();
            if (ObjectUtils.isEmpty(name)) {
                continue;
            }
            JSONObject queryObject = new JSONObject();
            queryObject.put("name", name);
            queryObject.put("evidence", query.getEvidence());
            queryObject.put("facticity", query.getFacticity());
            queryObject.put("legality", query.getLegality());
            queryObject.put("relevance", query.getRelevance());
            queryObject.put("fact_reason", query.getReason());
            //质证类型：1-被告及其他原告质证，2-原告及其他被告质证，3-反诉被告质证，4-反诉原告及其他反诉被告质证
            String queryType = query.getQueryType().toString();
            if ("1".equals(queryType)) {
                defendantAndOtherAccuserQueryArray.add(queryObject);
            } else if ("2".equals(queryType)) {
                accuserAndOtherDefendantQueryArray.add(queryObject);
            }
        }
        if (defendantAndOtherAccuserQueryArray == null || defendantAndOtherAccuserQueryArray.size() <= 0) {
            defendantAndOtherAccuserQueryArray.add(blankQueryObject());
        }
        if (accuserAndOtherDefendantQueryArray == null || accuserAndOtherDefendantQueryArray.size() <= 0) {
            accuserAndOtherDefendantQueryArray.add(blankQueryObject());
        }
        courtInvestigateObject.put("defendant_and_other_accuser_query", defendantAndOtherAccuserQueryArray);
        courtInvestigateObject.put("accuser_and_other_defendant_query", accuserAndOtherDefendantQueryArray);

        BasicInfo basicInfo = getBasicInfo(courtNumber);
        courtInvestigateObject.put("is_defendant_evidence", basicInfo.getIsDefendantEvidence());
    }

    public List<Query> getQueries(String courtNumber) {
        LambdaQueryWrapper<Query> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Query::getCourtNumber, courtNumber);
        queryWrapper.eq(Query::getDelFlag, YesOrNotEnum.N.getCode());
        return queryService.list(queryWrapper);
    }

    public JSONObject blankQueryObject() {
        JSONObject queryObject = new JSONObject();
        queryObject.put("name", "");
        queryObject.put("evidence", "");
        queryObject.put("facticity", "1");
        queryObject.put("legality", "1");
        queryObject.put("relevance", "1");
        queryObject.put("fact_reason", "");
        return queryObject;
    }


    @Override
    public PageResult<BasicInfo> findPage(BasicInfoRequest basicInfoRequest) {
        LambdaQueryWrapper<BasicInfo> wrapper = this.createWrapper(basicInfoRequest);
        Page<BasicInfo> page = this.page(PageFactory.defaultPage(), wrapper);
        return PageResultFactory.createPageResult(page);
    }

    @Override
    public void editStatus(BasicInfoRequest basicInfoRequest) {
        Long basicId = basicInfoRequest.getBasicId();
        Integer status = basicInfoRequest.getStatus();
        // 更新枚举，更新只能更新未删除状态的
        LambdaUpdateWrapper<BasicInfo> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(BasicInfo::getBasicId, basicId).and(i -> i.ne(BasicInfo::getDelFlag, YesOrNotEnum.Y.getCode())).set(BasicInfo::getStatus, status);
        this.update(updateWrapper);

        LoginUser loginUser = LoginContext.me().getLoginUser();
        // 清除缓存中的用户信息
        sysUserCacheOperatorApi.remove(String.valueOf(loginUser.getUserId()));
    }

    /**
     * 修改删除标记
     */
    @Override
    public void delete(BasicInfoRequest basicInfoRequest) {
        String courtNumber = basicInfoRequest.getCourtNumber();
        basicInfoService.deleteBasicInfo(courtNumber);
        accuserService.deleteAccuserInfo(courtNumber);
        defendantService.deleteDefendantInfo(courtNumber);
        agentService.deleteAgentInfo(courtNumber);
        stateService.deleteStateInfo(courtNumber);
        argueService.deleteArgueInfo(courtNumber);
        inquiryService.deleteInquiryInfo(courtNumber);
        queryService.deleteQueryInfo(courtNumber);
        proofService.deleteProofInfo(courtNumber);
        replyService.deleteReplyInfo(courtNumber);
        allegeService.deleteAllegeInfo(courtNumber);
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
        if (!superAdminFlag) {
            //非超级管理员只能看到自己的笔录
            Long userId = LoginContext.me().getLoginUser().getUserId();
            queryWrapper.eq(ObjectUtil.isNotEmpty(userId), BasicInfo::getUserId, userId);
        }
        Long basicId = basicInfoRequest.getBasicId();
        String courtNumber = basicInfoRequest.getCourtNumber();
        String courtCause = basicInfoRequest.getCourtCause();
        String judge = basicInfoRequest.getJudge();
        String courtClerk = basicInfoRequest.getCourtClerk();

        queryWrapper.eq(ObjectUtil.isNotEmpty(basicId), BasicInfo::getBasicId, basicId);
        queryWrapper.eq(ObjectUtil.isNotEmpty(courtNumber), BasicInfo::getCourtNumber, courtNumber);
        queryWrapper.like(ObjectUtil.isNotEmpty(courtCause), BasicInfo::getCourtCause, courtCause);
        queryWrapper.eq(ObjectUtil.isNotEmpty(judge), BasicInfo::getJudge, judge);
        queryWrapper.eq(ObjectUtil.isNotEmpty(courtClerk), BasicInfo::getJudge, courtClerk);

        // 查询未删除状态的
        queryWrapper.eq(BasicInfo::getDelFlag, YesOrNotEnum.N.getCode());

        return queryWrapper;
    }

}

