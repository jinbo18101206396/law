package cn.stylefeng.guns.modular.service.Impl;

import cn.stylefeng.guns.modular.entity.Accuser;
import cn.stylefeng.guns.modular.entity.Agent;
import cn.stylefeng.guns.modular.enums.*;
import cn.stylefeng.guns.modular.mapper.AccuserMapper;
import cn.stylefeng.guns.modular.service.AccuserService;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 原告信息表 服务实现类
 * </p>
 *
 * @author jinbo
 * @since 2022-01-19
 */
@Service
public class AccuserServiceImpl extends ServiceImpl<AccuserMapper, Accuser> implements AccuserService {

    @Resource
    private AccuserService accuserService;
    @Resource
    private AgentService agentService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveAccuserInfo(String courtNumber, JSONObject recordJsonObject) {
        //原告信息
        JSONArray accuserInfoArray = recordJsonObject.getJSONArray("accuserInfo");
        if (ObjectUtils.isEmpty(accuserInfoArray)) {
            return;
        }
        List<Accuser> accusers = getAccusers(courtNumber);
        if (accusers != null && accusers.size() > 0) {
            accuserService.delete(courtNumber);
        }
        for (int i = 0; i < accuserInfoArray.size(); i++) {
            Accuser accuser = new Accuser();
            JSONObject accuserInfoObject = accuserInfoArray.getJSONObject(i);
            String accuserName = accuserInfoObject.getString("accuser");
            String accuserShortName = "";
            String accuserType = accuserInfoObject.getString("accuser_type");
            if (accuserType.equals(AccuserDefendantTypeEnum.DEPARTMENT.getCode())) {
                accuserShortName = accuserInfoObject.getString("accuser_short");
                accuser.setAccuserAddress(accuserInfoObject.getString("accuser_address"));
                accuser.setAccuserRepresent(accuserInfoObject.getString("accuser_represent"));
                accuser.setAccuserDuty(accuserInfoObject.getString("accuser_duty"));
            } else if (accuserType.equals(AccuserDefendantTypeEnum.PERSON.getCode())) {
                accuserShortName = accuserName;
                accuser.setAccuserInfo(accuserInfoObject.getString("accuser_info"));
            }
            accuser.setAccuser(accuserName);
            accuser.setAccuserShort(accuserShortName);
            accuser.setAccuserType(accuserType);
            accuser.setCourtNumber(courtNumber);

            //是否听清诉讼权利和义务（1-听清，2-没听清）、是否申请回避(1-回避，2-不回避)
            if (recordJsonObject.containsKey("rightInfo")) {
                JSONObject rightInfoObject = recordJsonObject.getJSONObject("rightInfo");
                JSONArray accuserRightDutyArray = rightInfoObject.getJSONArray("accuser_right_duty");
                for (int j = 0; j < accuserRightDutyArray.size(); j++) {
                    JSONObject accuserRightDutyObject = accuserRightDutyArray.getJSONObject(j);
                    String rightDutyAccuserName = accuserRightDutyObject.getString("accuser");
                    if (!ObjectUtils.isEmpty(rightDutyAccuserName) && rightDutyAccuserName.equals(accuserShortName)) {
                        String rightDuty = accuserRightDutyObject.getString("right_duty");
                        String avoid = accuserRightDutyObject.getString("avoid");
                        accuser.setAccuserRightDuty(rightDuty);
                        accuser.setAccuserAvoid(avoid);
                    }
                }
            }
            //是否能够调解
            if (recordJsonObject.containsKey("mediateInfo")) {
                JSONObject mediateInfoObject = recordJsonObject.getJSONObject("mediateInfo");
                //是否能够调解（1-能，2-不能）、调解方案、庭外和解时限
                JSONArray mediateAccuserArray = mediateInfoObject.getJSONArray("mediate_accuser");
                for (int k = 0; k < mediateAccuserArray.size(); k++) {
                    JSONObject mediateAccuserObject = mediateAccuserArray.getJSONObject(k);
                    String mediate = mediateAccuserObject.getString("is_mediate");
                    String mediatePlan = mediateAccuserObject.getString("mediate_plan");
                    String timeLimit = mediateAccuserObject.getString("time_limit");
                    String mediateAccuserName = mediateAccuserObject.getString("accuser");
                    if (!ObjectUtils.isEmpty(mediateAccuserName) && mediateAccuserName.contains("（")) {
                        String name = mediateAccuserName.split("（")[0];
                        if (name.equals(accuserShortName)) {
                            accuser.setIsMediate(mediate);
                            accuser.setMediatePlan(mediatePlan == null ? "" : mediatePlan);
                            accuser.setTimeLimit(timeLimit == null ? "" : timeLimit);
                        }
                    }
                }
            }
            //是否同意电子裁判文书送达（1-同意，2-不同意）、邮件地址
            if (recordJsonObject.containsKey("deliveryInfo")) {
                JSONArray deliveryInfoArray = recordJsonObject.getJSONArray("deliveryInfo");
                for (int m = 0; m < deliveryInfoArray.size(); m++) {
                    JSONObject deliveryObject = deliveryInfoArray.getJSONObject(m);
                    //格式：姓名（类型），例如：张三（原告）
                    String deliveryAccuserName = deliveryObject.getString("name");
                    String email = deliveryObject.getString("email");
                    String delivery = deliveryObject.getString("is_delivery");
                    if (!ObjectUtils.isEmpty(deliveryAccuserName) && deliveryAccuserName.contains("（")) {
                        String name = deliveryAccuserName.split("（")[0];
                        String type = deliveryAccuserName.split("（")[1];
                        if (name.equals(accuserShortName) && type.startsWith("原告")) {
                            accuser.setIsDelivery(delivery == null ? "" : delivery);
                            accuser.setEmail(email == null ? "" : email);
                        }
                    }
                }
            }
            //最后陈述意见
            if (recordJsonObject.containsKey("finalStatementInfo")) {
                JSONArray finalStatementInfoArray = recordJsonObject.getJSONArray("finalStatementInfo");
                for (int m = 0; m < finalStatementInfoArray.size(); m++) {
                    JSONObject finalStatementObject = finalStatementInfoArray.getJSONObject(m);
                    //格式：姓名（类型），例如：张三（原告）
                    String finalStatementAccuserName = finalStatementObject.getString("name");
                    if (!ObjectUtils.isEmpty(finalStatementAccuserName) && finalStatementAccuserName.contains("（")) {
                        String name = finalStatementAccuserName.split("（")[0];
                        String type = finalStatementAccuserName.split("（")[1];
                        if (name.equals(accuserShortName) && type.startsWith("原告")) {
                            accuser.setFinalStatement(finalStatementObject.getString("final_statement"));
                        }
                    }
                }
            }
            this.save(accuser);
        }
    }

