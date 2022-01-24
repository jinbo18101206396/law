package cn.stylefeng.guns.modular.service;

import cn.stylefeng.guns.modular.entity.Inquiry;
import cn.stylefeng.guns.modular.model.request.InquiryRequest;
import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 法庭询问表 服务类
 * </p>
 *
 * @author jinbo
 * @since 2022-01-19
 */
public interface InquiryService extends IService<Inquiry> {

    /**
     * 新增
     *
     * @param inquiryRequest 请求参数
     * @return
     * @author 金波
     * @Date 2022-01-19
     */
    void add(InquiryRequest inquiryRequest);

    /**
     * 删除
     *
     * @param inquiryRequest 请求参数
     * @return
     * @author 金波
     * @Date 2022-01-19
     */
    void delete(InquiryRequest inquiryRequest);

    /**
     * 通过ID更新法庭询问信息
     *
     * @param inquiryRequest 请求参数
     * @return
     * @author 金波
     * @Date 2022-01-19
     */
    void updateById(InquiryRequest inquiryRequest);

    /**
     * 通过案号更新法庭询问信息
     *
     * @param inquiryRequest 请求参数
     * @return
     * @author 金波
     * @Date 2022-01-19
     */
    void updateByCourtNumber(InquiryRequest inquiryRequest);

    /**
     * 查看详情
     *
     * @param inquiryRequest 请求参数
     * @return 详情结果
     * @author 金波
     * @Date 2022-01-19
     */
    Inquiry detail(InquiryRequest inquiryRequest);

    /**
     * 查询列表
     *
     * @param inquiryRequest 请求参数
     * @return 列表
     * @author 金波
     * @date 2022/01/14 15:07
     */
    List<Inquiry> findList(InquiryRequest inquiryRequest);

    /**
     * 分页查询列表
     *
     * @param inquiryRequest 查看参数
     * @return 结果
     * @author 金波
     * @date 2022/01/14 15:07
     */
    PageResult<Inquiry> findPage(InquiryRequest inquiryRequest);

}
