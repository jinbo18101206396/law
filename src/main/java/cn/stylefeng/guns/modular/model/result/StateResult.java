package cn.stylefeng.guns.modular.model.result;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 基本信息陈述表
 * </p>
 *
 * @author jinbo
 * @since 2022-01-19
 */
@Data
public class StateResult implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 主键
     */
    private Long stateId;

    /**
     * 陈述类型：1-简易程序,2-普通程序独任制,3-普通程序合议制
     */
    private Integer stateType;

    /**
     * 陈述内容
     */
    private String stateContent;

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
