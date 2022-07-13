package cn.stylefeng.guns.modular.service.Impl;

import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.modular.entity.*;
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
    private ProofService proofService;
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
    private CacheOperatorApi<SysUserDTO> sysUserCacheOperatorApi;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveBasicInfo(String courtNumber, JSONObject recordJsonObject) {
        BasicInfo basicInfo = new BasicInfo();

        //当前用户
        Long userId = LoginContext.me().getLoginUser().getUserId();
        basicInfo.setUserId(userId);
        //基本信息
        String basicInfoJsonStr = recordJsonObject.getString("basicInfo");
        JSONObject basicInfoObject = JSONObject.parseObject(basicInfoJsonStr);

        //立案时间
        if (basicInfoObject.containsKey("filing_time")) {
            basicInfo.setFilingTime(basicInfoObject.get("filing_time").toString());
        }
        //开庭时间
        if (basicInfoObject.containsKey("court_time")) {
            basicInfo.setCourtTime(basicInfoObject.get("court_time").toString());
        }
        //开庭地点
        if (basicInfoObject.containsKey("court_place")) {
            basicInfo.setCourtPlace(basicInfoObject.get("court_place").toString());
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
            basicInfo.setCourtClerk(basicInfoObject.get("court_clerk").toString());
        }
        //案由
        if (basicInfoObject.containsKey("court_cause")) {
            basicInfo.setCourtCause(basicInfoObject.get("court_cause").toString());
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
                String counterClaimDefendantTodayIsReply = courtInvestigateObject.get("counterclaim_defendant_today_is_reply").toString();
                basicInfo.setCounterClaimDefendantTodayIsReply(counterClaimDefendantTodayIsReply);
            }
        }
        //原被告都统一且法院最终确认的调解方案
        if (recordJsonObject.containsKey("mediateInfo")) {
            JSONObject mediateInfoObject = recordJsonObject.getJSONObject("mediateInfo");
            String finalMediatePlan = "";
            if (mediateInfoObject.containsKey("final_mediate_plan")) {
                finalMediatePlan = mediateInfoObject.get("final_mediate_plan").toString();
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
        LambdaQueryWrapper<BasicInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BasicInfo::getCourtNumber, courtNumber);
        queryWrapper.eq(BasicInfo::getDelFlag, YesOrNotEnum.N.getCode());
        BasicInfo basicInfo = this.getOne(queryWrapper);
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
        BasicInfo basicInfo = basicInfoService.getOne(basicInfoQueryWrapper);
        return basicInfo;
    }

    @Override
    public Boolean deleteBasicInfo(String courtNumber) {
        LambdaUpdateWrapper<BasicInfo> basicUpdateWrapper = new LambdaUpdateWrapper<>();
        basicUpdateWrapper.set(BasicInfo::getDelFlag, YesOrNotEnum.Y.getCode()).eq(BasicInfo::getCourtNumber, courtNumber);
        return basicInfoService.update(basicUpdateWrapper);
    }

    @Override
    public List<BasicInfo> getBasicInfoList(String courtNumber) {
        LambdaQueryWrapper<BasicInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BasicInfo::getCourtNumber, courtNumber);
        queryWrapper.eq(BasicInfo::getDelFlag, YesOrNotEnum.N.getCode());

        List<BasicInfo> basicInfos = basicInfoService.list(queryWrapper);
        return basicInfos;
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
        LambdaQueryWrapper<Accuser> accuserQueryWrapper = new LambdaQueryWrapper<>();
        accuserQueryWrapper.eq(Accuser::getCourtNumber, courtNumber);
        accuserQueryWrapper.eq(Accuser::getDelFlag, YesOrNotEnum.N.getCode());
        List<Accuser> accusers = accuserService.list(accuserQueryWrapper);
        if (null == accusers || accusers.size() == 0) {
            JSONObject accuserRightDutyObject = new JSONObject();
            accuserRightDutyObject.put("accuser", "");
            accuserRightDutyObject.put("right_duty", "");
            accuserRightDutyObject.put("avoid", "");
            accuserRightDutyArray.add(accuserRightDutyObject);
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
        rightInfoObject.put("accuser_right_duty", accuserRightDutyArray);
        //被告
        JSONArray defendantRightDutyArray = new JSONArray();
        LambdaQueryWrapper<Defendant> defendentQueryWrapper = new LambdaQueryWrapper<>();
        defendentQueryWrapper.eq(Defendant::getCourtNumber, courtNumber);
        defendentQueryWrapper.eq(Defendant::getDelFlag, YesOrNotEnum.N.getCode());
        List<Defendant> defendants = defendantService.list(defendentQueryWrapper);
        if (null == defendants || defendants.size() == 0) {
            JSONObject defendantRightDutyObject = new JSONObject();
            defendantRightDutyObject.put("defendant", "");
            defendantRightDutyObject.put("right_duty", "");
            defendantRightDutyObject.put("avoid", "");
            defendantRightDutyArray.add(defendantRightDutyObject);
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
        rightInfoObject.put("defendant_right_duty", defendantRightDutyArray);
        //第三人
        JSONArray thirdRightDutyArray = new JSONArray();
        LambdaQueryWrapper<ThirdParty> thirdQueryWrapper = new LambdaQueryWrapper<>();
        thirdQueryWrapper.eq(ThirdParty::getCourtNumber, courtNumber);
        thirdQueryWrapper.eq(ThirdParty::getDelFlag, YesOrNotEnum.N.getCode());
        List<ThirdParty> thirdParties = thirdPartyService.list(thirdQueryWrapper);
        if (null == thirdParties || thirdParties.size() <= 0) {
            JSONObject thirdRightDutyObject = new JSONObject();
            thirdRightDutyObject.put("third_party", "");
            thirdRightDutyObject.put("right_duty", "");
            thirdRightDutyObject.put("avoid", "");
            thirdRightDutyArray.add(thirdRightDutyObject);
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
        LambdaQueryWrapper<Accuser> accuserQueryWrapper = new LambdaQueryWrapper<>();
        accuserQueryWrapper.eq(Accuser::getCourtNumber, courtNumber);
        accuserQueryWrapper.eq(Accuser::getDelFlag, YesOrNotEnum.N.getCode());
        List<Accuser> accusers = accuserService.list(accuserQueryWrapper);

        //若原告为空
        if (null == accusers || accusers.size() == 0) {
            JSONObject accuserDeliveryObject = new JSONObject();
            accuserDeliveryObject.put("name", "");
            accuserDeliveryObject.put("is_delivery", "1");
            accuserDeliveryObject.put("email", "");
            diliveryInfoArray.add(accuserDeliveryObject);
        } else {
            for (int i = 0; i < accusers.size(); i++) {
                JSONObject accuserDeliveryObject = new JSONObject();
                Accuser accuser = accusers.get(i);
                String accuserShortName = accuser.getAccuserShort();
                String isDelivery = accuser.getIsDelivery();
                if (ObjectUtils.isEmpty(isDelivery)) {
                    isDelivery = "1";
                }
                String email = accuser.getEmail();

                accuserDeliveryObject.put("name", accuserShortName + "（原告）");
                accuserDeliveryObject.put("is_delivery", isDelivery);
                accuserDeliveryObject.put("email", email);
                diliveryInfoArray.add(accuserDeliveryObject);
            }
        }

        //被告
        LambdaQueryWrapper<Defendant> defendantQueryWrapper = new LambdaQueryWrapper<>();
        defendantQueryWrapper.eq(Defendant::getCourtNumber, courtNumber);
        defendantQueryWrapper.eq(Defendant::getDelFlag, YesOrNotEnum.N.getCode());
        List<Defendant> defendants = defendantService.list(defendantQueryWrapper);
        //若被告为空
        if (null == defendants || defendants.size() == 0) {
            JSONObject defendantDeliveryObject = new JSONObject();
            defendantDeliveryObject.put("name", "");
            defendantDeliveryObject.put("is_delivery", "1");
            defendantDeliveryObject.put("email", "");
            diliveryInfoArray.add(defendantDeliveryObject);
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
        LambdaQueryWrapper<ThirdParty> thirdPartyQueryWrapper = new LambdaQueryWrapper<>();
        thirdPartyQueryWrapper.eq(ThirdParty::getCourtNumber, courtNumber);
        thirdPartyQueryWrapper.eq(ThirdParty::getDelFlag, YesOrNotEnum.N.getCode());
        List<ThirdParty> thirdParties = thirdPartyService.list(thirdPartyQueryWrapper);
        if (null == thirdParties || thirdParties.size() <= 0) {
            JSONObject thirdPartyDeliveryObject = new JSONObject();
            thirdPartyDeliveryObject.put("name", "");
            thirdPartyDeliveryObject.put("is_delivery", "1");
            thirdPartyDeliveryObject.put("email", "");
            diliveryInfoArray.add(thirdPartyDeliveryObject);
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
        //答辩内容
        courtInvesReply(courtNumber, courtInvestigateObject);
        //举证内容
        courtInvesProof(courtNumber, courtInvestigateObject);
        //质证内容
        courtInvesQuery(courtNumber, courtInvestigateObject);
        return courtInvestigateObject;
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
     * 被告质证
     */
    @Override
    public List<Query> getDefendantQuery(String courtNumber) {
        List<Query> defendantQueryList = new ArrayList<>();
        JSONObject courtInvestigateObject = this.getCourtInvestigateObject(courtNumber);
        JSONArray defendantQueryArray = courtInvestigateObject.getJSONArray("defendant_query");
        for (int i = 0; i < defendantQueryArray.size(); i++) {
            JSONObject defendantQueryObject = defendantQueryArray.getJSONObject(i);
            String defendant = defendantQueryObject.getString("defendant");
            String evidence = defendantQueryObject.getString("evidence");
            String facticity = defendantQueryObject.getString("facticity");
            String legality = defendantQueryObject.getString("legality");
            String relevance = defendantQueryObject.getString("relevance");
            String defendantQueryFactReason = defendantQueryObject.getString("defendant_query_fact_reason");
            if (!ObjectUtils.isEmpty(defendant) && !ObjectUtils.isEmpty(evidence) && !ObjectUtils.isEmpty(defendantQueryFactReason)) {
                if (defendant.contains("**")) {
                    defendant = defendant.replace("**", "、");
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
                } else if (evidence.contains(":")) {
                    String evidenceNum = evidence.split(":")[0];
                    evidenceNumber += evidenceNum;
                }
                //TODO 做枚举
                if ("1".equals(facticity)) {
                    facticity = "认可";
                } else {
                    facticity = "不认可";
                }
                if ("1".equals(legality)) {
                    legality = "认可";
                } else {
                    facticity = "不认可";
                }
                if ("1".equals(relevance)) {
                    relevance = "认可";
                } else {
                    facticity = "不认可";
                }
                Query query = new Query();
                query.setName(defendant);
                if (!ObjectUtils.isEmpty(evidenceNumber) && evidenceNumber.contains("、")) {
                    evidenceNumber = evidenceNumber.substring(0, evidenceNumber.length() - 1);
                }
                query.setEvidence(evidenceNumber);
                query.setFacticity(facticity);
                query.setLegality(legality);
                query.setRelevance(relevance);
                query.setReason(defendantQueryFactReason);
                defendantQueryList.add(query);
            }
        }
        return defendantQueryList;
    }

    @Override
    public CourtInvestigate getCourtInvestigateInfo(String courtNumber) {
        JSONObject courtInvestigateObject = this.getCourtInvestigateObject(courtNumber);
        //原告诉讼请求
        String accuserClaimItem = courtInvestigateObject.getString("accuser_claim_item");
        String accuserClaimFactReason = courtInvestigateObject.getString("accuser_claim_fact_reason");

        //原告举证
        String accuserEvidence = "";
        JSONArray accuserEvidenceArray = courtInvestigateObject.getJSONArray("accuser_evidence");
        for (int i = 0; i < accuserEvidenceArray.size(); i++) {
            JSONObject accuserEvidenceObject = accuserEvidenceArray.getJSONObject(i);
            String serial = accuserEvidenceObject.getString("serial");
            String evidenceType = accuserEvidenceObject.getString("evidence_type");
            String evidence = accuserEvidenceObject.getString("evidence");
            String content = accuserEvidenceObject.getString("content");
            accuserEvidence += serial + "." + evidence + "(" + evidenceType + "),证明事项：" + content;
        }
        //被告质证
        String defendantQuery = "";
        JSONArray defendantQueryArray = courtInvestigateObject.getJSONArray("defendant_query");
        for (int i = 0; i < defendantQueryArray.size(); i++) {
            JSONObject defendantQueryObject = defendantQueryArray.getJSONObject(i);
            String defendant = defendantQueryObject.getString("defendant");
            String evidence = defendantQueryObject.getString("evidence");
            String defendantQueryFactReason = defendantQueryObject.getString("defendant_query_fact_reason");
            if (!ObjectUtils.isEmpty(defendant) && !ObjectUtils.isEmpty(evidence) && !ObjectUtils.isEmpty(defendantQueryFactReason)) {
                if (defendant.contains("**")) {
                    defendant = defendant.replace("**", "、");
                }
                if (evidence.contains("**")) {
                    evidence = evidence.replace("**", "、");
                }
                defendantQuery += defendant + "质证（" + evidence + "）；事实和理由：" + defendantQueryFactReason;
            }
        }
        CourtInvestigate courtInvestigate = new CourtInvestigate();
        courtInvestigate.setAccuserClaimItem(accuserClaimItem);
        courtInvestigate.setAccuserClaimFactReason(accuserClaimFactReason);
        courtInvestigate.setAccuserEvidence(accuserEvidence);
        courtInvestigate.setDefendantQuery(defendantQuery);
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
        } else {
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
                    courtInvestigateObject.put("counterclaim_accuser_claim_item", claimItem);
                    //反诉原告的事实和理由
                    courtInvestigateObject.put("counterclaim_accuser_fact_reason", factReason);
                }
                //是否反诉
                courtInvestigateObject.put("is_counterclaim", allege.getIsCounterClaim());
            }
        }
    }

    /**
     * 法庭调查-答辩内容
     */
    public void courtInvesReply(String courtNumber, JSONObject courtInvestigateObject) {
        LambdaQueryWrapper<Reply> replyQueryWrapper = new LambdaQueryWrapper<>();
        replyQueryWrapper.eq(Reply::getCourtNumber, courtNumber);
        replyQueryWrapper.eq(Reply::getDelFlag, YesOrNotEnum.N.getCode());
        List<Reply> replies = replyService.list(replyQueryWrapper);
        JSONArray defendantReplyArray = new JSONArray();
        JSONArray counterClaimDefendantReplyArray = new JSONArray();

        LambdaQueryWrapper<BasicInfo> basicInfoQueryWrapper = new LambdaQueryWrapper<>();
        basicInfoQueryWrapper.eq(BasicInfo::getCourtNumber, courtNumber);
        basicInfoQueryWrapper.eq(BasicInfo::getDelFlag, YesOrNotEnum.N.getCode());
        BasicInfo basicInfo = basicInfoService.getOne(basicInfoQueryWrapper);
        String counterClaimDefendantTodayIsReply = basicInfo.getCounterClaimDefendantTodayIsReply();

        //若答辩内容为空
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
        courtInvestigateObject.put("counterclaim_defendant_today_is_reply", counterClaimDefendantTodayIsReply);

        if (defendantReplyArray == null || defendantReplyArray.size() <= 0) {
            JSONObject defendantReplyObject = new JSONObject();
            defendantReplyObject.put("name", "");
            defendantReplyObject.put("content", "");
            defendantReplyArray.add(defendantReplyObject);
        }
        courtInvestigateObject.put("defendant_reply", defendantReplyArray);

        if (counterClaimDefendantReplyArray == null || counterClaimDefendantReplyArray.size() <= 0) {
            JSONObject counterClaimDefendantReplyObject = new JSONObject();
            counterClaimDefendantReplyObject.put("name", "");
            counterClaimDefendantReplyObject.put("content", "");
            counterClaimDefendantReplyArray.add(counterClaimDefendantReplyObject);
        }
        courtInvestigateObject.put("counterclaim_defendant_reply", counterClaimDefendantReplyArray);
    }

    /**
     * 法庭调查-举证内容
     */
    public void courtInvesProof(String courtNumber, JSONObject courtInvestigateObject) {
        LambdaQueryWrapper<Proof> proofQueryWrapper = new LambdaQueryWrapper<>();
        proofQueryWrapper.eq(Proof::getCourtNumber, courtNumber);
        proofQueryWrapper.eq(Proof::getDelFlag, YesOrNotEnum.N.getCode());
        List<Proof> proofs = proofService.list(proofQueryWrapper);
        JSONArray accuserEvidenceArray = new JSONArray();
        JSONArray defendantEvidenceArray = new JSONArray();
        JSONArray counterClaimAccuserEvidenceArray = new JSONArray();
        JSONArray counterClaimDefendantEvidenceArray = new JSONArray();

        for (int i = 0; i < proofs.size(); i++) {
            Proof proof = proofs.get(i);
            JSONObject evidenceObject = new JSONObject();
            evidenceObject.put("name", proof.getName());
            evidenceObject.put("serial", proof.getSerial());
            evidenceObject.put("evidence", proof.getEvidence());
            evidenceObject.put("evidence_type", proof.getEvidenceType());
            evidenceObject.put("content", proof.getContent());
            String type = proof.getType();
            if ("原告".equals(type)) {
                accuserEvidenceArray.add(evidenceObject);
            } else if ("被告".equals(type)) {
                defendantEvidenceArray.add(evidenceObject);
            } else if ("反诉原告".equals(type)) {
                counterClaimAccuserEvidenceArray.add(evidenceObject);
            } else if ("反诉被告".equals(type)) {
                counterClaimDefendantEvidenceArray.add(evidenceObject);
            }
        }

        if (accuserEvidenceArray.size() <= 0) {
            JSONObject accuserEvidenceObject = new JSONObject();
            accuserEvidenceObject.put("serial", "1");
            accuserEvidenceObject.put("evidence", "");
            accuserEvidenceObject.put("evidence_type", "");
            accuserEvidenceObject.put("content", "");
            accuserEvidenceArray.add(accuserEvidenceObject);
        }
        courtInvestigateObject.put("accuser_evidence", accuserEvidenceArray);

        if (defendantEvidenceArray.size() <= 0) {
            JSONObject defendantEvidenceObject = new JSONObject();
            defendantEvidenceObject.put("name","");
            defendantEvidenceObject.put("serial", "1");
            defendantEvidenceObject.put("evidence", "");
            defendantEvidenceObject.put("evidence_type", "");
            defendantEvidenceObject.put("content", "");
            defendantEvidenceArray.add(defendantEvidenceObject);
        }
        courtInvestigateObject.put("defendant_evidence", defendantEvidenceArray);

        if (counterClaimAccuserEvidenceArray.size() <= 0) {
            JSONObject counterClaimAccuserEvidenceObject = new JSONObject();
            counterClaimAccuserEvidenceObject.put("serial", "1");
            counterClaimAccuserEvidenceObject.put("evidence", "");
            counterClaimAccuserEvidenceObject.put("evidence_type", "");
            counterClaimAccuserEvidenceObject.put("content", "");
            counterClaimAccuserEvidenceArray.add(counterClaimAccuserEvidenceObject);
        }
        courtInvestigateObject.put("counterclaim_accuser_evidence", counterClaimAccuserEvidenceArray);

        if (counterClaimDefendantEvidenceArray.size() <= 0) {
            JSONObject counterClaimDefendantEvidenceObject = new JSONObject();
            counterClaimDefendantEvidenceObject.put("serial", "1");
            counterClaimDefendantEvidenceObject.put("evidence", "");
            counterClaimDefendantEvidenceObject.put("evidence_type", "");
            counterClaimDefendantEvidenceObject.put("content", "");
            counterClaimDefendantEvidenceArray.add(counterClaimDefendantEvidenceObject);
        }
        courtInvestigateObject.put("counterclaim_defendant_evidence", counterClaimDefendantEvidenceArray);
    }

    /**
     * 法庭调查-质证内容
     */
    public void courtInvesQuery(String courtNumber, JSONObject courtInvestigateObject) {
        JSONArray defendantAndOtherAccuserQueryArray = new JSONArray();
        JSONArray accuserAndOtherDefendantQueryArray = new JSONArray();

        LambdaQueryWrapper<Query> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Query::getCourtNumber, courtNumber);
        queryWrapper.eq(Query::getDelFlag, YesOrNotEnum.N.getCode());
        List<Query> queries = queryService.list(queryWrapper);
        for (int i = 0; i < queries.size(); i++) {
            Query query = queries.get(i);
            String name = query.getName();
            if (ObjectUtils.isEmpty(name)) {
                continue;
            }
            String reason = query.getReason();
            String queryType = query.getQueryType().toString();
            String evidence = query.getEvidence();
            String facticity = query.getFacticity();
            String legality = query.getLegality();
            String relevance = query.getRelevance();
            //质证类型：1-被告及其他原告质证，2-原告及其他被告质证，3-反诉被告质证，4-反诉原告及其他反诉被告质证
            if ("1".equals(queryType)) {
                JSONObject defendantAndOtherAccuserQueryObject = new JSONObject();
                defendantAndOtherAccuserQueryObject.put("name", name);
                defendantAndOtherAccuserQueryObject.put("evidence", evidence);
                defendantAndOtherAccuserQueryObject.put("facticity", facticity);
                defendantAndOtherAccuserQueryObject.put("legality", legality);
                defendantAndOtherAccuserQueryObject.put("relevance", relevance);
                defendantAndOtherAccuserQueryObject.put("fact_reason", reason);
                defendantAndOtherAccuserQueryArray.add(defendantAndOtherAccuserQueryObject);
            } else if ("2".equals(queryType)) {
                JSONObject accuserAndOtherDefendantQueryObject = new JSONObject();
                accuserAndOtherDefendantQueryObject.put("name", name);
                accuserAndOtherDefendantQueryObject.put("evidence", evidence);
                accuserAndOtherDefendantQueryObject.put("facticity", facticity);
                accuserAndOtherDefendantQueryObject.put("legality", legality);
                accuserAndOtherDefendantQueryObject.put("relevance", relevance);
                accuserAndOtherDefendantQueryObject.put("fact_reason", reason);
                accuserAndOtherDefendantQueryArray.add(accuserAndOtherDefendantQueryObject);
            }
        }
        if (defendantAndOtherAccuserQueryArray == null || defendantAndOtherAccuserQueryArray.size() <= 0) {
            JSONObject defendantAndOtherAccuserQueryObject = new JSONObject();
            defendantAndOtherAccuserQueryObject.put("name", "");
            defendantAndOtherAccuserQueryObject.put("evidence", "");
            defendantAndOtherAccuserQueryObject.put("facticity", "1");
            defendantAndOtherAccuserQueryObject.put("legality", "1");
            defendantAndOtherAccuserQueryObject.put("relevance", "1");
            defendantAndOtherAccuserQueryObject.put("fact_reason", "");
            defendantAndOtherAccuserQueryArray.add(defendantAndOtherAccuserQueryObject);
        }
        if (accuserAndOtherDefendantQueryArray == null || accuserAndOtherDefendantQueryArray.size() <= 0) {
            JSONObject accuserAndOtherDefendantQueryObject = new JSONObject();
            accuserAndOtherDefendantQueryObject.put("name", "");
            accuserAndOtherDefendantQueryObject.put("evidence", "");
            accuserAndOtherDefendantQueryObject.put("facticity", "1");
            accuserAndOtherDefendantQueryObject.put("legality", "1");
            accuserAndOtherDefendantQueryObject.put("relevance", "1");
            accuserAndOtherDefendantQueryObject.put("fact_reason", "");
            accuserAndOtherDefendantQueryArray.add(accuserAndOtherDefendantQueryObject);
        }
        courtInvestigateObject.put("defendant_and_other_accuser_query", defendantAndOtherAccuserQueryArray);
        courtInvestigateObject.put("accuser_and_other_defendant_query", accuserAndOtherDefendantQueryArray);

        LambdaQueryWrapper<BasicInfo> basicInfoWrapper = new LambdaQueryWrapper<>();
        basicInfoWrapper.eq(BasicInfo::getCourtNumber, courtNumber);
        basicInfoWrapper.eq(BasicInfo::getDelFlag, YesOrNotEnum.N.getCode());
        BasicInfo basicInfo = basicInfoService.getOne(basicInfoWrapper);
        courtInvestigateObject.put("is_defendant_evidence", basicInfo.getIsDefendantEvidence());
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

