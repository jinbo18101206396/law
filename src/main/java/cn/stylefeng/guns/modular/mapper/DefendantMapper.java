package cn.stylefeng.guns.modular.mapper;

import cn.stylefeng.guns.modular.entity.Defendant;
import cn.stylefeng.guns.modular.model.request.DefendantRequest;
import cn.stylefeng.guns.modular.model.result.DefendantResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * <p>
 * 被告信息表 Mapper 接口
 * </p>
 *
 * @author jinbo
 * @since 2022-01-19
 */
public interface DefendantMapper extends BaseMapper<Defendant> {

    /**
     * 获取列表
     *
     * @author jinbo
     * @Date 2022-01-19
     */
    List<DefendantResult> findtList(@Param("defendantRequest") DefendantRequest defendantRequest);

    /**
     * 获取分页实体列表
     *
     * @author jinbo
     * @Date 2022-01-19
     */
    Page<DefendantResult> findPage(@Param("page") Page<Defendant> page, @Param("defendantRequest") DefendantRequest defendantRequest);

}
