package cn.stylefeng.guns.modular.service;

import cn.stylefeng.guns.modular.entity.JudgeSpeak;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 审判员说话内容 服务类
 * </p>
 *
 * @author jinbo
 * @since 2022-07-11
 */
public interface JudgeSpeakService extends IService<JudgeSpeak> {


    /**
     * 获取审判员说的话
     *
     * @author jinbo
     * @Date 2022-07-11
     */
    JSONObject getJudgeSpeak(String courtCause);

    /**
     * 编辑审判员说的话
     *
     * @author jinbo
     * @Date 2022-07-11
     */
    Boolean editJudgeSpeak(String courtCause, String module, String content);

}
