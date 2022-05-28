package cn.stylefeng.guns.modular.service.Impl;

import cn.stylefeng.guns.modular.entity.Accuser;
import cn.stylefeng.guns.modular.entity.Agent;
import cn.stylefeng.guns.modular.mapper.AccuserMapper;
import cn.stylefeng.guns.modular.service.AccuserService;
import cn.stylefeng.guns.modular.service.AgentService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

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
    public void saveAccuserInfo(String courtNumber,JSONObject recordJsonObject) {
        //原告信息
        JSONArray accuserInfoArray = recordJsonObject.getJSONArray("accuserInfo");

        for (int i = 0; i < accuserInfoArray.size(); i++) {
            Accuser accuser = new Accuser();
            JSONObject accuserInfoObject = accuserInfoArray.getJSONObject(i);
            accuser.setAccuser(accuserInfoObject.get("accuser").toString());
            String accuserShortName = accuserInfoObject.get("accuser_short").toString();
            //原告类型（1-机构，2-个人）
            String accuserType = accuserInfoObject.get("accuser_type").toString();
            accuser.setAccuserType(accuserType);
            accuser.setAccuserShort(accuserShortName);
            accuser.setAccuserAddress(accuserInfoObject.get("accuser_address").toString());
            //原告-机构
            if ("1".equals(accuserType)) {
                accuser.setAccuserRepresent(accuserInfoObject.get("accuser_represent").toString());
                accuser.setAccuserDuty(accuserInfoObject.get("accuser_duty").toString());
            }
            accuser.setCourtNumber(courtNumber);

            //是否听清诉讼权利和义务（1-听清，2-没听清）、是否申请回避(1-回避，2-不回避)
            if (recordJsonObject.containsKey("rightInfo")) {
                String rightInfo = recordJsonObject.getString("rightInfo");
                JSONObject rightInfoObject = JSONObject.parseObject(rightInfo);
                JSONArray accuserRightDutyArray = rightInfoObject.getJSONArray("accuser_right_duty");
                for (int j = 0; j < accuserRightDutyArray.size(); j++) {
                    JSONObject accuserRightDutyObject = accuserRightDutyArray.getJSONObject(j);
                    String rightDutyAccuserName = accuserRightDutyObject.get("accuser").toString();
                    if (!"".equals(rightDutyAccuserName) && rightDutyAccuserName.equals(accuserShortName)) {
                        accuser.setAccuserRightDuty(accuserRightDutyObject.get("right_duty").toString());
                        accuser.setAccuserAvoid(accuserRightDutyObject.get("avoid").toString());
                    }
                }
            }

            //是否能够调解
            if(recordJsonObject.containsKey("mediateInfo")){
                String mediateInfo = recordJsonObject.getString("mediateInfo");
                JSONObject mediateInfoObject = JSONObject.parseObject(mediateInfo);
                //是否能够调解（1-能，2-不能）、调解方案、庭外和解时限
                JSONArray mediateAccuserArray = mediateInfoObject.getJSONArray("mediate_accuser");
                for (int k = 0; k < mediateAccuserArray.size(); k++) {
                    JSONObject mediateAccuserObject = mediateAccuserArray.getJSONObject(k);
                    String mediateAccuserName = mediateAccuserObject.get("accuser").toString();
                    if (!"".equals(mediateAccuserName) && mediateAccuserName.equals(accuserShortName)) {
                        accuser.setIsMediate(mediateAccuserObject.get("is_mediate").toString());
                        accuser.setMediatePlan(mediateAccuserObject.get("mediate_plan").toString());
                        accuser.setTimeLimit(mediateAccuserObject.get("time_limit").toString());
                    }
                }
            }

            //是否同意电子裁判文书送达（1-同意，2-不同意）、邮件地址
            if(recordJsonObject.containsKey("deliveryInfo")){
                JSONArray deliveryInfoArray = recordJsonObject.getJSONArray("deliveryInfo");
                for (int m = 0; m < deliveryInfoArray.size(); m++) {
                    JSONObject deliveryObject = deliveryInfoArray.getJSONObject(m);
                    //格式：姓名（类型），例如：张三（原告）
                    String deliveryAccuserName = deliveryObject.get("name").toString();
                    String name = deliveryAccuserName.split("（")[0];
                    String type = deliveryAccuserName.split("（")[1];
                    if (name.equals(accuserShortName) && type.startsWith("原告")) {
                        accuser.setIsDelivery(deliveryObject.get("is_delivery").toString());
                        accuser.setEmail(deliveryObject.get("email").toString());
                    }
                }
            }

            //最后陈述意见
            if(recordJsonObject.containsKey("finalStatementInfo")){
                JSONArray finalStatementInfoArray = recordJsonObject.getJSONArray("finalStatementInfo");
                for (int m = 0; m < finalStatementInfoArray.size(); m++) {
                    JSONObject finalStatementObject = finalStatementInfoArray.getJSONObject(m);
                    //格式：姓名（类型），例如：张三（原告）
                    String finalStatementAccuserName = finalStatementObject.get("name").toString();
                    String name = finalStatementAccuserName.split("（")[0];
                    String type = finalStatementAccuserName.split("（")[1];
                    if (name.equals(accuserShortName) && type.startsWith("原告")) {
                        accuser.setFinalStatement(finalStatementObject.get("final_statement").toString());
                    }
                }
            }
            this.save(accuser);
        }
    }

    @Override
    public JSONArray getAccuserInfoArray(String courtNumber) {
        //原告信息
        JSONArray accuserInfoArray = new JSONArray();
        LambdaQueryWrapper<Accuser> accuserQueryWrapper = new LambdaQueryWrapper<>();
        accuserQueryWrapper.eq(Accuser::getCourtNumber, courtNumber);
        List<Accuser> accusers = accuserService.list(accuserQueryWrapper);

        //委托诉讼代理人
        LambdaQueryWrapper<Agent> agentQueryWrapper = new LambdaQueryWrapper<>();
        agentQueryWrapper.eq(Agent::getCourtNumber, courtNumber);
        agentQueryWrapper.eq(Agent::getAgentType, "1");
        List<Agent> agents = agentService.list(agentQueryWrapper);

        for (int i = 0; i < accusers.size(); i++) {
            JSONObject accuserInfoObject = new JSONObject();
            Accuser accuser = accusers.get(i);
            String accuserShort = accuser.getAccuserShort();

            JSONArray accuserAgentArray = new JSONArray();
            for (int j = 0; j < agents.size(); j++) {
                Agent agent = agents.get(j);
                //原告姓名
                String agentName = agent.getAgentName();
                //委托诉讼代理人
                String agentType = agent.getAgentType();
                JSONObject accuserAgentObject = new JSONObject();
                if (agentName.equals(accuserShort) && agentType.equals("1")) {
                    accuserAgentObject.put("agent", agent.getAgent());
                    accuserAgentObject.put("agent_address", agent.getAgentAddress());
                    accuserAgentArray.add(accuserAgentObject);
                }
            }
            accuserInfoObject.put("accuser_type", accuser.getAccuserType());
            accuserInfoObject.put("accuser", accuser.getAccuser());
            accuserInfoObject.put("accuser_short", accuserShort);
            accuserInfoObject.put("accuser_address", accuser.getAccuserAddress());
            accuserInfoObject.put("accuser_represent", accuser.getAccuserRepresent());
            accuserInfoObject.put("accuser_duty", accuser.getAccuserDuty());
            accuserInfoObject.put("accuser_agent", accuserAgentArray);
            accuserInfoArray.add(accuserInfoObject);
        }
        return accuserInfoArray;
    }
}
