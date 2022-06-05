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
                Query query = new Query();
                query.setName(otherDefendantQueryObject.get("defendant").toString());
                query.setEvidence(otherDefendantQueryObject.get("evidence").toString());
                query.setFacticity(otherDefendantQueryObject.get("facticity").toString());
                query.setLegality(otherDefendantQueryObject.get("legality").toString());
                query.setRelevance(otherDefendantQueryObject.get("relevance").toString());
                query.setReason(otherDefendantQueryObject.get("other_defendant_query_fact_reason").toString());
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
                Query query = new Query();
                query.setName(counterClaimDefendantQueryObject.get("counterclaim_defendant").toString());
                query.setEvidence(counterClaimDefendantQueryObject.get("evidence").toString());
                query.setFacticity(counterClaimDefendantQueryObject.get("facticity").toString());
                query.setLegality(counterClaimDefendantQueryObject.get("legality").toString());
                query.setRelevance(counterClaimDefendantQueryObject.get("relevance").toString());
                query.setReason(counterClaimDefendantQueryObject.get("counterclaim_defendant_query_fact_reason").toString());
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
                Query query = new Query();
                query.setName(counterClaimAccuserQueryObject.get("counterclaim_accuser").toString());
                query.setEvidence(counterClaimAccuserQueryObject.get("evidence").toString());
                query.setFacticity(counterClaimAccuserQueryObject.get("facticity").toString());
                query.setLegality(counterClaimAccuserQueryObject.get("legality").toString());
                query.setRelevance(counterClaimAccuserQueryObject.get("relevance").toString());
                query.setReason(counterClaimAccuserQueryObject.get("counterclaim_accuser_query_fact_reason").toString());
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
                Query query = new Query();
                query.setName(otherCounterClaimDefendantQueryObject.get("other_counterclaim_defendant").toString());
                query.setEvidence(otherCounterClaimDefendantQueryObject.get("evidence").toString());
                query.setFacticity(otherCounterClaimDefendantQueryObject.get("facticity").toString());
                query.setLegality(otherCounterClaimDefendantQueryObject.get("legality").toString());
                query.setRelevance(otherCounterClaimDefendantQueryObject.get("relevance").toString());
                query.setReason(otherCounterClaimDefendantQueryObject.get("other_counterclaim_defendant_query_fact_reason").toString());
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