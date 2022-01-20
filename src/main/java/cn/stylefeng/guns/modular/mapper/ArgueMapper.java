package cn.stylefeng.guns.modular.mapper;

import cn.stylefeng.guns.modular.entity.Argue;
import cn.stylefeng.guns.modular.model.request.ArgueRequest;
import cn.stylefeng.guns.modular.model.result.ArgueResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 法庭辩论表 Mapper 接口
 * </p>
 *
 * @author jinbo
 * @since 2022-01-19
 */
public interface ArgueMapper extends BaseMapper<Argue> {

    /**
     * 获取列表
     *
     * @author jinbo
     * @Date 2022-01-19
     */
    List<ArgueResult> findtList(@Param("argueRequest") ArgueRequest argueRequest);

    /**
     * 获取分页实体列表
     *
     * @author jinbo
     * @Date 2022-01-19
     */
    Page<ArgueResult> findPage(@Param("page") Page<Argue> page, @Param("argueRequest") ArgueRequest argueRequest);

}
