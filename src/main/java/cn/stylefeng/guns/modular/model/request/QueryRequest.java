package cn.stylefeng.guns.modular.model.request;

import cn.stylefeng.roses.kernel.rule.pojo.request.BaseRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * <p>
 * 质证表
 * </p>
 *
 * @author jinbo
 * @since 2022-01-19
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class QueryRequest extends BaseRequest {

    /**
     * 主键
     */
    @NotNull(message = "queryId不能为空", groups = {edit.class, delete.class, detail.class})
    private Long queryId;

    /**
     * 原告/被告/反诉原告/反诉被告简称
     */
    private String name;

    /**
     * 证据名称
     */
    private String evidence;

    /**
     * 真实性
     */
    private Boolean facticity;

    /**
     * 合法性
     */
    private Boolean legality;

    /**
     * 关联性
     */
    private Boolean relevance;

    /**
     * 理由
     */
    private String reason;

    /**
     * 是否反诉
     */
    private Boolean isCounterClaim;

    /**
     * 质证类型：1-被告质证，2-原告及其他被告质证
     */
    private Integer queryType;

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
