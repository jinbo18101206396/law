package cn.stylefeng.guns.modular.service;

import cn.stylefeng.guns.modular.entity.Accuser;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 原告信息表 服务类
 * </p>
 *
 * @author jinbo
 * @since 2022-01-19
 */
public interface AccuserService extends IService<Accuser> {


    /**
     * 保存原告信息
     *
     * @param recordJsonObject 查看参数
     * @return
     * @author 金波
     * @date 2022/05/23
     */
    void saveAccuserInfo(String courtNumber, JSONObject recordJsonObject);

    /**
     * 获取原告信息
     *
     * @param courtNumber 查看参数
     * @return
     * @author 金波
     * @date 2022/05/23
     */
    JSONArray getAccuserInfoArray(String courtNumber);

}
