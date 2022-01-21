package cn.stylefeng.guns.modular.service.Impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.modular.entity.Accuser;
import cn.stylefeng.guns.modular.mapper.AccuserMapper;
import cn.stylefeng.guns.modular.model.request.AccuserRequest;
import cn.stylefeng.guns.modular.service.AccuserService;
import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.system.api.exception.SystemModularException;
import cn.stylefeng.roses.kernel.system.api.exception.enums.organization.PositionExceptionEnum;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 原告信息表 服务实现类
 * </p>
 *
 * @author jinbo
 * @since 2022-01-19
 */
@Service
public class AccuserServiceImpl extends ServiceImpl<AccuserMapper, Accuser> implements AccuserService {


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(AccuserRequest accuserRequest) {
        Accuser accuser = new Accuser();
        BeanUtil.copyProperties(accuserRequest, accuser);
        this.save(accuser);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(AccuserRequest accuserRequest) {

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateById(AccuserRequest accuserRequest) {
        Accuser accuser = this.queryAccuserById(accuserRequest);
        BeanUtil.copyProperties(accuserRequest, accuser);
        this.updateById(accuser);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateByNumberAndType(AccuserRequest accuserRequest) {
        Accuser accuser = this.queryAccuserByWrapper(accuserRequest);
        BeanUtil.copyProperties(accuserRequest, accuser);
        this.updateById(accuser);
    }

    @Override
    public void updateRightDutyByAccuserAndNumber(AccuserRequest accuserRequest) {
        Accuser accuser = this.queryAccuserByWrapper(accuserRequest);
        String accuserRightDuty = accuserRequest.getAccuserRightDuty();
        accuser.setAccuserRightDuty(accuserRightDuty);
        this.updateById(accuser);
    }

    @Override
    public void updateAvoidByAccuserAndNumber(AccuserRequest accuserRequest) {
        Accuser accuser = this.queryAccuserByWrapper(accuserRequest);
        String accuserAvoid = accuserRequest.getAccuserAvoid();
        accuser.setAccuserAvoid(accuserAvoid);
        this.updateById(accuser);
    }

    @Override
    public void updateStatementByAccuserAndNumber(AccuserRequest accuserRequest) {
        Accuser accuser = this.queryAccuserByWrapper(accuserRequest);
        String finalStatement = accuserRequest.getFinalStatement();
        accuser.setFinalStatement(finalStatement);
        this.updateById(accuser);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Accuser detail(AccuserRequest accuserRequest) {
        return this.queryAccuserById(accuserRequest);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Accuser queryAccuserByCourtNumber(AccuserRequest accuserRequest) {
        return this.queryAccuserByWrapper(accuserRequest);
    }

    @Override
    public List<Accuser> findList(AccuserRequest accuserRequest) {
        return null;
    }

    @Override
    public PageResult<Accuser> findPage(AccuserRequest accuserRequest) {
        return null;
    }

    /**
     * 根据主键id获取对象信息
     *
     * @return 实体对象
     * @author 金波
     * @date 2022/01/14
     */
    private Accuser queryAccuserById(AccuserRequest accuserRequest) {
        Accuser accuser = this.getById(accuserRequest.getAccuserId());
        if (ObjectUtil.isEmpty(accuser)) {
            throw new SystemModularException(PositionExceptionEnum.CANT_FIND_POSITION, accuser.getAccuserId());
        }
        return accuser;
    }

    /**
     * 根据案号和原告类型获取对象信息
     *
     * @return 实体对象
     * @author 金波
     * @date 2022/01/14
     */
    private Accuser queryAccuserByWrapper(AccuserRequest accuserRequest) {
        LambdaQueryWrapper<Accuser> wrapper = this.createWrapper(accuserRequest);
        Accuser accuser = this.getOne(wrapper);
        if (ObjectUtil.isEmpty(accuser)) {
            throw new SystemModularException(PositionExceptionEnum.CANT_FIND_POSITION, accuser.getCourtNumber());
        }
        return accuser;
    }

    /**
     * 实体构建 QueryWrapper
     *
     * @author 金波
     * @date 2022/01/14
     */
    private LambdaQueryWrapper<Accuser> createWrapper(AccuserRequest accuserRequest) {
        LambdaQueryWrapper<Accuser> queryWrapper = new LambdaQueryWrapper<Accuser>();

        String courtNumber = accuserRequest.getCourtNumber();
        Integer accuserType = accuserRequest.getAccuserType();
        String accuser = accuserRequest.getAccuser();

        queryWrapper.eq(ObjectUtil.isNotNull(courtNumber), Accuser::getCourtNumber, courtNumber);
        queryWrapper.eq(ObjectUtil.isNotNull(accuserType), Accuser::getAccuserType, accuserType);
        queryWrapper.eq(ObjectUtil.isNotNull(accuser), Accuser::getAccuser, accuser);

        return queryWrapper;
    }
}
