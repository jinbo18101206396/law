package cn.stylefeng.guns.modular.mapper;

import cn.stylefeng.guns.modular.entity.Query;
import cn.stylefeng.guns.modular.model.request.QueryRequest;
import cn.stylefeng.guns.modular.model.result.QueryResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * <p>
 * 质证表 Mapper 接口
 * </p>
 *
 * @author jinbo
 * @since 2022-01-19
 */
public interface QueryMapper extends BaseMapper<Query> {

    /**
     * 获取列表
     *
     * @author jinbo
     * @Date 2022-01-19
     */
    List<QueryResult> findtList(@Param("queryRequest") QueryRequest queryRequest);

    /**
     * 获取分页实体列表
     *
     * @author jinbo
     * @Date 2022-01-19
     */
    Page<QueryResult> findPage(@Param("page") Page<Query> page, @Param("queryRequest") QueryRequest queryRequest);

}
