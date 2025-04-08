package org.jeecg.modules.demo.courseExam.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.math.BigDecimal;
import java.util.List;

import com.baomidou.mybatisplus.annotation.*;
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
 * @Description: course_exam
 * @Author: jeecg-boot
 * @Date:   2025-04-05
 * @Version: V1.0
 */
@Data
@TableName("course_exam")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="course_exam对象", description="course_exam")
public class CourseExam implements Serializable {
    private static final long serialVersionUID = 1L;

	/**id*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;
	/**问题标题*/
	@Excel(name = "问题标题", width = 15)
    @ApiModelProperty(value = "问题标题")
    private String question;
	/**选项A*/
	@Excel(name = "选项A", width = 15)
    @ApiModelProperty(value = "选项A")
    private String optionA;
	/**选项B*/
	@Excel(name = "选项B", width = 15)
    @ApiModelProperty(value = "选项B")
    private String optionB;
	/**选项C*/
	@Excel(name = "选项C", width = 15)
    @ApiModelProperty(value = "选项C")
    private String optionC;
	/**选项D*/
	@Excel(name = "选项D", width = 15)
    @ApiModelProperty(value = "选项D")
    private String optionD;
	/**答案(数字，从a-d依次为1-4)*/
	@Excel(name = "答案(数字，从a-d依次为1-4)", width = 15)
    @ApiModelProperty(value = "答案(数字，从a-d依次为1-4)")
    private Integer answer;
	/**课程编码*/
	@Excel(name = "课程编码", width = 15)
    @ApiModelProperty(value = "课程编码")
    private String objectCode;
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
    /**分数*/
    @ApiModelProperty(value = "分数")
    private Double score;

    /**
     * 答案汇总
     */
    @TableField(exist = false)
    private List<String> options;
}
