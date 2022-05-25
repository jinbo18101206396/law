package cn.stylefeng.guns.modular.service;

import cn.stylefeng.guns.modular.entity.Agent;
import cn.stylefeng.guns.modular.model.request.AgentRequest;
import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 委托诉讼代理人表 服务类
 * </p>
 *
 * @author jinbo
 * @since 2022-01-19
 */
public interface AgentService extends IService<Agent> {

    /**
     * 保存委托诉讼代理人
     *
     * @param recordJsonObject 查看参数
     * @return
     * @author 金波
     * @date 2022/05/23
     */
    void saveAgentInfo(String courtNumber, JSONObject recordJsonObject);

}
