package cn.stylefeng.guns.modular.service;

import cn.stylefeng.guns.modular.entity.BasicInfo;
import cn.stylefeng.guns.modular.entity.Defendant;
import cn.stylefeng.guns.modular.model.request.BasicInfoRequest;
import cn.stylefeng.guns.modular.model.request.DefendantRequest;
import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * <p>
 * 被告信息表 服务类
 * </p>
 *
 * @author jinbo
 * @since 2022-01-19
 */
public interface DefendantService extends IService<Defendant> {

    /**
     * 新增
     *
     * @author jinbo
     * @Date 2022-01-19
     */
    void add(DefendantRequest defendantRequest);

    /**
     * 删除
     *
     * @author jinbo
     * @Date 2022-01-19
     */
    void delete(DefendantRequest defendantRequest);

    /**
     * 更新
     *
     * @author jinbo
     * @Date 2022-01-19
     */
    void update(DefendantRequest defendantRequest);


    /**
     * 查看详情
     *
     * @param defendantRequest 查看参数
     * @return 详情结果
     * @author 金波
     * @date 2021/5/12 18:28
     */
    Defendant detail(DefendantRequest defendantRequest);

    /**
     * 查询列表
     *
     * @param defendantRequest 请求参数
     * @return 列表
     * @author 金波
     * @date 2022/01/14 15:07
     */
    List<Defendant> findList(DefendantRequest defendantRequest);

    /**
     * 分页查询列表
     *
     * @param defendantRequest 查看参数
     * @return 结果
     * @author 金波
     * @date 2022/01/14 15:07
     */
    PageResult<Defendant> findPage(DefendantRequest defendantRequest);
}
