/*
 * Copyright [2020-2030] [https://www.stylefeng.cn]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Guns采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1.请不要删除和修改根目录下的LICENSE文件。
 * 2.请不要删除和修改Guns源码头部的版权声明。
 * 3.请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 https://gitee.com/stylefeng/guns
 * 5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://gitee.com/stylefeng/guns
 * 6.若您的项目无法满足以上几点，可申请商业授权
 */
package cn.stylefeng.guns.modular.controller;

import cn.stylefeng.guns.modular.FileUtils;
import cn.stylefeng.guns.modular.entity.*;
import cn.stylefeng.guns.modular.enums.CounterClaimEnum;
import cn.stylefeng.guns.modular.enums.DefendantEvidenceEnum;
import cn.stylefeng.guns.modular.enums.RequestTypeEnum;
import cn.stylefeng.guns.modular.model.request.BasicInfoRequest;
import cn.stylefeng.guns.modular.service.*;
import cn.stylefeng.guns.utils.WordUtil;
import cn.stylefeng.roses.kernel.rule.pojo.response.ResponseData;
import cn.stylefeng.roses.kernel.rule.pojo.response.SuccessResponseData;
import cn.stylefeng.roses.kernel.rule.util.HttpServletUtil;
import cn.stylefeng.roses.kernel.scanner.api.annotation.ApiResource;
import cn.stylefeng.roses.kernel.scanner.api.annotation.GetResource;
import cn.stylefeng.roses.kernel.scanner.api.annotation.PostResource;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 笔录基本信息控制器
 *
 * @author 金波
 * @date 2022/01/14 15:07
 */
@RestController
@ApiResource(name = "笔录基本信息")
public class RecordController {

    private static final SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒");
    @Resource
    private BasicInfoService basicInfoService;
    @Resource
    private AccuserService accuserService;
    @Resource
    private DefendantService defendantService;
    @Resource
    private ThirdPartyService thirdPartyService;
    @Resource
    private StateService stateService;
    @Resource
    private AgentService agentService;
    @Resource
    private ArgueService argueService;
    @Resource
    private InquiryService inquiryService;
    @Resource
    private AllegeService allegeService;
    @Resource
    private ReplyService replyService;
    @Resource
    private ProofService proofService;
    @Resource
    private QueryService queryService;
    @Resource
    private CourtCauseService courtCauseService;
    @Resource
    private QuestionService questionService;
    @Resource
    private ClerkRelationService clerkRelationService;
    @Resource
    private JudgeSpeakService judgeSpeakService;
    @Resource
    private JudgeRandomInquiryService judgeRandomInquiryService;

    @Value("${record.backup.path}")
    private String backupPath;

    @Value("${record.templates.template-path}")
    private String templatePath;

    @Value("${record.templates.generate-path}")
    private String generatePath;

    @Value("${record.templates.template-name}")
    private String templateName;

    /**
     * 分页查询笔录基本信息(系统首页)
     *
     * @author 金波
     * @date 2022/01/14
     */
    @RequestMapping(name = "分页查询笔录", path = "/record/page")
    @ResponseBody
    public ResponseData page(BasicInfoRequest basicInfoRequest) {
        return new SuccessResponseData(basicInfoService.findPage(basicInfoRequest));
    }

