package cn.stylefeng.guns.modular.mapper;

import cn.stylefeng.guns.modular.entity.Allege;
import cn.stylefeng.guns.modular.model.request.AllegeRequest;
import cn.stylefeng.guns.modular.model.result.AllegeResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 诉称表 Mapper 接口
 * </p>
 *
 * @author jinbo
 * @since 2022-01-19
 */
public interface AllegeMapper extends BaseMapper<Allege> {

    /**
     * 获取列表
     *
     * @author jinbo
     * @Date 2022-01-19
     */
    List<AllegeResult> findtList(@Param("allegeRequest") AllegeRequest allegeRequest);

    /**
     * 获取分页实体列表
     *
     * @author jinbo
     * @Date 2022-01-19
     */
    Page<AllegeResult> findPage(@Param("page") Page<Allege> page, @Param("allegeRequest") AllegeRequest allegeRequest);

}
