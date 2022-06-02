package cn.stylefeng.guns.modular.service.Impl;

import cn.stylefeng.guns.modular.entity.Accuser;
import cn.stylefeng.guns.modular.entity.State;
import cn.stylefeng.guns.modular.mapper.StateMapper;
import cn.stylefeng.guns.modular.service.StateService;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 基本信息陈述表 服务实现类
 * </p>
 *
 * @author jinbo
 * @since 2022-05-24
 */
@Service
public class StateServiceImpl extends ServiceImpl<StateMapper, State> implements StateService {

    @Resource
    private StateService stateService;

    /**
     * 保存基本信息陈述
     */
    @Override
    public void saveStateInfo(String courtNumber, JSONObject recordJsonObject) {
        if (recordJsonObject.containsKey("stateInfo")) {
            String stateInfo = recordJsonObject.getString("stateInfo");
            JSONObject stateInfoObject = JSONObject.parseObject(stateInfo);
            State state = new State();
            state.setStateType(stateInfoObject.get("state_type").toString());
            state.setStateContent(stateInfoObject.get("state_content").toString());
            state.setCourtNumber(courtNumber);
            this.save(state);
        }
    }

    /**
     * 获取基本信息陈述
     */
    @Override
    public JSONObject getStateInfoObject(String courtNumber) {
        LambdaQueryWrapper<State> stateQueryWrapper = new LambdaQueryWrapper<>();
        stateQueryWrapper.eq(State::getCourtNumber, courtNumber);
        State state = stateService.getOne(stateQueryWrapper);
        JSONObject stateObject = new JSONObject();
        stateObject.put("state_type", state.getStateType());
        stateObject.put("state_content", state.getStateContent());
        return stateObject;
    }

    @Override
    public Boolean deleteStateInfo(String courtNumber) {
        LambdaQueryWrapper<State> stateQueryWrapper = new LambdaQueryWrapper<>();
        stateQueryWrapper.eq(State::getCourtNumber, courtNumber);
        return stateService.remove(stateQueryWrapper);
    }
}
