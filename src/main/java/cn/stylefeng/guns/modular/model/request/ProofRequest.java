package cn.stylefeng.guns.modular.model.request;

import cn.stylefeng.roses.kernel.rule.pojo.request.BaseRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * <p>
 * 举证表
 * </p>
 *
 * @author jinbo
 * @since 2022-01-19
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ProofRequest extends BaseRequest {

    /**
     * 主键
     */
    @NotNull(message = "proofId不能为空", groups = {edit.class, delete.class, detail.class})
    private Long proofId;

    /**
     * 原告/被告/反诉原告/反诉被告简称
     */
    private String name;

    /**
     * 证据名称
     */
    private String evidence;

    /**
     * 证明事项
     */
    private String content;

    /**
     * 是否反诉
     */
    private Boolean isCounterClaim;

    /**
     * 举证类型：1-原告，2-被告
     */
    private Integer proofType;

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
