package cn.stylefeng.guns.modular.service;

import cn.stylefeng.guns.modular.entity.ClerkRelation;
import cn.stylefeng.guns.modular.entity.Question;
import cn.stylefeng.guns.modular.entity.State;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 基本信息陈述表 服务类
 * </p>
 *
 * @author jinbo
 * @since 2022-01-19
 */
public interface ClerkRelationService extends IService<ClerkRelation> {


    /**
     * 获取书记员、审判员、开庭地点对应关系表
     *
     * @author jinbo
     * @Date 2022-05-31
     */
    JSONObject getClerkJudgePlaceRelation();

}
