package cn.stylefeng.guns.modular.service;

import cn.stylefeng.guns.modular.entity.Agent;
import cn.stylefeng.guns.modular.entity.Allege;
import cn.stylefeng.guns.modular.model.request.AgentRequest;
import cn.stylefeng.guns.modular.model.request.AllegeRequest;
import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * <p>
 * 诉称表 服务类
 * </p>
 *
 * @author jinbo
 * @since 2022-01-19
 */
public interface AllegeService extends IService<Allege> {

    /**
     * 新增
     *
     * @author jinbo
     * @Date 2022-01-19
     */
    void add(AllegeRequest allegeRequest);

    /**
     * 删除
     *
     * @author jinbo
     * @Date 2022-01-19
     */
    void delete(AllegeRequest allegeRequest);

    /**
     * 更新
     *
     * @author jinbo
     * @Date 2022-01-19
     */
    void update(AllegeRequest allegeRequest);

    /**
     * 查看详情
     *
     * @param allegeRequest 查看参数
     * @return 详情结果
     * @author 金波
     * @date 2021/5/12 18:28
     */
    Allege detail(AllegeRequest allegeRequest);

    /**
     * 查询列表
     *
     * @param allegeRequest 请求参数
     * @return 列表
     * @author 金波
     * @date 2022/01/14 15:07
     */
    List<Allege> findList(AllegeRequest allegeRequest);

    /**
     * 分页查询列表
     *
     * @param allegeRequest 查看参数
     * @return 结果
     * @author 金波
     * @date 2022/01/14 15:07
     */
    PageResult<Allege> findPage(AllegeRequest allegeRequest);

}
