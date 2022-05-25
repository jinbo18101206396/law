package cn.stylefeng.guns.modular.service;

import cn.stylefeng.guns.modular.entity.BasicInfo;
import cn.stylefeng.guns.modular.model.request.BasicInfoRequest;
import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 基本信息service
 *
 * @author 金波
 * @date 2022/01/14 15:07
 */
public interface BasicInfoService extends IService<BasicInfo> {

    /**
     * 保存笔录基本信息
     *
     * @author jinbo
     * @Date 2022-05-23
     */
    void saveBasicInfo(String courtNumber,JSONObject recordJsonObject);

    /**
     * 获取笔录基本信息详情
     *
     * @param courtNumber 查看参数
     * @return 详情结果
     * @author 金波
     * @date 2022/05/25
     */
    JSONObject getBasicInfoObject(String courtNumber);

    /**
     * 获取权利告知信息
     *
     * @param courtNumber 查看参数
     * @return 详情结果
     * @author 金波
     * @date 2022/05/25
     */
    JSONObject getRightInfoObject(String courtNumber);


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







