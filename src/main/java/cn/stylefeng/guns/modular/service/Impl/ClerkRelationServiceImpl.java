package cn.stylefeng.guns.modular.service.Impl;

import cn.stylefeng.guns.modular.entity.ClerkRelation;
import cn.stylefeng.guns.modular.mapper.ClerkRelationMapper;
import cn.stylefeng.guns.modular.service.ClerkRelationService;
import cn.stylefeng.roses.kernel.auth.api.context.LoginContext;
import cn.stylefeng.roses.kernel.system.modular.user.entity.SysUser;
import cn.stylefeng.roses.kernel.system.modular.user.service.SysUserService;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 书记员、审判员、开庭地点对应关系表 服务实现类
 * </p>
 *
 * @author jinbo
 * @since 2022-05-24
 */
@Service
public class ClerkRelationServiceImpl extends ServiceImpl<ClerkRelationMapper, ClerkRelation> implements ClerkRelationService {


    @Resource
    private SysUserService sysUserService;

    @Resource
    private ClerkRelationService clerkRelationService;

    @Override
    public JSONObject getClerkJudgePlaceRelation() {
        JSONObject clerkJudgePlaceObject = new JSONObject();

        Long userId = LoginContext.me().getLoginUser().getUserId();
        LambdaQueryWrapper<SysUser> userQueryWrapper = new LambdaQueryWrapper<>();
        userQueryWrapper.eq(SysUser::getUserId, userId);
        SysUser user = sysUserService.getOne(userQueryWrapper);
        String userName = user.getRealName();

        LambdaQueryWrapper<ClerkRelation> relationQueryWrapper = new LambdaQueryWrapper<>();
        relationQueryWrapper.eq(ClerkRelation::getCourtClerk, userName);
        ClerkRelation clerkRelation = clerkRelationService.getOne(relationQueryWrapper);

        clerkJudgePlaceObject.put("court_clerk", clerkRelation.getCourtClerk());
        clerkJudgePlaceObject.put("judge", clerkRelation.getJudge());
        clerkJudgePlaceObject.put("court_place", clerkRelation.getCourtPlace());
        return clerkJudgePlaceObject;
    }

}
