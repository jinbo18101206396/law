package cn.stylefeng.guns.modular.service;

import cn.stylefeng.guns.modular.entity.Agent;
import cn.stylefeng.guns.modular.model.request.AgentRequest;
import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
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
     * 新增
     *
     * @author jinbo
     * @Date 2022-01-19
     */
    void add(AgentRequest agentRequest);

    /**
     * 删除
     *
     * @author jinbo
     * @Date 2022-01-19
     */
    void delete(AgentRequest agentRequest);

    /**
     * 更新
     *
     * @author jinbo
     * @Date 2022-01-19
     */
    void update(AgentRequest agentRequest);

    /**
     * 查看详情
     *
     * @param agentRequest 查看参数
     * @return 详情结果
     * @author 金波
     * @date 2021/5/12 18:28
     */
    Agent detail(AgentRequest agentRequest);

    /**
     * 查询列表
     *
     * @param agentRequest 请求参数
     * @return 列表
     * @author 金波
     * @date 2022/01/14 15:07
     */
    List<Agent> findList(AgentRequest agentRequest);

    /**
     * 分页查询列表
     *
     * @param agentRequest 查看参数
     * @return 结果
     * @author 金波
     * @date 2022/01/14 15:07
     */
    PageResult<Agent> findPage(AgentRequest agentRequest);

}
