package cn.stylefeng.guns.modular.service;

import cn.stylefeng.guns.modular.entity.Proof;
import cn.stylefeng.guns.modular.model.request.ProofRequest;
import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 举证表 服务类
 * </p>
 *
 * @author jinbo
 * @since 2022-01-19
 */
public interface ProofService extends IService<Proof> {

    /**
     * 新增
     *
     * @author jinbo
     * @Date 2022-01-19
     */
    void add(ProofRequest proofRequest);

    /**
     * 删除
     *
     * @author jinbo
     * @Date 2022-01-19
     */
    void delete(ProofRequest proofRequest);

    /**
     * 通过Id更新举证信息
     *
     * @author jinbo
     * @Date 2022-01-19
     */
    void updateById(ProofRequest proofRequest);

    /**
     * 通过案号和名称更新举证信息
     *
     * @author jinbo
     * @Date 2022-01-19
     */
    void updateByNumberAndName(ProofRequest proofRequest);

    /**
     * 查看详情
     *
     * @param proofRequest 查看参数
     * @return 详情结果
     * @author 金波
     * @date 2021/5/12 18:28
     */
    Proof detail(ProofRequest proofRequest);

    /**
     * 查询列表
     *
     * @param proofRequest 请求参数
     * @return 列表
     * @author 金波
     * @date 2022/01/14 15:07
     */
    List<Proof> findList(ProofRequest proofRequest);

    /**
     * 分页查询列表
     *
     * @param proofRequest 查看参数
     * @return 结果
     * @author 金波
     * @date 2022/01/14 15:07
     */
    PageResult<Proof> findPage(ProofRequest proofRequest);

}
