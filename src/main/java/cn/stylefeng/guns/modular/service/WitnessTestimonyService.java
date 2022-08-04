package cn.stylefeng.guns.modular.service;

import cn.stylefeng.guns.modular.entity.WitnessTestimony;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 证人证言 服务类
 * </p>
 *
 * @author jinbo
 * @since 2022-08-03
 */
public interface WitnessTestimonyService extends IService<WitnessTestimony> {

    /**
     * 证人证言（彻底删除）
     *
     * @author jinbo
     * @Date 2022-08-04
     */
    void delete(String courtNumber);

}
