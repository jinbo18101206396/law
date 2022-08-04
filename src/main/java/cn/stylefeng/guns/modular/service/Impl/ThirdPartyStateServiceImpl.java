package cn.stylefeng.guns.modular.service.Impl;

import cn.stylefeng.guns.modular.entity.ThirdParty;
import cn.stylefeng.guns.modular.entity.ThirdPartyState;
import cn.stylefeng.guns.modular.mapper.ThirdPartyStateMapper;
import cn.stylefeng.guns.modular.service.ThirdPartyStateService;
import cn.stylefeng.roses.kernel.rule.enums.YesOrNotEnum;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 第三人述称表 服务实现类
 * </p>
 *
 * @author jinbo
 * @since 2022-08-03
 */
@Service
public class ThirdPartyStateServiceImpl extends ServiceImpl<ThirdPartyStateMapper, ThirdPartyState> implements ThirdPartyStateService {

    @Resource
    private ThirdPartyStateService thirdPartyStateService;

    /**
     * 保存第三人述称
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveThirdPartyStateInfo(String courtNumber, JSONObject courtInvestigateObject) {
        if (courtInvestigateObject != null && courtInvestigateObject.containsKey("third_party_state")) {
            List<ThirdPartyState> thirdPartStates = getThirdPartStates(courtNumber);
            if(thirdPartStates != null && thirdPartStates.size() > 0){
                thirdPartyStateService.delete(courtNumber);
            }
            JSONArray thirdPartyStateArray = courtInvestigateObject.getJSONArray("third_party_state");
            for (int i = 0; i < thirdPartyStateArray.size(); i++) {
                JSONObject thirdPartyStateObject = thirdPartyStateArray.getJSONObject(i);
                String name = thirdPartyStateObject.getString("name");
                String state = thirdPartyStateObject.getString("state");
                if(ObjectUtils.isEmpty(name) || ObjectUtils.isEmpty(state)){
                    continue;
                }
                ThirdPartyState thirdPartyState = new ThirdPartyState();
                thirdPartyState.setCourtNumber(courtNumber);
                thirdPartyState.setName(name);
                thirdPartyState.setState(state);
                thirdPartyStateService.save(thirdPartyState);
            }
        }
    }

    @Override
    public void delete(String courtNumber) {
        LambdaQueryWrapper<ThirdPartyState> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(ThirdPartyState::getCourtNumber, courtNumber);
        lambdaQueryWrapper.eq(ThirdPartyState::getDelFlag, YesOrNotEnum.N.getCode());
        baseMapper.delete(lambdaQueryWrapper);
    }

    public List<ThirdPartyState> getThirdPartStates(String courtNumber) {
        LambdaQueryWrapper<ThirdPartyState> thirdPartyStateQueryWrapper = new LambdaQueryWrapper<>();
        thirdPartyStateQueryWrapper.eq(ThirdPartyState::getCourtNumber, courtNumber);
        thirdPartyStateQueryWrapper.eq(ThirdPartyState::getDelFlag, YesOrNotEnum.N.getCode());
        return thirdPartyStateService.list(thirdPartyStateQueryWrapper);
    }
}