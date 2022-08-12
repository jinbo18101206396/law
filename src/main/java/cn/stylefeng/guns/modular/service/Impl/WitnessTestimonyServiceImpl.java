package cn.stylefeng.guns.modular.service.Impl;

import cn.stylefeng.guns.modular.entity.WitnessTestimony;
import cn.stylefeng.guns.modular.mapper.WitnessTestimonyMapper;
import cn.stylefeng.guns.modular.service.WitnessTestimonyService;
import cn.stylefeng.roses.kernel.rule.enums.YesOrNotEnum;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 证人证言 服务实现类
 * </p>
 *
 * @author jinbo
 * @since 2022-08-03
 */
@Service
public class WitnessTestimonyServiceImpl extends ServiceImpl<WitnessTestimonyMapper, WitnessTestimony> implements WitnessTestimonyService {

    @Resource
    private WitnessTestimonyService witnessTestimonyService;

    @Override
    public void delete(String courtNumber) {
        LambdaQueryWrapper<WitnessTestimony> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(WitnessTestimony::getCourtNumber, courtNumber);
        lambdaQueryWrapper.eq(WitnessTestimony::getDelFlag, YesOrNotEnum.N.getCode());
        baseMapper.delete(lambdaQueryWrapper);
    }

    @Override
    public Boolean deleteWitnessTestimonyInfo(String courtNumber) {
        LambdaUpdateWrapper<WitnessTestimony> witnessTestimonyWrapper = new LambdaUpdateWrapper<>();
        witnessTestimonyWrapper.set(WitnessTestimony::getDelFlag, YesOrNotEnum.Y.getCode()).eq(WitnessTestimony::getCourtNumber, courtNumber);
        return witnessTestimonyService.update(witnessTestimonyWrapper);
    }
}
