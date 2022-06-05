package cn.stylefeng.guns.modular.service.Impl;

import cn.stylefeng.guns.modular.entity.Accuser;
import cn.stylefeng.guns.modular.entity.Agent;
import cn.stylefeng.guns.modular.entity.Defendant;
import cn.stylefeng.guns.modular.mapper.DefendantMapper;
import cn.stylefeng.guns.modular.service.AgentService;
import cn.stylefeng.guns.modular.service.DefendantService;
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

    @Resource
    private DefendantService defendantService;
    @Resource
    private AgentService agentService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveDefendantInfo(String courtNumber, JSONObject recordJsonObject) {
        //被告信息
        JSONArray defendantInfoArray = recordJsonObject.getJSONArray("defendantInfo");
        if(ObjectUtils.isEmpty(defendantInfoArray)){
            return;
        }
        for (int i = 0; i < defendantInfoArray.size(); i++) {
            Defendant defendant = new Defendant();
            JSONObject defendantInfoObject = defendantInfoArray.getJSONObject(i);
            defendant.setDefendant(defendantInfoObject.get("defendant").toString());
            String defendantShortName = defendantInfoObject.get("defendant_short").toString();
            //被告类型（1-机构，2-个人）
            String defendantType = defendantInfoObject.get("defendant_type").toString();
            defendant.setDefendantType(defendantType);
            defendant.setDefendantShort(defendantShortName);
            defendant.setDefendantAddress(defendantInfoObject.get("defendant_address").toString());
            //被告（机构）
            if ("1".equals(defendantType)) {
                defendant.setDefendantRepresent(defendantInfoObject.get("defendant_represent").toString());
                defendant.setDefendantDuty(defendantInfoObject.get("defendant_duty").toString());
            }
            defendant.setCourtNumber(courtNumber);

            if (recordJsonObject.containsKey("rightInfo")) {
                //是否听清诉讼权利和义务（1-听清，2-没听清）、是否申请回避(1-回避，2-不回避)
                String rightInfo = recordJsonObject.getString("rightInfo");
                JSONObject rightInfoObject = JSONObject.parseObject(rightInfo);
                JSONArray defendantRightDutyArray = rightInfoObject.getJSONArray("defendant_right_duty");
                for (int j = 0; j < defendantRightDutyArray.size(); j++) {
                    JSONObject defendantRightDutyObject = defendantRightDutyArray.getJSONObject(j);
                    String rightDutyDefendantName = defendantRightDutyObject.get("defendant").toString();
                    if (!"".equals(rightDutyDefendantName) && rightDutyDefendantName.equals(defendantShortName)) {
                        defendant.setDefendantRightDuty(defendantRightDutyObject.get("right_duty").toString());
                        defendant.setDefendantAvoid(defendantRightDutyObject.get("avoid").toString());
                    }
                }
            }

            //是否能够调解
            if (recordJsonObject.containsKey("mediateInfo")) {
                String mediateInfo = recordJsonObject.getString("mediateInfo");
                JSONObject mediateInfoObject = JSONObject.parseObject(mediateInfo);
                //是否能够调解（1-能，2-不能）、调解方案、庭外和解时限
                JSONArray mediateDefendantArray = mediateInfoObject.getJSONArray("mediate_defendant");
                for (int k = 0; k < mediateDefendantArray.size(); k++) {
                    JSONObject mediateDefendantObject = mediateDefendantArray.getJSONObject(k);
                    String mediateDefendantName = mediateDefendantObject.get("defendant").toString();

                    if(!ObjectUtils.isEmpty(mediateDefendantName) && mediateDefendantName.contains("（")){
                        String defendantName = mediateDefendantName.split("（")[0];
                        if(defendantName.equals(defendantShortName)){
                            defendant.setIsMediate(mediateDefendantObject.get("is_mediate").toString());
                            defendant.setMediatePlan(mediateDefendantObject.get("mediate_plan").toString());
                        }
                    }
                }
            }

            //是否同意电子裁判文书送达（1-同意，2-不同意）、邮件地址
            if (recordJsonObject.containsKey("deliveryInfo")) {
                JSONArray deliveryInfoArray = recordJsonObject.getJSONArray("deliveryInfo");
                for (int m = 0; m < deliveryInfoArray.size(); m++) {
                    JSONObject deliveryObject = deliveryInfoArray.getJSONObject(m);
                    //格式：姓名（类型），例如：张三（被告）
                    String deliveryDefendantName = deliveryObject.get("name").toString();
                    String delivery = deliveryObject.get("is_delivery").toString();
                    if(!ObjectUtils.isEmpty(deliveryDefendantName) && deliveryDefendantName.contains("（")){
                        String name = deliveryDefendantName.split("（")[0];
                        String type = deliveryDefendantName.split("（")[1];
                        if (name.equals(defendantShortName) && type.startsWith("被告")) {
                            defendant.setIsDelivery(delivery);
                            defendant.setEmail(deliveryObject.get("email").toString());
                        }
                    }
                }
            }

            //最后陈述意见
            if (recordJsonObject.containsKey("finalStatementInfo")) {
                JSONArray finalStatementInfoArray = recordJsonObject.getJSONArray("finalStatementInfo");
                for (int m = 0; m < finalStatementInfoArray.size(); m++) {
                    JSONObject finalStatementObject = finalStatementInfoArray.getJSONObject(m);
                    //格式：姓名（类型），例如：张三（被告）
                    String finalStatementDefendantName = finalStatementObject.get("name").toString();
                    if(!ObjectUtils.isEmpty(finalStatementDefendantName) && finalStatementDefendantName.contains("（")){
                        String name = finalStatementDefendantName.split("（")[0];
                        String type = finalStatementDefendantName.split("（")[1];
                        if (name.equals(defendantShortName) && type.startsWith("被告")) {
                            defendant.setFinalStatement(finalStatementObject.get("final_statement").toString());
                        }
                    }
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
        defendantQueryWrapper.eq(Defendant::getDelFlag, YesOrNotEnum.N.getCode());
        List<Defendant> defendants = defendantService.list(defendantQueryWrapper);
        //委托诉讼代理人
        LambdaQueryWrapper<Agent> agentQueryWrapper = new LambdaQueryWrapper<>();
        agentQueryWrapper.eq(Agent::getCourtNumber, courtNumber);
        agentQueryWrapper.eq(Agent::getDelFlag, YesOrNotEnum.N.getCode());
        agentQueryWrapper.eq(Agent::getAgentType, "2");
        List<Agent> agents = agentService.list(agentQueryWrapper);

        //若被告为空
        if (null == defendants || defendants.size() == 0) {
            JSONObject defendantInfoObject = new JSONObject();
            defendantInfoObject.put("defendant_type", "1");
            defendantInfoObject.put("defendant", "");
            defendantInfoObject.put("defendant_short", "");
            defendantInfoObject.put("defendant_address", "");
            defendantInfoObject.put("defendant_represent", "");
            defendantInfoObject.put("defendant_duty", "");
            JSONArray defendantAgentArray = new JSONArray();
            JSONObject defendantAgentObject = new JSONObject();
            defendantAgentObject.put("agent", "");
            defendantAgentObject.put("agent_address", "");
            defendantAgentArray.add(defendantAgentObject);
            defendantInfoObject.put("defendant_agent", defendantAgentArray);
            defendantInfoArray.add(defendantInfoObject);
        } else {
            for (int i = 0; i < defendants.size(); i++) {
                Defendant defendant = defendants.get(i);
                String defendantType = defendant.getDefendantType();
                String defendantName = defendant.getDefendant();
                String defendantShort = defendant.getDefendantShort();
                String defendantAddress = defendant.getDefendantAddress();
                String defendantRepresent = defendant.getDefendantRepresent();
                String defendantDuty = defendant.getDefendantDuty();
                JSONArray defendantAgentArray = new JSONArray();
                if(null == agents || agents.size() == 0){
                    JSONObject defendantAgentObject = new JSONObject();
                    defendantAgentObject.put("agent", "");
                    defendantAgentObject.put("agent_address", "");
                    defendantAgentArray.add(defendantAgentObject);
                }else{
                    for (int j = 0; j < agents.size(); j++) {
                        Agent agent = agents.get(j);
                        //被告姓名
                        String agentName = agent.getAgentName();
                        //委托诉讼代理人
                        String agentType = agent.getAgentType();
                        JSONObject defendantAgentObject = new JSONObject();
                        if (agentName.equals(defendantShort) && agentType.equals("2")) {
                            defendantAgentObject.put("agent", agent.getAgent());
                            defendantAgentObject.put("agent_address", agent.getAgentAddress());
                            defendantAgentArray.add(defendantAgentObject);
                        }
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
        }
        return defendantInfoArray;
    }

    @Override
    public Boolean deleteDefendantInfo(String courtNumber) {
        LambdaUpdateWrapper<Defendant> defendantWrapper = new LambdaUpdateWrapper<>();
        defendantWrapper.set(Defendant::getDelFlag, YesOrNotEnum.Y.getCode()).eq(Defendant::getCourtNumber,courtNumber);
        return defendantService.update(defendantWrapper);
    }
}
