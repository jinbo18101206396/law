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
        String argueInfo = recordJsonObject.getString("argueInfo");
        if (!ObjectUtils.isEmpty(argueInfo)) {
            JSONObject argueInfoObject = JSONObject.parseObject(argueInfo);
            JSONArray argueArray = argueInfoObject.getJSONArray("argue");
            saveArgue(argueArray, counterClaim);

            if ("1".equals(counterClaim)) {
                JSONArray counterClaimArgueArray = argueInfoObject.getJSONArray("counterclaim_argue");
                saveCounterClaimArgue(counterClaimArgueArray, counterClaim);
            }
        }
    }

    public void saveArgue(JSONArray argueArray, String counterClaim) {
        for (int i = 0; i < argueArray.size(); i++) {
            JSONObject argueObject = argueArray.getJSONObject(i);
            //姓名格式，例如：张三（原告）
            String argueName = argueObject.get("name").toString();
            String name = argueName.split("（")[0];
            String type = argueName.split("（")[1];
            String argueContent = argueObject.get("argue").toString();

            Argue argue = new Argue();
            argue.setName(name);
            //辩论人的类型（原告，被告）
            argue.setType(type.substring(0, type.length() - 1));
            argue.setArgueContent(argueContent);
            argue.setIsCounterClaim(counterClaim);
        }
    }

    public void saveCounterClaimArgue(JSONArray counterClaimArgueArray, String counterClaim) {
        for (int i = 0; i < counterClaimArgueArray.size(); i++) {
            JSONObject cointerClaimArgueObject = counterClaimArgueArray.getJSONObject(i);
            //姓名格式，例如：张三（反诉被告）
            String argueName = cointerClaimArgueObject.get("name").toString();
            String name = argueName.split("（")[0];
            String type = argueName.split("（")[1];
            String argueContent = cointerClaimArgueObject.get("argue").toString();

            Argue argue = new Argue();
            argue.setName(name);
            //辩论人的类型（反诉原告，反诉被告）
            argue.setType(type.substring(0, type.length() - 1));
            argue.setArgueContent(argueContent);
            argue.setIsCounterClaim(counterClaim);
        }
    }

    @Override
    public JSONObject getArgueInfoObject(String courtNumber) {
        //法庭辩论
        LambdaQueryWrapper<Argue> argueQueryWrapper = new LambdaQueryWrapper<>();
        argueQueryWrapper.eq(Argue::getCourtNumber, courtNumber);
        List<Argue> argues = argueService.list(argueQueryWrapper);

        JSONArray argueArray = new JSONArray();
        JSONArray counterClaimArgueArray = new JSONArray();
        for (int i = 0; i < argues.size(); i++) {
            Argue argue = argues.get(i);
            String name = argue.getName();
            String argueContent = argue.getArgueContent();
            String type = argue.getType();
            String isCounterClaim = argue.getIsCounterClaim();

            JSONObject argueObject = new JSONObject();
            argueObject.put("name", name + "（" + type + "）");
            argueObject.put("argue", argueContent);

            if ("1".equals(isCounterClaim)) {
                counterClaimArgueArray.add(argueObject);
            } else {
                argueArray.add(argueObject);
            }
        }
        JSONObject argueInfoObject = new JSONObject();
        argueInfoObject.put("argue", argueArray);
        argueInfoObject.put("counterclaim_argue", counterClaimArgueArray);

        return argueInfoObject;
    }
}
