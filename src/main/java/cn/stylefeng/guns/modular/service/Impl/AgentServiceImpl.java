package cn.stylefeng.guns.modular.service.Impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.modular.entity.Agent;
import cn.stylefeng.guns.modular.mapper.AgentMapper;
import cn.stylefeng.guns.modular.model.request.AgentRequest;
import cn.stylefeng.guns.modular.service.AgentService;
import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.system.api.exception.SystemModularException;
import cn.stylefeng.roses.kernel.system.api.exception.enums.organization.PositionExceptionEnum;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 委托诉讼代理人表 服务实现类
 * </p>
 *
 * @author jinbo
 * @since 2022-01-19
 */
@Service
public class AgentServiceImpl extends ServiceImpl<AgentMapper, Agent> implements AgentService {


    @Override
    public void add(AgentRequest agentRequest) {
        Agent agent = new Agent();
        BeanUtil.copyProperties(agentRequest, agent);
        this.save(agent);
    }

    @Override
    public void delete(AgentRequest agentRequest) {

    }

    @Override
    public void update(AgentRequest agentRequest) {
        Agent agent = this.queryAgentById(agentRequest);
        BeanUtil.copyProperties(agentRequest, agent);
        this.updateById(agent);
    }

    @Override
    public Agent detail(AgentRequest agentRequest) {
        return this.queryAgentById(agentRequest);
    }

    @Override
    public List<Agent> findList(AgentRequest agentRequest) {
        return null;
    }

    @Override
    public PageResult<Agent> findPage(AgentRequest agentRequest) {
        return null;
    }

    /**
     * 根据主键id获取对象信息
     *
     * @return 实体对象
     * @author 金波
     * @date 2022/01/14 15:07
     */
    private Agent queryAgentById(AgentRequest agentRequest) {
        Agent agent = this.getById(agentRequest.getAgentId());
        if (ObjectUtil.isEmpty(agent)) {
            throw new SystemModularException(PositionExceptionEnum.CANT_FIND_POSITION, agent.getAgentId());
        }
        return agent;
    }

    /**
     * 实体构建 QueryWrapper
     *
     * @author 金波
     * @date 2022/01/14 15:07
     */
    private LambdaQueryWrapper<Agent> createWrapper(AgentRequest agentRequest) {
        LambdaQueryWrapper<Agent> queryWrapper = new LambdaQueryWrapper<Agent>();

        return queryWrapper;
    }
}
