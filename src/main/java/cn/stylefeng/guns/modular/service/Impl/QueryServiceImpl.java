package cn.stylefeng.guns.modular.service.Impl;

import cn.stylefeng.guns.modular.entity.Query;
import cn.stylefeng.guns.modular.mapper.QueryMapper;
import cn.stylefeng.guns.modular.service.QueryService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 质证表 服务实现类
 * </p>
 *
 * @author jinbo
 * @since 2022-01-19
 */
@Service
public class QueryServiceImpl extends ServiceImpl<QueryMapper, Query> implements QueryService {


    @Override
    public void saveDefendantQuery(String courtNumber, String counterClaim, JSONObject recordJsonObject) {
        JSONObject courtInvestigateObject = recordJsonObject.getJSONObject("courtInvestigate");
        JSONArray defendantQueryArray = courtInvestigateObject.getJSONArray("defendant_query");
        //被告质证
        for (int i = 0; i < defendantQueryArray.size(); i++) {
            JSONObject defendantQueryObject = defendantQueryArray.getJSONObject(i);
            String defendant = defendantQueryObject.get("defendant").toString();
            String evidence = defendantQueryObject.get("evidence").toString();
            Boolean facticity = (Boolean) defendantQueryObject.get("facticity");
            Boolean legality = (Boolean) defendantQueryObject.get("facticity");
            Boolean relevance = (Boolean) defendantQueryObject.get("facticity");
            String defendantQueryFactReason = defendantQueryObject.get("defendant_query_fact_reason").toString();

            Query query = new Query();
            query.setName(defendant);
            query.setEvidence(evidence);
            query.setFacticity(facticity);
            query.setLegality(legality);
            query.setRelevance(relevance);
            query.setReason(defendantQueryFactReason);
            query.setIsCounterClaim(counterClaim);
            query.setQueryType(1);
            query.setCourtNumber(courtNumber);
            this.save(query);
        }
    }

    @Override
    public void saveAccuserQuery(String courtNumber, String counterClaim, JSONObject recordJsonObject) {
        JSONObject courtInvestigateObject = recordJsonObject.getJSONObject("courtInvestigate");
        JSONArray accuserQueryArray = courtInvestigateObject.getJSONArray("accuser_query");
        //原告质证
        for (int i = 0; i < accuserQueryArray.size(); i++) {
            JSONObject accuserQueryObject = accuserQueryArray.getJSONObject(i);
            String accuser = accuserQueryObject.get("accuser").toString();
            String evidence = accuserQueryObject.get("evidence").toString();
            Boolean facticity = (Boolean) accuserQueryObject.get("facticity");
            Boolean legality = (Boolean) accuserQueryObject.get("legality");
            Boolean relevance = (Boolean) accuserQueryObject.get("relevance");
            String accuserQueryFactReason = accuserQueryObject.get("accuser_query_fact_reason").toString();

            Query query = new Query();
            query.setName(accuser);
            query.setEvidence(evidence);
            query.setFacticity(facticity);
            query.setLegality(legality);
            query.setRelevance(relevance);
            query.setReason(accuserQueryFactReason);
            query.setIsCounterClaim(counterClaim);
            query.setQueryType(2);
            query.setCourtNumber(courtNumber);
            this.save(query);
        }
    }

    @Override
    public void saveOtherDefendantQuery(String courtNumber, String counterClaim, JSONObject recordJsonObject) {
        JSONObject courtInvestigateObject = recordJsonObject.getJSONObject("courtInvestigate");
        JSONArray otherDefendantQueryArray = courtInvestigateObject.getJSONArray("other_defendant_query");
        //其他被告质证
        for (int i = 0; i < otherDefendantQueryArray.size(); i++) {
            JSONObject otherDefendantQueryObject = otherDefendantQueryArray.getJSONObject(i);
            String otherDefendant = otherDefendantQueryObject.get("defendant").toString();
            String evidence = otherDefendantQueryObject.get("evidence").toString();
            Boolean facticity = (Boolean) otherDefendantQueryObject.get("facticity");
            Boolean legality = (Boolean) otherDefendantQueryObject.get("legality");
            Boolean relevance = (Boolean) otherDefendantQueryObject.get("relevance");
            String otherDefendantQueryFactReason = otherDefendantQueryObject.get("other_defendant_query_fact_reason").toString();

            Query query = new Query();
            query.setName(otherDefendant);
            query.setEvidence(evidence);
            query.setFacticity(facticity);
            query.setLegality(legality);
            query.setRelevance(relevance);
            query.setReason(otherDefendantQueryFactReason);
            query.setIsCounterClaim(counterClaim);
            query.setQueryType(2);
            query.setCourtNumber(courtNumber);
            this.save(query);
        }
    }

