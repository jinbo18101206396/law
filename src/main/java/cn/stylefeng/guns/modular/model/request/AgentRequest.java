package cn.stylefeng.guns.modular.model.request;

import cn.stylefeng.roses.kernel.rule.pojo.request.BaseRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * <p>
 * 委托诉讼代理人表
 * </p>
 *
 * @author jinbo
 * @since 2022-01-19
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AgentRequest extends BaseRequest {

    /**
     * 主键
     */
    @NotNull(message = "agentId不能为空", groups = {edit.class, delete.class, detail.class})
    private Long agentId;

    /**
     * 委托诉讼代理人
     */
    private String agent;

    /**
     * 委托诉讼代理人单位
     */
    private String agentAddress;

    /**
     * 代理类型：1-原告代理，2-被告代理
     */
    private Integer agentType;

    /**
     * 原告或被告全称
     */
    private String agentName;

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
