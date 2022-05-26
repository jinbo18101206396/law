package cn.stylefeng.guns.modular.service.Impl;

import cn.stylefeng.guns.modular.entity.State;
import cn.stylefeng.guns.modular.mapper.StateMapper;
import cn.stylefeng.guns.modular.service.StateService;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

    private StateMapper stateMapper;

    @Override
    public void saveStateInfo(String courtNumber, JSONObject recordJsonObject) {
        //基本信息陈述
        String stateInfo = recordJsonObject.getString("stateInfo");
        if(stateInfo != "" && stateInfo != null){
            JSONObject stateInfoObject = JSONObject.parseObject(stateInfo);
            String stateType = stateInfoObject.get("state_type").toString();
            String stateContent = stateInfoObject.get("state_content").toString();
            State state = new State();
            state.setStateType(stateType);
            state.setStateContent(stateContent);
            state.setCourtNumber(courtNumber);
            this.save(state);
        }
    }

    @Override
    public JSONObject getStateInfoObject(String courtNumber) {
        //基本信息陈述
        LambdaQueryWrapper<State> stateQueryWrapper = new LambdaQueryWrapper<>();
        stateQueryWrapper.eq(State::getCourtNumber, courtNumber);
        State state = stateMapper.selectOne(stateQueryWrapper);
        String stateType = state.getStateType();
        String stateContent = state.getStateContent();
        JSONObject stateObject = new JSONObject();
        stateObject.put("state_type", stateType);
        stateObject.put("state_content", stateContent);
        return stateObject;
    }
}
