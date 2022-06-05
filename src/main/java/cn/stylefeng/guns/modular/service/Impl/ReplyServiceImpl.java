package cn.stylefeng.guns.modular.service.Impl;

import cn.stylefeng.guns.modular.entity.Accuser;
import cn.stylefeng.guns.modular.entity.Proof;
import cn.stylefeng.guns.modular.entity.Reply;
import cn.stylefeng.guns.modular.mapper.ReplyMapper;
import cn.stylefeng.guns.modular.service.ReplyService;
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
    public void saveDefendantReply(String courtNumber, String counterClaim, JSONObject recordJsonObject) {
        JSONObject courtInvestigateObject = recordJsonObject.getJSONObject("courtInvestigate");
        if (courtInvestigateObject.containsKey("defendant_reply")) {
            JSONArray defendantReplyArray = courtInvestigateObject.getJSONArray("defendant_reply");
            for (int i = 0; i < defendantReplyArray.size(); i++) {
                JSONObject defendantReplyObject = defendantReplyArray.getJSONObject(i);
                Reply reply = new Reply();
                reply.setName(defendantReplyObject.get("name").toString());
                reply.setType("被告");
                reply.setContent(defendantReplyObject.get("content").toString());
                reply.setIsCounterClaim(counterClaim);
                reply.setCourtNumber(courtNumber);
                this.save(reply);
            }
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
            for (int i = 0; i < counterClaimDefendantReplyArray.size(); i++) {
                JSONObject counterClaimDefendantReplyObject = counterClaimDefendantReplyArray.getJSONObject(i);
                Reply reply = new Reply();
                reply.setName(counterClaimDefendantReplyObject.get("name").toString());
                reply.setType("反诉被告");
                reply.setContent(counterClaimDefendantReplyObject.get("content").toString());
                reply.setIsCounterClaim(counterClaim);
                reply.setCourtNumber(courtNumber);
                this.save(reply);
            }
        }
    }

    @Override
    public Boolean deleteReplyInfo(String courtNumber) {
        LambdaUpdateWrapper<Reply> replyWrapper = new LambdaUpdateWrapper<>();
        replyWrapper.set(Reply::getDelFlag, YesOrNotEnum.Y.getCode()).eq(Reply::getCourtNumber,courtNumber);
        return replyService.update(replyWrapper);
    }
}
