package cn.stylefeng.guns.modular.service.Impl;

import cn.stylefeng.guns.modular.entity.JudgeRandomInquiry;
import cn.stylefeng.guns.modular.entity.ThirdParty;
import cn.stylefeng.guns.modular.mapper.JudgeRandomInquiryMapper;
import cn.stylefeng.guns.modular.service.JudgeRandomInquiryService;
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
 * 法官随机提问表 服务实现类
 * </p>
 *
 * @author jinbo
 * @since 2022-07-13
 */
@Service
public class JudgeRandomInquiryServiceImpl extends ServiceImpl<JudgeRandomInquiryMapper, JudgeRandomInquiry> implements JudgeRandomInquiryService {

    @Resource
    private JudgeRandomInquiryService judgeRandomInquiryService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveJudgeRandomInquiryInfo(String courtNumber, String counterClaim, JSONObject recordJsonObject) {

        JSONObject courtInvestigateObject = recordJsonObject.getJSONObject("courtInvestigate");

        if (courtInvestigateObject.containsKey("judge_inquiry_after_accuser_claim")) {
            JSONArray judgeInquiryAfterAccuserClaim = courtInvestigateObject.getJSONArray("judge_inquiry_after_accuser_claim");
            if (judgeInquiryAfterAccuserClaim != null && judgeInquiryAfterAccuserClaim.size() > 0) {
                saveJudgeRandomInquiry(judgeInquiryAfterAccuserClaim, courtNumber, "1");
            }
        }

        if (courtInvestigateObject.containsKey("judge_inquiry_after_defendant_reply")) {
            JSONArray judgeInquiryAfterDefendantReply = courtInvestigateObject.getJSONArray("judge_inquiry_after_defendant_reply");
            if (judgeInquiryAfterDefendantReply != null && judgeInquiryAfterDefendantReply.size() > 0) {
                saveJudgeRandomInquiry(judgeInquiryAfterDefendantReply, courtNumber, "2");
            }
        }

        if (recordJsonObject.containsKey("judge_inquiry_before_summarize")) {
            JSONArray judgeInquiryBeforeSummarize = recordJsonObject.getJSONArray("judge_inquiry_before_summarize");
            if (judgeInquiryBeforeSummarize != null && judgeInquiryBeforeSummarize.size() > 0) {
                saveJudgeRandomInquiry(judgeInquiryBeforeSummarize, courtNumber, "3");
            }
        }
    }

    public void saveJudgeRandomInquiry(JSONArray judgeInquiryArray, String courseNumber, String type) {
        for (int i = 0; i < judgeInquiryArray.size(); i++) {
            JSONObject judgeInquiryObject = judgeInquiryArray.getJSONObject(i);
            String question = judgeInquiryObject.getString("question");

            if (ObjectUtils.isEmpty(question)) {
                continue;
            }

            JSONArray answerArray = judgeInquiryObject.getJSONArray("answer");
            for (int j = 0; j < answerArray.size(); j++) {
                JSONObject answerObject = answerArray.getJSONObject(j);
                String name = answerObject.getString("name");
                String answer = answerObject.getString("answer");

                if (!ObjectUtils.isEmpty(name) || !ObjectUtils.isEmpty(answer)) {
                    JudgeRandomInquiry judgeRandomInquiry = new JudgeRandomInquiry();
                    judgeRandomInquiry.setQuestion(question);
                    judgeRandomInquiry.setAnswer(answer);
                    judgeRandomInquiry.setName(name);
                    judgeRandomInquiry.setType(type);
                    judgeRandomInquiry.setCourtNumber(courseNumber);

                    this.save(judgeRandomInquiry);
                }
            }
        }
    }


