package cn.stylefeng.guns.modular.service;

import cn.stylefeng.guns.modular.entity.Allege;
import cn.stylefeng.guns.modular.model.request.AllegeRequest;
import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 诉称表 服务类
 * </p>
 *
 * @author jinbo
 * @since 2022-01-19
 */
public interface AllegeService extends IService<Allege> {

    /**
     * 保存原告诉讼请求项信息
     *
     * @param recordJsonObject 请求参数
     * @return
     * @author 金波
     * @Date 2022-05-24
     */
    void saveAccuserClaimItem(String courtNumber, String counterClaim, JSONObject recordJsonObject);


    /**
     * 保存反诉原告诉讼请求项信息
     *
     * @param recordJsonObject 请求参数
     * @return
     * @author 金波
     * @Date 2022-05-24
     */
    void saveCounterClaimAccuserItem(String courtNumber, String counterClaim, JSONObject recordJsonObject);

}