    @Override
    public JSONArray getAccuserInfoArray(String courtNumber) {
        JSONArray accuserInfoArray = new JSONArray();
        List<Agent> agents = getAgents(courtNumber);
        List<Accuser> accusers = getAccusers(courtNumber);
        if (accusers == null || accusers.size() <= 0) {
            accuserInfoArray.add(blankAccuserInfoObject());
        }
        for (int i = 0; i < accusers.size(); i++) {
            JSONObject accuserInfoObject = new JSONObject();
            Accuser accuser = accusers.get(i);
            String accuserName = accuser.getAccuser();
            String accuserType = accuser.getAccuserType();
            String accuserInfo = accuser.getAccuserInfo();
            String accuserAddress = accuser.getAccuserAddress();
            String accuserRepresent = accuser.getAccuserRepresent();
            String accuserDuty = accuser.getAccuserDuty();
            String accuserShort = "";
            if (accuserType.equals(AccuserDefendantTypeEnum.PERSON.getCode())) {
                accuserShort = accuserName;
            } else if (accuserType.equals(AccuserDefendantTypeEnum.DEPARTMENT.getCode())) {
                accuserShort = accuser.getAccuserShort();
            }
            JSONArray accuserAgentArray = new JSONArray();
            if (agents == null || agents.size() <= 0) {
                accuserAgentArray.add(blankAgentObject());
            } else {
                for (int j = 0; j < agents.size(); j++) {
                    Agent agent = agents.get(j);
                    //原告姓名
                    String agentName = agent.getAgentName();
                    //委托诉讼代理人
                    String agentType = agent.getAgentType();
                    JSONObject accuserAgentObject = new JSONObject();
                    if (agentName.equals(accuserShort) && agentType.equals(AgentTypeEnum.ACCUSER.getCode())) {
                        accuserAgentObject.put("agent", agent.getAgent());
                        accuserAgentObject.put("agent_address", agent.getAgentAddress());
                        accuserAgentArray.add(accuserAgentObject);
                    }
                }
            }
            accuserInfoObject.put("accuser_type", accuserType);
            accuserInfoObject.put("accuser", accuserName);
            accuserInfoObject.put("accuser_short", accuserShort);
            accuserInfoObject.put("accuser_info", accuserInfo);
            accuserInfoObject.put("accuser_address", accuserAddress);
            accuserInfoObject.put("accuser_represent", accuserRepresent);
            accuserInfoObject.put("accuser_duty", accuserDuty);
            if (accuserAgentArray == null || accuserAgentArray.size() <= 0) {
                accuserAgentArray.add(blankAgentObject());
            }
            accuserInfoObject.put("accuser_agent", accuserAgentArray);
            accuserInfoArray.add(accuserInfoObject);
        }
        if (accuserInfoArray == null || accuserInfoArray.size() <= 0) {
            accuserInfoArray.add(blankAccuserInfoObject());
        }
        return accuserInfoArray;
    }

