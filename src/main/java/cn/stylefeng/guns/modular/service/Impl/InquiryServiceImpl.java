package cn.stylefeng.guns.modular.service.Impl;

import cn.stylefeng.guns.modular.entity.Inquiry;
import cn.stylefeng.guns.modular.mapper.InquiryMapper;
import cn.stylefeng.guns.modular.service.InquiryService;
import cn.stylefeng.roses.kernel.rule.enums.YesOrNotEnum;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 法庭询问表 服务实现类
 * </p>
 *
 * @author jinbo
 * @since 2022-01-19
 */
@Service
public class InquiryServiceImpl extends ServiceImpl<InquiryMapper, Inquiry> implements InquiryService {

    @Resource
    private InquiryService inquiryService;

    @Override
    public void saveInquiryInfo(String courtNumber, String counterClaim, JSONObject recordJsonObject) {
        //法庭询问

        if (recordJsonObject.containsKey("inquiryInfo")) {
            JSONArray inquiryInfoArray = recordJsonObject.getJSONArray("inquiryInfo");

            for (int i = 0; i < inquiryInfoArray.size(); i++) {
                JSONObject inquiryInfoObject = inquiryInfoArray.getJSONObject(i);
                String question = inquiryInfoObject.getString("inquiry_question");

                JSONArray inquiryAnswerArray = inquiryInfoObject.getJSONArray("inquiry_answer");
                for (int j = 0; j < inquiryAnswerArray.size(); j++) {
                    JSONObject inquiryAnswerObject = inquiryAnswerArray.getJSONObject(j);
                    String answer = inquiryAnswerObject.getString("answer");
                    String answerName = inquiryAnswerObject.getString("name");

                    if (!ObjectUtils.isEmpty(answerName) && answerName.contains("（")) {
                        String name = answerName.split("（")[0];
                        //原告、被告、反诉原告、反诉被告
                        String type = answerName.split("（")[1];
                        Inquiry inquiry = new Inquiry();
                        inquiry.setQuestion(question);
                        inquiry.setName(name);
                        inquiry.setType(type.substring(0, type.length() - 1));
                        inquiry.setAnswer(answer);
                        inquiry.setIsCounterClaim(counterClaim);
                        inquiry.setCourtNumber(courtNumber);
                        this.save(inquiry);
                    }
                }
            }
        }
    }

    @Override
    public JSONArray getInquiryInfoArray(String courtNumber) {
        //法庭询问
        JSONArray inquiryInfoArray = new JSONArray();
        LambdaQueryWrapper<Inquiry> inquiryQueryWrapper = new LambdaQueryWrapper<>();
        inquiryQueryWrapper.eq(Inquiry::getCourtNumber, courtNumber);
        inquiryQueryWrapper.eq(Inquiry::getDelFlag, YesOrNotEnum.N.getCode());
        List<Inquiry> inquiries = inquiryService.list(inquiryQueryWrapper);

        if (null == inquiries || inquiries.size() == 0) {
            JSONObject inquiryInfoObject = new JSONObject();
            inquiryInfoObject.put("inquiry_question", "");
            JSONArray inquiryAnswerArray = new JSONArray();
            JSONObject inquiryAnswerObject = new JSONObject();
            inquiryAnswerObject.put("name", "");
            inquiryAnswerObject.put("answer", "");
            inquiryAnswerArray.add(inquiryAnswerObject);
            inquiryInfoObject.put("inquiry_answer", inquiryAnswerArray);
            inquiryInfoArray.add(inquiryInfoObject);
        } else {
            String lastInquiryQuestion = "";
            JSONArray inquiryAnswerArray = null;
            JSONObject inquiryInfoObject = null;
            for (int i = 0; i < inquiries.size(); i++) {
                Inquiry inquiry = inquiries.get(i);
                String question = inquiry.getQuestion();
                if (ObjectUtils.isEmpty(question)) {
                    continue;
                }
                String answer = inquiry.getAnswer();
                String name = inquiry.getName();
                String type = inquiry.getType();
                String nameAndType = name + "（" + type + "）";
                JSONObject inquiryAnswerObject = new JSONObject();
                if (!lastInquiryQuestion.equals(question)) {
                    if (inquiryAnswerArray != null && inquiryAnswerArray.size() > 0) {
                        inquiryInfoObject.put("inquiry_answer", inquiryAnswerArray);
                        inquiryInfoArray.add(inquiryInfoObject);
                    }
                    inquiryInfoObject = new JSONObject();
                    lastInquiryQuestion = question;
                    inquiryInfoObject.put("inquiry_question", question);

                    inquiryAnswerArray = new JSONArray();
                    inquiryAnswerObject.put("name", nameAndType);
                    inquiryAnswerObject.put("answer", answer);
                    inquiryAnswerArray.add(inquiryAnswerObject);
                } else {
                    inquiryAnswerObject.put("name", nameAndType);
                    inquiryAnswerObject.put("answer", answer);
                    inquiryAnswerArray.add(inquiryAnswerObject);
                }
            }
            if (inquiryAnswerArray.size() > 0) {
                inquiryInfoObject.put("inquiry_answer", inquiryAnswerArray);
                inquiryInfoArray.add(inquiryInfoObject);
            }
        }
        return inquiryInfoArray;
    }

    @Override
    public List<Inquiry> getInquiryInfoList(String courtNumber) {
        List<Inquiry> inquiryList = new ArrayList<>();
        JSONArray inquiryInfoArray = this.getInquiryInfoArray(courtNumber);
        for (int i = 0; i < inquiryInfoArray.size(); i++) {
            JSONObject inquiryObject = inquiryInfoArray.getJSONObject(i);
            String question = inquiryObject.getString("inquiry_question");
            List<String> answerList = new ArrayList<>();
            JSONArray answerArray = inquiryObject.getJSONArray("inquiry_answer");
            for (int j = 0; j < answerArray.size(); j++) {
                JSONObject answerObject = answerArray.getJSONObject(j);
                String name = answerObject.getString("name");
                String answer = answerObject.getString("answer");
                String answerContent = "";
                if (!ObjectUtils.isEmpty(name) && !ObjectUtils.isEmpty(answer)) {
                    if (name.contains("原告")) {
                        name = name.replace("（原告）", "");
                        answerContent = "原告（" + name + "）：" + answer;
                    } else if(name.contains("被告")) {
                        name = name.replace("（被告）", "");
                        answerContent = "被告（" + name + "）：" + answer;
                    }else if(name.contains("第三人")){
                        name = name.replace("（第三人）", "");
                        answerContent = "第三人（" + name + "）：" + answer;
                    }
                    answerList.add(answerContent);
                }
            }
            Inquiry inquiry = new Inquiry();
            inquiry.setQuestion(question);
            inquiry.setAnswerList(answerList);
            inquiryList.add(inquiry);
        }
        return inquiryList;
    }

    @Override
    public Boolean deleteInquiryInfo(String courtNumber) {
        LambdaUpdateWrapper<Inquiry> inquiryWrapper = new LambdaUpdateWrapper<>();
        inquiryWrapper.set(Inquiry::getDelFlag, YesOrNotEnum.Y.getCode()).eq(Inquiry::getCourtNumber, courtNumber);
        return inquiryService.update(inquiryWrapper);
    }
}