    /**
     * 保存笔录信息
     * <p>
     *
     * @author 金波
     * @date 2022/05/22
     */
    @PostResource(name = "保存笔录信息", path = "/record/add")
    public ResponseData saveRecord(@RequestBody String recordJsonStr) {
        System.out.println("提交的数据：" + recordJsonStr);
        //每次提交的recordJson保存一份在本地
        FileUtils.writerFile(recordJsonStr, backupPath);

        JSONObject recordJson = JSONObject.parseObject(recordJsonStr);
        JSONObject recordJsonObject = recordJson.getJSONObject("recordJson");
        String courtNumber = "";
        String courtCause = "";
        if (recordJsonObject.containsKey("basicInfo")) {
            String basicInfo = recordJsonObject.getString("basicInfo");
            JSONObject basicInfoObject = JSONObject.parseObject(basicInfo);
            courtNumber = basicInfoObject.getString("court_number");
            courtCause = basicInfoObject.getString("court_cause");
        }
        if (ObjectUtils.isEmpty(courtNumber)) {
            return new SuccessResponseData("案号不能为空");
        }
        String requestType = recordJson.getString("requestType");
        List<BasicInfo> basicInfoList = basicInfoService.getBasicInfoList(courtNumber);
        if (basicInfoList != null && basicInfoList.size() > 0) {
            if (!ObjectUtils.isEmpty(requestType) && requestType.equals(RequestTypeEnum.NEW.getCode())) {
                return new SuccessResponseData("案号不能重复");
            }
            //编辑笔录，若案号已存在，则清空库中当前案号的信息，重新插入最新数据
            basicInfoService.delete(courtNumber);
            accuserService.delete(courtNumber);
            defendantService.delete(courtNumber);
            thirdPartyService.delete(courtNumber);
            agentService.delete(courtNumber);
            stateService.delete(courtNumber);
            argueService.delete(courtNumber);
            inquiryService.delete(courtNumber);
            queryService.delete(courtNumber);
            proofService.delete(courtNumber);
            replyService.delete(courtNumber);
            allegeService.delete(courtNumber);
            judgeRandomInquiryService.delete(courtNumber);
            judgeSpeakService.delete(courtNumber);
        }
        //基本信息
        basicInfoService.saveBasicInfo(courtNumber, recordJsonObject);
        //原告信息
        accuserService.saveAccuserInfo(courtNumber, recordJsonObject);
        //被告信息
        defendantService.saveDefendantInfo(courtNumber, recordJsonObject);
        //第三人信息
        thirdPartyService.saveThirdPartyInfo(courtNumber, recordJsonObject);
        //委托诉讼代理人
        agentService.saveAgentInfo(courtNumber, recordJsonObject);
        //基本信息陈述
        stateService.saveStateInfo(courtNumber, recordJsonObject);
        //原告诉讼请求项和事实与理由
        allegeService.saveAccuserClaimItem(courtNumber, CounterClaimEnum.NOT_COUNTER_CLAIM.getCode(), recordJsonObject);
        //法官随机提问（审判员最后陈述前）
        judgeRandomInquiryService.saveJudgeRandomInquiryInfo(courtNumber, CounterClaimEnum.NOT_COUNTER_CLAIM.getCode(), recordJsonObject);
        //被告答辩
        replyService.saveDefendantReply(courtNumber, CounterClaimEnum.NOT_COUNTER_CLAIM.getCode(), recordJsonObject);

        JSONObject courtInvestigateObject = null;
        if (recordJsonObject.containsKey("courtInvestigate")) {
            String courtInvestigate = recordJsonObject.getString("courtInvestigate");
            courtInvestigateObject = JSONObject.parseObject(courtInvestigate);
        }
        //被告是否举证
        String defendantEvidence = "";
        if (courtInvestigateObject != null && courtInvestigateObject.containsKey("is_defendant_evidence")) {
            defendantEvidence = courtInvestigateObject.getString("is_defendant_evidence");
        }
        //是否反诉
        String counterClaim = "";
        if (courtInvestigateObject != null && courtInvestigateObject.containsKey("is_counterclaim")) {
            counterClaim = courtInvestigateObject.getString("is_counterclaim");
        }
        //反诉原告诉讼请求项
        if (!"".equals(counterClaim) && counterClaim.equals(CounterClaimEnum.COUNTER_CLAIM.getCode())) {
            allegeService.saveCounterClaimAccuserItem(courtNumber, CounterClaimEnum.COUNTER_CLAIM.getCode(), recordJsonObject);
        }
        //反诉被告今日是否答辩
        String counterClaimDefendantTodayIsReply = "";
        if (courtInvestigateObject != null && courtInvestigateObject.containsKey("counterclaim_defendant_today_is_reply")) {
            counterClaimDefendantTodayIsReply = courtInvestigateObject.get("counterclaim_defendant_today_is_reply").toString();
        }
        //反诉被告今日不答辩，则提前结束
        if (!"".equals(counterClaim) && !"".equals(counterClaimDefendantTodayIsReply) && counterClaim.equals(CounterClaimEnum.COUNTER_CLAIM.getCode()) && "2".equals(counterClaimDefendantTodayIsReply)) {
            return new SuccessResponseData("反诉被告今日不答辩，提前结束！");
        }

        //法庭辩论信息
        argueService.saveArgueInfo(courtNumber, counterClaim, recordJsonObject);
        //法庭询问信息
        inquiryService.saveInquiryInfo(courtNumber, counterClaim, recordJsonObject);
        //原告举证
        proofService.saveAccuserEvidence(courtNumber, CounterClaimEnum.NOT_COUNTER_CLAIM.getCode(), recordJsonObject);
        //被告及其他原告质证
        queryService.saveDefendantAndOtherAccuserQuery(courtNumber, CounterClaimEnum.NOT_COUNTER_CLAIM.getCode(), recordJsonObject);

        if (!"".equals(defendantEvidence) && defendantEvidence.equals(DefendantEvidenceEnum.Y.getCode())) {
            //被告举证
            proofService.saveDefendantEvidence(courtNumber, CounterClaimEnum.NOT_COUNTER_CLAIM.getCode(), recordJsonObject);
            //原告及其他被告质证
            queryService.saveAccuserAndOtherDefendantQuery(courtNumber, CounterClaimEnum.NOT_COUNTER_CLAIM.getCode(), recordJsonObject);
        }

        if (!"".equals(counterClaim) && counterClaim.equals(CounterClaimEnum.COUNTER_CLAIM.getCode())) {
            //反诉被告答辩
            replyService.saveCounterClaimDefendantReply(courtNumber, CounterClaimEnum.COUNTER_CLAIM.getCode(), recordJsonObject);
            //反诉原告举证
            proofService.saveCounterClaimAccuserEvidence(courtNumber, CounterClaimEnum.COUNTER_CLAIM.getCode(), recordJsonObject);
            //反诉被告质证
            queryService.saveCounterClaimDefendantQuery(courtNumber, CounterClaimEnum.COUNTER_CLAIM.getCode(), recordJsonObject);
            //反诉被告举证
            proofService.saveCounterClaimDefendantEvidence(courtNumber, CounterClaimEnum.COUNTER_CLAIM.getCode(), recordJsonObject);
            //反诉原告质证
            queryService.saveCounterClaimAccuserQuery(courtNumber, CounterClaimEnum.COUNTER_CLAIM.getCode(), recordJsonObject);
            //其他反诉被告质证
            queryService.saveOtherCounterClaimDefendantQuery(courtNumber, CounterClaimEnum.COUNTER_CLAIM.getCode(), recordJsonObject);
        }
        //保存所有审判员说的话
        judgeSpeakService.saveJudgeSpeaks(courtNumber,courtCause,recordJsonObject);
        return new SuccessResponseData();
    }

