package org.jeecg.modules.demo.chapterList.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.annotation.*;
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
 * @Description: 章节列表
 * @Author: jeecg-boot
 * @Date:   2025-02-16
 * @Version: V1.0
 */
@Data
@TableName("chapter_list")
@ApiModel(value="chapter_list对象", description="章节列表")
public class ChapterList implements Serializable {
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
	/**章节名称*/
    @Excel(name = "章节名称", width = 15)
    @ApiModelProperty(value = "章节名称")
    private String chapterName;
	/**章节编码*/
    @Excel(name = "章节编码", width = 15)
    @ApiModelProperty(value = "章节编码")
    private String chapterCode;
	/**父章节id*/
    @Excel(name = "父章节id", width = 15)
    @ApiModelProperty(value = "父章节id")
    private String patientId;
    /**父章节id*/
    @Excel(name = "课程编码", width = 15)
    @ApiModelProperty(value = "课程编码")
    private String objectCode;
    /**是否观看*/
    @Excel(name = "是否观看", width = 15)
    @ApiModelProperty(value = "是否观看")
    private String watch;
    /**具体内容*/
    @TableField(exist = false)
    private String context;
    /**视频内容*/
    @TableField(exist = false)
    private String vedio;
    /**视频内容*/
    @TableField(exist = false)
    private String file;
    /**子级数据*/
    @TableField(exist = false)
    private List<ChapterList> childList;

}
