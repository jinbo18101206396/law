package cn.stylefeng.guns.modular.service.Impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.modular.entity.Allege;
import cn.stylefeng.guns.modular.entity.Argue;
import cn.stylefeng.guns.modular.mapper.ArgueMapper;
import cn.stylefeng.guns.modular.model.request.ArgueRequest;
import  cn.stylefeng.guns.modular.service.ArgueService;
import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.system.api.exception.SystemModularException;
import cn.stylefeng.roses.kernel.system.api.exception.enums.organization.PositionExceptionEnum;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 法庭辩论表 服务实现类
 * </p>
 *
 * @author jinbo
 * @since 2022-01-19
 */
@Service
public class ArgueServiceImpl extends ServiceImpl<ArgueMapper, Argue> implements ArgueService {


    @Override
    public void add(ArgueRequest argueRequest) {
        Argue argue = new Argue();
        BeanUtil.copyProperties(argueRequest, argue);
        this.save(argue);
    }

    @Override
    public void delete(ArgueRequest argueRequest) {

    }

    @Override
    public void update(ArgueRequest argueRequest) {
        Argue argue = this.queryArgueById(argueRequest);
        BeanUtil.copyProperties(argueRequest,argue);
        this.updateById(argue);
    }

    @Override
    public Argue detail(ArgueRequest argueRequest) {
        return this.queryArgueById(argueRequest);
    }

    @Override
    public List<Argue> findList(ArgueRequest argueRequest) {
        return null;
    }

    @Override
    public PageResult<Argue> findPage(ArgueRequest argueRequest) {
        return null;
    }

    /**
     * 根据主键id获取对象信息
     *
     * @return 实体对象
     * @author 金波
     * @date 2022/01/14 15:07
     */
    private Argue queryArgueById(ArgueRequest argueRequest) {
        Argue argue = this.getById(argueRequest.getArgueId());
        if (ObjectUtil.isEmpty(argue)) {
            throw new SystemModularException(PositionExceptionEnum.CANT_FIND_POSITION, argue.getArgueId());
        }
        return argue;
    }

    /**
     * 实体构建 QueryWrapper
     *
     * @author 金波
     * @date 2022/01/14 15:07
     */
    private LambdaQueryWrapper<Argue> createWrapper(ArgueRequest argueRequest) {
        LambdaQueryWrapper<Argue> queryWrapper = new LambdaQueryWrapper<Argue>();

        return queryWrapper;
    }
}
