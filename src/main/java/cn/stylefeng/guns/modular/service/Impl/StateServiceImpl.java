package cn.stylefeng.guns.modular.service.Impl;

import cn.stylefeng.guns.modular.entity.State;
import cn.stylefeng.guns.modular.mapper.StateMapper;
import cn.stylefeng.guns.modular.service.StateService;
import cn.stylefeng.roses.kernel.rule.enums.YesOrNotEnum;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

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
            String stateType = stateInfoObject.get("state_type").toString();
            String stateContent = stateInfoObject.get("state_content").toString();

            State state = new State();
            state.setStateType(stateType);
            state.setStateContent(stateContent);
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
        stateQueryWrapper.eq(State::getDelFlag, YesOrNotEnum.N.getCode());
        State state = stateService.getOne(stateQueryWrapper);
        String stateType = "1";
        String stateContent = "";
        if (!ObjectUtils.isEmpty(state)) {
            stateType = state.getStateType();
            stateContent = state.getStateContent();
        }
        JSONObject stateObject = new JSONObject();
        stateObject.put("state_type", stateType);
        stateObject.put("state_content", stateContent);
        return stateObject;
    }

    @Override
    public State getStateInfo(String courtNumber) {
        JSONObject stateInfoObject = this.getStateInfoObject(courtNumber);
        String stateContent = stateInfoObject.getString("state_content");
        State state = new State();
        state.setStateContent(stateContent);
        return state;
    }

    @Override
    public Boolean deleteStateInfo(String courtNumber) {
        LambdaUpdateWrapper<State> stateWrapper = new LambdaUpdateWrapper<>();
        stateWrapper.set(State::getDelFlag, YesOrNotEnum.Y.getCode()).eq(State::getCourtNumber, courtNumber);
        return stateService.update(stateWrapper);
    }
}
