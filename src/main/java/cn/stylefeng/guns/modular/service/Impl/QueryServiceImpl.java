package cn.stylefeng.guns.modular.service.Impl;

import cn.stylefeng.guns.modular.entity.Query;
import cn.stylefeng.guns.modular.entity.ThirdParty;
import cn.stylefeng.guns.modular.mapper.QueryMapper;
import cn.stylefeng.guns.modular.service.QueryService;
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
     * 被告及其他原告质证
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveDefendantAndOtherAccuserQuery(String courtNumber, String counterClaim, JSONObject recordJsonObject) {
        JSONObject courtInvestigateObject = recordJsonObject.getJSONObject("courtInvestigate");
        if (courtInvestigateObject.containsKey("defendant_and_other_accuser_query")) {
            JSONArray defendantAndOtherAccuserQueryArray = courtInvestigateObject.getJSONArray("defendant_and_other_accuser_query");
            if (!ObjectUtils.isEmpty(defendantAndOtherAccuserQueryArray)) {
                saveQueryInfo(courtNumber, counterClaim, 1, defendantAndOtherAccuserQueryArray);
            }
        }
    }

    /**
     * 原告及其他被告质证
     */
    @Override
    public void saveAccuserAndOtherDefendantQuery(String courtNumber, String counterClaim, JSONObject recordJsonObject) {
        JSONObject courtInvestigateObject = recordJsonObject.getJSONObject("courtInvestigate");
        if (courtInvestigateObject.containsKey("accuser_and_other_defendant_query")) {
            JSONArray accuserAndOtherDefendantQueryArray = courtInvestigateObject.getJSONArray("accuser_and_other_defendant_query");
            if (!ObjectUtils.isEmpty(accuserAndOtherDefendantQueryArray)) {
                saveQueryInfo(courtNumber, counterClaim, 2, accuserAndOtherDefendantQueryArray);
            }
        }
    }

    public void saveQueryInfo(String courtNumber, String counterClaim, Integer queryType, JSONArray queryArray) {
        for (int i = 0; i < queryArray.size(); i++) {
            JSONObject queryObject = queryArray.getJSONObject(i);
            String name = queryObject.getString("name");
            String evidence = queryObject.getString("evidence");
            String facticity = queryObject.getString("facticity");
            String legality = queryObject.getString("legality");
            String relevance = queryObject.getString("relevance");
            String factReason = queryObject.getString("fact_reason");
            if (!ObjectUtils.isEmpty(name) && !ObjectUtils.isEmpty(evidence) && !ObjectUtils.isEmpty(factReason)) {
                Query query = new Query();
                query.setName(name);
                query.setEvidence(evidence);
                query.setFacticity(facticity);
                query.setLegality(legality);
                query.setRelevance(relevance);
                query.setReason(factReason);
                query.setIsCounterClaim(counterClaim);
                query.setQueryType(queryType);
                query.setCourtNumber(courtNumber);
                this.save(query);
            }
        }
    }

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
                String defendant = defendantQueryObject.getString("defendant");
                String evidence = defendantQueryObject.getString("evidence");
                String defendantQueryFactReason = defendantQueryObject.getString("defendant_query_fact_reason");
                if (!ObjectUtils.isEmpty(defendant) && !ObjectUtils.isEmpty(evidence) && !ObjectUtils.isEmpty(defendantQueryFactReason)) {
                    Query query = new Query();
                    query.setName(defendant);
                    query.setEvidence(evidence);
                    query.setFacticity(defendantQueryObject.getString("facticity"));
                    query.setLegality(defendantQueryObject.getString("legality"));
                    query.setRelevance(defendantQueryObject.getString("relevance"));
                    query.setReason(defendantQueryFactReason);
                    query.setIsCounterClaim(counterClaim);
                    query.setQueryType(1);
                    query.setCourtNumber(courtNumber);
                    this.save(query);
                }
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
                String accuser = accuserQueryObject.getString("accuser");
                String evidence = accuserQueryObject.getString("evidence");
                String accuserQueryFactReason = accuserQueryObject.getString("accuser_query_fact_reason");
                if (!ObjectUtils.isEmpty(accuser) && !ObjectUtils.isEmpty(evidence) && !ObjectUtils.isEmpty(accuserQueryFactReason)) {
                    Query query = new Query();
                    query.setName(accuser);
                    query.setEvidence(evidence);
                    query.setFacticity(accuserQueryObject.getString("facticity"));
                    query.setLegality(accuserQueryObject.getString("legality"));
                    query.setRelevance(accuserQueryObject.getString("relevance"));
                    query.setReason(accuserQueryFactReason);
                    query.setIsCounterClaim(counterClaim);
                    query.setQueryType(2);
                    query.setCourtNumber(courtNumber);
                    this.save(query);
                }
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
                String defendant = otherDefendantQueryObject.getString("defendant");
                if (ObjectUtils.isEmpty(defendant)) {
                    continue;
                }
                String evidence = otherDefendantQueryObject.getString("evidence");
                String facticity = otherDefendantQueryObject.getString("facticity");
                String legality = otherDefendantQueryObject.getString("legality");
                String relevance = otherDefendantQueryObject.getString("relevance");
                String reason = otherDefendantQueryObject.getString("other_defendant_query_fact_reason");

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
                String counterclaimDefendant = counterClaimDefendantQueryObject.getString("counterclaim_defendant");
                if (ObjectUtils.isEmpty(counterclaimDefendant)) {
                    continue;
                }
                String evidence = counterClaimDefendantQueryObject.getString("evidence");
                String facticity = counterClaimDefendantQueryObject.getString("facticity");
                String legality = counterClaimDefendantQueryObject.getString("legality");
                String relevance = counterClaimDefendantQueryObject.getString("relevance");
                String reason = counterClaimDefendantQueryObject.getString("counterclaim_defendant_query_fact_reason");

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
                String counterclaimAccuser = counterClaimAccuserQueryObject.getString("counterclaim_accuser");
                if (ObjectUtils.isEmpty(counterclaimAccuser)) {
                    continue;
                }
                String evidence = counterClaimAccuserQueryObject.getString("evidence");
                String facticity = counterClaimAccuserQueryObject.getString("facticity");
                String legality = counterClaimAccuserQueryObject.getString("legality");
                String relevance = counterClaimAccuserQueryObject.getString("relevance");
                String reason = counterClaimAccuserQueryObject.getString("counterclaim_accuser_query_fact_reason");

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
                String otherCounterClaimDefendant = otherCounterClaimDefendantQueryObject.getString("other_counterclaim_defendant");
                if (ObjectUtils.isEmpty(otherCounterClaimDefendant)) {
                    continue;
                }
                String evidence = otherCounterClaimDefendantQueryObject.getString("evidence");
                String facticity = otherCounterClaimDefendantQueryObject.getString("facticity");
                String legality = otherCounterClaimDefendantQueryObject.getString("legality");
                String relevance = otherCounterClaimDefendantQueryObject.getString("relevance");
                String reason = otherCounterClaimDefendantQueryObject.getString("other_counterclaim_defendant_query_fact_reason");

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
        queryWrapper.set(Query::getDelFlag, YesOrNotEnum.Y.getCode()).eq(Query::getCourtNumber, courtNumber);
        return queryService.update(queryWrapper);
    }

    @Override
    public void delete(String courtNumber) {
        LambdaQueryWrapper<Query> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Query::getCourtNumber,courtNumber);
        lambdaQueryWrapper.eq(Query::getDelFlag, YesOrNotEnum.N.getCode());
        baseMapper.delete(lambdaQueryWrapper);
    }
}