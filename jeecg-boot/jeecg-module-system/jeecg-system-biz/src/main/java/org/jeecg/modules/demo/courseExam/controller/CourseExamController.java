package org.jeecg.modules.demo.courseExam.controller;

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
import org.jeecg.modules.demo.courseExam.entity.CourseExam;
import org.jeecg.modules.demo.courseExam.service.ICourseExamService;

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
 * @Description: course_exam
 * @Author: jeecg-boot
 * @Date:   2025-04-05
 * @Version: V1.0
 */
@Api(tags="course_exam")
@RestController
@RequestMapping("/courseExam/courseExam")
@Slf4j
public class CourseExamController extends JeecgController<CourseExam, ICourseExamService> {
	@Autowired
	private ICourseExamService courseExamService;
	
	/**
	 * 分页列表查询
	 *
	 * @param courseExam
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "course_exam-分页列表查询")
	@ApiOperation(value="course_exam-分页列表查询", notes="course_exam-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<CourseExam>> queryPageList(CourseExam courseExam,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
        QueryWrapper<CourseExam> queryWrapper = QueryGenerator.initQueryWrapper(courseExam, req.getParameterMap());
		Page<CourseExam> page = new Page<CourseExam>(pageNo, pageSize);
		IPage<CourseExam> pageList = courseExamService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param courseExam
	 * @return
	 */
	@AutoLog(value = "course_exam-添加")
	@ApiOperation(value="course_exam-添加", notes="course_exam-添加")
	@RequiresPermissions("courseExam:course_exam:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody CourseExam courseExam) {
		courseExamService.save(courseExam);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param courseExam
	 * @return
	 */
	@AutoLog(value = "course_exam-编辑")
	@ApiOperation(value="course_exam-编辑", notes="course_exam-编辑")
	@RequiresPermissions("courseExam:course_exam:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody CourseExam courseExam) {
		courseExamService.updateById(courseExam);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "course_exam-通过id删除")
	@ApiOperation(value="course_exam-通过id删除", notes="course_exam-通过id删除")
	@RequiresPermissions("courseExam:course_exam:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		courseExamService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "course_exam-批量删除")
	@ApiOperation(value="course_exam-批量删除", notes="course_exam-批量删除")
	@RequiresPermissions("courseExam:course_exam:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.courseExamService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "course_exam-通过id查询")
	@ApiOperation(value="course_exam-通过id查询", notes="course_exam-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<CourseExam> queryById(@RequestParam(name="id",required=true) String id) {
		CourseExam courseExam = courseExamService.getById(id);
		if(courseExam==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(courseExam);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param courseExam
    */
    @RequiresPermissions("courseExam:course_exam:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, CourseExam courseExam) {
        return super.exportXls(request, courseExam, CourseExam.class, "course_exam");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequiresPermissions("courseExam:course_exam:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, CourseExam.class);
    }

	 /**
	  * 随机查询(查询50条)
	  *
	  * @param courseExam
	  * @return
	  */
	 @PostMapping(value = "/randomQuery")
	 public Result<List<CourseExam>> randomQuery(@RequestBody CourseExam courseExam) {
		 return courseExamService.randomQuery(courseExam);
	 }

}
