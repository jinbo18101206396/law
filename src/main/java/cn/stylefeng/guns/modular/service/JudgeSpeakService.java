package cn.stylefeng.guns.modular.service;

import cn.stylefeng.guns.modular.entity.JudgeSpeak;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

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
     * 获取审判员说的话（回显数据）
     *
     * @author jinbo
     * @Date 2022-07-19
     */
    JSONObject getJudgeSpeaks(String courtNumber,JSONObject recordJson);


    /**
     * 获取审判员说的话(生成笔录)
     *
     * @author jinbo
     * @Date 2022-07-19
     */
    Map<String, Object> getJudgeSpeaks(String courtNumber, Map<String, Object> recordMap);

    /**
     * 保存审判员说的话
     *
     * @author jinbo
     * @Date 2022-07-19
     */
    Boolean saveJudgeSpeaks(String courtNumber, String courtCause, JSONObject recordJsonObject);

    /**
     * 删除审判员说话（彻底删除）
     *
     * @param courtNumber 请求参数
     * @return
     * @author 金波
     * @Date 2022-07-19
     */
    void delete(String courtNumber);

}
