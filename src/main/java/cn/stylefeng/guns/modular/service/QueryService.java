package cn.stylefeng.guns.modular.service;

import cn.stylefeng.guns.modular.entity.Query;
import cn.stylefeng.guns.modular.model.request.ProofRequest;
import cn.stylefeng.guns.modular.model.request.QueryRequest;
import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

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
     * 保存被告质证信息
     *
     * @param recordJsonObject 请求参数
     * @return
     * @author 金波
     * @Date 2022-05-24
     */
    void saveDefendantQuery(String courtNumber, String counterClaim, JSONObject recordJsonObject);




    /**
     * 保存原告质证信息
     *
     * @param recordJsonObject 请求参数
     * @return
     * @author 金波
     * @Date 2022-05-24
     */
    void saveAccuserQuery(String courtNumber, String counterClaim, JSONObject recordJsonObject);

    /**
     * 保存其他被告质证信息
     *
     * @param recordJsonObject 请求参数
     * @return
     * @author 金波
     * @Date 2022-05-24
     */
    void saveOtherDefendantQuery(String courtNumber, String counterClaim, JSONObject recordJsonObject);

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
     * 删除质证信息
     *
     * @param courtNumber 请求参数
     * @return
     * @author 金波
     * @Date 2022-06-02
     */
    Boolean deleteQueryInfo(String courtNumber);
}