    @Override
    public JSONArray getJudgeRandomInquiryInfoArray(String courtNumber) {
        JSONArray judgeInquiryBeforeSummarize = new JSONArray();

        LambdaQueryWrapper<JudgeRandomInquiry> judgeRandomInquiryWrapper = new LambdaQueryWrapper<>();
        judgeRandomInquiryWrapper.eq(JudgeRandomInquiry::getCourtNumber, courtNumber);
        judgeRandomInquiryWrapper.eq(JudgeRandomInquiry::getType, "3");
        judgeRandomInquiryWrapper.eq(JudgeRandomInquiry::getDelFlag, YesOrNotEnum.N.getCode());
        List<JudgeRandomInquiry> judgeRandomInquiries = judgeRandomInquiryService.list(judgeRandomInquiryWrapper);

        if (judgeRandomInquiries == null || judgeRandomInquiries.size() <= 0) {
            JSONObject inquiryInfoObject = new JSONObject();
            inquiryInfoObject.put("question", "");
            JSONArray inquiryAnswerArray = new JSONArray();
            JSONObject inquiryAnswerObject = new JSONObject();
            inquiryAnswerObject.put("name", "");
            inquiryAnswerObject.put("answer", "");
            inquiryAnswerArray.add(inquiryAnswerObject);
            inquiryInfoObject.put("answer", inquiryAnswerArray);
            judgeInquiryBeforeSummarize.add(inquiryInfoObject);
        } else {
            String lastQuestion = "";
            JSONArray inquiryAnswerArray = null;
            JSONObject inquiryInfoObject = null;

            for (int i = 0; i < judgeRandomInquiries.size(); i++) {
                JudgeRandomInquiry judgeRandomInquiry = judgeRandomInquiries.get(i);
                String question = judgeRandomInquiry.getQuestion();
                if (ObjectUtils.isEmpty(question)) {
                    continue;
                }
                String answer = judgeRandomInquiry.getAnswer();
                String name = judgeRandomInquiry.getName();

                JSONObject inquiryAnswerObject = new JSONObject();
                if (!lastQuestion.equals(question)) {
                    if (inquiryAnswerArray != null && inquiryAnswerArray.size() > 0) {
                        inquiryInfoObject.put("answer", inquiryAnswerArray);
                        judgeInquiryBeforeSummarize.add(inquiryInfoObject);
                    }
                    lastQuestion = question;
                    inquiryInfoObject = new JSONObject();
                    inquiryInfoObject.put("question", question);
                    inquiryAnswerArray = new JSONArray();
                    inquiryAnswerObject.put("name", name);
                    inquiryAnswerObject.put("answer", answer);
                    inquiryAnswerArray.add(inquiryAnswerObject);
                } else {
                    inquiryAnswerObject.put("name", name);
                    inquiryAnswerObject.put("answer", answer);
                    inquiryAnswerArray.add(inquiryAnswerObject);
                }
            }
            if (inquiryAnswerArray != null && inquiryAnswerArray.size() > 0) {
                inquiryInfoObject.put("answer", inquiryAnswerArray);
                judgeInquiryBeforeSummarize.add(inquiryInfoObject);
            }
        }
        return judgeInquiryBeforeSummarize;
    }

    @Override
    public Boolean deleteJudgeRandomInquiryInfo(String courtNumber) {
        LambdaUpdateWrapper<JudgeRandomInquiry> judgeRandomInquiryWrapper = new LambdaUpdateWrapper<>();
        judgeRandomInquiryWrapper.set(JudgeRandomInquiry::getDelFlag, YesOrNotEnum.Y.getCode()).eq(JudgeRandomInquiry::getCourtNumber, courtNumber);
        return judgeRandomInquiryService.update(judgeRandomInquiryWrapper);
    }

    @Override
    public void delete(String courtNumber) {
        LambdaQueryWrapper<JudgeRandomInquiry> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(JudgeRandomInquiry::getCourtNumber,courtNumber);
        lambdaQueryWrapper.eq(JudgeRandomInquiry::getDelFlag, YesOrNotEnum.N.getCode());
        baseMapper.delete(lambdaQueryWrapper);
    }
}
