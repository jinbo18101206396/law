package cn.stylefeng.guns.modular.service.Impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.modular.entity.Argue;
import cn.stylefeng.guns.modular.entity.BasicInfo;
import cn.stylefeng.guns.modular.entity.Defendant;
import cn.stylefeng.guns.modular.entity.State;
import cn.stylefeng.guns.modular.mapper.DefendantMapper;
import cn.stylefeng.guns.modular.model.request.DefendantRequest;
import cn.stylefeng.guns.modular.model.request.StateRequest;
import  cn.stylefeng.guns.modular.service.DefendantService;
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
    public void update(DefendantRequest defendantRequest) {
        Defendant defendant = this.queryDefendantById(defendantRequest);
        BeanUtil.copyProperties(defendantRequest,defendant);
        this.updateById(defendant);
    }

    @Override
    public Defendant detail(DefendantRequest defendantRequest) {
        return this.queryDefendantById(defendantRequest);
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
     * @date 2022/01/14 15:07
     */
    private Defendant queryDefendantById(DefendantRequest defendantRequest) {
        Defendant defendant = this.getById(defendantRequest.getDefendantId());
        if (ObjectUtil.isEmpty(defendant)) {
            throw new SystemModularException(PositionExceptionEnum.CANT_FIND_POSITION, defendant.getDefendantId());
        }
        return defendant;
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
