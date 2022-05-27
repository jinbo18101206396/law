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
        if (courtInvestigateObject.containsKey("defendant_query")) {
            JSONArray defendantQueryArray = courtInvestigateObject.getJSONArray("defendant_query");
            //被告质证
            for (int i = 0; i < defendantQueryArray.size(); i++) {
                JSONObject defendantQueryObject = defendantQueryArray.getJSONObject(i);
                String defendant = defendantQueryObject.get("defendant").toString();
                String evidence = defendantQueryObject.get("evidence").toString();
                Boolean facticity = Boolean.valueOf(defendantQueryObject.get("facticity").toString());
                Boolean legality = Boolean.valueOf(defendantQueryObject.get("legality").toString());
                Boolean relevance = Boolean.valueOf(defendantQueryObject.get("relevance").toString());
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
    }

    @Override
    public void saveAccuserQuery(String courtNumber, String counterClaim, JSONObject recordJsonObject) {
        JSONObject courtInvestigateObject = recordJsonObject.getJSONObject("courtInvestigate");
        //原告质证
        if (courtInvestigateObject.containsKey("accuser_query")) {
            JSONArray accuserQueryArray = courtInvestigateObject.getJSONArray("accuser_query");
            for (int i = 0; i < accuserQueryArray.size(); i++) {
                JSONObject accuserQueryObject = accuserQueryArray.getJSONObject(i);
                String accuser = accuserQueryObject.get("accuser").toString();
                String evidence = accuserQueryObject.get("evidence").toString();
                Boolean facticity = Boolean.valueOf(accuserQueryObject.get("facticity").toString());
                Boolean legality = Boolean.valueOf(accuserQueryObject.get("legality").toString());
                Boolean relevance = Boolean.valueOf(accuserQueryObject.get("relevance").toString());
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
    }

    @Override
    public void saveOtherDefendantQuery(String courtNumber, String counterClaim, JSONObject recordJsonObject) {
        JSONObject courtInvestigateObject = recordJsonObject.getJSONObject("courtInvestigate");
        //其他被告质证
        if (recordJsonObject.containsKey("other_defendant_query")) {
            JSONArray otherDefendantQueryArray = courtInvestigateObject.getJSONArray("other_defendant_query");
            for (int i = 0; i < otherDefendantQueryArray.size(); i++) {
                JSONObject otherDefendantQueryObject = otherDefendantQueryArray.getJSONObject(i);
                String otherDefendant = otherDefendantQueryObject.get("defendant").toString();
                String evidence = otherDefendantQueryObject.get("evidence").toString();
                Boolean facticity = Boolean.valueOf(otherDefendantQueryObject.get("facticity").toString());
                Boolean legality = Boolean.valueOf(otherDefendantQueryObject.get("legality").toString());
                Boolean relevance = Boolean.valueOf(otherDefendantQueryObject.get("relevance").toString());
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
    }

    @Override
    public void saveCounterClaimDefendantQuery(String courtNumber, String counterClaim, JSONObject recordJsonObject) {
        JSONObject courtInvestigateObject = recordJsonObject.getJSONObject("courtInvestigate");
        //反诉被告质证
        if(courtInvestigateObject.containsKey("counterclaim_defendant_query")){
            JSONArray counterClaimDefendantQueryArray = courtInvestigateObject.getJSONArray("counterclaim_defendant_query");
            for (int i = 0; i < counterClaimDefendantQueryArray.size(); i++) {
                JSONObject counterClaimDefendantQueryObject = counterClaimDefendantQueryArray.getJSONObject(i);
                String counterClaimDefendant = counterClaimDefendantQueryObject.get("counterclaim_defendant").toString();
                String evidence = counterClaimDefendantQueryObject.get("evidence").toString();
                Boolean facticity = Boolean.valueOf(counterClaimDefendantQueryObject.get("facticity").toString());
                Boolean legality = Boolean.valueOf(counterClaimDefendantQueryObject.get("legality").toString());
                Boolean relevance = Boolean.valueOf(counterClaimDefendantQueryObject.get("relevance").toString());
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
    }

    @Override
    public void saveCounterClaimAccuserQuery(String courtNumber, String counterClaim, JSONObject recordJsonObject) {
        JSONObject courtInvestigateObject = recordJsonObject.getJSONObject("courtInvestigate");
        //反诉原告质证
        if(courtInvestigateObject.containsKey("counterclaim_accuser_query")){
            JSONArray counterClaimAccuserQueryArray = courtInvestigateObject.getJSONArray("counterclaim_accuser_query");
            for (int i = 0; i < counterClaimAccuserQueryArray.size(); i++) {
                JSONObject counterClaimAccuserQueryObject = counterClaimAccuserQueryArray.getJSONObject(i);
                String counterClaimAccuser = counterClaimAccuserQueryObject.get("counterclaim_accuser").toString();
                String evidence = counterClaimAccuserQueryObject.get("evidence").toString();
                Boolean facticity = Boolean.valueOf(counterClaimAccuserQueryObject.get("facticity").toString());
                Boolean legality = Boolean.valueOf(counterClaimAccuserQueryObject.get("legality").toString());
                Boolean relevance = Boolean.valueOf(counterClaimAccuserQueryObject.get("relevance").toString());
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
    }

    @Override
    public void saveOtherCounterClaimDefendantQuery(String courtNumber, String counterClaim, JSONObject recordJsonObject) {
        JSONObject courtInvestigateObject = recordJsonObject.getJSONObject("courtInvestigate");
        //其他反诉被告质证
        if(courtInvestigateObject.containsKey("other_counterclaim_defendant_query")){
            JSONArray otherCounterClaimDefendantQueryArray = courtInvestigateObject.getJSONArray("other_counterclaim_defendant_query");
            for (int i = 0; i < otherCounterClaimDefendantQueryArray.size(); i++) {
                JSONObject otherCounterClaimDefendantQueryObject = otherCounterClaimDefendantQueryArray.getJSONObject(i);
                String otherCounterClaimDefendant = otherCounterClaimDefendantQueryObject.get("other_counterclaim_defendant").toString();
                String evidence = otherCounterClaimDefendantQueryObject.get("evidence").toString();
                Boolean facticity = Boolean.valueOf(otherCounterClaimDefendantQueryObject.get("facticity").toString());
                Boolean legality = Boolean.valueOf(otherCounterClaimDefendantQueryObject.get("legality").toString());
                Boolean relevance = Boolean.valueOf(otherCounterClaimDefendantQueryObject.get("relevance").toString());
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
}
