package cn.stylefeng.guns.modular.mapper;

import cn.stylefeng.guns.modular.entity.State;
import cn.stylefeng.guns.modular.model.request.StateRequest;
import cn.stylefeng.guns.modular.model.result.StateResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 基本信息陈述表 Mapper 接口
 * </p>
 *
 * @author jinbo
 * @since 2022-01-19
 */
public interface StateMapper extends BaseMapper<State> {

    /**
     * 获取列表
     *
     * @author jinbo
     * @Date 2022-01-19
     */
    List<StateResult> findtList(@Param("stateRequest") StateRequest stateRequest);


    /**
     * 获取分页实体列表
     *
     * @author jinbo
     * @Date 2022-01-19
     */
    Page<StateResult> findPage(@Param("page") Page<State> page, @Param("stateRequest") StateRequest stateRequest);


}
