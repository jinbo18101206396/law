package cn.stylefeng.guns.modular.service.Impl;

import cn.stylefeng.guns.modular.entity.Accuser;
import cn.stylefeng.guns.modular.entity.Inquiry;
import cn.stylefeng.guns.modular.entity.Query;
import cn.stylefeng.guns.modular.mapper.QueryMapper;
import cn.stylefeng.guns.modular.service.QueryService;
import cn.stylefeng.roses.kernel.rule.enums.YesOrNotEnum;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;

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

    @Resource
    private QueryService queryService;

    /**
     * 被告质证
     */
    @Override
    public void saveDefendantQuery(String courtNumber, String counterClaim, JSONObject recordJsonObject) {
        JSONObject courtInvestigateObject = recordJsonObject.getJSONObject("courtInvestigate");
        if (courtInvestigateObject.containsKey("defendant_query")) {
            JSONArray defendantQueryArray = courtInvestigateObject.getJSONArray("defendant_query");
            //被告质证
            for (int i = 0; i < defendantQueryArray.size(); i++) {
                JSONObject defendantQueryObject = defendantQueryArray.getJSONObject(i);
                Query query = new Query();
                query.setName(defendantQueryObject.get("defendant").toString());
                query.setEvidence(defendantQueryObject.get("evidence").toString());
                query.setFacticity(defendantQueryObject.get("facticity").toString());
                query.setLegality(defendantQueryObject.get("legality").toString());
                query.setRelevance(defendantQueryObject.get("relevance").toString());
                query.setReason(defendantQueryObject.get("defendant_query_fact_reason").toString());
                query.setIsCounterClaim(counterClaim);
                query.setQueryType(1);
                query.setCourtNumber(courtNumber);
                this.save(query);
            }
        }
    }

    /**
     * 原告质证
     */
    @Override
    public void saveAccuserQuery(String courtNumber, String counterClaim, JSONObject recordJsonObject) {
        JSONObject courtInvestigateObject = recordJsonObject.getJSONObject("courtInvestigate");
        if (courtInvestigateObject.containsKey("accuser_query")) {
            JSONArray accuserQueryArray = courtInvestigateObject.getJSONArray("accuser_query");
            for (int i = 0; i < accuserQueryArray.size(); i++) {
                JSONObject accuserQueryObject = accuserQueryArray.getJSONObject(i);
                Query query = new Query();
                query.setName(accuserQueryObject.get("accuser").toString());
                query.setEvidence(accuserQueryObject.get("evidence").toString());
                query.setFacticity(accuserQueryObject.get("facticity").toString());
                query.setLegality(accuserQueryObject.get("legality").toString());
                query.setRelevance(accuserQueryObject.get("relevance").toString());
                query.setReason(accuserQueryObject.get("accuser_query_fact_reason").toString());
                query.setIsCounterClaim(counterClaim);
                query.setQueryType(2);
                query.setCourtNumber(courtNumber);
                this.save(query);
            }
        }
    }

    /**
     * 其他被告质证
     */
    @Override
    public void saveOtherDefendantQuery(String courtNumber, String counterClaim, JSONObject recordJsonObject) {
        JSONObject courtInvestigateObject = recordJsonObject.getJSONObject("courtInvestigate");
        if (courtInvestigateObject.containsKey("other_defendant_query")) {
            JSONArray otherDefendantQueryArray = courtInvestigateObject.getJSONArray("other_defendant_query");
            for (int i = 0; i < otherDefendantQueryArray.size(); i++) {
                JSONObject otherDefendantQueryObject = otherDefendantQueryArray.getJSONObject(i);
                String defendant = otherDefendantQueryObject.get("defendant").toString();
                if(ObjectUtils.isEmpty(defendant)){
                    continue;
                }
                String evidence = otherDefendantQueryObject.get("evidence").toString();
                String facticity = otherDefendantQueryObject.get("facticity").toString();
                String legality = otherDefendantQueryObject.get("legality").toString();
                String relevance = otherDefendantQueryObject.get("relevance").toString();
                String reason = otherDefendantQueryObject.get("other_defendant_query_fact_reason").toString();

                Query query = new Query();
                query.setName(defendant);
                query.setEvidence(evidence);
                query.setFacticity(facticity);
                query.setLegality(legality);
                query.setRelevance(relevance);
                query.setReason(reason);
                query.setIsCounterClaim(counterClaim);
                query.setQueryType(2);
                query.setCourtNumber(courtNumber);
                this.save(query);
            }
        }
    }

    /**
     * 反诉被告质证
     */
    @Override
    public void saveCounterClaimDefendantQuery(String courtNumber, String counterClaim, JSONObject recordJsonObject) {
        JSONObject courtInvestigateObject = recordJsonObject.getJSONObject("courtInvestigate");
        if (courtInvestigateObject.containsKey("counterclaim_defendant_query")) {
            JSONArray counterClaimDefendantQueryArray = courtInvestigateObject.getJSONArray("counterclaim_defendant_query");
            for (int i = 0; i < counterClaimDefendantQueryArray.size(); i++) {
                JSONObject counterClaimDefendantQueryObject = counterClaimDefendantQueryArray.getJSONObject(i);
                String counterclaimDefendant = counterClaimDefendantQueryObject.get("counterclaim_defendant").toString();
                if(ObjectUtils.isEmpty(counterclaimDefendant)){
                    continue;
                }
                String evidence = counterClaimDefendantQueryObject.get("evidence").toString();
                String facticity = counterClaimDefendantQueryObject.get("facticity").toString();
                String legality = counterClaimDefendantQueryObject.get("legality").toString();
                String relevance = counterClaimDefendantQueryObject.get("relevance").toString();
                String reason = counterClaimDefendantQueryObject.get("counterclaim_defendant_query_fact_reason").toString();

                Query query = new Query();
                query.setName(counterclaimDefendant);
                query.setEvidence(evidence);
                query.setFacticity(facticity);
                query.setLegality(legality);
                query.setRelevance(relevance);
                query.setReason(reason);
                query.setIsCounterClaim(counterClaim);
                query.setQueryType(3);
                query.setCourtNumber(courtNumber);
                this.save(query);
            }
        }
    }

    /**
     * 反诉原告质证
     */
    @Override
    public void saveCounterClaimAccuserQuery(String courtNumber, String counterClaim, JSONObject recordJsonObject) {
        JSONObject courtInvestigateObject = recordJsonObject.getJSONObject("courtInvestigate");
        if (courtInvestigateObject.containsKey("counterclaim_accuser_query")) {
            JSONArray counterClaimAccuserQueryArray = courtInvestigateObject.getJSONArray("counterclaim_accuser_query");
            for (int i = 0; i < counterClaimAccuserQueryArray.size(); i++) {
                JSONObject counterClaimAccuserQueryObject = counterClaimAccuserQueryArray.getJSONObject(i);
                String counterclaimAccuser = counterClaimAccuserQueryObject.get("counterclaim_accuser").toString();
                if(ObjectUtils.isEmpty(counterclaimAccuser)){
                    continue;
                }
                String evidence = counterClaimAccuserQueryObject.get("evidence").toString();
                String facticity = counterClaimAccuserQueryObject.get("facticity").toString();
                String legality = counterClaimAccuserQueryObject.get("legality").toString();
                String relevance = counterClaimAccuserQueryObject.get("relevance").toString();
                String reason = counterClaimAccuserQueryObject.get("counterclaim_accuser_query_fact_reason").toString();

                Query query = new Query();
                query.setName(counterclaimAccuser);
                query.setEvidence(evidence);
                query.setFacticity(facticity);
                query.setLegality(legality);
                query.setRelevance(relevance);
                query.setReason(reason);
                query.setIsCounterClaim(counterClaim);
                query.setQueryType(4);
                query.setCourtNumber(courtNumber);
                this.save(query);
            }
        }
    }

    /**
     * 其他反诉被告质证
     */
    @Override
    public void saveOtherCounterClaimDefendantQuery(String courtNumber, String counterClaim, JSONObject recordJsonObject) {
        JSONObject courtInvestigateObject = recordJsonObject.getJSONObject("courtInvestigate");
        if (courtInvestigateObject.containsKey("other_counterclaim_defendant_query")) {
            JSONArray otherCounterClaimDefendantQueryArray = courtInvestigateObject.getJSONArray("other_counterclaim_defendant_query");
            for (int i = 0; i < otherCounterClaimDefendantQueryArray.size(); i++) {
                JSONObject otherCounterClaimDefendantQueryObject = otherCounterClaimDefendantQueryArray.getJSONObject(i);
                String otherCounterClaimDefendant = otherCounterClaimDefendantQueryObject.get("other_counterclaim_defendant").toString();
                if(ObjectUtils.isEmpty(otherCounterClaimDefendant)){
                    continue;
                }
                String evidence = otherCounterClaimDefendantQueryObject.get("evidence").toString();
                String facticity = otherCounterClaimDefendantQueryObject.get("facticity").toString();
                String legality = otherCounterClaimDefendantQueryObject.get("legality").toString();
                String relevance = otherCounterClaimDefendantQueryObject.get("relevance").toString();
                String reason = otherCounterClaimDefendantQueryObject.get("other_counterclaim_defendant_query_fact_reason").toString();

                Query query = new Query();
                query.setName(otherCounterClaimDefendant);
                query.setEvidence(evidence);
                query.setFacticity(facticity);
                query.setLegality(legality);
                query.setRelevance(relevance);
                query.setReason(reason);
                query.setIsCounterClaim(counterClaim);
                query.setQueryType(4);
                query.setCourtNumber(courtNumber);
                this.save(query);
            }
        }
    }

    @Override
    public Boolean deleteQueryInfo(String courtNumber) {
        LambdaUpdateWrapper<Query> queryWrapper = new LambdaUpdateWrapper<>();
        queryWrapper.set(Query::getDelFlag, YesOrNotEnum.Y.getCode()).eq(Query::getCourtNumber,courtNumber);
        return queryService.update(queryWrapper);
    }
}