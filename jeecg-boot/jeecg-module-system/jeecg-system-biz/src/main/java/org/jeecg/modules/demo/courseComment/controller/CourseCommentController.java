package org.jeecg.modules.demo.courseComment.controller;

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
import org.jeecg.modules.demo.courseComment.entity.CourseComment;
import org.jeecg.modules.demo.courseComment.service.ICourseCommentService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

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
 * @Description: course_comment
 * @Author: jeecg-boot
 * @Date:   2025-04-04
 * @Version: V1.0
 */
@Api(tags="course_comment")
@RestController
@RequestMapping("/courseComment/courseComment")
@Slf4j
public class CourseCommentController extends JeecgController<CourseComment, ICourseCommentService> {
	@Autowired
	private ICourseCommentService courseCommentService;
	
	/**
	 * 分页列表查询
	 *
	 * @param courseComment
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "course_comment-分页列表查询")
	@ApiOperation(value="course_comment-分页列表查询", notes="course_comment-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<CourseComment>> queryPageList(CourseComment courseComment,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
        QueryWrapper<CourseComment> queryWrapper = QueryGenerator.initQueryWrapper(courseComment, req.getParameterMap());
		Page<CourseComment> page = new Page<CourseComment>(pageNo, pageSize);
		IPage<CourseComment> pageList = courseCommentService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param courseComment
	 * @return
	 */
	@AutoLog(value = "course_comment-添加")
	@ApiOperation(value="course_comment-添加", notes="course_comment-添加")
	@RequiresPermissions("courseComment:course_comment:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody CourseComment courseComment) {
		courseCommentService.save(courseComment);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param courseComment
	 * @return
	 */
	@AutoLog(value = "course_comment-编辑")
	@ApiOperation(value="course_comment-编辑", notes="course_comment-编辑")
	@RequiresPermissions("courseComment:course_comment:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody CourseComment courseComment) {
		courseCommentService.updateById(courseComment);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "course_comment-通过id删除")
	@ApiOperation(value="course_comment-通过id删除", notes="course_comment-通过id删除")
	@RequiresPermissions("courseComment:course_comment:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		courseCommentService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "course_comment-批量删除")
	@ApiOperation(value="course_comment-批量删除", notes="course_comment-批量删除")
	@RequiresPermissions("courseComment:course_comment:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.courseCommentService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "course_comment-通过id查询")
	@ApiOperation(value="course_comment-通过id查询", notes="course_comment-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<CourseComment> queryById(@RequestParam(name="id",required=true) String id) {
		CourseComment courseComment = courseCommentService.getById(id);
		if(courseComment==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(courseComment);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param courseComment
    */
    @RequiresPermissions("courseComment:course_comment:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, CourseComment courseComment) {
        return super.exportXls(request, courseComment, CourseComment.class, "course_comment");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequiresPermissions("courseComment:course_comment:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, CourseComment.class);
    }

	 /**
	  *  根据课程编码和章节编码查询所有评论
	  *
	  * @param courseComment
	  * @return
	  */
	 @AutoLog(value = "course_comment-查询所有")
	 @ApiOperation(value="course_comment-查询所有", notes="course_comment-查询所有")
	 @PostMapping(value = "/selectByObjectCodeAndChapterCode")
	 public Result<List<CourseComment>> selectByObjectCodeAndChapterCode(@RequestBody CourseComment courseComment) {
		 return courseCommentService.selectByObjectCodeAndChapterCode(courseComment);
	 }

}
