package cn.stylefeng.guns.modular.mapper;

import cn.stylefeng.guns.modular.entity.Inquiry;
import cn.stylefeng.guns.modular.model.request.InquiryRequest;
import cn.stylefeng.guns.modular.model.result.InquiryResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 法庭询问表 Mapper 接口
 * </p>
 *
 * @author jinbo
 * @since 2022-01-19
 */
public interface InquiryMapper extends BaseMapper<Inquiry> {

    /**
     * 获取列表
     *
     * @author jinbo
     * @Date 2022-01-19
     */
    List<InquiryResult> findtList(@Param("inquiryRequest") InquiryRequest inquiryRequest);

    /**
     * 获取分页实体列表
     *
     * @author jinbo
     * @Date 2022-01-19
     */
    Page<InquiryResult> findPage(@Param("page") Page<Inquiry> page, @Param("inquiryRequest") InquiryRequest inquiryRequest);

}
