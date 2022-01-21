package cn.stylefeng.guns.modular.service.Impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.modular.entity.Defendant;
import cn.stylefeng.guns.modular.mapper.DefendantMapper;
import cn.stylefeng.guns.modular.model.request.DefendantRequest;
import cn.stylefeng.guns.modular.service.DefendantService;
import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.system.api.exception.SystemModularException;
import cn.stylefeng.roses.kernel.system.api.exception.enums.organization.PositionExceptionEnum;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 被告信息表 服务实现类
 * </p>
 *
 * @author jinbo
 * @since 2022-01-19
 */
@Service
public class DefendantServiceImpl extends ServiceImpl<DefendantMapper, Defendant> implements DefendantService {


    @Override
    public void add(DefendantRequest defendantRequest) {
        Defendant defendant = new Defendant();
        BeanUtil.copyProperties(defendantRequest, defendant);
        this.save(defendant);
    }

    @Override
    public void delete(DefendantRequest defendantRequest) {

    }

    @Override
    public void updateById(DefendantRequest defendantRequest) {
        Defendant defendant = this.queryDefendantById(defendantRequest);
        BeanUtil.copyProperties(defendantRequest, defendant);
        this.updateById(defendant);
    }

    @Override
    public void updateByNumberAndType(DefendantRequest defendantRequest) {
        Defendant defendant = this.queryDefendantByWrapper(defendantRequest);
        BeanUtil.copyProperties(defendantRequest, defendant);
        this.updateById(defendant);
    }

    @Override
    public Defendant detail(DefendantRequest defendantRequest) {
        return this.queryDefendantById(defendantRequest);
    }

    @Override
    public Defendant queryDefendantByCourtNumber(DefendantRequest defendantRequest) {
        return this.queryDefendantByWrapper(defendantRequest);
    }

    @Override
    public void updateRightDutyByDefendantAndNumber(DefendantRequest defendantRequest) {
        Defendant defendant = this.queryDefendantByWrapper(defendantRequest);
        String defendantRightDuty = defendantRequest.getDefendantRightDuty();
        defendant.setDefendantRightDuty(defendantRightDuty);
        this.updateById(defendant);
    }

    @Override
    public void updateAvoidByDefendantAndNumber(DefendantRequest defendantRequest) {
        Defendant defendant = this.queryDefendantByWrapper(defendantRequest);
        String defendantAvoid = defendantRequest.getDefendantAvoid();
        defendant.setDefendantAvoid(defendantAvoid);
        this.updateById(defendant);
    }

    @Override
    public void updateStatementByDefendantAndNumber(DefendantRequest defendantRequest) {
        Defendant defendant = this.queryDefendantByWrapper(defendantRequest);
        String finalStatement = defendantRequest.getFinalStatement();
        defendant.setFinalStatement(finalStatement);
        this.updateById(defendant);
    }

    @Override
    public List<Defendant> findList(DefendantRequest defendantRequest) {
        return null;
    }

    @Override
    public PageResult<Defendant> findPage(DefendantRequest defendantRequest) {
        return null;
    }

    /**
     * 根据主键id获取对象信息
     *
     * @return 实体对象
     * @author 金波
     * @date 2022/01/14
     */
    private Defendant queryDefendantById(DefendantRequest defendantRequest) {
        Defendant defendant = this.getById(defendantRequest.getDefendantId());
        if (ObjectUtil.isEmpty(defendant)) {
            throw new SystemModularException(PositionExceptionEnum.CANT_FIND_POSITION, defendant.getDefendantId());
        }
        return defendant;
    }

    /**
     * 根据案号和被告类型获取对象信息
     *
     * @return 实体对象
     * @author 金波
     * @date 2022/01/14
     */
    private Defendant queryDefendantByWrapper(DefendantRequest defendantRequest) {
        LambdaQueryWrapper<Defendant> wrapper = this.createWrapper(defendantRequest);
        Defendant defendant = this.getOne(wrapper);
        if (ObjectUtil.isEmpty(defendant)) {
            throw new SystemModularException(PositionExceptionEnum.CANT_FIND_POSITION, defendant.getCourtNumber());
        }
        return defendant;
    }

    /**
     * 实体构建 QueryWrapper
     *
     * @author 金波
     * @date 2022/01/14
     */
    private LambdaQueryWrapper<Defendant> createWrapper(DefendantRequest defendantRequest) {
        LambdaQueryWrapper<Defendant> queryWrapper = new LambdaQueryWrapper<Defendant>();
        String courtNumber = defendantRequest.getCourtNumber();
        Integer accuserType = defendantRequest.getDefendantType();
        String defendant = defendantRequest.getDefendant();

        queryWrapper.eq(ObjectUtil.isNotNull(courtNumber), Defendant::getCourtNumber, courtNumber);
        queryWrapper.eq(ObjectUtil.isNotNull(accuserType), Defendant::getDefendantType, accuserType);
        queryWrapper.eq(ObjectUtil.isNotNull(defendant), Defendant::getDefendant, defendant);

        return queryWrapper;
    }
}
