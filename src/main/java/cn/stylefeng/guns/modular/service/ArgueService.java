package cn.stylefeng.guns.modular.service;

import cn.stylefeng.guns.modular.entity.Argue;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

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
     * 获取法庭辩论信息
     *
     * @author jinbo
     * @Date 2022-05-25
     */
    Argue getArgueInfo(String courtNumber);

    /**
     * 获取法庭辩论信息-生成笔录
     *
     * @author jinbo
     * @Date 2022-07-07
     */
    List<Argue> getArgueList(String courtNumber);


    /**
     * 删除法庭辩论信息
     *
     * @author jinbo
     * @Date 2022-05-25
     */
    Boolean deleteArgueInfo(String courtNumber);

}
