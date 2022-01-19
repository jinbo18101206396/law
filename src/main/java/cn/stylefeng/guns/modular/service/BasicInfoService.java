package cn.stylefeng.guns.modular.service;

import cn.stylefeng.guns.modular.entity.BasicInfo;
import cn.stylefeng.guns.modular.model.request.BasicInfoRequest;
import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * 基本信息service
 *
 * @author 金波
 * @date 2022/01/14 15:07
 */
public interface BasicInfoService extends IService<BasicInfo> {

    /**
     * 新增笔录基本信息
     *
     * @author jinbo
     * @Date 2022-01-19
     */
    void add(BasicInfoRequest basicInfoRequest);

    /**
     * 删除笔录
     *
     * @param basicInfoRequest 删除参数
     * @author 金波
     * @date 2022/01/14 15:07
     */
    void del(BasicInfoRequest basicInfoRequest);

    /**
     * 编辑笔录基本信息
     *
     * @param basicInfoRequest 请求参数封装
     * @author 金波
     * @date 2022/01/14 15:07
     */
    void update(BasicInfoRequest basicInfoRequest);

    /**
     * 查看笔录基本信息详情
     *
     * @param basicInfoRequest 查看参数
     * @return 详情结果
     * @author 金波
     * @date 2021/5/12 18:28
     */
    BasicInfo detail(BasicInfoRequest basicInfoRequest);

    /**
     * 查询笔录列表
     *
     * @param basicInfoRequest 请求参数
     * @return 笔录列表
     * @author 金波
     * @date 2022/01/14 15:07
     */
    List<BasicInfo> findList(BasicInfoRequest basicInfoRequest);

    /**
     * 分页查询笔录列表
     *
     * @param basicInfoRequest 查看参数
     * @return 笔录结果
     * @author 金波
     * @date 2022/01/14 15:07
     */
    PageResult<BasicInfo> findPage(BasicInfoRequest basicInfoRequest);

}







