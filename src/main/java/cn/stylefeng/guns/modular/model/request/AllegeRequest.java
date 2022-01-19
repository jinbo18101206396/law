package cn.stylefeng.guns.modular.model.request;

import cn.stylefeng.roses.kernel.rule.pojo.request.BaseRequest;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * <p>
 * 诉称表
 * </p>
 *
 * @author jinbo
 * @since 2022-01-19
 */
@Data
public class AllegeRequest extends BaseRequest {

    /**
     * 主键
     */
    @NotNull(message = "allegeId不能为空", groups = {edit.class, delete.class, detail.class})
    private Long allegeId;

    /**
     * 原告或反诉原告简称
     */
    private String name;

    /**
     * 诉讼请求项
     */
    private String claimItem;

    /**
     * 事实与理由
     */
    private String factReason;

    /**
     * 是否反诉
     */
    private Boolean isCounterClaim;

    /**
     * 案号
     */
    private String courtNumber;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建人
     */
    private Long createUser;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 更新人
     */
    private Long updateUser;

}
