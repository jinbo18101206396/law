package cn.stylefeng.guns.modular.service.Impl;

import cn.stylefeng.guns.modular.entity.Reply;
import cn.stylefeng.guns.modular.entity.ThirdParty;
import cn.stylefeng.guns.modular.mapper.ReplyMapper;
import cn.stylefeng.guns.modular.service.ReplyService;
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
 * 答辩表 服务实现类
 * </p>
 *
 * @author jinbo
 * @since 2022-01-19
 */
@Service
public class ReplyServiceImpl extends ServiceImpl<ReplyMapper, Reply> implements ReplyService {

    @Resource
    private ReplyService replyService;

    /**
     * 被告答辩
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveDefendantReply(String courtNumber, String counterClaim, JSONObject recordJsonObject) {
        JSONObject courtInvestigateObject = recordJsonObject.getJSONObject("courtInvestigate");
        if (courtInvestigateObject.containsKey("defendant_reply")) {
            JSONArray defendantReplyArray = courtInvestigateObject.getJSONArray("defendant_reply");
            saveReply(defendantReplyArray, "被告", courtNumber, counterClaim);
        }
    }

    /**
     * 反诉被告答辩
     */
    @Override
    public void saveCounterClaimDefendantReply(String courtNumber, String counterClaim, JSONObject recordJsonObject) {
        JSONObject courtInvestigateObject = recordJsonObject.getJSONObject("courtInvestigate");
        if (courtInvestigateObject.containsKey("counterclaim_defendant_reply")) {
            JSONArray counterClaimDefendantReplyArray = courtInvestigateObject.getJSONArray("counterclaim_defendant_reply");
            saveReply(counterClaimDefendantReplyArray, "反诉被告", courtNumber, counterClaim);
        }
    }

    public void saveReply(JSONArray replyArray, String type, String courtNumber, String counterClaim) {
        for (int i = 0; i < replyArray.size(); i++) {
            JSONObject replyObject = replyArray.getJSONObject(i);
            String name = replyObject.getString("name");
            String content = replyObject.getString("content");
            if (ObjectUtils.isEmpty(name) || ObjectUtils.isEmpty(content)) {
                continue;
            }
            Reply reply = new Reply();
            reply.setName(name);
            reply.setType(type);
            reply.setContent(content);
            reply.setIsCounterClaim(counterClaim);
            reply.setCourtNumber(courtNumber);
            this.save(reply);
        }
    }

    @Override
    public Boolean deleteReplyInfo(String courtNumber) {
        LambdaUpdateWrapper<Reply> replyWrapper = new LambdaUpdateWrapper<>();
        replyWrapper.set(Reply::getDelFlag, YesOrNotEnum.Y.getCode()).eq(Reply::getCourtNumber, courtNumber);
        return replyService.update(replyWrapper);
    }

    @Override
    public void delete(String courtNumber) {
        LambdaQueryWrapper<Reply> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Reply::getCourtNumber,courtNumber);
        lambdaQueryWrapper.eq(Reply::getDelFlag, YesOrNotEnum.N.getCode());
        baseMapper.delete(lambdaQueryWrapper);
    }
}