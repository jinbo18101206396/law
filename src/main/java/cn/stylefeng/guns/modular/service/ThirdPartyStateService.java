package cn.stylefeng.guns.modular.service;

import cn.stylefeng.guns.modular.entity.State;
import cn.stylefeng.guns.modular.entity.ThirdPartyState;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 第三人述称表 服务类
 * </p>
 *
 * @author jinbo
 * @since 2022-08-03
 */
public interface ThirdPartyStateService extends IService<ThirdPartyState> {

    /**
     * 保存第三人述称
     *
     * @author jinbo
     * @Date 2022-08-03
     */
    void saveThirdPartyStateInfo(String courtNumber, JSONObject courtInvestigateObject);

    /**
     * 删除基本信息陈述（彻底删除）
     *
     * @author jinbo
     * @Date 2022-07-16
     */
    void delete(String courtNumber);

}
