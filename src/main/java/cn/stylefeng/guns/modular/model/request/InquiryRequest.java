package cn.stylefeng.guns.modular.model.request;

import cn.stylefeng.roses.kernel.rule.pojo.request.BaseRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * <p>
 * 法庭询问表
 * </p>
 *
 * @author jinbo
 * @since 2022-01-19
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class InquiryRequest extends BaseRequest {

    /**
     * 主键
     */
    @NotNull(message = "inquiryId不能为空", groups = {edit.class, delete.class, detail.class})
    private Long inquiryId;

    /**
     * 问题
     */
    private String question;

    /**
     * 原告简称
     */
    private String accuser;

    /**
     * 原告回答
     */
    private String accuserAnswer;

    /**
     * 被告简称
     */
    private String defendant;

    /**
     * 被告回答
     */
    private String defendantAnswer;

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
