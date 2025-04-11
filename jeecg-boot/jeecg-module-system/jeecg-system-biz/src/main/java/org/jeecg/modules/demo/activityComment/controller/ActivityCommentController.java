package org.jeecg.modules.demo.activityComment.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.query.QueryRuleEnum;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.demo.activityComment.entity.ActivityComment;
import org.jeecg.modules.demo.activityComment.service.IActivityCommentService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.modules.demo.courseComment.entity.CourseComment;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.apache.shiro.authz.annotation.RequiresPermissions;

 /**
 * @Description: 活动评论表
 * @Author: jeecg-boot
 * @Date:   2025-04-09
 * @Version: V1.0
 */
@Api(tags="活动评论表")
@RestController
@RequestMapping("/activityComment/activityComment")
@Slf4j
public class ActivityCommentController extends JeecgController<ActivityComment, IActivityCommentService> {
	@Autowired
	private IActivityCommentService activityCommentService;

	/**
	 * 分页列表查询
	 *
	 * @param activityComment
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "活动评论表-分页列表查询")
	@ApiOperation(value="活动评论表-分页列表查询", notes="活动评论表-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<ActivityComment>> queryPageList(ActivityComment activityComment,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
        QueryWrapper<ActivityComment> queryWrapper = QueryGenerator.initQueryWrapper(activityComment, req.getParameterMap());
		Page<ActivityComment> page = new Page<ActivityComment>(pageNo, pageSize);
		IPage<ActivityComment> pageList = activityCommentService.page(page, queryWrapper);
		return Result.OK(pageList);
	}

	/**
	 *   添加
	 *
	 * @param activityComment
	 * @return
	 */
	@AutoLog(value = "活动评论表-添加")
	@ApiOperation(value="活动评论表-添加", notes="活动评论表-添加")
	@RequiresPermissions("activityComment:activity_comment:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody ActivityComment activityComment) {
		activityCommentService.save(activityComment);
		return Result.OK("添加成功！");
	}

	/**
	 *  编辑
	 *
	 * @param activityComment
	 * @return
	 */
	@AutoLog(value = "活动评论表-编辑")
	@ApiOperation(value="活动评论表-编辑", notes="活动评论表-编辑")
	@RequiresPermissions("activityComment:activity_comment:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody ActivityComment activityComment) {
		activityCommentService.updateById(activityComment);
		return Result.OK("编辑成功!");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "活动评论表-通过id删除")
	@ApiOperation(value="活动评论表-通过id删除", notes="活动评论表-通过id删除")
	@RequiresPermissions("activityComment:activity_comment:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		activityCommentService.removeById(id);
		return Result.OK("删除成功!");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "活动评论表-批量删除")
	@ApiOperation(value="活动评论表-批量删除", notes="活动评论表-批量删除")
	@RequiresPermissions("activityComment:activity_comment:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.activityCommentService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "活动评论表-通过id查询")
	@ApiOperation(value="活动评论表-通过id查询", notes="活动评论表-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<ActivityComment> queryById(@RequestParam(name="id",required=true) String id) {
		ActivityComment activityComment = activityCommentService.getById(id);
		if(activityComment==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(activityComment);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param activityComment
    */
    @RequiresPermissions("activityComment:activity_comment:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, ActivityComment activityComment) {
        return super.exportXls(request, activityComment, ActivityComment.class, "活动评论表");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequiresPermissions("activityComment:activity_comment:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, ActivityComment.class);
    }

	 /**
	  *  根据课程编码和章节编码查询所有评论
	  *
	  * @param activityComment
	  * @return
	  */
	 @AutoLog(value = "activity_comment-查询所有")
	 @ApiOperation(value="activity_comment-查询所有", notes="activity_comment-查询所有")
	 @PostMapping(value = "/selectByActivityId")
	 public Result<List<ActivityComment>> selectByActivityId(@RequestBody ActivityComment activityComment) {
		 return activityCommentService.selectByActivityId(activityComment);
	 }
}
