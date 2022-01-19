package cn.stylefeng.guns.modular.model.request;

import cn.stylefeng.roses.kernel.rule.pojo.request.BaseRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * <p>
 * 答辩表
 * </p>
 *
 * @author jinbo
 * @since 2022-01-19
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ReplyRequest extends BaseRequest {

    /**
     * 主键
     */
    @NotNull(message = "replyId不能为空", groups = {edit.class, delete.class, detail.class})
    private Long replyId;

    /**
     * 被告或反诉被告简称
     */
    private String name;

    /**
     * 答辩内容
     */
    private String content;

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
