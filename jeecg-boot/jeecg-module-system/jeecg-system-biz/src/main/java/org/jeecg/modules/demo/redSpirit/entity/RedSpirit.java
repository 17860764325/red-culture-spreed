package org.jeecg.modules.demo.redSpirit.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableLogic;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecg.common.constant.ProvinceCityArea;
import org.jeecg.common.util.SpringContextUtils;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecg.common.aspect.annotation.Dict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Description: 红色精神
 * @Author: jeecg-boot
 * @Date:   2025-02-15
 * @Version: V1.0
 */
@Data
@TableName("red_spirit")
@ApiModel(value="red_spirit对象", description="红色精神")
public class RedSpirit implements Serializable {
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
	/**精神名称*/
    @Excel(name = "精神名称", width = 15)
    @ApiModelProperty(value = "精神名称")
    private String spiritName;
	/**概述1*/
    @Excel(name = "概述1", width = 15)
    @ApiModelProperty(value = "概述1")
    private String overviewOne;
	/**概述二*/
    @Excel(name = "概述二", width = 15)
    @ApiModelProperty(value = "概述二")
    private String overviewTwo;
	/**概述三*/
    @Excel(name = "概述三", width = 15)
    @ApiModelProperty(value = "概述三")
    private String overviewThree;
	/**概述四*/
    @Excel(name = "概述四", width = 15)
    @ApiModelProperty(value = "概述四")
    private String overviewFour;
	/**文章编码*/
    @Excel(name = "文章编码", width = 15)
    @ApiModelProperty(value = "文章编码")
    private String pageNumber;
    /**文章类型*/
    @Excel(name = "文章类型", width = 15)
    @ApiModelProperty(value = "文章编码")
    private String pageType                 ;
}
