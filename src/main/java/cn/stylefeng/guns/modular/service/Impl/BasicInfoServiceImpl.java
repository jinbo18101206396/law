package cn.stylefeng.guns.modular.service.Impl;

import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.modular.entity.Accuser;
import cn.stylefeng.guns.modular.entity.BasicInfo;
import cn.stylefeng.guns.modular.entity.Defendant;
import cn.stylefeng.guns.modular.mapper.AccuserMapper;
import cn.stylefeng.guns.modular.mapper.BasicInfoMapper;
import cn.stylefeng.guns.modular.mapper.DefendantMapper;
import cn.stylefeng.guns.modular.model.request.BasicInfoRequest;
import cn.stylefeng.guns.modular.service.BasicInfoService;
import cn.stylefeng.roses.kernel.db.api.factory.PageFactory;
import cn.stylefeng.roses.kernel.db.api.factory.PageResultFactory;
import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.rule.enums.YesOrNotEnum;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 基本信息服务实现类
 *
 * @author 金波
 * @date 2022/01/14 15:07
 */

@Slf4j
@Service
public class BasicInfoServiceImpl extends ServiceImpl<BasicInfoMapper, BasicInfo> implements BasicInfoService {

    private BasicInfoMapper basicInfoMapper;
    private AccuserMapper accuserMapper;
    private DefendantMapper defendantMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveBasicInfo(String courtNumber, JSONObject recordJsonObject) {
        BasicInfo basicInfo = new BasicInfo();
        JSONObject basicInfoObject = JSONObject.parseObject(recordJsonObject.getString("basicInfo"));
        basicInfo.setFilingTime(basicInfoObject.get("filing_time").toString());
        basicInfo.setCourtTime(basicInfoObject.get("court_time").toString());
        basicInfo.setCourtPlace(basicInfoObject.get("court_place").toString());
        //审判长（可多位，用逗号分隔）
        String chiefJudge = "";
        JSONArray chiefJudgeArray = basicInfoObject.getJSONArray("chief_judge");
        if (chiefJudgeArray.size() > 0) {
            for (int i = 0; i < chiefJudgeArray.size(); i++) {
                chiefJudge += chiefJudgeArray.getJSONObject(i).getString("name") + ",";
            }
            basicInfo.setChiefJudge(chiefJudge.substring(0, chiefJudge.length() - 1));
        }
        //审判员（可多位，用逗号分隔）
        String judge = "";
        JSONArray judgeArray = basicInfoObject.getJSONArray("judge");
        if (judgeArray.size() > 0) {
            for (int i = 0; i < judgeArray.size(); i++) {
                judge += judgeArray.getJSONObject(i).getString("name") + ",";
            }
            basicInfo.setJudge(judge.substring(0, judge.length() - 1));
        }
        //陪审员（可多位，用逗号分隔）
        String juror = "";
        JSONArray jurorArray = basicInfoObject.getJSONArray("juror");
        if (jurorArray.size() > 0) {
            for (int i = 0; i < jurorArray.size(); i++) {
                juror += jurorArray.getJSONObject(i).getString("name") + ",";
            }
            basicInfo.setJuror(juror.substring(0, juror.length() - 1));
        }
        //人民陪审员（可多位，用逗号分隔）
        String peopleJuror = "";
        JSONArray peopleJurorArray = basicInfoObject.getJSONArray("people_juror");
        if (peopleJurorArray.size() > 0) {
            for (int i = 0; i < peopleJurorArray.size(); i++) {
                peopleJuror += peopleJurorArray.getJSONObject(i).getString("name") + ",";
            }
            basicInfo.setPeopleJuror(peopleJuror.substring(0, peopleJuror.length() - 1));
        }
        basicInfo.setCourtClerk(basicInfoObject.get("court_clerk").toString());
        basicInfo.setCourtCause(basicInfoObject.get("court_cause").toString());
        basicInfo.setCourtNumber(courtNumber);
        this.save(basicInfo);
    }

    @Override
    public JSONObject getBasicInfoObject(String courtNumber) {
        //笔录基本信息
        LambdaQueryWrapper<BasicInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BasicInfo::getCourtNumber, courtNumber);
        BasicInfo basicInfo = basicInfoMapper.selectOne(queryWrapper);

        JSONObject basicInfoObject = new JSONObject();
        basicInfoObject.put("filing_time", basicInfo.getFilingTime());
        basicInfoObject.put("court_time", basicInfo.getCourtTime());
        basicInfoObject.put("court_place", basicInfo.getCourtPlace());

        //审判长
        JSONArray chiefJudgeArray = new JSONArray();
        String chiefJudge = basicInfo.getChiefJudge();
        String[] chiefJudges = chiefJudge.split(",");
        for (int i = 0; i < chiefJudges.length; i++) {
            JSONObject chiefJudgeObject = new JSONObject();
            chiefJudgeObject.put("name", chiefJudges[i]);
            chiefJudgeArray.add(chiefJudgeObject);
        }
        basicInfoObject.put("chief_judge", chiefJudgeArray);

        //审判员
        JSONArray judgeArray = new JSONArray();
        String judge = basicInfo.getJudge();
        String[] judges = judge.split(",");
        for (int i = 0; i < judges.length; i++) {
            JSONObject judgeObject = new JSONObject();
            judgeObject.put("name", judges[i]);
            judgeArray.add(judgeObject);
        }
        basicInfoObject.put("judge", judgeArray);

