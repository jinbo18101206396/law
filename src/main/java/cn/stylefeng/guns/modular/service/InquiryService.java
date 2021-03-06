package cn.stylefeng.guns.modular.service;

import cn.stylefeng.guns.modular.entity.Inquiry;
import cn.stylefeng.guns.modular.model.request.InquiryRequest;
import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 法庭询问表 服务类
 * </p>
 *
 * @author jinbo
 * @since 2022-05-24
 */
public interface InquiryService extends IService<Inquiry> {

    /**
     * 保存法庭询问信息
     *
     * @param recordJsonObject 请求参数
     * @return
     * @author 金波
     * @Date 2022-05-24
     */
    void saveInquiryInfo(String courtNumber, String counterClaim, JSONObject recordJsonObject);

    /**
     * 获取法庭询问信息
     *
     * @param courtNumber 请求参数
     * @return
     * @author 金波
     * @Date 2022-05-25
     */
    JSONArray getInquiryInfoArray(String courtNumber);


    /**
     * 获取法庭询问信息列表
     *
     * @param courtNumber 请求参数
     * @return
     * @author 金波
     * @Date 2022-05-25
     */
    List<Inquiry> getInquiryInfoList(String courtNumber);

    /**
     * 删除法庭询问信息（修改删除标记）
     *
     * @param courtNumber 请求参数
     * @return
     * @author 金波
     * @Date 2022-06-02
     */
    Boolean deleteInquiryInfo(String courtNumber);

    /**
     * 删除法庭询问信息（彻底删除）
     *
     * @param courtNumber 请求参数
     * @return
     * @author 金波
     * @Date 2022-07-16
     */
    void delete(String courtNumber);

}
