package cn.stylefeng.guns.modular.service;

import cn.stylefeng.guns.modular.entity.Query;
import cn.stylefeng.guns.modular.model.request.QueryRequest;
import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 质证表 服务类
 * </p>
 *
 * @author jinbo
 * @since 2022-01-19
 */
public interface QueryService extends IService<Query> {

    /**
     * 新增
     *
     * @author jinbo
     * @Date 2022-01-19
     */
    void add(QueryRequest queryRequest);

    /**
     * 删除
     *
     * @author jinbo
     * @Date 2022-01-19
     */
    void delete(QueryRequest queryRequest);

    /**
     * 更新
     *
     * @author jinbo
     * @Date 2022-01-19
     */
    void update(QueryRequest queryRequest);

    /**
     * 查看详情
     *
     * @param queryRequest 查看参数
     * @return 详情结果
     * @author 金波
     * @date 2021/5/12 18:28
     */
    Query detail(QueryRequest queryRequest);

    /**
     * 查询列表
     *
     * @param queryRequest 请求参数
     * @return 列表
     * @author 金波
     * @date 2022/01/14 15:07
     */
    List<Query> findList(QueryRequest queryRequest);

    /**
     * 分页查询列表
     *
     * @param queryRequest 查看参数
     * @return 结果
     * @author 金波
     * @date 2022/01/14 15:07
     */
    PageResult<Query> findPage(QueryRequest queryRequest);

}
