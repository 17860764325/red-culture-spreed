package org.jeecg.modules.demo.personStudy.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableLogic;
import org.jeecg.common.constant.ProvinceCityArea;
import org.jeecg.common.util.SpringContextUtils;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecg.common.aspect.annotation.Dict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Description: 用户课程学习信息
 * @Author: jeecg-boot
 * @Date:   2025-02-16
 * @Version: V1.0
 */
@Data
@TableName("person_study")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="person_study对象", description="用户课程学习信息")
public class PersonStudy implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private String id;
	/**创建人*/
    @ApiModelProperty(value = "创建人")
    private String createBy;
	/**创建日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建日期")
    private Date createTime;
	/**更新人*/
    @ApiModelProperty(value = "更新人")
    private String updateBy;
	/**更新日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新日期")
    private Date updateTime;
	/**所属部门*/
    @ApiModelProperty(value = "所属部门")
    private String sysOrgCode;
	/**用户账号*/
	@Excel(name = "用户账号", width = 15)
    @ApiModelProperty(value = "用户账号")
    private String userAccount;
	/**用户名称*/
	@Excel(name = "用户名称", width = 15)
    @ApiModelProperty(value = "用户名称")
    private String userName;
	/**学习次数*/
	@Excel(name = "学习次数", width = 15)
    @ApiModelProperty(value = "学习次数")
    private String studyTimes;
	/**学习时长*/
	@Excel(name = "学习时长", width = 15)
    @ApiModelProperty(value = "学习时长")
    private String studyTime;
	/**发帖次数*/
	@Excel(name = "发帖次数", width = 15)
    @ApiModelProperty(value = "发帖次数")
    private String sendMessages;
}
