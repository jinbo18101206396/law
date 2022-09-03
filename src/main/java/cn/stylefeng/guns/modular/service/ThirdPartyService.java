package cn.stylefeng.guns.modular.service;

import cn.stylefeng.guns.modular.entity.ThirdParty;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 第三人信息表 服务类
 * </p>
 *
 * @author jinbo
 * @since 2022-07-12
 */
public interface ThirdPartyService extends IService<ThirdParty> {

    /**
     * 保存第三人信息
     *
     * @param recordJsonObject 查看参数
     * @return
     * @author 金波
     * @date 2022-07-12
     */
    void saveThirdPartyInfo(String courtNumber, JSONObject recordJsonObject);

    /**
     * 获取第三人信息
     *
     * @param courtNumber 查看参数
     * @return
     * @author 金波
     * @date 2022-07-12
     */
    JSONArray getThirdPartyInfoArray(String courtNumber);

    /**
     * 获取第三人信息列表
     *
     * @param courtNumber 查看参数
     * @return
     * @author 金波
     * @date 2022-07-12
     */
    List<ThirdParty> getThirdPartyInfoList(String courtNumber, Map<String, Object> recordMap);

    /**
     * 删除第三人信息（修改删除标记）
     *
     * @param courtNumber 查看参数
     * @return
     * @author 金波
     * @date 2022-07-12
     */
    Boolean deleteThirdPartyInfo(String courtNumber);

    /**
     * 删除第三人信息（彻底删除）
     *
     * @param courtNumber 查看参数
     * @return
     * @author 金波
     * @date 2022-07-16
     */
    void delete(String courtNumber);

}
