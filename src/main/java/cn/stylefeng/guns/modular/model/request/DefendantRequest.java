package cn.stylefeng.guns.modular.model.request;

import cn.stylefeng.roses.kernel.rule.pojo.request.BaseRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * <p>
 * 被告信息表
 * </p>
 *
 * @author jinbo
 * @since 2022-01-19
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DefendantRequest extends BaseRequest {

    /**
     * 主键
     */
    @NotNull(message = "defendantId不能为空", groups = {edit.class, delete.class, detail.class})
    private Long defendantId;

    /**
     * 被告全称
     */
    @NotNull(message = "defendant不能为空", groups = {add.class, edit.class})
    private String defendant;

    /**
     * 被告简称
     */
    @NotNull(message = "defendantShort不能为空", groups = {add.class, edit.class})
    private String defendantShort;

    /**
     * 被告类型：1-单位，2-个人
     */
    @NotNull(message = "defendantType不能为空", groups = {add.class, edit.class})
    private Integer defendantType;

    /**
     * 被告地址
     */
    @NotNull(message = "defendantAddress不能为空", groups = {add.class, edit.class})
    private String defendantAddress;

    /**
     * 法人代表
     */
    private String defendantRepresent;

    /**
     * 法人职务
     */
    private String defendantDuty;

    /**
     * 案号
     */
    @NotNull(message = "courtNumber不能为空", groups = {add.class, edit.class})
    private String courtNumber;

    /**
     * 是否听清诉讼权力和义务
     */
    private String defendantRightDuty;

    /**
     * 对审判员和书记员是否申请回避
     */
    private String defendantAvoid;

    /**
     * 是否能够调解
     */
    private Boolean isMediate;

    /**
     * 调解方案
     */
    private String mediatePlan;

    /**
     * 庭外和解时限
     */
    private String timeLimit;

    /**
     * 是否同意电子裁判文书送达
     */
    private Boolean isDelivery;

    /**
     * 电子邮件地址
     */
    private String email;

    /**
     * 最后陈述意见
     */
    private String finalStatement;

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
