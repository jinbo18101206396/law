package cn.stylefeng.guns.modular.service;

import cn.stylefeng.guns.modular.entity.Defendant;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 被告信息表 服务类
 * </p>
 *
 * @author jinbo
 * @since 2022-01-19
 */
public interface DefendantService extends IService<Defendant> {

    /**
     * 保存被告信息
     *
     * @param recordJsonObject 查看参数
     * @return
     * @author 金波
     * @date 2022/05/24
     */
    void saveDefendantInfo(String courtNumber, JSONObject recordJsonObject);

    /**
     * 获取被告信息
     *
     * @param courtNumber 查看参数
     * @return
     * @author 金波
     * @date 2022/05/24
     */
    JSONArray getDefendantInfoArray(String courtNumber);

    /**
     * 获取被告信息列表
     *
     * @param courtNumber 查看参数
     * @return
     * @author 金波
     * @date 2022/06/16
     */
    List<Defendant> getDefendantInfoList(String courtNumber, Map<String, Object> recordMap);

    /**
     * 删除被告信息（修改删除标记）
     *
     * @param courtNumber 查看参数
     * @return
     * @author 金波
     * @date 2022/06/02
     */
    Boolean deleteDefendantInfo(String courtNumber);

    /**
     * 删除被告信息（彻底删除）
     *
     * @param courtNumber 查看参数
     * @return
     * @author 金波
     * @date 2022/07/16
     */
    void delete(String courtNumber);

}
