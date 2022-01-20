package cn.stylefeng.guns.modular.service;

import cn.stylefeng.guns.modular.entity.State;
import cn.stylefeng.guns.modular.model.request.StateRequest;
import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 基本信息陈述表 服务类
 * </p>
 *
 * @author jinbo
 * @since 2022-01-19
 */
public interface StateService extends IService<State> {

    /**
     * 新增
     *
     * @author jinbo
     * @Date 2022-01-19
     */
    void add(StateRequest stateRequest);

    /**
     * 删除
     *
     * @author jinbo
     * @Date 2022-01-19
     */
    void delete(StateRequest stateRequest);

    /**
     * 更新
     *
     * @author jinbo
     * @Date 2022-01-19
     */
    void update(StateRequest stateRequest);


    /**
     * 查看详情
     *
     * @param stateRequest 查看参数
     * @return 详情结果
     * @author jinbo
     * @Date 2022-01-19
     */
    State detail(StateRequest stateRequest);

    /**
     * 根据陈述类型获取陈述内容
     *
     * @param stateRequest 查看参数
     * @return 详情结果
     * @author jinbo
     * @Date 2022-01-19
     */
    State content(StateRequest stateRequest);

    /**
     * 查询列表
     *
     * @param stateRequest 请求参数
     * @return 列表
     * @author jinbo
     * @Date 2022-01-19
     */
    List<State> findList(StateRequest stateRequest);

    /**
     * 分页查询列表
     *
     * @param stateRequest 查看参数
     * @return 结果
     * @author 金波
     * @date 2022/01/14 15:07
     */
    PageResult<State> findPage(StateRequest stateRequest);


}
