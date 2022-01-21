package cn.stylefeng.guns.modular.service.Impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.modular.entity.Allege;
import cn.stylefeng.guns.modular.mapper.AllegeMapper;
import cn.stylefeng.guns.modular.model.request.AllegeRequest;
import cn.stylefeng.guns.modular.service.AllegeService;
import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.system.api.exception.SystemModularException;
import cn.stylefeng.roses.kernel.system.api.exception.enums.organization.PositionExceptionEnum;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 诉称表 服务实现类
 * </p>
 *
 * @author jinbo
 * @since 2022-01-19
 */
@Service
public class AllegeServiceImpl extends ServiceImpl<AllegeMapper, Allege> implements AllegeService {


    @Override
    public void add(AllegeRequest allegeRequest) {
        Allege allege = new Allege();
        BeanUtil.copyProperties(allegeRequest, allege);
        this.save(allege);
    }

    @Override
    public void delete(AllegeRequest allegeRequest) {

    }

    @Override
    public void updateById(AllegeRequest allegeRequest) {
        Allege allege = this.queryAllegeById(allegeRequest);
        BeanUtil.copyProperties(allegeRequest, allege);
        this.updateById(allege);
    }

    @Override
    public Allege detail(AllegeRequest allegeRequest) {
        return this.queryAllegeById(allegeRequest);
    }

    @Override
    public List<Allege> findList(AllegeRequest allegeRequest) {
        return null;
    }

    @Override
    public PageResult<Allege> findPage(AllegeRequest allegeRequest) {
        return null;
    }

    /**
     * 根据主键id获取对象信息
     *
     * @return 实体对象
     * @author 金波
     * @date 2022/01/14 15:07
     */
    private Allege queryAllegeById(AllegeRequest allegeRequest) {
        Allege allege = this.getById(allegeRequest.getAllegeId());
        if (ObjectUtil.isEmpty(allege)) {
            throw new SystemModularException(PositionExceptionEnum.CANT_FIND_POSITION, allege.getAllegeId());
        }
        return allege;
    }

    /**
     * 实体构建 QueryWrapper
     *
     * @author 金波
     * @date 2022/01/14 15:07
     */
    private LambdaQueryWrapper<Allege> createWrapper(AllegeRequest allegeRequest) {
        LambdaQueryWrapper<Allege> queryWrapper = new LambdaQueryWrapper<Allege>();

        return queryWrapper;
    }
}
