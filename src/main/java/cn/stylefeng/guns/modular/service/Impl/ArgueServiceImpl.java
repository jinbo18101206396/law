package cn.stylefeng.guns.modular.service.Impl;

import cn.stylefeng.guns.modular.entity.Argue;
import cn.stylefeng.guns.modular.mapper.ArgueMapper;
import cn.stylefeng.guns.modular.service.ArgueService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
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
            String argueName = argueObject.get("name").toString();
            String name = "";
            String type = "";
            if(!ObjectUtils.isEmpty(argueName) && argueName.contains("（")){
                name = argueName.split("（")[0];
                type = argueName.split("（")[1];
                type = type.substring(0, type.length() - 1);
            }
            String argueContent = argueObject.get("argue").toString();
            Argue argue = new Argue();
            argue.setName(name);
            //辩论人的类型（原告，被告）
            argue.setType(type);
            argue.setArgueContent(argueContent);
            argue.setIsCounterClaim(counterClaim);
            argue.setCourtNumber(courtNumber);
            this.save(argue);
        }
    }

    public void saveCounterClaimArgue(JSONArray counterClaimArgueArray, String counterClaim, String courtNumber) {
        for (int i = 0; i < counterClaimArgueArray.size(); i++) {
            JSONObject cointerClaimArgueObject = counterClaimArgueArray.getJSONObject(i);
            //姓名格式，例如：张三（反诉被告）
            String argueName = cointerClaimArgueObject.get("name").toString();
            String name = argueName.split("（")[0];
            String type = argueName.split("（")[1];
            Argue argue = new Argue();
            argue.setName(name);
            //辩论人的类型（反诉原告，反诉被告）
            argue.setType(type.substring(0, type.length() - 1));
            argue.setArgueContent(cointerClaimArgueObject.get("argue").toString());
            argue.setIsCounterClaim(counterClaim);
            argue.setCourtNumber(courtNumber);
            this.save(argue);
        }
    }

    /**
     * 获取法庭辩论信息
     */
    @Override
    public JSONObject getArgueInfoObject(String courtNumber) {
        LambdaQueryWrapper<Argue> argueQueryWrapper = new LambdaQueryWrapper<>();
        argueQueryWrapper.eq(Argue::getCourtNumber, courtNumber);
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

    @Override
    public Boolean deleteArgueInfo(String courtNumber) {
        LambdaQueryWrapper<Argue> argueQueryWrapper = new LambdaQueryWrapper<>();
        argueQueryWrapper.eq(Argue::getCourtNumber, courtNumber);
        return argueService.remove(argueQueryWrapper);
    }
}
