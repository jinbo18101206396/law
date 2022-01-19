package cn.stylefeng.guns.modular.service.Impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.modular.entity.BasicInfo;
import cn.stylefeng.guns.modular.entity.Reply;
import cn.stylefeng.guns.modular.entity.State;
import cn.stylefeng.guns.modular.mapper.StateMapper;
import cn.stylefeng.guns.modular.model.request.ProofRequest;
import cn.stylefeng.guns.modular.model.request.ReplyRequest;
import cn.stylefeng.guns.modular.model.request.StateRequest;
import  cn.stylefeng.guns.modular.service.StateService;
import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.system.api.exception.SystemModularException;
import cn.stylefeng.roses.kernel.system.api.exception.enums.organization.PositionExceptionEnum;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 基本信息陈述表 服务实现类
 * </p>
 *
 * @author jinbo
 * @since 2022-01-19
 */
@Service
public class StateServiceImpl extends ServiceImpl<StateMapper, State> implements StateService {


    @Override
    public void add(StateRequest stateRequest) {
        State state = new State();
        BeanUtil.copyProperties(stateRequest, state);
        this.save(state);
    }

    @Override
    public void delete(StateRequest stateRequest) {

    }

    @Override
    public void update(StateRequest stateRequest) {
        State state = this.queryStateById(stateRequest);
        BeanUtil.copyProperties(stateRequest,state);
        this.updateById(state);
    }

    @Override
    public State detail(StateRequest stateRequest) {
        return this.queryStateById(stateRequest);
    }

    @Override
    public List<State> findList(StateRequest stateRequest) {
        return null;
    }

    @Override
    public PageResult<State> findPage(StateRequest stateRequest) {
        return null;
    }

    /**
     * 根据主键id获取对象信息
     *
     * @return 实体对象
     * @author 金波
     * @date 2022/01/14 15:07
     */
    private State queryStateById(StateRequest stateRequest) {
        State state = this.getById(stateRequest.getStateId());
        if (ObjectUtil.isEmpty(state)) {
            throw new SystemModularException(PositionExceptionEnum.CANT_FIND_POSITION, state.getStateId());
        }
        return state;
    }

    /**
     * 实体构建 QueryWrapper
     *
     * @author 金波
     * @date 2022/01/14 15:07
     */
    private LambdaQueryWrapper<State> createWrapper(StateRequest stateRequest) {
        LambdaQueryWrapper<State> queryWrapper = new LambdaQueryWrapper<State>();

        return queryWrapper;
    }
}
