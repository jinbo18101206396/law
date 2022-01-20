package cn.stylefeng.guns.modular.mapper;

import cn.stylefeng.guns.modular.entity.Accuser;
import cn.stylefeng.guns.modular.model.request.AccuserRequest;
import cn.stylefeng.guns.modular.model.result.AccuserResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 原告信息表 Mapper 接口
 * </p>
 *
 * @author jinbo
 * @since 2022-01-19
 */
public interface AccuserMapper extends BaseMapper<Accuser> {

    /**
     * 获取列表
     *
     * @author jinbo
     * @Date 2022-01-19
     */
    List<AccuserResult> findtList(@Param("accuserRequest") AccuserRequest accuserRequest);


    /**
     * 获取分页实体列表
     *
     * @author jinbo
     * @Date 2022-01-19
     */
    Page<AccuserResult> findPage(@Param("page") Page<Accuser> page, @Param("accuserRequest") AccuserRequest accuserRequest);

}
