package cn.stylefeng.guns.modular.service;

import cn.stylefeng.guns.modular.entity.Question;
import cn.stylefeng.guns.modular.entity.State;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 问题表 服务类
 * </p>
 *
 * @author jinbo
 * @since 2022-01-19
 */
public interface QuestionService extends IService<Question> {


    /**
     * 获取法庭询问问题列表
     *
     * @author jinbo
     * @Date 2022-05-31
     */
    List<String> getInquiryQuestionList();

    /**
     * 获取证人证言问题列表
     *
     * @author jinbo
     * @Date 2022-08-12
     */
    List<Question> getWitnessQuestionList(String keyword);

}
