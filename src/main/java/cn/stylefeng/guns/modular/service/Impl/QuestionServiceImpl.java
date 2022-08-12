package cn.stylefeng.guns.modular.service.Impl;

import cn.stylefeng.guns.modular.entity.Question;
import cn.stylefeng.guns.modular.mapper.QuestionMapper;
import cn.stylefeng.guns.modular.service.QuestionService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 问题表 服务实现类
 * </p>
 *
 * @author jinbo
 * @since 2022-05-31
 */
@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question> implements QuestionService {

    @Resource
    private QuestionService questionService;

    @Override
    public List<String> getInquiryQuestionList() {
        List<Question> inquiryQuestions = getQuestions("1","inquiry");
        List<String> inquiryQuestionList = new ArrayList<>();
        for (int i = 0; i < inquiryQuestions.size(); i++) {
            Question question = inquiryQuestions.get(i);
            inquiryQuestionList.add(question.getQuestion());
        }
        return inquiryQuestionList;
    }

    @Override
    public List<Question> getWitnessQuestionList() {
        return getQuestions("1", "witness");
    }

    private List<Question> getQuestions(String type, String module){
        LambdaQueryWrapper<Question> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Question::getType, type);
        queryWrapper.eq(Question::getModule, module);
        return questionService.list(queryWrapper);
    }
}
