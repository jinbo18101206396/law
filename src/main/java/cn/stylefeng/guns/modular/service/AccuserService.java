package cn.stylefeng.guns.modular.service;

import cn.stylefeng.guns.modular.entity.Accuser;
import cn.stylefeng.guns.modular.entity.BasicInfo;
import cn.stylefeng.guns.modular.model.request.AccuserRequest;
import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * <p>
 * 原告信息表 服务类
 * </p>
 *
 * @author jinbo
 * @since 2022-01-19
 */
public interface AccuserService extends IService<Accuser> {

    /**
     * 新增
     *
     * @author jinbo
     * @Date 2022-01-19
     */
    void add(AccuserRequest accuserRequest);

    /**
     * 删除
     *
     * @author jinbo
     * @Date 2022-01-19
     */
    void delete(AccuserRequest accuserRequest);

    /**
     * 更新
     *
     * @author jinbo
     * @Date 2022-01-19
     */
    void update(AccuserRequest accuserRequest);

    /**
     * 查看详情
     *
     * @param accuserRequest 查看参数
     * @return 详情结果
     * @author 金波
     * @date 2021/5/12 18:28
     */
    Accuser detail(AccuserRequest accuserRequest);

    /**
     * 查询列表
     *
     * @param accuserRequest 请求参数
     * @return 列表
     * @author 金波
     * @date 2022/01/14 15:07
     */
    List<Accuser> findList(AccuserRequest accuserRequest);

    /**
     * 分页查询列表
     *
     * @param accuserRequest 查看参数
     * @return 结果
     * @author 金波
     * @date 2022/01/14 15:07
     */
    PageResult<Accuser> findPage(AccuserRequest accuserRequest);

}
