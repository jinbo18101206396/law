package cn.stylefeng.guns.modular.service.Impl;

import cn.stylefeng.guns.modular.entity.Agent;
import cn.stylefeng.guns.modular.mapper.AgentMapper;
import cn.stylefeng.guns.modular.service.AgentService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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
    public void saveAgentInfo(String courtNumber, JSONObject recordJsonObject) {
        //原告信息
        JSONArray accuserInfoArray = recordJsonObject.getJSONArray("accuserInfo");
        if (accuserInfoArray.size() > 0) {
            saveAccuserAgent(courtNumber, accuserInfoArray);
        }
        //被告信息
        JSONArray defendantInfoArray = recordJsonObject.getJSONArray("defendantInfo");
        if (defendantInfoArray.size() > 0) {
            saveDefendantAgent(courtNumber, defendantInfoArray);
        }
    }

    public void saveAccuserAgent(String courtNumber, JSONArray accuserInfoArray) {
        for (int i = 0; i < accuserInfoArray.size(); i++) {
            JSONObject accuserInfoObject = accuserInfoArray.getJSONObject(i);
            String accuserName = accuserInfoObject.get("accuser").toString();
            JSONArray accuserAgentArray = accuserInfoObject.getJSONArray("accuser_agent");
            //原告代理
            for (int j = 0; j < accuserAgentArray.size(); j++) {
                Agent accuserAgent = new Agent();
                JSONObject accuserAgentObject = accuserAgentArray.getJSONObject(j);
                String agentName = accuserAgentObject.get("agent").toString();
                String agentAddress = accuserAgentObject.get("agent_address").toString();
                accuserAgent.setAgent(agentName);
                accuserAgent.setAgentAddress(agentAddress);
                accuserAgent.setAgentName(accuserName);
                //代理类型（1-原告代理，2-被告代理）
                accuserAgent.setAgentType("1");
                accuserAgent.setCourtNumber(courtNumber);
                this.save(accuserAgent);
            }
        }
    }

    public void saveDefendantAgent(String courtNumber, JSONArray defendantInfoArray) {
        for (int m = 0; m < defendantInfoArray.size(); m++) {
            JSONObject defendantInfoObject = defendantInfoArray.getJSONObject(m);
            String defendantName = defendantInfoObject.get("defendant").toString();
            JSONArray defendantAgentArray = defendantInfoObject.getJSONArray("defendant_agent");
            //被告代理
            for (int n = 0; n < defendantAgentArray.size(); n++) {
                Agent defendantAgent = new Agent();
                JSONObject defendantAgentObject = defendantAgentArray.getJSONObject(n);
                String agentName = defendantAgentObject.get("agent").toString();
                String agentAddress = defendantAgentObject.get("agent_address").toString();
                defendantAgent.setAgent(agentName);
                defendantAgent.setAgentAddress(agentAddress);
                defendantAgent.setAgentName(defendantName);
                //代理类型（1-原告代理，2-被告代理）
                defendantAgent.setAgentType("2");
                defendantAgent.setCourtNumber(courtNumber);
                this.save(defendantAgent);
            }
        }
    }
}
