package cn.stylefeng.guns.modular.service.Impl;

import cn.stylefeng.guns.modular.entity.JudgeSpeak;
import cn.stylefeng.guns.modular.mapper.JudgeSpeakMapper;
import cn.stylefeng.guns.modular.service.JudgeSpeakService;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 审判员说话内容 服务实现类
 * </p>
 *
 * @author jinbo
 * @since 2022-07-11
 */
@Service
public class JudgeSpeakServiceImpl extends ServiceImpl<JudgeSpeakMapper, JudgeSpeak> implements JudgeSpeakService {

    @Resource
    private JudgeSpeakService judgeSpeakService;

    @Override
    public JSONObject getJudgeSpeak(String courtCause) {
        JSONObject judgeSpeakObject = new JSONObject();
        LambdaQueryWrapper<JudgeSpeak> judgeSpeakWrapper = new LambdaQueryWrapper<>();
        judgeSpeakWrapper.eq(JudgeSpeak::getCourtCause,courtCause);
        List<JudgeSpeak> judgeSpeakList = judgeSpeakService.list(judgeSpeakWrapper);
        for(int i=0;i<judgeSpeakList.size();i++){
            JudgeSpeak judgeSpeak = judgeSpeakList.get(i);
            String module = judgeSpeak.getModule();
            String content = judgeSpeak.getContent();
            judgeSpeakObject.put(module,content);
        }
        return judgeSpeakObject;
    }
}
