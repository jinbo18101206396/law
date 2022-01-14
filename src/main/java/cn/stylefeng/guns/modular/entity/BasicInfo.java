package cn.stylefeng.guns.modular.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

    /**
     * <p>
     * 基本信息表
     * </p>
     *
     */
    @TableName("basic_info")
    public class BasicInfo implements Serializable {

        private static final long serialVersionUID=1L;

        /**
         * 主键
         */
        @TableId(value = "basic_id", type = IdType.AUTO)
        private Long basicId;

        /**
         * 立案时间
         */
        @TableField("filing_time")
        private String filingTime;

        /**
         * 开庭时间
         */
        @TableField("court_time")
        private String courtTime;

        /**
         * 开庭地点
         */
        @TableField("court_place")
        private String courtPlace;

        /**
         * 审判长
         */
        @TableField("chief_judge")
        private String chiefJudge;

        /**
         * 审判员
         */
        @TableField("judge")
        private String judge;

        /**
         * 陪审员
         */
        @TableField("juror")
        private String juror;

        /**
         * 书记员
         */
        @TableField("court_clerk")
        private String courtClerk;

        /**
         * 案号
         */
        @TableField("court_number")
        private String courtNumber;

        /**
         * 案由
         */
        @TableField("court_cause")
        private String courtCause;

        /**
         * 案件状态（1-在审，2-结案）
         */
        @TableField("court_cause")
        private Integer status;

        /**
         * 删除标记：Y-已删除，N-未删除
         */
        @TableField("del_flag")
        private String delFlag;

        /**
         * 创建人
         */
        @TableField(value = "create_user", fill = FieldFill.INSERT)
        private Long createUser;

        /**
         * 创建时间
         */
        @TableField(value = "create_time", fill = FieldFill.INSERT)
        private Date createTime;

        /**
         * 更新人
         */
        @TableField(value = "update_user", fill = FieldFill.UPDATE)
        private Long updateUser;

        /**
         * 更新时间
         */
        @TableField(value = "update_time", fill = FieldFill.UPDATE)
        private Date updateTime;

        public static long getSerialVersionUID() {
            return serialVersionUID;
        }


        public Long getBasicId() {
            return basicId;
        }

        public void setBasicId(Long basicId) {
            this.basicId = basicId;
        }

        public String getFilingTime() {
            return filingTime;
        }

        public void setFilingTime(String filingTime) {
            this.filingTime = filingTime;
        }

        public String getCourtTime() {
            return courtTime;
        }

        public void setCourtTime(String courtTime) {
            this.courtTime = courtTime;
        }

        public String getCourtPlace() {
            return courtPlace;
        }

        public void setCourtPlace(String courtPlace) {
            this.courtPlace = courtPlace;
        }

        public String getChiefJudge() {
            return chiefJudge;
        }

        public void setChiefJudge(String chiefJudge) {
            this.chiefJudge = chiefJudge;
        }

        public String getJudge() {
            return judge;
        }

        public void setJudge(String judge) {
            this.judge = judge;
        }

        public String getJuror() {
            return juror;
        }

        public void setJuror(String juror) {
            this.juror = juror;
        }

        public String getCourtClerk() {
            return courtClerk;
        }

        public void setCourtClerk(String courtClerk) {
            this.courtClerk = courtClerk;
        }

        public String getCourtNumber() {
            return courtNumber;
        }

        public void setCourtNumber(String courtNumber) {
            this.courtNumber = courtNumber;
        }

        public String getCourtCause() {
            return courtCause;
        }

        public void setCourtCause(String courtCause) {
            this.courtCause = courtCause;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public String getDelFlag() {
            return delFlag;
        }

        public void setDelFlag(String delFlag) {
            this.delFlag = delFlag;
        }

        public Long getCreateUser() {
            return createUser;
        }

        public void setCreateUser(Long createUser) {
            this.createUser = createUser;
        }

        public Date getCreateTime() {
            return createTime;
        }

        public void setCreateTime(Date createTime) {
            this.createTime = createTime;
        }

        public Long getUpdateUser() {
            return updateUser;
        }

        public void setUpdateUser(Long updateUser) {
            this.updateUser = updateUser;
        }

        public Date getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(Date updateTime) {
            this.updateTime = updateTime;
        }

        @Override
        public String toString() {
            return "BasicInfo{" +
                    "basicId=" + basicId +
                    ", filingTime='" + filingTime + '\'' +
                    ", courtTime='" + courtTime + '\'' +
                    ", courtPlace='" + courtPlace + '\'' +
                    ", chiefJudge='" + chiefJudge + '\'' +
                    ", judge='" + judge + '\'' +
                    ", juror='" + juror + '\'' +
                    ", courtClerk='" + courtClerk + '\'' +
                    ", courtNumber='" + courtNumber + '\'' +
                    ", courtCause='" + courtCause + '\'' +
                    ", status=" + status +
                    ", delFlag='" + delFlag + '\'' +
                    ", createUser=" + createUser +
                    ", createTime=" + createTime +
                    ", updateUser=" + updateUser +
                    ", updateTime=" + updateTime +
                    '}';
        }
    }
