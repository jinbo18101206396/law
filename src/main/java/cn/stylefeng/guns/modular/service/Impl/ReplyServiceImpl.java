package cn.stylefeng.guns.modular.service.Impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.modular.entity.Reply;
import cn.stylefeng.guns.modular.mapper.ReplyMapper;
import cn.stylefeng.guns.modular.model.request.ProofRequest;
import cn.stylefeng.guns.modular.model.request.ReplyRequest;
import cn.stylefeng.guns.modular.service.ReplyService;
import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.system.api.exception.SystemModularException;
import cn.stylefeng.roses.kernel.system.api.exception.enums.organization.PositionExceptionEnum;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 答辩表 服务实现类
 * </p>
 *
 * @author jinbo
 * @since 2022-01-19
 */
@Service
public class ReplyServiceImpl extends ServiceImpl<ReplyMapper, Reply> implements ReplyService {


    @Override
    public void add(ReplyRequest replyRequest) {
        Reply reply = new Reply();
        BeanUtil.copyProperties(replyRequest, reply);
        this.save(reply);
    }

    @Override
    public void delete(ReplyRequest replyRequest) {

    }

    @Override
    public void updateById(ReplyRequest replyRequest) {
        Reply reply = this.queryReplyById(replyRequest);
        BeanUtil.copyProperties(replyRequest, reply);
        this.updateById(reply);
    }

    @Override
    public Reply detail(ReplyRequest replyRequest) {
        return this.queryReplyById(replyRequest);
    }

    @Override
    public List<Reply> findList(ReplyRequest replyRequest) {
        return null;
    }

    @Override
    public PageResult<Reply> findPage(ReplyRequest replyRequest) {
        return null;
    }

    /**
     * 根据主键id获取对象信息
     *
     * @return 实体对象
     * @author 金波
     * @date 2022/01/14 15:07
     */
    private Reply queryReplyById(ReplyRequest replyRequest) {
        Reply reply = this.getById(replyRequest.getReplyId());
        if (ObjectUtil.isEmpty(reply)) {
            throw new SystemModularException(PositionExceptionEnum.CANT_FIND_POSITION, reply.getReplyId());
        }
        return reply;
    }

    /**
     * 实体构建 QueryWrapper
     *
     * @author 金波
     * @date 2022/01/14 15:07
     */
    private LambdaQueryWrapper<Reply> createWrapper(ProofRequest proofRequest) {
        LambdaQueryWrapper<Reply> queryWrapper = new LambdaQueryWrapper<Reply>();

        return queryWrapper;
    }
}
