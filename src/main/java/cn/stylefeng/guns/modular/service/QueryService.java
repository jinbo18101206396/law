package cn.stylefeng.guns.modular.service;

import cn.stylefeng.guns.modular.entity.Query;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 质证表 服务类
 * </p>
 *
 * @author jinbo
 * @since 2022-01-19
 */
public interface QueryService extends IService<Query> {

    /**
     * 保存被告及其他原告质证信息
     *
     * @param recordJsonObject 请求参数
     * @return
     * @author 金波
     * @Date 2022-07-12
     */
    void saveDefendantAndOtherAccuserQuery(String courtNumber, String counterClaim, JSONObject recordJsonObject);

    /**
     * 保存原告及其他被告质证信息
     *
     * @param recordJsonObject 请求参数
     * @return
     * @author 金波
     * @Date 2022-07-12
     */
    void saveAccuserAndOtherDefendantQuery(String courtNumber, String counterClaim, JSONObject recordJsonObject);

    /**
     * 保存反诉被告质证信息
     *
     * @param recordJsonObject 请求参数
     * @return
     * @author 金波
     * @Date 2022-05-24
     */
    void saveCounterClaimDefendantQuery(String courtNumber, String counterClaim, JSONObject recordJsonObject);

    /**
     * 保存反诉原告质证信息
     *
     * @param recordJsonObject 请求参数
     * @return
     * @author 金波
     * @Date 2022-05-24
     */
    void saveCounterClaimAccuserQuery(String courtNumber, String counterClaim, JSONObject recordJsonObject);

    /**
     * 保存其他反诉被告质证信息
     *
     * @param recordJsonObject 请求参数
     * @return
     * @author 金波
     * @Date 2022-05-24
     */
    void saveOtherCounterClaimDefendantQuery(String courtNumber, String counterClaim, JSONObject recordJsonObject);


    /**
     * 删除质证信息（修改删除标记）
     *
     * @param courtNumber 请求参数
     * @return
     * @author 金波
     * @Date 2022-06-02
     */
    Boolean deleteQueryInfo(String courtNumber);

    /**
     * 删除质证信息（彻底删除）
     *
     * @param courtNumber 请求参数
     * @return
     * @author 金波
     * @Date 2022-07-16
     */
    void delete(String courtNumber);
}
