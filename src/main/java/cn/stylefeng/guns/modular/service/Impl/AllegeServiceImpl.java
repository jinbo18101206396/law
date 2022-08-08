package cn.stylefeng.guns.modular.service.Impl;

import cn.stylefeng.guns.modular.entity.Allege;
import cn.stylefeng.guns.modular.enums.AccuserDefendantTypeEnum;
import cn.stylefeng.guns.modular.mapper.AllegeMapper;
import cn.stylefeng.guns.modular.service.AllegeService;
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
import java.util.List;

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

    @Resource
    private AllegeService allegeService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveAccuserClaimItem(String courtNumber, String counterClaim, JSONObject recordJsonObject) {
        JSONArray accuserInfoArray = recordJsonObject.getJSONArray("accuserInfo");
        String allAccuserName = allAccuserNames(accuserInfoArray);
        JSONObject courtInvestigateObject = recordJsonObject.getJSONObject("courtInvestigate");
        //原告的诉讼请求项和事实与理由
        if (courtInvestigateObject.containsKey("accuser_claim_item") && courtInvestigateObject.containsKey("accuser_claim_fact_reason") && courtInvestigateObject.containsKey("is_change_claim_item") && courtInvestigateObject.containsKey("claim_item_after_change") && courtInvestigateObject.containsKey("fact_reason_after_change")) {
            List<Allege> alleges = getAlleges(courtNumber);
            if(alleges != null && alleges.size() > 0){
                allegeService.delete(courtNumber);
            }
            String accuserClaimItem = courtInvestigateObject.getString("accuser_claim_item");
            String accuserClaimFactReason = courtInvestigateObject.getString("accuser_claim_fact_reason");
            String isChangeClaimItem = courtInvestigateObject.getString("is_change_claim_item");
            String claimItemAfterChange = courtInvestigateObject.getString("claim_item_after_change");
            String factReasonAfterChange = courtInvestigateObject.getString("fact_reason_after_change");

            Allege allege = new Allege();
            allege.setName(allAccuserName);
            allege.setType("原告");
            allege.setClaimItem(accuserClaimItem);
            allege.setFactReason(accuserClaimFactReason);
            allege.setIsCounterClaim(counterClaim);
            allege.setIsChangeClaimItem(isChangeClaimItem);
            allege.setClaimItemAfterChange(claimItemAfterChange);
            allege.setFactReasonAfterChange(factReasonAfterChange);
            allege.setCourtNumber(courtNumber);
            this.save(allege);
        }
    }

    /**
     * 拼接所有原告姓名
     * */
    public String allAccuserNames(JSONArray accuserInfoArray){
        StringBuffer accuserName = new StringBuffer();
        if (!ObjectUtils.isEmpty(accuserInfoArray)) {
            for (int i = 0; i < accuserInfoArray.size(); i++) {
                JSONObject accuserInfoObject = accuserInfoArray.getJSONObject(i);
                String accuser = accuserInfoObject.getString("accuser");
                String accuserShort = "";
                String accuserType = accuserInfoObject.getString("accuser_type");
                if(accuserType.equals(AccuserDefendantTypeEnum.DEPARTMENT.getCode())){
                    accuserShort = accuserInfoObject.getString("accuser_short");
                }else if(accuserType.equals(AccuserDefendantTypeEnum.PERSON.getCode())){
                    accuserShort = accuser;
                }
                accuserName.append(accuserShort);
            }
        }
        return accuserName.toString();
    }

    @Override
    public void saveCounterClaimAccuserItem(String courtNumber, String counterClaim, JSONObject recordJsonObject) {
        JSONArray defendantInfoArray = recordJsonObject.getJSONArray("defendantInfo");
        //拼接所有被告的姓名
        StringBuffer defendantName = new StringBuffer();
        for (int i = 0; i < defendantInfoArray.size(); i++) {
            JSONObject defendantInfoObject = defendantInfoArray.getJSONObject(i);
            String defendantShort = defendantInfoObject.getString("defendant_short");
            defendantName.append(defendantShort);
        }
        JSONObject courtInvestigateObject = recordJsonObject.getJSONObject("courtInvestigate");
        //反诉原告的诉讼请求项和事实与理由
        if (courtInvestigateObject.containsKey("counterclaim_accuser_claim_item") && courtInvestigateObject.containsKey("counterclaim_accuser_fact_reason")) {
            String counterClaimAccuserItem = courtInvestigateObject.getString("counterclaim_accuser_claim_item");
            String counterClaimAccuserFactReason = courtInvestigateObject.getString("counterclaim_accuser_fact_reason");
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

    public List<Allege> getAlleges(String courtNumber) {
        LambdaQueryWrapper<Allege> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Allege::getCourtNumber,courtNumber);
        lambdaQueryWrapper.eq(Allege::getDelFlag, YesOrNotEnum.N.getCode());
        return allegeService.list(lambdaQueryWrapper);
    }

    @Override
    public Boolean deleteAllegeInfo(String courtNumber) {
        LambdaUpdateWrapper<Allege> allegeWrapper = new LambdaUpdateWrapper<>();
        allegeWrapper.set(Allege::getDelFlag, YesOrNotEnum.Y.getCode()).eq(Allege::getCourtNumber, courtNumber);
        return allegeService.update(allegeWrapper);
    }

    @Override
    public void delete(String courtNumber) {
        LambdaQueryWrapper<Allege> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Allege::getCourtNumber,courtNumber);
        lambdaQueryWrapper.eq(Allege::getDelFlag, YesOrNotEnum.N.getCode());
        baseMapper.delete(lambdaQueryWrapper);
    }
}
