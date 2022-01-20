package cn.stylefeng.guns.modular.service;

import cn.stylefeng.guns.modular.entity.Reply;
import cn.stylefeng.guns.modular.model.request.ReplyRequest;
import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 答辩表 服务类
 * </p>
 *
 * @author jinbo
 * @since 2022-01-19
 */
public interface ReplyService extends IService<Reply> {

    /**
     * 新增
     *
     * @author jinbo
     * @Date 2022-01-19
     */
    void add(ReplyRequest replyRequest);

    /**
     * 删除
     *
     * @author jinbo
     * @Date 2022-01-19
     */
    void delete(ReplyRequest replyRequest);

    /**
     * 更新
     *
     * @author jinbo
     * @Date 2022-01-19
     */
    void update(ReplyRequest replyRequest);

    /**
     * 查看详情
     *
     * @param replyRequest 查看参数
     * @return 详情结果
     * @author 金波
     * @date 2021/5/12 18:28
     */
    Reply detail(ReplyRequest replyRequest);

    /**
     * 查询列表
     *
     * @param replyRequest 请求参数
     * @return 列表
     * @author 金波
     * @date 2022/01/14 15:07
     */
    List<Reply> findList(ReplyRequest replyRequest);

    /**
     * 分页查询列表
     *
     * @param replyRequest 查看参数
     * @return 结果
     * @author 金波
     * @date 2022/01/14 15:07
     */
    PageResult<Reply> findPage(ReplyRequest replyRequest);

}
