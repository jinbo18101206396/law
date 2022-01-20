package cn.stylefeng.guns.modular.model.result;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 举证表
 * </p>
 *
 * @author jinbo
 * @since 2022-01-19
 */
@Data
public class ProofResult implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 主键
     */
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
