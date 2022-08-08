package cn.stylefeng.guns.modular.model.result;

import lombok.Data;

import java.io.Serializable;
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
public class AllegeResult implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 主键
     */
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
    private String isCounterClaim;

    /**
     * 是否变更诉讼请求
     */
    private String isChangeClaimItem;

    /**
     * 变更后的诉讼请求项
     */
    private String claimItemAfterChange;

    /**
     * 变更后的事实与理由
     */
    private String factReasonAfterChange;

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
