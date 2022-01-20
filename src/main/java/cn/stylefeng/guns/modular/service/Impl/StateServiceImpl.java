package cn.stylefeng.guns.modular.service.Impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.modular.entity.Accuser;
import cn.stylefeng.guns.modular.entity.BasicInfo;
import cn.stylefeng.guns.modular.entity.Defendant;
import cn.stylefeng.guns.modular.entity.State;
import cn.stylefeng.guns.modular.mapper.StateMapper;
import cn.stylefeng.guns.modular.model.request.AccuserRequest;
import cn.stylefeng.guns.modular.model.request.BasicInfoRequest;
import cn.stylefeng.guns.modular.model.request.DefendantRequest;
import cn.stylefeng.guns.modular.model.request.StateRequest;
import cn.stylefeng.guns.modular.service.AccuserService;
import cn.stylefeng.guns.modular.service.BasicInfoService;
import cn.stylefeng.guns.modular.service.DefendantService;
import cn.stylefeng.guns.modular.service.StateService;
import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.system.api.exception.SystemModularException;
import cn.stylefeng.roses.kernel.system.api.exception.enums.organization.PositionExceptionEnum;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 基本信息陈述表 服务实现类
 * </p>
 *
 * @author jinbo
 * @since 2022-01-19
 */
@Service
public class StateServiceImpl extends ServiceImpl<StateMapper, State> implements StateService {

    @Resource
    private BasicInfoService basicInfoService;

    @Resource
    private AccuserService accuserService;

    @Resource
    private DefendantService defendantService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(StateRequest stateRequest) {
        State state = new State();
        BeanUtil.copyProperties(stateRequest, state);
        this.save(state);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(StateRequest stateRequest) {

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(StateRequest stateRequest) {
        State state = this.queryStateById(stateRequest);
        BeanUtil.copyProperties(stateRequest, state);
        this.updateById(state);
    }

    @Override
    public State detail(StateRequest stateRequest) {
        return this.queryStateById(stateRequest);
    }

    @Override
    public State content(StateRequest stateRequest) {
        Integer stateType = stateRequest.getStateType();
        String courtNumber = stateRequest.getCourtNumber();

        BasicInfoRequest basicInfoRequest = new BasicInfoRequest();
        basicInfoRequest.setCourtNumber(courtNumber);
        BasicInfo basicInfo = basicInfoService.queryBasicInfoByCourtNumber(basicInfoRequest);
        String judge = basicInfo.getJudge();
        String juror = basicInfo.getJuror();
        String peopleJuror = basicInfo.getPeopleJuror();
        String courtClerk = basicInfo.getCourtClerk();

        AccuserRequest accuserRequest = new AccuserRequest();
        accuserRequest.setCourtNumber(courtNumber);
        Accuser accuserInfo = accuserService.queryAccuserByCourtNumber(accuserRequest);
        String accuser = accuserInfo.getAccuser();

        DefendantRequest defendantRequest = new DefendantRequest();
        defendantRequest.setCourtNumber(courtNumber);
        Defendant defendantInfo = defendantService.queryDefendantByCourtNumber(defendantRequest);
        String defendant = defendantInfo.getDefendant();

        String content = "审判员：当事人身份经核对无误，法庭宣布双方当事人及其诉讼代理人身份符合法律规定，出庭资格合法，可以参加诉讼。现在宣布开庭。北京市海淀区人民法院今天依法适用";
        if (stateType.equals(1)) {
            content += "简易程序，公开审理原告" + accuser + "诉被告" + defendant + "一案，由本院审判员" + judge + "独任审判，书记员" + courtClerk + "担任法庭记录。";
        } else if (stateType.equals(2)) {
            content += "普通程序独任制，公开审理原告" + accuser + "诉被告" + defendant + "一案，由本院审判员" + judge + "担任审判长，与人民陪审员" + peopleJuror + "共同组成合议庭，书记员" + courtClerk + "担任法庭记录。";
        } else if (stateType.equals(3)) {
            content += "普通程序合议制,公开审理原告" + accuser + "诉被告" + defendant + "一案，由本院审判员" + judge + "担任审判长，与陪审员" + juror + "，人民陪审员" + peopleJuror + "共同组成合议庭，书记员" + courtClerk + "担任法庭记录。";
        }
        State state = new State();
        state.setStateType(stateType);
        state.setStateContent(content);
        return state;
    }

    @Override
    public List<State> findList(StateRequest stateRequest) {
        return null;
    }

    @Override
    public PageResult<State> findPage(StateRequest stateRequest) {
        return null;
    }

    /**
     * 根据主键id获取对象信息
     *
     * @return 实体对象
     * @author 金波
     * @date 2022/01/14 15:07
     */
    private State queryStateById(StateRequest stateRequest) {
        State state = this.getById(stateRequest.getStateId());
        if (ObjectUtil.isEmpty(state)) {
            throw new SystemModularException(PositionExceptionEnum.CANT_FIND_POSITION, state.getStateId());
        }
        return state;
    }

    /**
     * 实体构建 QueryWrapper
     *
     * @author 金波
     * @date 2022/01/14 15:07
     */
    private LambdaQueryWrapper<State> createWrapper(StateRequest stateRequest) {
        LambdaQueryWrapper<State> queryWrapper = new LambdaQueryWrapper<State>();

        return queryWrapper;
    }
}
