package cn.stylefeng.guns.modular.service;

import cn.stylefeng.guns.modular.entity.State;
import cn.stylefeng.guns.modular.model.request.StateRequest;
import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 基本信息陈述表 服务类
 * </p>
 *
 * @author jinbo
 * @since 2022-01-19
 */
public interface StateService extends IService<State> {

    /**
     * 保存基本信息陈述
     *
     * @author jinbo
     * @Date 2022-05-24
     */
    void saveStateInfo(String courtNumber, JSONObject recordJsonObject);


    /**
     * 获取基本信息陈述
     *
     * @author jinbo
     * @Date 2022-05-25
     */
    JSONObject getStateInfoObject(String courtNumber);


    /**
     * 删除基本信息陈述
     *
     * @author jinbo
     * @Date 2022-06-02
     */
    Boolean deleteStateInfo(String courtNumber);

}
