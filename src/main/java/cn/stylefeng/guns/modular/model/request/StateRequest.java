package cn.stylefeng.guns.modular.model.request;

import cn.stylefeng.roses.kernel.rule.pojo.request.BaseRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * <p>
 * 基本信息陈述表
 * </p>
 *
 * @author jinbo
 * @since 2022-01-19
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class StateRequest extends BaseRequest {

    /**
     * 主键
     */
    @NotNull(message = "stateId不能为空", groups = {edit.class, delete.class, detail.class})
    private Long stateId;

    /**
     * 陈述类型：1-简易程序,2-普通程序独任制,3-普通程序合议制
     */
    @NotNull(message = "stateType不能为空", groups = {add.class, edit.class})
    private Integer stateType;

    /**
     * 陈述内容
     */
    private String stateContent;

    /**
     * 案号
     */
    @NotNull(message = "courtNumber不能为空", groups = {add.class, edit.class})
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
