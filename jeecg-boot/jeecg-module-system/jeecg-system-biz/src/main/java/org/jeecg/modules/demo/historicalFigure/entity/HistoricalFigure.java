package org.jeecg.modules.demo.historicalFigure.entity;

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
 * @Description: 历史人物
 * @Author: jeecg-boot
 * @Date:   2025-04-02
 * @Version: V1.0
 */
@Data
@TableName("historical_figure")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="historical_figure对象", description="历史人物")
public class HistoricalFigure implements Serializable {
    private static final long serialVersionUID = 1L;

	/**id*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;
	/**人物姓名*/
	@Excel(name = "人物姓名", width = 15)
    @ApiModelProperty(value = "人物姓名")
    private String name;
	/**出生年份*/
	@Excel(name = "出生年份", width = 15)
    @ApiModelProperty(value = "出生年份")
    private String birthYear;
	/**逝世年份*/
	@Excel(name = "逝世年份", width = 15)
    @ApiModelProperty(value = "逝世年份")
    private String deathYear;
	/**主要事迹*/
	@Excel(name = "主要事迹", width = 15)
    @ApiModelProperty(value = "主要事迹")
    private String mainAchievement;
	/**关联红色文化ID列表*/
	@Excel(name = "关联红色文化ID列表", width = 15)
    @ApiModelProperty(value = "关联红色文化ID列表")
    private String relatedCultureIds;
	/**创建人*/
    @ApiModelProperty(value = "创建人")
    private String createBy;
	/**创建时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
	/**更新人*/
    @ApiModelProperty(value = "更新人")
    private String updateBy;
	/**更新时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;
	/**所属部门编码*/
    @ApiModelProperty(value = "所属部门编码")
    private String sysOrgCode;
    /**人物图像*/
    @ApiModelProperty(value = "人物图像")
    private String imgUrl;
}