    public JSONObject blankAccuserInfoObject() {
        JSONObject accuserInfoObject = new JSONObject();
        accuserInfoObject.put("accuser_type", AccuserDefendantTypeEnum.DEPARTMENT.getCode());
        accuserInfoObject.put("accuser", "");
        accuserInfoObject.put("accuser_short", "");
        accuserInfoObject.put("accuser_info", "");
        accuserInfoObject.put("accuser_address", "");
        accuserInfoObject.put("accuser_represent", "");
        accuserInfoObject.put("accuser_duty", "");
        JSONArray accuserAgentArray = new JSONArray();
        JSONObject accuserAgentObject = new JSONObject();
        accuserAgentObject.put("agent", "");
        accuserAgentObject.put("agent_address", "");
        accuserAgentArray.add(accuserAgentObject);
        accuserInfoObject.put("accuser_agent", accuserAgentArray);
        return accuserInfoObject;
    }

    public JSONObject blankAgentObject() {
        JSONObject agentObject = new JSONObject();
        agentObject.put("agent", "");
        agentObject.put("agent_address", "");
        return agentObject;
    }

    public List<Accuser> getAccusers(String courtNumber) {
        LambdaQueryWrapper<Accuser> accuserQueryWrapper = new LambdaQueryWrapper<>();
        accuserQueryWrapper.eq(Accuser::getCourtNumber, courtNumber);
        accuserQueryWrapper.eq(Accuser::getDelFlag, YesOrNotEnum.N.getCode());
        return accuserService.list(accuserQueryWrapper);
    }

    public List<Agent> getAgents(String courtNumber) {
        LambdaQueryWrapper<Agent> agentQueryWrapper = new LambdaQueryWrapper<>();
        agentQueryWrapper.eq(Agent::getCourtNumber, courtNumber);
        agentQueryWrapper.eq(Agent::getDelFlag, YesOrNotEnum.N.getCode());
        agentQueryWrapper.eq(Agent::getAgentType, AgentTypeEnum.ACCUSER.getCode());
        return agentService.list(agentQueryWrapper);
    }

