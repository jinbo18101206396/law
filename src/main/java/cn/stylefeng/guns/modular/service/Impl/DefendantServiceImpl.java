package cn.stylefeng.guns.modular.service.Impl;

import cn.stylefeng.guns.modular.entity.Agent;
import cn.stylefeng.guns.modular.entity.Defendant;
import cn.stylefeng.guns.modular.mapper.AgentMapper;
import cn.stylefeng.guns.modular.mapper.DefendantMapper;
import cn.stylefeng.guns.modular.service.DefendantService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 被告信息表 服务实现类
 * </p>
 *
 * @author jinbo
 * @since 2022-01-19
 */
@Service
public class DefendantServiceImpl extends ServiceImpl<DefendantMapper, Defendant> implements DefendantService {

    private DefendantMapper defendantMapper;
    private AgentMapper agentMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveDefendantInfo(String court_number, JSONObject recordJsonObject) {
        //被告信息
        JSONArray defendantInfoArray = recordJsonObject.getJSONArray("defendantInfo");
        //权利告知
        JSONObject rightInfoObject = JSONObject.parseObject(recordJsonObject.getString("rightInfo"));
        //是否能够调解
        JSONObject mediateInfoObject = JSONObject.parseObject(recordJsonObject.getString("mediateInfo"));
        //电子判决文书送达
        JSONArray deliveryInfoArray = recordJsonObject.getJSONArray("deliveryInfo");
        //最后陈述意见
        JSONArray finalStatementInfoArray = recordJsonObject.getJSONArray("finalStatementInfo");

        for (int i = 0; i < defendantInfoArray.size(); i++) {
            Defendant defendant = new Defendant();
            JSONObject defendantInfoObject = defendantInfoArray.getJSONObject(i);
            String defendantName = defendantInfoObject.get("defendant").toString();
            defendant.setDefendant(defendantName);
            defendant.setDefendantShort(defendantInfoObject.get("defendant_short").toString());
            defendant.setDefendantType(defendantInfoObject.get("defendant_type").toString());
            defendant.setDefendantAddress(defendantInfoObject.get("defendant_address").toString());
            defendant.setDefendantRepresent(defendantInfoObject.get("defendant_represent").toString());
            defendant.setDefendantDuty(defendantInfoObject.get("defendant_duty").toString());
            defendant.setCourtNumber(court_number);

            //是否听清诉讼权利和义务（1-听清，2-没听清）、是否申请回避(1-回避，2-不回避)
            JSONArray defendantRightDutyArray = rightInfoObject.getJSONArray("defendant_right_duty");
            for (int j = 0; j < defendantRightDutyArray.size(); j++) {
                JSONObject defendantRightDutyObject = defendantRightDutyArray.getJSONObject(j);
                String rightDutyDefendantName = defendantRightDutyObject.get("defendant").toString();
                if (rightDutyDefendantName.equals(defendantName)) {
                    defendant.setDefendantRightDuty(defendantRightDutyObject.get("right_duty").toString());
                    defendant.setDefendantAvoid(defendantRightDutyObject.get("avoid").toString());
                }
            }

            //是否能够调解（1-能，2-不能）、调解方案、庭外和解时限
            JSONArray mediateDefendantArray = mediateInfoObject.getJSONArray("mediate_defendant");
            for (int k = 0; k < mediateDefendantArray.size(); k++) {
                JSONObject mediateDefendantObject = mediateDefendantArray.getJSONObject(k);
                String mediateDefendantName = mediateDefendantObject.get("defendant").toString();
                if (mediateDefendantName.equals(defendantName)) {
                    defendant.setIsMediate(mediateDefendantObject.get("is_mediate").toString());
                    defendant.setMediatePlan(mediateDefendantObject.get("mediate_plan").toString());
                    defendant.setTimeLimit(mediateDefendantObject.get("time_limit").toString());
                }
            }

            //是否同意电子裁判文书送达（1-同意，2-不同意）、邮件地址
            for (int m = 0; m < deliveryInfoArray.size(); m++) {
                JSONObject deliveryObject = deliveryInfoArray.getJSONObject(m);
                //格式：姓名（类型），例如：张三（被告）
                String deliveryDefendantName = deliveryObject.get("name").toString();
                String name = deliveryDefendantName.split("（")[0];
                String type = deliveryDefendantName.split("（")[1];
                if (name.equals(defendantName) && type.startsWith("被告")) {
                    defendant.setIsDelivery(deliveryObject.get("is_delivery").toString());
                    defendant.setEmail(deliveryObject.get("email").toString());
                }
            }

            //最后陈述意见
            for (int m = 0; m < finalStatementInfoArray.size(); m++) {
                JSONObject finalStatementObject = finalStatementInfoArray.getJSONObject(m);
                //格式：姓名（类型），例如：张三（被告）
                String finalStatementDefendantName = finalStatementObject.get("name").toString();
                String name = finalStatementDefendantName.split("（")[0];
                String type = finalStatementDefendantName.split("（")[1];
                if (name.equals(defendantName) && type.startsWith("被告")) {
                    defendant.setFinalStatement(finalStatementObject.get("final_statement").toString());
                }
            }
            this.save(defendant);
        }
    }

    @Override
    public JSONArray getDefendantInfoArray(String courtNumber) {
        //被告信息
        JSONArray defendantInfoArray = new JSONArray();
        LambdaQueryWrapper<Defendant> defendantQueryWrapper = new LambdaQueryWrapper<>();
        defendantQueryWrapper.eq(Defendant::getCourtNumber, courtNumber);
        List<Defendant> defendants = defendantMapper.selectList(defendantQueryWrapper);

        //委托诉讼代理人
        LambdaQueryWrapper<Agent> agentQueryWrapper = new LambdaQueryWrapper<>();
        agentQueryWrapper.eq(Agent::getCourtNumber, courtNumber);
        agentQueryWrapper.eq(Agent::getAgentType, "2");
        List<Agent> agents = agentMapper.selectList(agentQueryWrapper);

        for (int i = 0; i < defendants.size(); i++) {
            Defendant defendant = defendants.get(i);
            String defendantType = defendant.getDefendantType();
            String defendantName = defendant.getDefendant();
            String defendantShort = defendant.getDefendantShort();
            String defendantAddress = defendant.getDefendantAddress();
            String defendantRepresent = defendant.getDefendantRepresent();
            String defendantDuty = defendant.getDefendantDuty();

            JSONArray defendantAgentArray = new JSONArray();
            for (int j = 0; j < agents.size(); j++) {
                Agent agent = agents.get(j);
                //被告姓名
                String agentName = agent.getAgentName();
                //委托诉讼代理人
                String name = agent.getAgent();
                String agentAddress = agent.getAgentAddress();
                JSONObject defendantAgentObject = new JSONObject();
                if (agentName.equals(defendantShort) || agentName.equals(defendantName)) {
                    defendantAgentObject.put("agent", name);
                    defendantAgentObject.put("agent_address", agentAddress);
                    defendantAgentArray.add(defendantAgentObject);
                }
            }
            JSONObject defendantInfoObject = new JSONObject();
            defendantInfoObject.put("defendant_type", defendantType);
            defendantInfoObject.put("defendant", defendantName);
            defendantInfoObject.put("defendant_short", defendantShort);
            defendantInfoObject.put("defendant_address", defendantAddress);
            defendantInfoObject.put("defendant_represent", defendantRepresent);
            defendantInfoObject.put("defendant_duty", defendantDuty);
            defendantInfoObject.put("defendant_agent", defendantAgentArray);
            defendantInfoArray.add(defendantInfoObject);
        }
        return defendantInfoArray;
    }
}
