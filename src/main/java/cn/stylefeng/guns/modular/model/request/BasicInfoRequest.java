package cn.stylefeng.guns.modular.model.request;

import cn.stylefeng.roses.kernel.rule.pojo.request.BaseRequest;
import cn.stylefeng.roses.kernel.scanner.api.annotation.field.ChineseDescription;
import cn.stylefeng.roses.kernel.validator.api.validators.status.StatusValue;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 基本信息
 *
 * @author 金波
 * @date 2022/01/14 15:07
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BasicInfoRequest extends BaseRequest {

    public Object getStatus;
    /**
     * 主键
     */
    @NotNull(message = "basicId不能为空", groups = {edit.class, delete.class, detail.class})
    private Long basicId;

    /**
     * 立案时间
     */
    @NotNull(message = "filingTime不能为空", groups = {add.class, edit.class})
    private Date filingTime;

    /**
     * 开庭时间
     */
    @NotNull(message = "courtTime不能为空", groups = {add.class, edit.class})
    private Date courtTime;

    /**
     * 开庭地点
     */
    @NotNull(message = "courtPlace不能为空", groups = {add.class, edit.class})
    private String courtPlace;

    /**
     * 审判长
     */
    private String chiefJudge;

    /**
     * 审判员
     */
    private String judge;

    /**
     * 陪审员
     */
    private String juror;

    /**
     * 人民陪审员
     */
    private String peopleJuror;

    /**
     * 书记员
     */
    @NotNull(message = "courtClerk不能为空", groups = {add.class, edit.class})
    private String courtClerk;

    /**
     * 案号
     */
    @NotNull(message = "courtNumber不能为空", groups = {add.class, edit.class})
    private String courtNumber;

    /**
     * 案由
     */
    @NotNull(message = "courtCause不能为空", groups = {add.class, edit.class})
    private String courtCause;

    /**
     * 案件状态
     */
    @NotNull(message = "状态不能为空", groups = updateStatus.class)
    @StatusValue(message = "状态不正确", groups = updateStatus.class)
    @ChineseDescription("状态（字典 1在审 2结案）")
    private Integer status;

    /**
     * 删除标记：Y-已删除，N-未删除
     */
    private String delFlag;

    /**
     * 创建人
     */
    private Long createUser;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新人
     */
    private Long updateUser;

    /**
     * 更新时间
     */
    private Date updateTime;


    public @interface changeStatus {
    }

}
