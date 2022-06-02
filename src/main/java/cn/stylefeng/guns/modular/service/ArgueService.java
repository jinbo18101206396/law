package cn.stylefeng.guns.modular.service;

import cn.stylefeng.guns.modular.entity.Argue;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 法庭辩论表 服务类
 * </p>
 *
 * @author jinbo
 * @since 2022-01-19
 */
public interface ArgueService extends IService<Argue> {


    /**
     * 保存法庭辩论信息
     *
     * @author jinbo
     * @Date 2022-05-23
     */
    void saveArgueInfo(String courtNumber, String counterClaim, JSONObject recordJsonObject);

    /**
     * 获取法庭辩论信息
     *
     * @author jinbo
     * @Date 2022-05-25
     */
    JSONObject getArgueInfoObject(String courtNumber);


    /**
     * 删除法庭辩论信息
     *
     * @author jinbo
     * @Date 2022-05-25
     */
    Boolean deleteArgueInfo(String courtNumber);

}