    /**
     * 查看笔录详情
     *
     * @author 金波
     * @date 2022/05/25
     */
    @GetResource(name = "查看笔录详情", path = "/record/detail")
    public ResponseData recordDetail(String courtNumber) {
        JSONObject recordJson = new JSONObject();

        //基本信息
        JSONObject basicInfoObject = basicInfoService.getBasicInfoObject(courtNumber);
        recordJson.put("basicInfo", basicInfoObject);

        //原告信息
        JSONArray accuserInfoArray = accuserService.getAccuserInfoArray(courtNumber);
        recordJson.put("accuserInfo", accuserInfoArray);

        //被告信息
        JSONArray defendantInfoArray = defendantService.getDefendantInfoArray(courtNumber);
        recordJson.put("defendantInfo", defendantInfoArray);

        //第三人信息
        JSONArray thirdPartyInfoArray = thirdPartyService.getThirdPartyInfoArray(courtNumber);
        recordJson.put("thirdPartyInfo", thirdPartyInfoArray);

        //基本信息陈述
        JSONObject stateInfoObject = stateService.getStateInfoObject(courtNumber);
        recordJson.put("stateInfo", stateInfoObject);

        //权利告知
        JSONObject rightInfoObject = basicInfoService.getRightInfoObject(courtNumber);
        recordJson.put("rightInfo", rightInfoObject);

        //法庭调查
        JSONObject courtInvestigateObject = basicInfoService.getCourtInvestigateObject(courtNumber);
        recordJson.put("courtInvestigate", courtInvestigateObject);

        //法庭询问
        JSONArray inquiryInfoArray = inquiryService.getInquiryInfoArray(courtNumber);
        recordJson.put("inquiryInfo", inquiryInfoArray);

        //法庭辩论
        JSONObject argueInfoObject = argueService.getArgueInfoObject(courtNumber);
        recordJson.put("argueInfo", argueInfoObject);

        //最后陈述意见
        JSONArray finalStatementInfoArray = basicInfoService.getFinalStatementInfoArray(courtNumber);
        recordJson.put("finalStatementInfo", finalStatementInfoArray);

        //是否能够调解
        JSONObject mediateInfoObject = basicInfoService.getMediateInfoObject(courtNumber);
        recordJson.put("mediateInfo", mediateInfoObject);

        //电子裁判文书送达
        JSONArray deliveryInfoArray = basicInfoService.getDiliveryInfoArray(courtNumber);
        recordJson.put("deliveryInfo", deliveryInfoArray);

        //审判员随机提问（审判员最终陈述前）
        JSONArray judgeRandomInquiryBeforeSummarize = judgeRandomInquiryService.getJudgeRandomInquiryBeforeSummarize(courtNumber);
        recordJson.put("judge_inquiry_before_summarize", judgeRandomInquiryBeforeSummarize);

        //审判员最终总结
        String summarize = basicInfoService.getSummarize(courtNumber);
        recordJson.put("summarize", summarize);

        //审判员说的话
        judgeSpeakService.getJudgeSpeaks(courtNumber,recordJson);

        System.out.println("回显的数据：" + recordJson.toString());

        return new SuccessResponseData(recordJson);
    }

