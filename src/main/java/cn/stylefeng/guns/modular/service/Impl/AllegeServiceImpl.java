package cn.stylefeng.guns.modular.service.Impl;

import cn.stylefeng.guns.modular.entity.Allege;
import cn.stylefeng.guns.modular.mapper.AllegeMapper;
import cn.stylefeng.guns.modular.service.AllegeService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 诉称表 服务实现类
 * </p>
 *
 * @author jinbo
 * @since 2022-01-19
 */
@Service
public class AllegeServiceImpl extends ServiceImpl<AllegeMapper, Allege> implements AllegeService {


    @Override
    public void saveAccuserClaimItem(String courtNumber, String counterClaim, JSONObject recordJsonObject) {
        //拼接所有原告的姓名
        JSONArray accuserInfoArray = recordJsonObject.getJSONArray("accuserInfo");
        StringBuffer accuserName = new StringBuffer();
        for (int i = 0; i < accuserInfoArray.size(); i++) {
            JSONObject accuserInfoObject = accuserInfoArray.getJSONObject(i);
            String accuserShort = accuserInfoObject.get("accuser_short").toString();
            accuserName.append(accuserShort);
        }
        JSONObject courtInvestigateObject = recordJsonObject.getJSONObject("courtInvestigate");
        //原告的诉讼请求项和事实与理由
        if (courtInvestigateObject.containsKey("accuser_claim_item") && courtInvestigateObject.containsKey("accuser_claim_fact_reason")) {
            String accuserClaimItem = courtInvestigateObject.get("accuser_claim_item").toString();
            String accuserClaimFactReason = courtInvestigateObject.get("accuser_claim_fact_reason").toString();

            Allege allege = new Allege();
            allege.setName(accuserName.toString());
            allege.setType("原告");
            allege.setClaimItem(accuserClaimItem);
            allege.setFactReason(accuserClaimFactReason);
            allege.setIsCounterClaim(counterClaim);
            allege.setCourtNumber(courtNumber);
            this.save(allege);
        }
    }

    @Override
    public void saveCounterClaimAccuserItem(String courtNumber, String counterClaim, JSONObject recordJsonObject) {
        JSONArray defendantInfoArray = recordJsonObject.getJSONArray("defendantInfo");
        //拼接所有被告的姓名
        StringBuffer defendantName = new StringBuffer();
        for (int i = 0; i < defendantInfoArray.size(); i++) {
            JSONObject defendantInfoObject = defendantInfoArray.getJSONObject(i);
            String defendantShort = defendantInfoObject.get("defendant_short").toString();
            defendantName.append(defendantShort);
        }
        JSONObject courtInvestigateObject = recordJsonObject.getJSONObject("courtInvestigate");
        //反诉原告的诉讼请求项和事实与理由
        if (courtInvestigateObject.containsKey("counterclaim_accuser_claim_item") && courtInvestigateObject.containsKey("counterclaim_accuser_fact_reason")) {
            String counterClaimAccuserItem = courtInvestigateObject.get("counterclaim_accuser_claim_item").toString();
            String counterClaimAccuserFactReason = courtInvestigateObject.get("counterclaim_accuser_fact_reason").toString();
            Allege allege = new Allege();
            allege.setName(defendantName.toString());
            allege.setType("反诉原告");
            allege.setClaimItem(counterClaimAccuserItem);
            allege.setFactReason(counterClaimAccuserFactReason);
            allege.setIsCounterClaim(counterClaim);
            allege.setCourtNumber(courtNumber);
            this.save(allege);
        }
    }
}
