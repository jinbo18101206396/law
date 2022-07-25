package cn.stylefeng.guns.modular.service.Impl;

import cn.stylefeng.guns.modular.entity.Agent;
import cn.stylefeng.guns.modular.entity.Defendant;
import cn.stylefeng.guns.modular.enums.*;
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
        if (ObjectUtils.isEmpty(defendantInfoArray)) {
            return;
        }
        for (int i = 0; i < defendantInfoArray.size(); i++) {
            Defendant defendant = new Defendant();
            JSONObject defendantInfoObject = defendantInfoArray.getJSONObject(i);
            String defendantLongName = defendantInfoObject.getString("defendant");
            String defendantShortName = "";
            String defendantType = defendantInfoObject.getString("defendant_type");
            if (defendantType.equals(AccuserDefendantTypeEnum.DEPARTMENT.getCode())) {
                defendantShortName = defendantInfoObject.getString("defendant_short");
            } else if (defendantType.equals(AccuserDefendantTypeEnum.PERSON.getCode())) {
                defendantShortName = defendantLongName;
            }
            String defendantInfo = defendantInfoObject.getString("defendant_info");
            defendant.setDefendant(defendantLongName);
            defendant.setDefendantShort(defendantShortName);
            defendant.setDefendantType(defendantType);
            defendant.setDefendantInfo(defendantInfo);
            defendant.setDefendantAddress(defendantInfoObject.getString("defendant_address"));
            defendant.setCourtNumber(courtNumber);
            if (defendantType.equals(AccuserDefendantTypeEnum.DEPARTMENT.getCode())) {
                defendant.setDefendantRepresent(defendantInfoObject.getString("defendant_represent"));
                defendant.setDefendantDuty(defendantInfoObject.getString("defendant_duty"));
            }

            if (recordJsonObject.containsKey("rightInfo")) {
                //是否听清诉讼权利和义务（1-听清，2-没听清）、是否申请回避(1-回避，2-不回避)
                String rightInfo = recordJsonObject.getString("rightInfo");
                JSONObject rightInfoObject = JSONObject.parseObject(rightInfo);
                JSONArray defendantRightDutyArray = rightInfoObject.getJSONArray("defendant_right_duty");
                for (int j = 0; j < defendantRightDutyArray.size(); j++) {
                    JSONObject defendantRightDutyObject = defendantRightDutyArray.getJSONObject(j);
                    String rightDutyDefendantName = defendantRightDutyObject.getString("defendant");
                    if (!ObjectUtils.isEmpty(rightDutyDefendantName) && rightDutyDefendantName.equals(defendantShortName)) {
                        defendant.setDefendantRightDuty(defendantRightDutyObject.getString("right_duty"));
                        defendant.setDefendantAvoid(defendantRightDutyObject.getString("avoid"));
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
                    String mediateDefendantName = mediateDefendantObject.getString("defendant");
                    String isMediate = mediateDefendantObject.getString("is_mediate");
                    String mediatePlan = mediateDefendantObject.getString("mediate_plan");
                    if (!ObjectUtils.isEmpty(mediateDefendantName) && mediateDefendantName.contains("（")) {
                        String defendantName = mediateDefendantName.split("（")[0];
                        if (defendantName.equals(defendantShortName)) {
                            defendant.setIsMediate(ObjectUtils.isEmpty(isMediate) ? "" : isMediate);
                            defendant.setMediatePlan(ObjectUtils.isEmpty(mediatePlan) ? "" : mediatePlan);
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
                    String deliveryDefendantName = deliveryObject.getString("name");
                    String delivery = deliveryObject.getString("is_delivery");
                    String email = deliveryObject.getString("email");
                    if (!ObjectUtils.isEmpty(deliveryDefendantName) && deliveryDefendantName.contains("（")) {
                        String name = deliveryDefendantName.split("（")[0];
                        String type = deliveryDefendantName.split("（")[1];
                        if (name.equals(defendantShortName) && type.startsWith("被告")) {
                            defendant.setIsDelivery(delivery);
                            defendant.setEmail(ObjectUtils.isEmpty(email) ? "" : email);
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
                    if (!ObjectUtils.isEmpty(finalStatementDefendantName) && finalStatementDefendantName.contains("（")) {
                        String name = finalStatementDefendantName.split("（")[0];
                        String type = finalStatementDefendantName.split("（")[1];
                        String finalStatement = finalStatementObject.getString("final_statement");
                        if (name.equals(defendantShortName) && type.startsWith("被告")) {
                            defendant.setFinalStatement(ObjectUtils.isEmpty(finalStatement) ? "" : finalStatement);
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
        List<Defendant> defendants = getDefendants(courtNumber);
        List<Agent> agents = getAgents(courtNumber);

        for (int i = 0; i < defendants.size(); i++) {
            Defendant defendant = defendants.get(i);
            String defendantName = defendant.getDefendant();
            String defendantShort = "";
            String defendantType = defendant.getDefendantType();
            if (defendantType.equals(AccuserDefendantTypeEnum.DEPARTMENT.getCode())) {
                defendantShort = defendant.getDefendantShort();
            } else if (defendantType.equals(AccuserDefendantTypeEnum.PERSON.getCode())) {
                defendantShort = defendantName;
            }
            String defendantInfo = defendant.getDefendantInfo();
            String defendantAddress = defendant.getDefendantAddress();
            String defendantRepresent = defendant.getDefendantRepresent();
            String defendantDuty = defendant.getDefendantDuty();
            JSONArray defendantAgentArray = new JSONArray();
            if (null == agents || agents.size() <= 0) {
                defendantAgentArray.add(blankAgent());
            } else {
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
            defendantInfoObject.put("defendant_info", defendantInfo);
            defendantInfoObject.put("defendant_address", defendantAddress);
            defendantInfoObject.put("defendant_represent", defendantRepresent);
            defendantInfoObject.put("defendant_duty", defendantDuty);
            if (defendantAgentArray == null || defendantAgentArray.size() <= 0) {
                defendantAgentArray.add(blankAgent());
            }
            defendantInfoObject.put("defendant_agent", defendantAgentArray);
            defendantInfoArray.add(defendantInfoObject);
        }
        if (defendantInfoArray == null || defendantInfoArray.size() <= 0) {
            defendantInfoArray.add(blankDefendant());
        }
        return defendantInfoArray;
    }

    public List<Defendant> getDefendants(String courtNumber) {
        LambdaQueryWrapper<Defendant> defendantQueryWrapper = new LambdaQueryWrapper<>();
        defendantQueryWrapper.eq(Defendant::getCourtNumber, courtNumber);
        defendantQueryWrapper.eq(Defendant::getDelFlag, YesOrNotEnum.N.getCode());
        return defendantService.list(defendantQueryWrapper);
    }

    public List<Agent> getAgents(String courtNumber) {
        LambdaQueryWrapper<Agent> agentQueryWrapper = new LambdaQueryWrapper<>();
        agentQueryWrapper.eq(Agent::getCourtNumber, courtNumber);
        agentQueryWrapper.eq(Agent::getDelFlag, YesOrNotEnum.N.getCode());
        agentQueryWrapper.eq(Agent::getAgentType, AgentTypeEnum.DEFENDANT.getCode());
        return agentService.list(agentQueryWrapper);
    }

    public JSONObject blankAgent() {
        JSONObject agentObject = new JSONObject();
        agentObject.put("agent", "");
        agentObject.put("agent_address", "");
        return agentObject;
    }

    public JSONObject blankDefendant() {
        JSONObject defendantInfoObject = new JSONObject();
        defendantInfoObject.put("defendant_type", AccuserDefendantTypeEnum.DEPARTMENT.getCode());
        defendantInfoObject.put("defendant", "");
        defendantInfoObject.put("defendant_short", "");
        defendantInfoObject.put("defendant_info", "");
        defendantInfoObject.put("defendant_address", "");
        defendantInfoObject.put("defendant_represent", "");
        defendantInfoObject.put("defendant_duty", "");
        JSONArray defendantAgentArray = new JSONArray();
        JSONObject defendantAgentObject = new JSONObject();
        defendantAgentObject.put("agent", "");
        defendantAgentObject.put("agent_address", "");
        defendantAgentArray.add(defendantAgentObject);
        defendantInfoObject.put("defendant_agent", defendantAgentArray);
        return defendantInfoObject;
    }

    @Override
    public List<Defendant> getDefendantInfoList(String courtNumber) {
        List<Defendant> defendantList = getDefendants(courtNumber);
        List<Agent> agents = getAgents(courtNumber);
        for (int i = 0; i < defendantList.size(); i++) {
            Defendant defendant = defendantList.get(i);
            String defendantName = defendant.getDefendant();
            String defendantShort = "";
            String defendantAddress = defendant.getDefendantAddress();
            String defendantType = defendant.getDefendantType();
            if (defendantType.equals(AccuserDefendantTypeEnum.DEPARTMENT.getCode())) {
                defendantShort = defendant.getDefendantShort();
            } else if (defendantType.equals(AccuserDefendantTypeEnum.PERSON.getCode())) {
                defendantShort = defendantName;
            }
            String defendantInfo = defendant.getDefendantInfo();
            if (defendantType.equals(AccuserDefendantTypeEnum.DEPARTMENT.getCode())) {
                defendant.setDefendant(defendantName + "（简称：" + defendantShort + "），地址：" + defendantAddress);
                defendant.setDefendantType("机构");
            } else if (defendantType.equals(AccuserDefendantTypeEnum.PERSON.getCode())) {
                defendant.setDefendant(defendantName + "，" + defendantInfo);
                defendant.setDefendantType("个人");
            }
            String defendantRepresent = defendant.getDefendantRepresent();
            String defendantDuty = defendant.getDefendantDuty();
            if (!ObjectUtils.isEmpty(defendantRepresent) && !ObjectUtils.isEmpty(defendantDuty)) {
                defendant.setDefendantRepresent(defendantRepresent + "，" + defendantDuty);
            }
            String defendantRightDuty = defendant.getDefendantRightDuty();
            if (!ObjectUtils.isEmpty(defendantRightDuty)) {
                defendant.setDefendantRightDuty(RightDutyEnum.getMessage(defendantRightDuty));
            }
            String defendantAvoid = defendant.getDefendantAvoid();
            if (!ObjectUtils.isEmpty(defendantAvoid)) {
                defendant.setDefendantAvoid(AvoidEnum.getMessage(defendantAvoid));
            }
            String mediate = defendant.getIsMediate();
            String mediatePlan = defendant.getMediatePlan();
            if (!ObjectUtils.isEmpty(mediate)) {
                if (mediate.equals(MediateEnum.Y.getCode())) {
                    mediate = "能，调解方案：" + mediatePlan;
                } else if (mediate.equals(MediateEnum.N.getCode())) {
                    mediate = "不能";
                }
                defendant.setIsMediate(mediate);
            }
            String delivery = defendant.getIsDelivery();
            String email = defendant.getEmail();
            if(delivery.equals(DeliveryEnum.N.getCode()) && ObjectUtils.isEmpty(email)){
                email = defendant.getDefendantAddress();
            }
            if (!ObjectUtils.isEmpty(delivery)) {
                if (delivery.equals(DeliveryEnum.Y.getCode())) {
                    delivery = "同意，邮箱地址：" + email;
                } else if (delivery.equals(DeliveryEnum.N.getCode())) {
                    delivery = "不同意，邮寄地址：" + email;
                }
                defendant.setIsDelivery(delivery);
            }
            String defendantAgent = "";
            for (int j = 0; j < agents.size(); j++) {
                Agent agent = agents.get(j);
                String agentName = agent.getAgentName();
                String agent1 = agent.getAgent();
                String agentAddress = agent.getAgentAddress();
                if (agentName.equals(defendantShort) && !ObjectUtils.isEmpty(agent1) && !ObjectUtils.isEmpty(agentAddress)) {
                    String defendantAndAddress = agent1 + "," + agentAddress;
                    defendantAgent += defendantAndAddress + "。";
                }
            }
            defendant.setDefendantAgent(defendantAgent);
        }
        return defendantList;
    }

    @Override
    public Boolean deleteDefendantInfo(String courtNumber) {
        LambdaUpdateWrapper<Defendant> defendantWrapper = new LambdaUpdateWrapper<>();
        defendantWrapper.set(Defendant::getDelFlag, YesOrNotEnum.Y.getCode()).eq(Defendant::getCourtNumber, courtNumber);
        return defendantService.update(defendantWrapper);
    }

    @Override
    public void delete(String courtNumber) {
        LambdaQueryWrapper<Defendant> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Defendant::getCourtNumber, courtNumber);
        lambdaQueryWrapper.eq(Defendant::getDelFlag, YesOrNotEnum.N.getCode());
        baseMapper.delete(lambdaQueryWrapper);
    }
}