    /**
     * 获取案由列表
     *
     * @author 金波
     * @date 2022/05/31
     */
    @GetResource(name = "案由列表", path = "/record/court/cause")
    public ResponseData getCourtCauseList() {
        List<String> courtCauseList = courtCauseService.getCourtCauseList();
        return new SuccessResponseData(courtCauseList);
    }

    /**
     * 获取法庭询问的问题列表
     *
     * @author 金波
     * @date 2022/05/31
     */
    @GetResource(name = "问题列表", path = "/record/question")
    public ResponseData getQuestionList() {
        List<String> questionList = questionService.getQuestionList();
        return new SuccessResponseData(questionList);
    }

    /**
     * 获取书记员、审判员、开庭地点对应关系
     *
     * @author 金波
     * @date 2022/05/31
     */
    @GetResource(name = "书记员、审判员、开庭地点对应关系", path = "/record/clerk/relation")
    public ResponseData getClerkJudgePlaceRelation() {
        JSONObject clerkJudgePlaceRelation = clerkRelationService.getClerkJudgePlaceRelation();
        return new SuccessResponseData(clerkJudgePlaceRelation);
    }

    /**
     * 修改案件状态
     *
     * @author 金波
     * @date 2022/06/04
     */
    @PostResource(name = "修改案件状态", path = "/record/changeStatus")
    public ResponseData changeStatus(@RequestBody @Validated(BasicInfoRequest.changeStatus.class) BasicInfoRequest basicInfoRequest) {
        basicInfoService.editStatus(basicInfoRequest);
        return new SuccessResponseData();
    }

    /**
     * 删除笔录
     *
     * @author 金波
     * @date 2022/06/04
     */
    @PostResource(name = "删除笔录", path = "/record/delete")
    public ResponseData delete(@RequestBody @Validated(BasicInfoRequest.delete.class) BasicInfoRequest basicInfoRequest) {
        basicInfoService.delete(basicInfoRequest);
        return new SuccessResponseData();
    }

