package cn.stylefeng.guns.modular.service.Impl;

import cn.stylefeng.guns.modular.entity.JudgeRandomInquiry;
import cn.stylefeng.guns.modular.enums.JudgeRandomInquiryEnum;
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
        List<JudgeRandomInquiry> judgeRandomInquires = getJudgeRandomInquires(courtNumber);
        if(judgeRandomInquires != null && judgeRandomInquires.size() > 0){
            judgeRandomInquiryService.delete(courtNumber);
        }

        JSONObject courtInvestigateObject = recordJsonObject.getJSONObject("courtInvestigate");
        if (courtInvestigateObject.containsKey("judge_inquiry_after_accuser_claim")) {
            JSONArray judgeInquiryAfterAccuserClaim = courtInvestigateObject.getJSONArray("judge_inquiry_after_accuser_claim");
            if (judgeInquiryAfterAccuserClaim != null && judgeInquiryAfterAccuserClaim.size() > 0) {
                saveJudgeRandomInquiry(judgeInquiryAfterAccuserClaim, courtNumber, JudgeRandomInquiryEnum.AFTER_ACCUSER_CLAIM.getCode());
            }
        }

        if (courtInvestigateObject.containsKey("judge_inquiry_after_defendant_reply")) {
            JSONArray judgeInquiryAfterDefendantReply = courtInvestigateObject.getJSONArray("judge_inquiry_after_defendant_reply");
            if (judgeInquiryAfterDefendantReply != null && judgeInquiryAfterDefendantReply.size() > 0) {
                saveJudgeRandomInquiry(judgeInquiryAfterDefendantReply, courtNumber, JudgeRandomInquiryEnum.AFTER_DEFENDANT_REPLY.getCode());
            }
        }

        if (recordJsonObject.containsKey("judge_inquiry_before_summarize")) {
            JSONArray judgeInquiryBeforeSummarize = recordJsonObject.getJSONArray("judge_inquiry_before_summarize");
            if (judgeInquiryBeforeSummarize != null && judgeInquiryBeforeSummarize.size() > 0) {
                saveJudgeRandomInquiry(judgeInquiryBeforeSummarize, courtNumber, JudgeRandomInquiryEnum.BEFORE_SUMMARIZE.getCode());
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

    public List<JudgeRandomInquiry> getJudgeRandomInquires(String courtNumber) {
        LambdaQueryWrapper<JudgeRandomInquiry> judgeRandomInquiryWrapper = new LambdaQueryWrapper<>();
        judgeRandomInquiryWrapper.eq(JudgeRandomInquiry::getCourtNumber, courtNumber);
        judgeRandomInquiryWrapper.eq(JudgeRandomInquiry::getDelFlag, YesOrNotEnum.N.getCode());
        return judgeRandomInquiryService.list(judgeRandomInquiryWrapper);
    }

    public JSONObject blankJudgeRandomInquiry() {
        JSONObject inquiryInfoObject = new JSONObject();
        inquiryInfoObject.put("question", "");
        JSONArray inquiryAnswerArray = new JSONArray();
        JSONObject inquiryAnswerObject = new JSONObject();
        inquiryAnswerObject.put("name", "");
        inquiryAnswerObject.put("answer", "");
        inquiryAnswerArray.add(inquiryAnswerObject);
        inquiryInfoObject.put("answer", inquiryAnswerArray);
        return inquiryInfoObject;
    }

    /**
     * 法庭调查-法官随机提问
     */
    @Override
    public JSONObject getJudgeRandomInquiry(String courtNumber) {
        JSONObject judgeRandomInquiryObject = new JSONObject();

        JSONArray judgeInquiryAfterAccuserClaimArray = new JSONArray();
        JSONArray judgeInquiryAfterDefendantReplyArray = new JSONArray();
        JSONArray judgeInquiryBeforeSummarize = new JSONArray();

        List<JudgeRandomInquiry> judgeRandomInquiries = getJudgeRandomInquires(courtNumber);
        if (judgeRandomInquiries == null || judgeRandomInquiries.size() <= 0) {
            JSONObject inquiryInfoObject = blankJudgeRandomInquiry();
            judgeInquiryAfterAccuserClaimArray.add(inquiryInfoObject);
            judgeInquiryAfterDefendantReplyArray.add(inquiryInfoObject);
            judgeInquiryBeforeSummarize.add(inquiryInfoObject);
        } else {
            String lastQuestion = "";
            String type = "";
            String lastType = "";
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
                type = judgeRandomInquiry.getType();

                JSONObject inquiryAnswerObject = new JSONObject();
                if (!lastQuestion.equals(question)) {
                    if (inquiryAnswerArray != null && inquiryAnswerArray.size() > 0) {
                        inquiryInfoObject.put("answer", inquiryAnswerArray);
                        if (lastType.equals(JudgeRandomInquiryEnum.AFTER_ACCUSER_CLAIM.getCode())) {
                            judgeInquiryAfterAccuserClaimArray.add(inquiryInfoObject);
                        } else if (lastType.equals(JudgeRandomInquiryEnum.AFTER_DEFENDANT_REPLY.getCode())) {
                            judgeInquiryAfterDefendantReplyArray.add(inquiryInfoObject);
                        } else if (lastType.equals(JudgeRandomInquiryEnum.BEFORE_SUMMARIZE.getCode())) {
                            judgeInquiryBeforeSummarize.add(inquiryInfoObject);
                        }
                    }
                    lastType = type;
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
                if (type.equals(JudgeRandomInquiryEnum.AFTER_ACCUSER_CLAIM.getCode())) {
                    judgeInquiryAfterAccuserClaimArray.add(inquiryInfoObject);
                } else if (type.equals(JudgeRandomInquiryEnum.AFTER_DEFENDANT_REPLY.getCode())) {
                    judgeInquiryAfterDefendantReplyArray.add(inquiryInfoObject);
                } else if (type.equals(JudgeRandomInquiryEnum.BEFORE_SUMMARIZE.getCode())) {
                    judgeInquiryBeforeSummarize.add(inquiryInfoObject);
                }
            }
        }
        if (judgeInquiryAfterAccuserClaimArray == null || judgeInquiryAfterAccuserClaimArray.size() <= 0) {
            judgeInquiryAfterAccuserClaimArray.add(blankJudgeRandomInquiry());
        }
        if (judgeInquiryAfterDefendantReplyArray == null || judgeInquiryAfterDefendantReplyArray.size() <= 0) {
            judgeInquiryAfterDefendantReplyArray.add(blankJudgeRandomInquiry());
        }
        if (judgeInquiryBeforeSummarize == null || judgeInquiryBeforeSummarize.size() <= 0) {
            judgeInquiryBeforeSummarize.add(blankJudgeRandomInquiry());
        }
        judgeRandomInquiryObject.put("judge_inquiry_after_accuser_claim", judgeInquiryAfterAccuserClaimArray);
        judgeRandomInquiryObject.put("judge_inquiry_after_defendant_reply", judgeInquiryAfterDefendantReplyArray);
        judgeRandomInquiryObject.put("judge_inquiry_before_summarize", judgeInquiryBeforeSummarize);
        return judgeRandomInquiryObject;
    }

    @Override
    public JSONArray getJudgeRandomInquiryBeforeSummarize(String courtNumber) {
        JSONObject judgeRandomInquiry = getJudgeRandomInquiry(courtNumber);
        return judgeRandomInquiry.getJSONArray("judge_inquiry_before_summarize");
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
        lambdaQueryWrapper.eq(JudgeRandomInquiry::getCourtNumber, courtNumber);
        lambdaQueryWrapper.eq(JudgeRandomInquiry::getDelFlag, YesOrNotEnum.N.getCode());
        baseMapper.delete(lambdaQueryWrapper);
    }
}
