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
    public List<String> getQuestionList() {
        LambdaQueryWrapper<Question> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Question::getType, "1");
        List<Question> questions = questionService.list(queryWrapper);

        List<String> questionList = new ArrayList<>();
        for (int i = 0; i < questions.size(); i++) {
            Question question = questions.get(i);
            questionList.add(question.getQuestion());
        }
        return questionList;
    }
}