        //陪审员
        JSONArray jurorArray = new JSONArray();
        String juror = basicInfo.getJuror();
        String[] jurors = juror.split(",");
        for (int i = 0; i < jurors.length; i++) {
            JSONObject jurorObject = new JSONObject();
            jurorObject.put("name", jurors[i]);
            jurorArray.add(jurorObject);
        }
        basicInfoObject.put("juror", jurorArray);

        //人民陪审员
        JSONArray peopleJurorArray = new JSONArray();
        String peopleJuror = basicInfo.getPeopleJuror();
        String[] peopleJurors = peopleJuror.split(",");
        for (int i = 0; i < peopleJurors.length; i++) {
            JSONObject peopleJurorObject = new JSONObject();
            peopleJurorObject.put("name", peopleJurors[i]);
            peopleJurorArray.add(peopleJurorObject);
        }
        basicInfoObject.put("people_juror", peopleJurorArray);
        basicInfoObject.put("court_clerk", basicInfo.getCourtClerk());
        basicInfoObject.put("court_cause", basicInfo.getCourtCause());
        return basicInfoObject;
    }

    //权利告知
    public JSONObject getRightInfoObject(String courtNumber) {
        JSONObject rightInfoObject = new JSONObject();
        rightInfoObject.put("judge_right_duty", "审判员：依据《中华人民共和国民事诉讼法》的规定，当事人在法庭上享有下列权利：1.原告有权承认、变更、放弃自己的诉讼请求，被告有权反驳原告的诉讼请求或提起反诉；2.当事人有权申请回避；3.当事人有权举证；4.当事人有权辩论、有权请求法庭调解,当事人在享有上述权利的同时，负有以下义务：1.当事人有依法行使诉讼权利的义务；2.当事人有听从法庭指挥、遵守法庭纪律的义务；3.当事人有如实陈述事实、如实举证的义务。上述诉讼权利和义务双方是否听清？");
        rightInfoObject.put("judge_avoid", "审判员：当事人对审判员和书记是否申请回避？");

        //原告
        JSONArray accuserRightDutyArray = new JSONArray();
        LambdaQueryWrapper<Accuser> accuserQueryWrapper = new LambdaQueryWrapper<>();
        accuserQueryWrapper.eq(Accuser::getCourtNumber, courtNumber);
        List<Accuser> accusers = accuserMapper.selectList(accuserQueryWrapper);
        for (int i = 0; i < accusers.size(); i++) {
            Accuser accuser = accusers.get(i);
            String accuserName = accuser.getAccuser();
            String accuserRightDuty = accuser.getAccuserRightDuty();
            String accuserAvoid = accuser.getAccuserAvoid();
            JSONObject accuserRightDutyObject = new JSONObject();
            accuserRightDutyObject.put("accuser", accuserName);
            accuserRightDutyObject.put("right_duty", accuserRightDuty);
            accuserRightDutyObject.put("avoid", accuserAvoid);
            accuserRightDutyArray.add(accuserRightDutyObject);
        }
        rightInfoObject.put("accuser_right_duty", accuserRightDutyArray);

        //被告
        JSONArray defendantRightDutyArray = new JSONArray();
        LambdaQueryWrapper<Defendant> defendentQueryWrapper = new LambdaQueryWrapper<>();
        defendentQueryWrapper.eq(Defendant::getCourtNumber, courtNumber);
        List<Defendant> defendants = defendantMapper.selectList(defendentQueryWrapper);
        for (int i = 0; i < defendants.size(); i++) {
            Defendant defendant = defendants.get(i);
            String defendantName = defendant.getDefendant();
            String defendantRightDuty = defendant.getDefendantRightDuty();
            String defendantAvoid = defendant.getDefendantAvoid();
            JSONObject defendantRightDutyObject = new JSONObject();
            defendantRightDutyObject.put("defendant", defendantName);
            defendantRightDutyObject.put("right_duty", defendantRightDuty);
            defendantRightDutyObject.put("avoid", defendantAvoid);
            defendantRightDutyArray.add(defendantRightDutyObject);
        }
        rightInfoObject.put("defendant_right_duty", defendantRightDutyArray);
        return rightInfoObject;
    }

    @Override
    public PageResult<BasicInfo> findPage(BasicInfoRequest basicInfoRequest) {
        LambdaQueryWrapper<BasicInfo> wrapper = this.createWrapper(basicInfoRequest);
        Page<BasicInfo> page = this.page(PageFactory.defaultPage(), wrapper);
        return PageResultFactory.createPageResult(page);
    }

    /**
     * 实体构建 QueryWrapper
     *
     * @author 金波
     * @date 2022/01/14 15:07
     */
    private LambdaQueryWrapper<BasicInfo> createWrapper(BasicInfoRequest basicInfoRequest) {
        LambdaQueryWrapper<BasicInfo> queryWrapper = new LambdaQueryWrapper<>();
        Long basicId = basicInfoRequest.getBasicId();
        String judge = basicInfoRequest.getJudge();
        String courtNumber = basicInfoRequest.getCourtNumber();
        String courtCause = basicInfoRequest.getCourtCause();
        int status = basicInfoRequest.getStatus();

        queryWrapper.eq(ObjectUtil.isNotEmpty(basicId), BasicInfo::getBasicId, basicId);
        queryWrapper.like(ObjectUtil.isNotEmpty(judge), BasicInfo::getJudge, judge);
        queryWrapper.eq(ObjectUtil.isNotEmpty(courtNumber), BasicInfo::getCourtNumber, courtNumber);
        queryWrapper.like(ObjectUtil.isNotEmpty(courtCause), BasicInfo::getCourtCause, courtCause);

        // 查询未删除状态的
        queryWrapper.eq(BasicInfo::getDelFlag, YesOrNotEnum.N.getCode());

        return queryWrapper;
    }
}

