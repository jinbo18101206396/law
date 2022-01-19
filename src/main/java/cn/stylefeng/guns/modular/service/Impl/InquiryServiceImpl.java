package cn.stylefeng.guns.modular.service.Impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.modular.entity.Defendant;
import cn.stylefeng.guns.modular.entity.Inquiry;
import cn.stylefeng.guns.modular.mapper.InquiryMapper;
import cn.stylefeng.guns.modular.model.request.InquiryRequest;
import  cn.stylefeng.guns.modular.service.InquiryService;
import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.system.api.exception.SystemModularException;
import cn.stylefeng.roses.kernel.system.api.exception.enums.organization.PositionExceptionEnum;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * <p>
 * 法庭询问表 服务实现类
 * </p>
 *
 * @author jinbo
 * @since 2022-01-19
 */
@Service
public class InquiryServiceImpl extends ServiceImpl<InquiryMapper, Inquiry> implements InquiryService {


    @Override
    public void add(InquiryRequest inquiryRequest) {
        Inquiry inquiry = new Inquiry();
        BeanUtil.copyProperties(inquiryRequest, inquiry);
        this.save(inquiry);
    }

    @Override
    public void delete(InquiryRequest inquiryRequest) {

    }

    @Override
    public void update(InquiryRequest inquiryRequest) {
        Inquiry inquiry = this.queryInqueryById(inquiryRequest);
        BeanUtil.copyProperties(inquiryRequest,inquiry);
        this.updateById(inquiry);
    }

    @Override
    public Inquiry detail(InquiryRequest inquiryRequest) {
        return this.queryInqueryById(inquiryRequest);
    }

    @Override
    public List<Inquiry> findList(InquiryRequest inquiryRequest) {
        return null;
    }

    @Override
    public PageResult<Inquiry> findPage(InquiryRequest inquiryRequest) {
        return null;
    }

    /**
     * 根据主键id获取对象信息
     *
     * @return 实体对象
     * @author 金波
     * @date 2022/01/14 15:07
     */
    private Inquiry queryInqueryById(InquiryRequest inquiryRequest) {
        Inquiry inquiry = this.getById(inquiryRequest.getInquiryId());
        if (ObjectUtil.isEmpty(inquiry)) {
            throw new SystemModularException(PositionExceptionEnum.CANT_FIND_POSITION, inquiry.getInquiryId());
        }
        return inquiry;
    }

    /**
     * 实体构建 QueryWrapper
     *
     * @author 金波
     * @date 2022/01/14 15:07
     */
    private LambdaQueryWrapper<Inquiry> createWrapper(InquiryRequest inquiryRequest) {
        LambdaQueryWrapper<Inquiry> queryWrapper = new LambdaQueryWrapper<Inquiry>();

        return queryWrapper;
    }
}