    /**
     * 生成笔录
     *
     * @author 金波
     * @date 2022/06/08
     */
    @PostResource(name = "生成笔录", path = "/record/generate")
    public ResponseData generateRecord(@RequestBody String courtNumberJson) {
        String courtNumber = JSONObject.parseObject(courtNumberJson).getString("courtNumber");
        String generateFile = generatePath + courtNumber + "-" + simpleFormat.format(new Date()) + ".doc";

        Map<String, Object> recordMap = new HashMap<>();
        BasicInfo basicInfo = basicInfoService.getBasicInfo(courtNumber);
        recordMap.put("basicInfo", basicInfo);

        List<Accuser> accuserList = accuserService.getAccuserInfoList(courtNumber);
        recordMap.put("accuserList", accuserList);

        List<Defendant> defendantList = defendantService.getDefendantInfoList(courtNumber);
        recordMap.put("defendantList", defendantList);

        JSONObject judgeRandomInquiry = judgeRandomInquiryService.getJudgeRandomInquiry(courtNumber);
        recordMap.put("judgeInquiryAfterAccuserClaimArray", judgeRandomInquiry.getJSONArray("judge_inquiry_after_accuser_claim"));
        recordMap.put("judgeInquiryAfterDefendantReplyArray", judgeRandomInquiry.getJSONArray("judge_inquiry_after_defendant_reply"));
        recordMap.put("judgeInquiryBeforeSummarizeArray", judgeRandomInquiry.getJSONArray("judge_inquiry_before_summarize"));

        List<ThirdParty> thirdPartyList = thirdPartyService.getThirdPartyInfoList(courtNumber);
        recordMap.put("thirdPartyList", thirdPartyList);

        State stateInfo = stateService.getStateInfo(courtNumber);
        recordMap.put("state", stateInfo);

        CourtInvestigate courtInvestigateInfo = basicInfoService.getCourtInvestigateInfo(courtNumber);
        recordMap.put("courtInvestigate", courtInvestigateInfo);

        List<Reply> defendantReplyList = basicInfoService.getDefendantReply(courtNumber);
        recordMap.put("defendantReplyList", defendantReplyList);

        List<Query> defendantQueryList = basicInfoService.getDefendantQuery(courtNumber);
        recordMap.put("defendantQueryList", defendantQueryList);

        List<Inquiry> inquiryInfoList = inquiryService.getInquiryInfoList(courtNumber);
        recordMap.put("inquiryList", inquiryInfoList);

        List<Argue> argueList = argueService.getArgueList(courtNumber);
        recordMap.put("argueList", argueList);

        WordUtil.generateWord(recordMap, templatePath, templateName, generateFile);
        return new SuccessResponseData();
    }

    /**
     * 读取生成的笔录文件
     *
     * @author jinbo
     * @date 2022/6/17
     */
    @GetResource(name = "读取生成的笔录文件列表", path = "/record/list")
    public ResponseData recordList(String courtNumber) {
        List<Record> records = WordUtil.listRecord(generatePath, courtNumber);
        return new SuccessResponseData(records);
    }

    /**
     * 下载笔录
     *
     * @author jinbo
     * @date 2022/6/17
     */
    @GetResource(name = "下载笔录", path = "/record/download")
    public ResponseData recordDownload(String recordPath) throws UnsupportedEncodingException {
        HttpServletResponse response = HttpServletUtil.getResponse();
        String filepath = java.net.URLDecoder.decode(recordPath, "utf-8");
        if (!ObjectUtils.isEmpty(filepath)) {
            WordUtil.downloadRecord(response, recordPath);
        }
        return new SuccessResponseData();
    }

    /**
     * 删除已经生成的笔录
     *
     * @author jinbo
     * @date 2022/6/19
     */
    @PostResource(name = "删除已经生成的笔录", path = "/record/word/delete")
    public ResponseData recordDelete(@RequestBody Record record) {
        String recordPath = record.getRecordPath();
        Boolean deleteFlag = false;
        if (!ObjectUtils.isEmpty(recordPath)) {
            deleteFlag = WordUtil.deleteRecord(recordPath);
        }
        return new SuccessResponseData(deleteFlag);
    }
}
