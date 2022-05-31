package cn.stylefeng.guns.modular.service;

import cn.stylefeng.guns.modular.entity.CourtCause;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 案由表表 服务类
 * </p>
 *
 * @author jinbo
 * @since 2022-05-31
 */
public interface CourtCauseService extends IService<CourtCause> {


    /**
     * 获取案由列表
     *
     * @author jinbo
     * @Date 2022-05-31
     */
    List<String> getCourtCauseList();

}
