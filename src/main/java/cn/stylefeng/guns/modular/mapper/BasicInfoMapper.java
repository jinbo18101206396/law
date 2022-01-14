package cn.stylefeng.guns.modular.mapper;
import cn.stylefeng.guns.modular.entity.BasicInfo;
import cn.stylefeng.guns.modular.model.request.BasicInfoRequest;
import cn.stylefeng.guns.modular.model.result.BasicInfoResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

/**
 * <p>
 * 基本信息表 Mapper 接口
 * </p>
 *
 * @author jinbo
 * @since 2022-01-14
 */
public interface BasicInfoMapper extends BaseMapper<BasicInfo> {

    /**
     * 查询项目列表
     *
     * @param page           分页参数
     * @param basicInfoRequest 查询条件信息
     * @author jinbo
     * @since 2022-01-14
     */
    Page<BasicInfoResult> findPage(@Param("page") Page<BasicInfo> page, @Param("basicInfoRequest") BasicInfoRequest basicInfoRequest);

    /**
     * 查询项目列表
     *
     * @param basicInfoRequest 查询条件信息
     * @author jinbo
     * @since 2022-01-14
     */
    List<BasicInfoResult> findtList(@Param("basicInfoRequest") BasicInfoRequest basicInfoRequest);

}
