package cn.stylefeng.guns.modular.service.Impl;

import cn.stylefeng.guns.modular.entity.Agent;
import cn.stylefeng.guns.modular.enums.AccuserDefendantTypeEnum;
import cn.stylefeng.guns.modular.enums.AgentTypeEnum;
import cn.stylefeng.guns.modular.mapper.AgentMapper;
import cn.stylefeng.guns.modular.service.AgentService;
import cn.stylefeng.roses.kernel.rule.enums.YesOrNotEnum;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;

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

    @Resource
    private AgentService agentService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveAgentInfo(String courtNumber, JSONObject recordJsonObject) {
        //原告信息
        JSONArray accuserInfoArray = recordJsonObject.getJSONArray("accuserInfo");
        if (!ObjectUtils.isEmpty(accuserInfoArray)) {
            saveAccuserAgent(courtNumber, accuserInfoArray);
        }
        //被告信息
        JSONArray defendantInfoArray = recordJsonObject.getJSONArray("defendantInfo");
        if (!ObjectUtils.isEmpty(accuserInfoArray)) {
            saveDefendantAgent(courtNumber, defendantInfoArray);
        }
        //第三人信息
        JSONArray thirdPartyInfoArray = recordJsonObject.getJSONArray("thirdPartyInfo");
        if (!ObjectUtils.isEmpty(thirdPartyInfoArray)) {
            saveThirdPartyAgent(courtNumber, thirdPartyInfoArray);
        }
    }

    @Override
    public Boolean deleteAgentInfo(String courtNumber) {
        LambdaUpdateWrapper<Agent> agentWrapper = new LambdaUpdateWrapper<>();
        agentWrapper.set(Agent::getDelFlag, YesOrNotEnum.Y.getCode()).eq(Agent::getCourtNumber, courtNumber);
        return agentService.update(agentWrapper);
    }

    @Override
    public void delete(String courtNumber) {
        LambdaQueryWrapper<Agent> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Agent::getCourtNumber, courtNumber);
        lambdaQueryWrapper.eq(Agent::getDelFlag, YesOrNotEnum.N.getCode());
        baseMapper.delete(lambdaQueryWrapper);
    }

    public void saveAccuserAgent(String courtNumber, JSONArray accuserInfoArray) {
        for (int i = 0; i < accuserInfoArray.size(); i++) {
            JSONObject accuserInfoObject = accuserInfoArray.getJSONObject(i);
            String accuserShortName = accuserInfoObject.getString("accuser_short");
            JSONArray accuserAgentArray = accuserInfoObject.getJSONArray("accuser_agent");
            String accuserType = accuserInfoObject.getString("accuser_type");
            if (!ObjectUtils.isEmpty(accuserType) && accuserType.equals(AccuserDefendantTypeEnum.PERSON.getCode())) {
                accuserShortName = accuserInfoObject.getString("accuser");
            }
            if (!ObjectUtils.isEmpty(accuserAgentArray)) {
                saveAgent(courtNumber, accuserShortName, AgentTypeEnum.ACCUSER.getCode(), accuserAgentArray);
            }
        }
    }

    public void saveDefendantAgent(String courtNumber, JSONArray defendantInfoArray) {
        for (int m = 0; m < defendantInfoArray.size(); m++) {
            JSONObject defendantInfoObject = defendantInfoArray.getJSONObject(m);
            String defendantShortName = defendantInfoObject.getString("defendant_short");
            JSONArray defendantAgentArray = defendantInfoObject.getJSONArray("defendant_agent");
            String defendantType = defendantInfoObject.getString("defendant_type");
            if (!ObjectUtils.isEmpty(defendantType) && "2".equals(defendantType)) {
                defendantShortName = defendantInfoObject.getString("defendant");
            }
            if (!ObjectUtils.isEmpty(defendantAgentArray)) {
                saveAgent(courtNumber, defendantShortName, AgentTypeEnum.DEFENDANT.getCode(), defendantAgentArray);
            }
        }
    }

    public void saveThirdPartyAgent(String courtNumber, JSONArray thirdPartyInfoArray) {
        for (int m = 0; m < thirdPartyInfoArray.size(); m++) {
            JSONObject thirdPartyInfoObject = thirdPartyInfoArray.getJSONObject(m);
            String thirdPartyName = thirdPartyInfoObject.getString("third_party");
            String thirdPartyShortName = "";
            JSONArray thirdPartyAgentArray = thirdPartyInfoObject.getJSONArray("third_party_agent");
            String thirdPartyType = thirdPartyInfoObject.getString("third_party_type");
            if (thirdPartyType.equals(AccuserDefendantTypeEnum.PERSON.getCode())) {
                thirdPartyShortName = thirdPartyName;
            } else if (thirdPartyType.equals(AccuserDefendantTypeEnum.DEPARTMENT.getCode())) {
                thirdPartyShortName = thirdPartyInfoObject.getString("third_party_short");
            }
            if (!ObjectUtils.isEmpty(thirdPartyAgentArray)) {
                saveAgent(courtNumber, thirdPartyShortName, AgentTypeEnum.THIRD.getCode(), thirdPartyAgentArray);
            }
        }
    }

    public void saveAgent(String courtNumber, String shortName, String agentType, JSONArray agentArray) {
        for (int n = 0; n < agentArray.size(); n++) {
            Agent agent = new Agent();
            JSONObject agentObject = agentArray.getJSONObject(n);
            String agentName = agentObject.getString("agent");
            if (ObjectUtils.isEmpty(agentName)) {
                continue;
            }
            String agentAddress = agentObject.getString("agent_address");
            agent.setAgent(agentName);
            agent.setAgentAddress(agentAddress);
            agent.setAgentName(shortName);
            //代理类型（1-原告代理，2-被告代理，3-第三人代理）
            agent.setAgentType(agentType);
            agent.setCourtNumber(courtNumber);
            this.save(agent);
        }
    }
}
