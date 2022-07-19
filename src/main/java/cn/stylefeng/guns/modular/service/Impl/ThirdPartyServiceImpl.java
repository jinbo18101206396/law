package cn.stylefeng.guns.modular.service.Impl;

import cn.stylefeng.guns.modular.entity.Agent;
import cn.stylefeng.guns.modular.entity.ThirdParty;
import cn.stylefeng.guns.modular.enums.AccuserDefendantTypeEnum;
import cn.stylefeng.guns.modular.enums.AgentTypeEnum;
import cn.stylefeng.guns.modular.mapper.ThirdPartyMapper;
import cn.stylefeng.guns.modular.service.AgentService;
import cn.stylefeng.guns.modular.service.ThirdPartyService;
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
 * 第三人信息表 服务实现类
 * </p>
 *
 * @author jinbo
 * @since 2022-07-12
 */
@Service
public class ThirdPartyServiceImpl extends ServiceImpl<ThirdPartyMapper, ThirdParty> implements ThirdPartyService {

    @Resource
    private ThirdPartyService thirdPartyService;
    @Resource
    private AgentService agentService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveThirdPartyInfo(String courtNumber, JSONObject recordJsonObject) {
        //第三人信息
        JSONArray thirdPartyInfoArray = recordJsonObject.getJSONArray("thirdPartyInfo");
        if (ObjectUtils.isEmpty(thirdPartyInfoArray)) {
            return;
        }
        for (int i = 0; i < thirdPartyInfoArray.size(); i++) {
            JSONObject thirdPartyInfoObject = thirdPartyInfoArray.getJSONObject(i);
            String thirdPartyName = thirdPartyInfoObject.getString("third_party");
            String thirdPartyShort = "";
            String thirdPartyType = thirdPartyInfoObject.getString("third_party_type");
            if (thirdPartyType.equals(AccuserDefendantTypeEnum.PERSON.getCode())) {
                thirdPartyShort = thirdPartyName;
            } else if (thirdPartyType.equals(AccuserDefendantTypeEnum.DEPARTMENT.getCode())) {
                thirdPartyShort = thirdPartyInfoObject.getString("third_party_short");
            }
            String thirdPartyInfo = thirdPartyInfoObject.getString("third_party_info");
            String thirdPartyAddress = thirdPartyInfoObject.getString("third_party_address");
            String thirdPartyRepresent = thirdPartyInfoObject.getString("third_party_represent");
            String thirdPartyDuty = thirdPartyInfoObject.getString("third_party_duty");
            ThirdParty thirdParty = new ThirdParty();
            thirdParty.setThirdParty(thirdPartyName);
            thirdParty.setThirdPartyShort(thirdPartyShort);
            thirdParty.setThirdPartyType(thirdPartyType);
            thirdParty.setCourtNumber(courtNumber);
            //第三人（机构）
            if (thirdPartyType.equals(AccuserDefendantTypeEnum.DEPARTMENT.getCode())) {
                thirdParty.setThirdPartyRepresent(thirdPartyRepresent);
                thirdParty.setThirdPartyDuty(thirdPartyDuty);
                thirdParty.setThirdPartyAddress(thirdPartyAddress);
            } else if (thirdPartyType.equals(AccuserDefendantTypeEnum.PERSON.getCode())) {
                thirdParty.setThirdPartyInfo(thirdPartyInfo);
            }
            //是否听清诉讼权利和义务（1-听清，2-没听清）、是否申请回避(1-回避，2-不回避)
            if (recordJsonObject.containsKey("rightInfo")) {
                String rightInfo = recordJsonObject.getString("rightInfo");
                JSONObject rightInfoObject = JSONObject.parseObject(rightInfo);
                JSONArray thirdPartyRightDutyArray = rightInfoObject.getJSONArray("third_party_right_duty");
                for (int j = 0; j < thirdPartyRightDutyArray.size(); j++) {
                    JSONObject thirdPartyRightDutyObject = thirdPartyRightDutyArray.getJSONObject(j);
                    String rightDutyThirdPartyName = thirdPartyRightDutyObject.getString("third_party");
                    String rightDuty = thirdPartyRightDutyObject.getString("right_duty");
                    String avoid = thirdPartyRightDutyObject.getString("avoid");
                    if (!ObjectUtils.isEmpty(rightDutyThirdPartyName) && rightDutyThirdPartyName.equals(thirdPartyShort)) {
                        thirdParty.setThirdPartyRightDuty(rightDuty);
                        thirdParty.setThirdPartyAvoid(avoid);
                    }
                }
            }
            //是否能够调解
            if (recordJsonObject.containsKey("mediateInfo")) {
                String mediateInfo = recordJsonObject.getString("mediateInfo");
                JSONObject mediateInfoObject = JSONObject.parseObject(mediateInfo);
                //是否能够调解（1-能，2-不能）、调解方案、庭外和解时限
                JSONArray mediateThirdPartyArray = mediateInfoObject.getJSONArray("mediate_third_party");
                for (int k = 0; k < mediateThirdPartyArray.size(); k++) {
                    JSONObject mediateThirdPartyObject = mediateThirdPartyArray.getJSONObject(k);
                    String mediateThirdPartyName = mediateThirdPartyObject.getString("third_party");
                    String isMediate = mediateThirdPartyObject.getString("is_mediate");
                    String mediatePlan = mediateThirdPartyObject.getString("mediate_plan");
                    if (!ObjectUtils.isEmpty(mediateThirdPartyName) && mediateThirdPartyName.contains("（")) {
                        String name = mediateThirdPartyName.split("（")[0];
                        if (name.equals(thirdPartyShort)) {
                            thirdParty.setIsMediate(ObjectUtils.isEmpty(isMediate) ? "" : isMediate);
                            thirdParty.setMediatePlan(ObjectUtils.isEmpty(mediatePlan) ? "" : mediatePlan);
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
                        if (name.equals(thirdPartyShort) && type.startsWith("第三人")) {
                            thirdParty.setIsDelivery(delivery);
                            thirdParty.setEmail(ObjectUtils.isEmpty(email) ? "" : email);
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
                    String finalStatementDefendantName = finalStatementObject.getString("name");
                    if (!ObjectUtils.isEmpty(finalStatementDefendantName) && finalStatementDefendantName.contains("（")) {
                        String name = finalStatementDefendantName.split("（")[0];
                        String type = finalStatementDefendantName.split("（")[1];
                        String finalStatement = finalStatementObject.getString("final_statement");
                        if (name.equals(thirdPartyShort) && type.startsWith("第三人")) {
                            thirdParty.setFinalStatement(ObjectUtils.isEmpty(finalStatement) ? "" : finalStatement);
                        }
                    }
                }
            }
            this.save(thirdParty);
        }
    }

    @Override
    public JSONArray getThirdPartyInfoArray(String courtNumber) {
        JSONArray thirdPartyInfoArray = new JSONArray();
        List<Agent> agents = getAgents(courtNumber);
        List<ThirdParty> thirdParties = getThirdParts(courtNumber);
        for (int i = 0; i < thirdParties.size(); i++) {
            ThirdParty thirdParty = thirdParties.get(i);
            String thirdPartyName = thirdParty.getThirdParty();
            String thirdPartyShort = "";
            String thirdPartyType = thirdParty.getThirdPartyType();
            if (thirdPartyType.equals(AccuserDefendantTypeEnum.DEPARTMENT.getCode())) {
                thirdPartyShort = thirdParty.getThirdPartyShort();
            } else if (thirdPartyType.equals(AccuserDefendantTypeEnum.PERSON.getCode())) {
                thirdPartyShort = thirdPartyName;
            }
            String thirdPartyInfo = thirdParty.getThirdPartyInfo();
            String thirdPartyAddress = thirdParty.getThirdPartyAddress();
            String thirdPartyRepresent = thirdParty.getThirdPartyRepresent();
            String thirdPartyDuty = thirdParty.getThirdPartyDuty();
            JSONArray thirdPartyAgentArray = new JSONArray();
            if (agents == null || agents.size() <= 0) {
                thirdPartyAgentArray.add(blankAgent());
            } else {
                for (int j = 0; j < agents.size(); j++) {
                    Agent agent = agents.get(j);
                    String agentName = agent.getAgentName();
                    String agentType = agent.getAgentType();
                    JSONObject thirdPartyAgentObject = new JSONObject();
                    if (agentName.equals(thirdPartyShort) && agentType.equals(AgentTypeEnum.THIRD.getCode())) {
                        thirdPartyAgentObject.put("agent", agent.getAgent());
                        thirdPartyAgentObject.put("agent_address", agent.getAgentAddress());
                        thirdPartyAgentArray.add(thirdPartyAgentObject);
                    }
                }
            }
            JSONObject thirdPartyInfoObject = new JSONObject();
            thirdPartyInfoObject.put("third_party_type", thirdPartyType);
            thirdPartyInfoObject.put("third_party", thirdPartyName);
            thirdPartyInfoObject.put("third_party_short", thirdPartyShort);
            thirdPartyInfoObject.put("third_party_info", thirdPartyInfo);
            thirdPartyInfoObject.put("third_party_address", thirdPartyAddress);
            thirdPartyInfoObject.put("third_party_represent", thirdPartyRepresent);
            thirdPartyInfoObject.put("third_party_duty", thirdPartyDuty);
            if (thirdPartyAgentArray == null || thirdPartyAgentArray.size() <= 0) {
                thirdPartyAgentArray.add(blankAgent());
            }
            thirdPartyInfoObject.put("third_party_agent", thirdPartyAgentArray);
            thirdPartyInfoArray.add(thirdPartyInfoObject);
        }
        if (thirdPartyInfoArray == null || thirdPartyInfoArray.size() <= 0) {
            thirdPartyInfoArray.add(blankThirdParty());
        }
        return thirdPartyInfoArray;
    }

    public List<ThirdParty> getThirdParts(String courtNumber) {
        LambdaQueryWrapper<ThirdParty> thirdPartyQueryWrapper = new LambdaQueryWrapper<>();
        thirdPartyQueryWrapper.eq(ThirdParty::getCourtNumber, courtNumber);
        thirdPartyQueryWrapper.eq(ThirdParty::getDelFlag, YesOrNotEnum.N.getCode());
        return thirdPartyService.list(thirdPartyQueryWrapper);
    }

    public List<Agent> getAgents(String courtNumber) {
        LambdaQueryWrapper<Agent> agentQueryWrapper = new LambdaQueryWrapper<>();
        agentQueryWrapper.eq(Agent::getCourtNumber, courtNumber);
        agentQueryWrapper.eq(Agent::getDelFlag, YesOrNotEnum.N.getCode());
        agentQueryWrapper.eq(Agent::getAgentType, AgentTypeEnum.THIRD.getCode());
        return agentService.list(agentQueryWrapper);
    }

    public JSONObject blankAgent() {
        JSONObject agentObject = new JSONObject();
        agentObject.put("agent", "");
        agentObject.put("agent_address", "");
        return agentObject;
    }

    public JSONObject blankThirdParty() {
        JSONObject thirdPartyInfoObject = new JSONObject();
        thirdPartyInfoObject.put("third_party_type", AccuserDefendantTypeEnum.DEPARTMENT.getCode());
        thirdPartyInfoObject.put("third_party", "");
        thirdPartyInfoObject.put("third_party_short", "");
        thirdPartyInfoObject.put("third_party_info", "");
        thirdPartyInfoObject.put("third_party_address", "");
        thirdPartyInfoObject.put("third_party_represent", "");
        thirdPartyInfoObject.put("third_party_duty", "");
        JSONArray thirdPartyAgentArray = new JSONArray();
        JSONObject thirdPartyAgentObject = new JSONObject();
        thirdPartyAgentObject.put("agent", "");
        thirdPartyAgentObject.put("agent_address", "");
        thirdPartyAgentArray.add(thirdPartyAgentObject);
        thirdPartyInfoObject.put("third_party_agent", thirdPartyAgentArray);
        return thirdPartyInfoObject;
    }

    @Override
    public List<ThirdParty> getThirdPartyInfoList(String courtNumber) {
        return null;
    }

    @Override
    public Boolean deleteThirdPartyInfo(String courtNumber) {
        LambdaUpdateWrapper<ThirdParty> thirdPartyWrapper = new LambdaUpdateWrapper<>();
        thirdPartyWrapper.set(ThirdParty::getDelFlag, YesOrNotEnum.Y.getCode()).eq(ThirdParty::getCourtNumber, courtNumber);
        return thirdPartyService.update(thirdPartyWrapper);
    }

    @Override
    public void delete(String courtNumber) {
        LambdaQueryWrapper<ThirdParty> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(ThirdParty::getCourtNumber, courtNumber);
        lambdaQueryWrapper.eq(ThirdParty::getDelFlag, YesOrNotEnum.N.getCode());
        baseMapper.delete(lambdaQueryWrapper);
    }
}
