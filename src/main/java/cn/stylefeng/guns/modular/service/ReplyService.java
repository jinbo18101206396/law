package cn.stylefeng.guns.modular.service;

import cn.stylefeng.guns.modular.entity.Reply;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 答辩表 服务类
 * </p>
 *
 * @author jinbo
 * @since 2022-01-19
 */
public interface ReplyService extends IService<Reply> {

    /**
     * 保存被告答辩信息
     *
     * @param recordJsonObject 请求参数
     * @return
     * @author 金波
     * @Date 2022-05-24
     */
    void saveDefendantReply(String courtNumber, String counterClaim, JSONObject recordJsonObject);

    /**
     * 保存反诉被告答辩信息
     *
     * @param recordJsonObject 请求参数
     * @return
     * @author 金波
     * @Date 2022-05-24
     */
    void saveCounterClaimDefendantReply(String courtNumber, String counterClaim, JSONObject recordJsonObject);


    /**
     * 删除被告答辩信息
     *
     * @param courtNumber 请求参数
     * @return
     * @author 金波
     * @Date 2022-06-02
     */
    Boolean deleteReplyInfo(String courtNumber);

}
