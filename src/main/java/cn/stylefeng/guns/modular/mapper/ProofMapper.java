package cn.stylefeng.guns.modular.mapper;

import cn.stylefeng.guns.modular.entity.Proof;
import cn.stylefeng.guns.modular.model.request.ProofRequest;
import cn.stylefeng.guns.modular.model.result.ProofResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * <p>
 * 举证表 Mapper 接口
 * </p>
 *
 * @author jinbo
 * @since 2022-01-19
 */
public interface ProofMapper extends BaseMapper<Proof> {

    /**
     * 获取列表
     *
     * @author jinbo
     * @Date 2022-01-19
     */
    List<ProofResult> findtList(@Param("proofRequest") ProofRequest proofRequest);

    /**
     * 获取分页实体列表
     *
     * @author jinbo
     * @Date 2022-01-19
     */
    Page<ProofResult> findPage(@Param("page") Page<Proof> page, @Param("proofRequest") ProofRequest proofRequest);

}
