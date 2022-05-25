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

import cn.stylefeng.guns.modular.model.request.BasicInfoRequest;
import cn.stylefeng.guns.modular.service.*;
import cn.stylefeng.roses.kernel.rule.pojo.response.ResponseData;
import cn.stylefeng.roses.kernel.rule.pojo.response.SuccessResponseData;
import cn.stylefeng.roses.kernel.scanner.api.annotation.ApiResource;
import cn.stylefeng.roses.kernel.scanner.api.annotation.GetResource;
import cn.stylefeng.roses.kernel.scanner.api.annotation.PostResource;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 笔录基本信息控制器
 *
 * @author 金波
 * @date 2022/01/14 15:07
 */
@RestController
@ApiResource(name = "笔录基本信息")
public class RecordController {

    @Resource
    private BasicInfoService basicInfoService;

    @Resource
    private AccuserService accuserService;

    @Resource
    private DefendantService defendantService;

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


    /**
     * 编辑笔录基本信息
     *
     * @author 金波
     * @date 2022/01/14
     */
    @PostResource(name = "编辑笔录基本信息", path = "/basic/edit")
    public ResponseData edit(@RequestBody @Validated(BasicInfoRequest.edit.class) BasicInfoRequest basicInfoRequest) {

        return new SuccessResponseData();
    }

    /**
     * 删除笔录
     *
     * @author 金波
     * @date 2022/01/14
     */
    @PostResource(name = "删除笔录基本信息", path = "/basic/delete")
    public ResponseData delete(@RequestBody @Validated(BasicInfoRequest.delete.class) BasicInfoRequest basicInfoRequest) {

        return new SuccessResponseData();
    }

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
     *
     * @author 金波
     * @date 2022/05/22
     */
    @PostResource(name = "保存笔录信息", path = "/record/add")
    public ResponseData add(@RequestBody String recordJson) {
        JSONObject recordJsonObject = JSONObject.parseObject(recordJson);
        JSONObject basicInfoObject = JSONObject.parseObject(recordJsonObject.getString("basicInfo"));
        JSONObject courtInvestigateObject = JSONObject.parseObject(recordJsonObject.getString("courtInvestigate"));
        //案号
        String courtNumber = basicInfoObject.get("court_number").toString();
        //是否反诉
        String counterClaim = courtInvestigateObject.get("is_counterclaim").toString();

        //TODO 被告今日不答辩，流程结束

        //基本信息
        basicInfoService.saveBasicInfo(courtNumber, recordJsonObject);
        //原告信息
        accuserService.saveAccuserInfo(courtNumber, recordJsonObject);
        //被告信息
        defendantService.saveDefendantInfo(courtNumber, recordJsonObject);
        //委托诉讼代理人
        agentService.saveAgentInfo(courtNumber, recordJsonObject);
        //基本信息陈述
        stateService.saveStateInfo(courtNumber, recordJsonObject);

        //法庭辩论信息
        argueService.saveArgueInfo(courtNumber, counterClaim, recordJsonObject);
        //法庭询问信息
        inquiryService.saveInquiryInfo(courtNumber, counterClaim, recordJsonObject);

        //原告诉称信息
        allegeService.saveAccuserClaimItem(courtNumber, "2", recordJsonObject);
        //反诉原告诉称信息
        allegeService.saveCounterClaimAccuserItem(courtNumber, "1", recordJsonObject);

        //被告答辩
        replyService.saveDefendantReply(courtNumber, "2", recordJsonObject);
        //反诉被告答辩
        replyService.saveCounterClaimDefendantReply(courtNumber, "1", recordJsonObject);

        //原告举证
        proofService.saveAccuserEvidence(courtNumber, "2", recordJsonObject);
        //被告举证
        proofService.saveDefendantEvidence(courtNumber, "2", recordJsonObject);
        //反诉原告举证
        proofService.saveCounterClaimAccuserEvidence(courtNumber, "1", recordJsonObject);
        //反诉被告举证
        proofService.saveCounterClaimDefendantEvidence(courtNumber, "1", recordJsonObject);

        //被告质证
        queryService.saveDefendantQuery(courtNumber, "2", recordJsonObject);
        //原告质证
        queryService.saveAccuserQuery(courtNumber, "2", recordJsonObject);
        //其他被告质证
        queryService.saveOtherDefendantQuery(courtNumber, "2", recordJsonObject);
        //反诉被告质证
        queryService.saveCounterClaimDefendantQuery(courtNumber, "1", recordJsonObject);
        //反诉原告质证
        queryService.saveCounterClaimAccuserQuery(courtNumber, "1", recordJsonObject);
        //其他反诉被告质证
        queryService.saveOtherCounterClaimDefendantQuery(courtNumber, "1", recordJsonObject);

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

        //基本信息陈述
        JSONObject stateInfoObject = stateService.getStateInfoObject(courtNumber);
        recordJson.put("stateInfo", stateInfoObject);

        //权利告知
        JSONObject rightInfoObject = basicInfoService.getRightInfoObject(courtNumber);
        recordJson.put("rightInfo", rightInfoObject);

        //法庭调查
        //JSONObject courtInvestigateObject = new JSONObject();
        //recordJson.put("courtInvestigate", courtInvestigateObject);

        //法庭询问
        JSONArray inquiryInfoArray = inquiryService.getInquiryInfoArray(courtNumber);
        recordJson.put("inquiryInfo", inquiryInfoArray);

        //法庭辩论
        //JSONObject argueInfoObject = argueService.getArgueInfoObject(courtNumber);
        //recordJson.put("argueInfo", argueInfoObject);

        //最后陈述意见
        //JSONArray finalStatementInfoArray = new JSONArray();
        //recordJson.put("finalStatementInfo", finalStatementInfoArray);

        //是否能够调解
        //JSONObject mediateInfoObject = new JSONObject();
        //recordJson.put("mediateInfo", mediateInfoObject);

        //电子裁判文书送达
        //JSONArray deliveryInfoArray = new JSONArray();
        //recordJson.put("deliveryInfo", deliveryInfoArray);

        return new SuccessResponseData(recordJson.toString());
    }
}
