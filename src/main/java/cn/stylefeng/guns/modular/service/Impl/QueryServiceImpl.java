package cn.stylefeng.guns.modular.service.Impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.modular.entity.Query;
import cn.stylefeng.guns.modular.mapper.QueryMapper;
import cn.stylefeng.guns.modular.model.request.QueryRequest;
import cn.stylefeng.guns.modular.service.QueryService;
import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.system.api.exception.SystemModularException;
import cn.stylefeng.roses.kernel.system.api.exception.enums.organization.PositionExceptionEnum;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 质证表 服务实现类
 * </p>
 *
 * @author jinbo
 * @since 2022-01-19
 */
@Service
public class QueryServiceImpl extends ServiceImpl<QueryMapper, Query> implements QueryService {

    @Override
    public void add(QueryRequest queryRequest) {
        Query query = new Query();
        BeanUtil.copyProperties(queryRequest, query);
        this.save(query);
    }

    @Override
    public void delete(QueryRequest queryRequest) {

    }

    @Override
    public void update(QueryRequest queryRequest) {
        Query query = this.queryQueryById(queryRequest);
        BeanUtil.copyProperties(queryRequest, query);
        this.updateById(query);
    }

    @Override
    public Query detail(QueryRequest queryRequest) {
        return this.queryQueryById(queryRequest);
    }

    @Override
    public List<Query> findList(QueryRequest queryRequest) {
        return null;
    }

    @Override
    public PageResult<Query> findPage(QueryRequest queryRequest) {
        return null;
    }


    /**
     * 根据主键id获取对象信息
     *
     * @return 实体对象
     * @author 金波
     * @date 2022/01/14 15:07
     */
    private Query queryQueryById(QueryRequest queryRequest) {
        Query query = this.getById(queryRequest.getQueryId());
        if (ObjectUtil.isEmpty(query)) {
            throw new SystemModularException(PositionExceptionEnum.CANT_FIND_POSITION, query.getQueryId());
        }
        return query;
    }

    /**
     * 实体构建 QueryWrapper
     *
     * @author 金波
     * @date 2022/01/14 15:07
     */
    private LambdaQueryWrapper<Query> createWrapper(QueryRequest queryRequest) {
        LambdaQueryWrapper<Query> queryWrapper = new LambdaQueryWrapper<Query>();

        return queryWrapper;
    }
}
