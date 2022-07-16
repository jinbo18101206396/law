package cn.stylefeng.guns.modular.service;

import cn.stylefeng.guns.modular.entity.Proof;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 举证表 服务类
 * </p>
 *
 * @author jinbo
 * @since 2022-01-19
 */
public interface ProofService extends IService<Proof> {

    /**
     * 保存原告举证信息
     *
     * @param recordJsonObject 请求参数
     * @return
     * @author 金波
     * @Date 2022-05-24
     */
    void saveAccuserEvidence(String courtNumber, String counterClaim, JSONObject recordJsonObject);

    /**
     * 保存被告举证信息
     *
     * @param recordJsonObject 请求参数
     * @return
     * @author 金波
     * @Date 2022-05-24
     */
    void saveDefendantEvidence(String courtNumber, String counterClaim, JSONObject recordJsonObject);

    /**
     * 保存反诉原告举证信息
     *
     * @param recordJsonObject 请求参数
     * @return
     * @author 金波
     * @Date 2022-05-24
     */
    void saveCounterClaimAccuserEvidence(String courtNumber, String counterClaim, JSONObject recordJsonObject);

    /**
     * 保存反诉被告举证信息
     *
     * @param recordJsonObject 请求参数
     * @return
     * @author 金波
     * @Date 2022-05-24
     */
    void saveCounterClaimDefendantEvidence(String courtNumber, String counterClaim, JSONObject recordJsonObject);


    /**
     * 删除举证信息（修改删除标记）
     *
     * @param courtNumber 请求参数
     * @return
     * @author 金波
     * @Date 2022-06-02
     */
    Boolean deleteProofInfo(String courtNumber);

    /**
     * 删除举证信息（彻底删除）
     *
     * @param courtNumber 请求参数
     * @return
     * @author 金波
     * @Date 2022-06-02
     */
    void delete(String courtNumber);

}
