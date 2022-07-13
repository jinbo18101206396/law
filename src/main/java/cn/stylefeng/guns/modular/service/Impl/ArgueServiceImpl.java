package cn.stylefeng.guns.modular.service.Impl;

import cn.stylefeng.guns.modular.entity.Argue;
import cn.stylefeng.guns.modular.mapper.ArgueMapper;
import cn.stylefeng.guns.modular.service.ArgueService;
import cn.stylefeng.roses.kernel.rule.enums.YesOrNotEnum;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 法庭辩论表 服务实现类
 * </p>
 *
 * @author jinbo
 * @since 2022-01-19
 */
@Service
public class ArgueServiceImpl extends ServiceImpl<ArgueMapper, Argue> implements ArgueService {

    @Resource
    private ArgueService argueService;

    @Override
    public void saveArgueInfo(String courtNumber, String counterClaim, JSONObject recordJsonObject) {
        //法庭辩论
        if (recordJsonObject.containsKey("argueInfo")) {
            String argueInfo = recordJsonObject.getString("argueInfo");
            JSONObject argueInfoObject = JSONObject.parseObject(argueInfo);
            JSONArray argueArray = argueInfoObject.getJSONArray("argue");
            saveArgue(argueArray, counterClaim, courtNumber);

            if (!"".equals(counterClaim) && "1".equals(counterClaim)) {
                JSONArray counterClaimArgueArray = argueInfoObject.getJSONArray("counterclaim_argue");
                saveCounterClaimArgue(counterClaimArgueArray, counterClaim, courtNumber);
            }
        }
    }

    public void saveArgue(JSONArray argueArray, String counterClaim, String courtNumber) {
        for (int i = 0; i < argueArray.size(); i++) {
            JSONObject argueObject = argueArray.getJSONObject(i);
            //姓名格式，例如：张三（原告）
            String argueName = argueObject.getString("name");
            String argueContent = argueObject.getString("argue");
            if (!ObjectUtils.isEmpty(argueName) && argueName.contains("（") && !ObjectUtils.isEmpty(argueContent)) {
                String name = argueName.split("（")[0];
                String type = argueName.split("（")[1];
                Argue argue = new Argue();
                argue.setName(name);
                argue.setType(type.substring(0, type.length() - 1));
                argue.setArgueContent(argueContent);
                argue.setIsCounterClaim(counterClaim);
                argue.setCourtNumber(courtNumber);
                this.save(argue);
            }
        }
    }

    public void saveCounterClaimArgue(JSONArray counterClaimArgueArray, String counterClaim, String courtNumber) {
        for (int i = 0; i < counterClaimArgueArray.size(); i++) {
            JSONObject cointerClaimArgueObject = counterClaimArgueArray.getJSONObject(i);
            //姓名格式，例如：张三（反诉被告）
            String argueName = cointerClaimArgueObject.getString("name");
            String argueContent = cointerClaimArgueObject.getString("argue");
            if (!ObjectUtils.isEmpty(argueName) && argueName.contains("（") && !ObjectUtils.isEmpty(argueContent)) {
                String name = argueName.split("（")[0];
                String type = argueName.split("（")[1];
                Argue argue = new Argue();
                argue.setName(name);
                argue.setType(type.substring(0, type.length() - 1));
                argue.setArgueContent(argueContent);
                argue.setIsCounterClaim(counterClaim);
                argue.setCourtNumber(courtNumber);
                this.save(argue);
            }
        }
    }

    /**
     * 获取法庭辩论信息
     */
    @Override
    public JSONObject getArgueInfoObject(String courtNumber) {
        LambdaQueryWrapper<Argue> argueQueryWrapper = new LambdaQueryWrapper<>();
        argueQueryWrapper.eq(Argue::getCourtNumber, courtNumber);
        argueQueryWrapper.eq(Argue::getDelFlag, YesOrNotEnum.N.getCode());
        List<Argue> argues = argueService.list(argueQueryWrapper);
        JSONArray argueArray = new JSONArray();
        JSONArray counterClaimArgueArray = new JSONArray();
        //若辩论为空
        if (null == argues || argues.size() == 0) {
            JSONObject argueObject = new JSONObject();
            argueObject.put("name", "");
            argueObject.put("argue", "");
            argueArray.add(argueObject);

            JSONObject counterClaimArgueObject = new JSONObject();
            counterClaimArgueObject.put("name", "");
            counterClaimArgueObject.put("argue", "");
            counterClaimArgueArray.add(counterClaimArgueObject);
        } else {
            for (int i = 0; i < argues.size(); i++) {
                Argue argue = argues.get(i);
                String name = argue.getName();
                String type = argue.getType();
                String isCounterClaim = argue.getIsCounterClaim();

                JSONObject argueObject = new JSONObject();
                argueObject.put("name", name + "（" + type + "）");
                argueObject.put("argue", argue.getArgueContent());

                if ("1".equals(isCounterClaim) && type.startsWith("反诉")) {
                    counterClaimArgueArray.add(argueObject);
                } else {
                    argueArray.add(argueObject);
                }
            }
        }
        JSONObject argueInfoObject = new JSONObject();
        argueInfoObject.put("argue", argueArray);
        argueInfoObject.put("counterclaim_argue", counterClaimArgueArray);
        return argueInfoObject;
    }

    /**
     * 法庭辩论
     */
    @Override
    public List<Argue> getArgueList(String courtNumber) {
        List<Argue> argueList = new ArrayList<>();
        JSONObject argueInfoObject = this.getArgueInfoObject(courtNumber);
        JSONArray argueArray = argueInfoObject.getJSONArray("argue");
        for (int i = 0; i < argueArray.size(); i++) {
            JSONObject argueObject = argueArray.getJSONObject(i);
            String name = argueObject.getString("name");
            String argueContent = argueObject.getString("argue");
            if (!ObjectUtils.isEmpty(name) && !ObjectUtils.isEmpty(argueContent)) {
                Argue argue = new Argue();
                if (name.contains("原告")) {
                    name = name.replace("（原告）", "");
                    argue.setType("原告");
                } else {
                    name = name.replace("（被告）", "");
                    argue.setType("被告");
                }
                argue.setName(name);
                argue.setArgueContent(argueContent);
                argueList.add(argue);
            }
        }
        return argueList;
    }

    @Override
    public Argue getArgueInfo(String courtNumber) {
        JSONObject argueInfoObject = this.getArgueInfoObject(courtNumber);
        JSONArray argueArray = argueInfoObject.getJSONArray("argue");
        String accuserArgue = "";
        String defendantArgue = "";
        for (int i = 0; i < argueArray.size(); i++) {
            JSONObject argueObject = argueArray.getJSONObject(i);
            String name = argueObject.getString("name");
            String argue = argueObject.getString("argue");
            if (!ObjectUtils.isEmpty(name) && !ObjectUtils.isEmpty(argue)) {
                if (name.contains("原告")) {
                    name = name.replace("（原告）", "");
                    accuserArgue += argue + "；";
                } else {
                    name = name.replace("（被告）", "");
                    defendantArgue += argue + "；";
                }
            }
        }
        Argue argue = new Argue();
        argue.setAccuserArgue(accuserArgue);
        argue.setDefendantArgue(defendantArgue);
        return argue;
    }

    @Override
    public Boolean deleteArgueInfo(String courtNumber) {
        LambdaUpdateWrapper<Argue> argueWrapper = new LambdaUpdateWrapper<>();
        argueWrapper.set(Argue::getDelFlag, YesOrNotEnum.Y.getCode()).eq(Argue::getCourtNumber, courtNumber);
        return argueService.update(argueWrapper);
    }
}
