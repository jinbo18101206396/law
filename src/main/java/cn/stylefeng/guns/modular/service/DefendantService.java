package cn.stylefeng.guns.modular.service;

import cn.stylefeng.guns.modular.entity.Defendant;
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
     * @param defendantRequest 查看参数
     * @return
     * @author 金波
     * @date 2022/01/20
     */
    void add(DefendantRequest defendantRequest);

    /**
     * 删除
     *
     * @param defendantRequest 查看参数
     * @return
     * @author 金波
     * @date 2022/01/20
     */
    void delete(DefendantRequest defendantRequest);

    /**
     * 通过ID编辑被告信息
     *
     * @param defendantRequest 查看参数
     * @return
     * @author 金波
     * @date 2022/01/20
     */
    void updateById(DefendantRequest defendantRequest);

    /**
     * 通过案号和被告类型编辑被告信息
     *
     * @param defendantRequest 查看参数
     * @return
     * @author 金波
     * @date 2021/5/12 18:28
     */
    void updateByNumberAndType(DefendantRequest defendantRequest);

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
     * 根据案号查看被告信息
     *
     * @param defendantRequest 查看参数
     * @return 详情结果
     * @author 金波
     * @date 2021/5/12 18:28
     */
    Defendant queryDefendantByCourtNumber(DefendantRequest defendantRequest);

    /**
     * 通过被告全称和案号更新权利告知信息
     *
     * @param defendantRequest 查看参数
     * @return
     * @author 金波
     * @date 2022/01/20
     */
    void updateRightDutyByDefendantAndNumber(DefendantRequest defendantRequest);

    /**
     * 通过被告全称和案号更新是否申请回避信息
     *
     * @param defendantRequest 查看参数
     * @return
     * @author 金波
     * @date 2022/01/20
     */
    void updateAvoidByDefendantAndNumber(DefendantRequest defendantRequest);

    /**
     * 通过被告全称和案号更新最后陈述意见
     *
     * @param defendantRequest 查看参数
     * @return
     * @author 金波
     * @date 2022/01/21
     */
    void updateStatementByDefendantAndNumber(DefendantRequest defendantRequest);

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