    @Override
    public void saveCounterClaimDefendantQuery(String courtNumber, String counterClaim, JSONObject recordJsonObject) {
        JSONObject courtInvestigateObject = recordJsonObject.getJSONObject("courtInvestigate");
        JSONArray counterClaimDefendantQueryArray = courtInvestigateObject.getJSONArray("counterclaim_defendant_query");
        //反诉被告质证
        for (int i = 0; i < counterClaimDefendantQueryArray.size(); i++) {
            JSONObject counterClaimDefendantQueryObject = counterClaimDefendantQueryArray.getJSONObject(i);
            String counterClaimDefendant = counterClaimDefendantQueryObject.get("counterclaim_defendant").toString();
            String evidence = counterClaimDefendantQueryObject.get("evidence").toString();
            Boolean facticity = (Boolean) counterClaimDefendantQueryObject.get("facticity");
            Boolean legality = (Boolean) counterClaimDefendantQueryObject.get("legality");
            Boolean relevance = (Boolean) counterClaimDefendantQueryObject.get("relevance");
            String counterClaimDefendantQueryFactReason = counterClaimDefendantQueryObject.get("counterclaim_defendant_query_fact_reason").toString();

            Query query = new Query();
            query.setName(counterClaimDefendant);
            query.setEvidence(evidence);
            query.setFacticity(facticity);
            query.setLegality(legality);
            query.setRelevance(relevance);
            query.setReason(counterClaimDefendantQueryFactReason);
            query.setIsCounterClaim(counterClaim);
            query.setQueryType(3);
            query.setCourtNumber(courtNumber);
            this.save(query);
        }
    }

    @Override
    public void saveCounterClaimAccuserQuery(String courtNumber, String counterClaim, JSONObject recordJsonObject) {
        JSONObject courtInvestigateObject = recordJsonObject.getJSONObject("courtInvestigate");
        JSONArray counterClaimAccuserQueryArray = courtInvestigateObject.getJSONArray("counterclaim_accuser_query");
        //反诉原告质证
        for (int i = 0; i < counterClaimAccuserQueryArray.size(); i++) {
            JSONObject counterClaimAccuserQueryObject = counterClaimAccuserQueryArray.getJSONObject(i);
            String counterClaimAccuser = counterClaimAccuserQueryObject.get("counterclaim_accuser").toString();
            String evidence = counterClaimAccuserQueryObject.get("evidence").toString();
            Boolean facticity = (Boolean) counterClaimAccuserQueryObject.get("facticity");
            Boolean legality = (Boolean) counterClaimAccuserQueryObject.get("legality");
            Boolean relevance = (Boolean) counterClaimAccuserQueryObject.get("relevance");
            String counterClaimAccuserQueryFactReason = counterClaimAccuserQueryObject.get("counterclaim_accuser_query_fact_reason").toString();

            Query query = new Query();
            query.setName(counterClaimAccuser);
            query.setEvidence(evidence);
            query.setFacticity(facticity);
            query.setLegality(legality);
            query.setRelevance(relevance);
            query.setReason(counterClaimAccuserQueryFactReason);
            query.setIsCounterClaim(counterClaim);
            query.setQueryType(4);
            query.setCourtNumber(courtNumber);
            this.save(query);
        }
    }

    @Override
    public void saveOtherCounterClaimDefendantQuery(String courtNumber, String counterClaim, JSONObject recordJsonObject) {
        JSONObject courtInvestigateObject = recordJsonObject.getJSONObject("courtInvestigate");
        JSONArray otherCounterClaimDefendantQueryArray = courtInvestigateObject.getJSONArray("other_counterclaim_defendant_query");
        //其他反诉被告质证
        for (int i = 0; i < otherCounterClaimDefendantQueryArray.size(); i++) {
            JSONObject otherCounterClaimDefendantQueryObject = otherCounterClaimDefendantQueryArray.getJSONObject(i);
            String otherCounterClaimDefendant = otherCounterClaimDefendantQueryObject.get("other_counterclaim_defendant").toString();
            String evidence = otherCounterClaimDefendantQueryObject.get("evidence").toString();
            Boolean facticity = (Boolean) otherCounterClaimDefendantQueryObject.get("facticity");
            Boolean legality = (Boolean) otherCounterClaimDefendantQueryObject.get("legality");
            Boolean relevance = (Boolean) otherCounterClaimDefendantQueryObject.get("relevance");
            String otherCounterClaimDefendantQueryFactReason = otherCounterClaimDefendantQueryObject.get("other_counterclaim_defendant_query_fact_reason").toString();

            Query query = new Query();
            query.setName(otherCounterClaimDefendant);
            query.setEvidence(evidence);
            query.setFacticity(facticity);
            query.setLegality(legality);
            query.setRelevance(relevance);
            query.setReason(otherCounterClaimDefendantQueryFactReason);
            query.setIsCounterClaim(counterClaim);
            query.setQueryType(4);
            query.setCourtNumber(courtNumber);
            this.save(query);
        }
    }
}
