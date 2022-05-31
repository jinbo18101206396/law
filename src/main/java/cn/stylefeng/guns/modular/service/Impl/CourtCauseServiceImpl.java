package cn.stylefeng.guns.modular.service.Impl;

import cn.stylefeng.guns.modular.entity.CourtCause;
import cn.stylefeng.guns.modular.mapper.CourtCauseMapper;
import cn.stylefeng.guns.modular.service.CourtCauseService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 案由表 服务实现类
 * </p>
 *
 * @author jinbo
 * @since 2022-05-24
 */
@Service
public class CourtCauseServiceImpl extends ServiceImpl<CourtCauseMapper, CourtCause> implements CourtCauseService {

    @Resource
    private CourtCauseService courtCauseService;

    @Override
    public List<String> getCourtCauseList() {
        LambdaQueryWrapper<CourtCause> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CourtCause::getType, "1");
        List<CourtCause> courtCauses = courtCauseService.list(queryWrapper);

        List<String> courtCauseList = new ArrayList<>();
        for (int i = 0; i < courtCauses.size(); i++) {
            CourtCause courtCause = courtCauses.get(i);
            courtCauseList.add(courtCause.getCause());
        }
        return courtCauseList;
    }
}