    @Override
    public List<Accuser> getAccuserInfoList(String courtNumber, Map<String, Object> recordMap) {
        List<Agent> agents = getAgents(courtNumber);
        List<Accuser> accuserList = getAccusers(courtNumber);
        for (int i = 0; i < accuserList.size(); i++) {
            Accuser accuser = accuserList.get(i);
            String accuserName = accuser.getAccuser();
            if (ObjectUtils.isEmpty(accuserName)) {
                continue;
            }
            String accuserShort = accuser.getAccuserShort();
            String accuserAddress = accuser.getAccuserAddress();
            String accuserType = accuser.getAccuserType();
            String accuserInfo = accuser.getAccuserInfo();
            if (accuserType.equals(AccuserDefendantTypeEnum.DEPARTMENT.getCode())) {
                accuser.setAccuser(accuserName + "（简称：" + accuserShort + "），地址：" + accuserAddress);
                accuser.setAccuserType("机构");
            } else if (accuserType.equals(AccuserDefendantTypeEnum.PERSON.getCode())) {
                accuser.setAccuser(accuserName + "，" + accuserInfo);
                accuser.setAccuserType("个人");
            }
            String accuserRepresent = accuser.getAccuserRepresent();
            String accuserDuty = accuser.getAccuserDuty();
            if (!ObjectUtils.isEmpty(accuserRepresent) && !ObjectUtils.isEmpty(accuserDuty)) {
                accuser.setAccuserRepresent(accuserRepresent + "，" + accuserDuty);
            }
            String accuserRightDuty = accuser.getAccuserRightDuty();
            if (!ObjectUtils.isEmpty(accuserRightDuty)) {
                accuser.setAccuserRightDuty(RightDutyEnum.getMessage(accuserRightDuty));
            }
            String accuserAvoid = accuser.getAccuserAvoid();
            if (!ObjectUtils.isEmpty(accuserAvoid)) {
                accuser.setAccuserAvoid(AvoidEnum.getMessage(accuserAvoid));
            }
            String isFinalStatement = "";
            String finalStatement = accuser.getFinalStatement();
            if(!ObjectUtils.isEmpty(finalStatement)){
                isFinalStatement = "是";
            }
            recordMap.put("accuserIsFinalStatement",isFinalStatement);
            accuser.setFinalStatement(finalStatement);

            String mediate = accuser.getIsMediate();
            String mediatePlan = accuser.getMediatePlan();
            String timeLimit = accuser.getTimeLimit();
            if (mediate.equals(MediateEnum.Y.getCode())) {
                if(!ObjectUtils.isEmpty(mediatePlan) || !ObjectUtils.isEmpty(timeLimit)){
                    mediate = "能，调解方案：" + mediatePlan + "，庭外和解时限：" + timeLimit;
                }else{
                    mediate = "";
                }
            } else if (mediate.equals(MediateEnum.N.getCode())) {
                mediate = "不能";
            }
            accuser.setIsMediate(mediate);
            recordMap.put("accuserIsMediate",mediate);

            String isDelivery = "";
            String delivery = accuser.getIsDelivery();
            String email = accuser.getEmail();
            if(ObjectUtils.isEmpty(email)){
                accuser.setIsDelivery("");
            }else{
                if (delivery.equals(DeliveryEnum.Y.getCode())) {
                    delivery = "同意，电子邮箱：" + email;
                } else if (delivery.equals(DeliveryEnum.N.getCode())) {
                    delivery = "不同意，纸质文书送达地址：" + email;
                }
                accuser.setIsDelivery(delivery);
                isDelivery = "是";
            }
            recordMap.put("accuserIsDelivery",isDelivery);

            List<String> accuserAgentList = new ArrayList<>();
            for (int j = 0; j < agents.size(); j++) {
                Agent agent = agents.get(j);
                String agentName = agent.getAgentName();
                String agent1 = agent.getAgent();
                String agentAddress = agent.getAgentAddress();
                if (agentName.equals(accuserShort) && !ObjectUtils.isEmpty(agent1) && !ObjectUtils.isEmpty(agentAddress)) {
                    String agentAndAddress = agent1 + "，" + agentAddress + "。";
                    accuserAgentList.add(agentAndAddress);
                }
            }
            accuser.setAccuserAgent(accuserAgentList);
            accuser.setIsSupplyEvidence("没有");
        }
        return accuserList;
    }

    @Override
    public Boolean deleteAccuserInfo(String courtNumber) {
        LambdaUpdateWrapper<Accuser> accuserWrapper = new LambdaUpdateWrapper<>();
        accuserWrapper.set(Accuser::getDelFlag, YesOrNotEnum.Y.getCode()).eq(Accuser::getCourtNumber, courtNumber);
        return accuserService.update(accuserWrapper);
    }

    @Override
    public void delete(String courtNumber) {
        LambdaQueryWrapper<Accuser> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Accuser::getCourtNumber, courtNumber);
        lambdaQueryWrapper.eq(Accuser::getDelFlag, YesOrNotEnum.N.getCode());
        baseMapper.delete(lambdaQueryWrapper);
    }
}
