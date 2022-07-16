package cn.stylefeng.guns.modular.service;

import cn.stylefeng.guns.modular.entity.JudgeRandomInquiry;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 法官随机提问表 服务类
 * </p>
 *
 * @author jinbo
 * @since 2022-07-13
 */
public interface JudgeRandomInquiryService extends IService<JudgeRandomInquiry> {

    /**
     * 保存法官随机提问信息
     *
     * @param recordJsonObject 请求参数
     * @return
     * @author 金波
     * @Date 2022-07-13
     */
    void saveJudgeRandomInquiryInfo(String courtNumber, String counterClaim, JSONObject recordJsonObject);

    /**
     * 获取法庭询问信息
     *
     * @param courtNumber 请求参数
     * @return
     * @author 金波
     * @Date 2022-07-13
     */
    JSONArray getJudgeRandomInquiryInfoArray(String courtNumber);


    /**
     * 删除法庭询问信息（修改删除标记）
     *
     * @param courtNumber 请求参数
     * @return
     * @author 金波
     * @Date 2022-07-13
     */
    Boolean deleteJudgeRandomInquiryInfo(String courtNumber);

    /**
     * 删除法庭询问信息（彻底删除）
     *
     * @param courtNumber 请求参数
     * @return
     * @author 金波
     * @Date 2022-07-13
     */
    void delete(String courtNumber);

}
