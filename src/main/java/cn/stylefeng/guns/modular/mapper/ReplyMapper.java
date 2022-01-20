package cn.stylefeng.guns.modular.mapper;

import cn.stylefeng.guns.modular.entity.Reply;
import cn.stylefeng.guns.modular.model.request.ReplyRequest;
import cn.stylefeng.guns.modular.model.result.ReplyResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 答辩表 Mapper 接口
 * </p>
 *
 * @author jinbo
 * @since 2022-01-19
 */
public interface ReplyMapper extends BaseMapper<Reply> {

    /**
     * 获取列表
     *
     * @author jinbo
     * @Date 2022-01-19
     */
    List<ReplyResult> findtList(@Param("replyRequest") ReplyRequest replyRequest);

    /**
     * 获取分页实体列表
     *
     * @author jinbo
     * @Date 2022-01-19
     */
    Page<ReplyResult> findPage(@Param("page") Page<Reply> page, @Param("replyRequest") ReplyRequest replyRequest);
}
