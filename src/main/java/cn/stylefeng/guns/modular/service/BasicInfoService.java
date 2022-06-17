package cn.stylefeng.guns.modular.service;

import cn.stylefeng.guns.modular.entity.BasicInfo;
import cn.stylefeng.guns.modular.entity.CourtInvestigate;
import cn.stylefeng.guns.modular.model.request.BasicInfoRequest;
import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

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
     * 获取笔录基本信息
     *
     * @param courtNumber 查看参数
     * @return 详情结果
     * @author 金波
     * @date 2022/06/16
     */
    BasicInfo getBasicInfo(String courtNumber);

    /**
     * 删除笔录基本信息
     *
     * @param courtNumber 查看参数
     * @return 详情结果
     * @author 金波
     * @date 2022/06/02
     */
    Boolean deleteBasicInfo(String courtNumber);

    /**
     * 获取笔录基本信息列表
     *
     * @param courtNumber 查看参数
     * @return 详情结果
     * @author 金波
     * @date 2022/05/26
     */
    List<BasicInfo> getBasicInfoList(String courtNumber);

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
     * 获取电子裁判文书送达信息
     *
     * @param courtNumber 查看参数
     * @return 详情结果
     * @author 金波
     * @date 2022/05/25
     */
    JSONArray getDiliveryInfoArray(String courtNumber);


    /**
     * 获取调解信息
     *
     * @param courtNumber 查看参数
     * @return 详情结果
     * @author 金波
     * @date 2022/05/25
     */
    JSONObject getMediateInfoObject(String courtNumber);


    /**
     * 获取最后陈述意见
     *
     * @param courtNumber 查看参数
     * @return 详情结果
     * @author 金波
     * @date 2022/05/25
     */
    JSONArray getFinalStatementInfoArray(String courtNumber);

    /**
     * 获取审判员最终总结
     *
     * @param courtNumber 查看参数
     * @return 详情结果
     * @author 金波
     * @date 2022/06/02
     */
    String getSummarize(String courtNumber);


    /**
     * 获取法庭调查信息
     *
     * @param courtNumber 查看参数
     * @return 详情结果
     * @author 金波
     * @date 2022/05/25
     */
    JSONObject getCourtInvestigateObject(String courtNumber);

    /**
     * 获取法庭调查信息
     *
     * @param courtNumber 查看参数
     * @return 详情结果
     * @author 金波
     * @date 2022/05/25
     */
    CourtInvestigate getCourtInvestigateInfo(String courtNumber);


    /**
     * 分页查询笔录列表
     *
     * @param basicInfoRequest 查看参数
     * @return 笔录结果
     * @author 金波
     * @date 2022/01/14 15:07
     */
    PageResult<BasicInfo> findPage(BasicInfoRequest basicInfoRequest);

    /**
     * 修改案件状态
     *
     * @param basicInfoRequest 查看参数
     * @author 金波
     * @date 2022/06/04 15:07
     */
    void editStatus(BasicInfoRequest basicInfoRequest);

    /**
     * 删除笔录
     *
     * @param basicInfoRequest 查看参数
     * @author 金波
     * @date 2022/06/04
     */
    void delete(BasicInfoRequest basicInfoRequest);

}







