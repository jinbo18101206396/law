package cn.stylefeng.guns.modular.service.Impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.modular.entity.BasicInfo;
import cn.stylefeng.guns.modular.mapper.BasicInfoMapper;
import cn.stylefeng.guns.modular.model.request.BasicInfoRequest;
import cn.stylefeng.guns.modular.service.BasicInfoService;
import cn.stylefeng.roses.kernel.db.api.factory.PageFactory;
import cn.stylefeng.roses.kernel.db.api.factory.PageResultFactory;
import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.rule.enums.YesOrNotEnum;
import cn.stylefeng.roses.kernel.system.api.exception.SystemModularException;
import cn.stylefeng.roses.kernel.system.api.exception.enums.organization.PositionExceptionEnum;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
 * 基本信息服务实现类
 *
 * @author 金波
 * @date 2022/01/14 15:07
 */

@Slf4j
@Service
public class BasicInfoServiceImpl extends ServiceImpl<BasicInfoMapper, BasicInfo> implements BasicInfoService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(BasicInfoRequest basicInfoRequest) {
        BasicInfo basicInfo = new BasicInfo();
        BeanUtil.copyProperties(basicInfoRequest, basicInfo);
        this.save(basicInfo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void del(BasicInfoRequest basicInfoRequest) {
        BasicInfo basicInfo = this.queryBasicInfoById(basicInfoRequest);
        basicInfo.setDelFlag(YesOrNotEnum.Y.getCode());
        this.updateById(basicInfo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(BasicInfoRequest basicInfoRequest) {
        BasicInfo basicInfo = this.queryBasicInfoById(basicInfoRequest);
        BeanUtil.copyProperties(basicInfoRequest,basicInfo);
        this.updateById(basicInfo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BasicInfo detail(BasicInfoRequest basicInfoRequest) {
        return this.queryBasicInfoById(basicInfoRequest);
    }

    @Override
    public List<BasicInfo> findList(BasicInfoRequest basicInfoRequest) {
        return this.list(this.createWrapper(basicInfoRequest));
    }

    @Override
    public PageResult<BasicInfo> findPage(BasicInfoRequest basicInfoRequest) {
        LambdaQueryWrapper<BasicInfo> wrapper = this.createWrapper(basicInfoRequest);
        Page<BasicInfo> page = this.page(PageFactory.defaultPage(), wrapper);
        return PageResultFactory.createPageResult(page);
    }

    /**
     * 根据主键id获取对象信息
     *
     * @return 实体对象
     * @author 金波
     * @date 2022/01/14 15:07
     */
    private BasicInfo queryBasicInfoById(BasicInfoRequest basicInfoRequest) {
        BasicInfo basicInfo = this.getById(basicInfoRequest.getBasicId());
        if (ObjectUtil.isEmpty(basicInfo) || YesOrNotEnum.Y.getCode().equals(basicInfo.getDelFlag())) {
            throw new SystemModularException(PositionExceptionEnum.CANT_FIND_POSITION, basicInfo.getBasicId());
        }
        return basicInfo;
    }

    /**
     * 实体构建 QueryWrapper
     *
     * @author 金波
     * @date 2022/01/14 15:07
     */
    private LambdaQueryWrapper<BasicInfo> createWrapper(BasicInfoRequest basicInfoRequest) {
        LambdaQueryWrapper<BasicInfo> queryWrapper = new LambdaQueryWrapper<BasicInfo>();
        Long basicId = basicInfoRequest.getBasicId();
        String judge = basicInfoRequest.getJudge();
        String courtNumber = basicInfoRequest.getCourtNumber();
        String courtCause = basicInfoRequest.getCourtCause();
        int status = basicInfoRequest.getStatus();

        queryWrapper.eq(ObjectUtil.isNotNull(basicId), BasicInfo::getBasicId, basicId);
        queryWrapper.eq(ObjectUtil.isNotNull(judge), BasicInfo::getJudge, judge);
        queryWrapper.eq(ObjectUtil.isNotNull(courtNumber), BasicInfo::getCourtNumber, courtNumber);
        queryWrapper.eq(ObjectUtil.isNotNull(courtCause), BasicInfo::getCourtCause, courtCause);
        queryWrapper.eq(ObjectUtil.isNotNull(status), BasicInfo::getStatus, status);

        // 查询未删除状态的
        queryWrapper.eq(BasicInfo::getDelFlag, YesOrNotEnum.N.getCode());

        return queryWrapper;
    }
}

