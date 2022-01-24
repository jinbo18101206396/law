package cn.stylefeng.guns.modular.service.Impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.modular.entity.Accuser;
import cn.stylefeng.guns.modular.entity.Proof;
import cn.stylefeng.guns.modular.mapper.ProofMapper;
import cn.stylefeng.guns.modular.model.request.AccuserRequest;
import cn.stylefeng.guns.modular.model.request.ProofRequest;
import cn.stylefeng.guns.modular.service.ProofService;
import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.system.api.exception.SystemModularException;
import cn.stylefeng.roses.kernel.system.api.exception.enums.organization.PositionExceptionEnum;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 举证表 服务实现类
 * </p>
 *
 * @author jinbo
 * @since 2022-01-19
 */
@Service
public class ProofServiceImpl extends ServiceImpl<ProofMapper, Proof> implements ProofService {


    @Override
    public void add(ProofRequest proofRequest) {
        Proof proof = new Proof();
        BeanUtil.copyProperties(proofRequest, proof);
        this.save(proof);
    }

    @Override
    public void delete(ProofRequest proofRequest) {

    }

    @Override
    public void updateById(ProofRequest proofRequest) {
        Proof proof = this.queryProofById(proofRequest);
        BeanUtil.copyProperties(proofRequest, proof);
        this.updateById(proof);
    }

    @Override
    public void updateByNumberAndName(ProofRequest proofRequest) {
        Proof proof = this.queryProofByWrapper(proofRequest);
        BeanUtil.copyProperties(proofRequest, proof);
        this.updateById(proof);
    }

    @Override
    public Proof detail(ProofRequest proofRequest) {
        return this.queryProofById(proofRequest);
    }

    @Override
    public List<Proof> findList(ProofRequest proofRequest) {
        return null;
    }

    @Override
    public PageResult<Proof> findPage(ProofRequest proofRequest) {
        return null;
    }

    /**
     * 根据主键id获取对象信息
     *
     * @return 实体对象
     * @author 金波
     * @date 2022/01/14 15:07
     */
    private Proof queryProofById(ProofRequest proofRequest) {
        Proof proof = this.getById(proofRequest.getProofId());
        if (ObjectUtil.isEmpty(proof)) {
            throw new SystemModularException(PositionExceptionEnum.CANT_FIND_POSITION, proof.getProofId());
        }
        return proof;
    }

    /**
     * 根据案号和名称获取对象信息
     *
     * @return 实体对象
     * @author 金波
     * @date 2022/01/14
     */
    private Proof queryProofByWrapper(ProofRequest proofRequest) {
        LambdaQueryWrapper<Proof> wrapper = this.createWrapper(proofRequest);
        Proof proof = this.getOne(wrapper);
        if (ObjectUtil.isEmpty(proof)) {
            throw new SystemModularException(PositionExceptionEnum.CANT_FIND_POSITION, proof.getCourtNumber());
        }
        return proof;
    }

    /**
     * 实体构建 QueryWrapper
     *
     * @author 金波
     * @date 2022/01/21
     */
    private LambdaQueryWrapper<Proof> createWrapper(ProofRequest proofRequest) {
        LambdaQueryWrapper<Proof> queryWrapper = new LambdaQueryWrapper<Proof>();
        String courtNumber = proofRequest.getCourtNumber();
        String name = proofRequest.getName();
        queryWrapper.eq(ObjectUtil.isNotNull(courtNumber), Proof::getCourtNumber, courtNumber);
        queryWrapper.eq(ObjectUtil.isNotNull(name), Proof::getName, name);
        return queryWrapper;
    }
}
