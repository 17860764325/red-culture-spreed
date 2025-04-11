package org.jeecg.modules.demo.activityComment.entity;

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
import org.jeecg.modules.demo.courseComment.entity.CourseComment;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecg.common.aspect.annotation.Dict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Description: 活动评论表
 * @Author: jeecg-boot
 * @Date:   2025-04-09
 * @Version: V1.0
 */
@Data
@TableName("activity_comment")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="activity_comment对象", description="活动评论表")
public class ActivityComment implements Serializable {
    private static final long serialVersionUID = 1L;

	/**id*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;
	/**头像链接*/
	@Excel(name = "头像链接", width = 15)
    @ApiModelProperty(value = "头像链接")
    private String avatar;
	/**名称*/
	@Excel(name = "名称", width = 15)
    @ApiModelProperty(value = "名称")
    private String name;
	/**被评论用户id*/
	@Excel(name = "被评论用户id", width = 15)
    @ApiModelProperty(value = "被评论用户id")
    private String replyToId;
	/**评论用户id*/
	@Excel(name = "评论用户id", width = 15)
    @ApiModelProperty(value = "评论用户id")
    private String commentId;
	/**顶部评论ID*/
	@Excel(name = "顶部评论ID", width = 15)
    @ApiModelProperty(value = "顶部评论ID")
    private String topToId;
	/**回复内容*/
	@Excel(name = "回复内容", width = 15)
    @ApiModelProperty(value = "回复内容")
    private String content;
	/**活动ID*/
	@Excel(name = "活动ID", width = 15)
    @ApiModelProperty(value = "活动ID")
    private String activityId;
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
    /**
     * 子级数据(回复的评论)
     */
    @TableField(exist = false)
    private List<ActivityComment> replies;
    /**
     * 被评论用户name
     */
    @TableField(exist = false)
    private String replyToName;
}
