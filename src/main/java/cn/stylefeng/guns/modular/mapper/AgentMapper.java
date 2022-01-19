package cn.stylefeng.guns.modular.mapper;

import cn.stylefeng.guns.modular.entity.Agent;
import cn.stylefeng.guns.modular.model.request.AgentRequest;
import cn.stylefeng.guns.modular.model.result.AgentResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * <p>
 * 委托诉讼代理人表 Mapper 接口
 * </p>
 *
 * @author jinbo
 * @since 2022-01-19
 */
public interface AgentMapper extends BaseMapper<Agent> {

    /**
     * 获取列表
     *
     * @author jinbo
     * @Date 2022-01-19
     */
    List<AgentResult> findtList(@Param("agentRequest") AgentRequest agentRequest);

    /**
     * 获取分页实体列表
     *
     * @author jinbo
     * @Date 2022-01-19
     */
    Page<AgentResult> findPage(@Param("page") Page<Agent> page, @Param("agentRequest") AgentRequest agentRequest);

}
