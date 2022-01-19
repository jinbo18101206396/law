package cn.stylefeng.guns.modular.service;

import cn.stylefeng.guns.modular.entity.Allege;
import cn.stylefeng.guns.modular.entity.Argue;
import cn.stylefeng.guns.modular.entity.BasicInfo;
import cn.stylefeng.guns.modular.model.request.AllegeRequest;
import cn.stylefeng.guns.modular.model.request.ArgueRequest;
import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * <p>
 * 法庭辩论表 服务类
 * </p>
 *
 * @author jinbo
 * @since 2022-01-19
 */
public interface ArgueService extends IService<Argue> {

    /**
     * 新增
     *
     * @author jinbo
     * @Date 2022-01-19
     */
    void add(ArgueRequest argueRequest);

    /**
     * 删除
     *
     * @author jinbo
     * @Date 2022-01-19
     */
    void delete(ArgueRequest argueRequest);

    /**
     * 更新
     *
     * @author jinbo
     * @Date 2022-01-19
     */
    void update(ArgueRequest argueRequest);


    /**
     * 查看详情
     *
     * @param argueRequest 查看参数
     * @return 详情结果
     * @author 金波
     * @date 2021/5/12 18:28
     */
    Argue detail(ArgueRequest argueRequest);

    /**
     * 查询列表
     *
     * @param argueRequest 请求参数
     * @return 列表
     * @author 金波
     * @date 2022/01/14 15:07
     */
    List<Argue> findList(ArgueRequest argueRequest);

    /**
     * 分页查询列表
     *
     * @param argueRequest 查看参数
     * @return 结果
     * @author 金波
     * @date 2022/01/14 15:07
     */
    PageResult<Argue> findPage(ArgueRequest argueRequest);
}
