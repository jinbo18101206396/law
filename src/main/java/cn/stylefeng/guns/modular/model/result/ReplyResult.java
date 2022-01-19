package cn.stylefeng.guns.modular.model.result;

import lombok.Data;
import java.util.Date;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 答辩表
 * </p>
 *
 * @author jinbo
 * @since 2022-01-19
 */
@Data
public class ReplyResult implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 主键
     */
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
