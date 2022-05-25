package cn.stylefeng.guns.modular.service.Impl;

import cn.stylefeng.guns.modular.entity.Reply;
import cn.stylefeng.guns.modular.mapper.ReplyMapper;
import cn.stylefeng.guns.modular.service.ReplyService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

    @Override
    public void saveDefendantReply(String courtNumber, String counterClaim, JSONObject recordJsonObject) {
        JSONObject courtInvestigateObject = recordJsonObject.getJSONObject("courtInvestigate");
        JSONArray defendantReplyArray = courtInvestigateObject.getJSONArray("defendant_reply");

        for (int i = 0; i < defendantReplyArray.size(); i++) {
            JSONObject defendantReplyObject = defendantReplyArray.getJSONObject(i);
            String name = defendantReplyObject.get("name").toString();
            String content = defendantReplyObject.get("content").toString();

            Reply reply = new Reply();
            reply.setName(name);
            reply.setType("被告");
            reply.setContent(content);
            reply.setIsCounterClaim(counterClaim);
            reply.setCourtNumber(courtNumber);
            this.save(reply);
        }
    }

    @Override
    public void saveCounterClaimDefendantReply(String courtNumber, String counterClaim, JSONObject recordJsonObject) {
        JSONObject courtInvestigateObject = recordJsonObject.getJSONObject("courtInvestigate");
        JSONArray counterClaimDefendantReplyArray = courtInvestigateObject.getJSONArray("counterclaim_defendant_reply");

        for (int i = 0; i < counterClaimDefendantReplyArray.size(); i++) {
            JSONObject counterClaimDefendantReplyObject = counterClaimDefendantReplyArray.getJSONObject(i);
            String name = counterClaimDefendantReplyObject.get("name").toString();
            String content = counterClaimDefendantReplyObject.get("content").toString();

            Reply reply = new Reply();
            reply.setName(name);
            reply.setType("反诉被告");
            reply.setContent(content);
            reply.setIsCounterClaim(counterClaim);
            reply.setCourtNumber(courtNumber);
            this.save(reply);
        }
    }
}
