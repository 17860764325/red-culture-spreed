package org.jeecg.modules.demo.examResult.controller;

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
import org.jeecg.modules.demo.examResult.entity.ExamResult;
import org.jeecg.modules.demo.examResult.service.IExamResultService;

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
 * @Description: 考试结果表
 * @Author: jeecg-boot
 * @Date:   2025-04-08
 * @Version: V1.0
 */
@Api(tags="考试结果表")
@RestController
@RequestMapping("/examResult/examResult")
@Slf4j
public class ExamResultController extends JeecgController<ExamResult, IExamResultService> {
	@Autowired
	private IExamResultService examResultService;
	
	/**
	 * 分页列表查询
	 *
	 * @param examResult
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "考试结果表-分页列表查询")
	@ApiOperation(value="考试结果表-分页列表查询", notes="考试结果表-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<ExamResult>> queryPageList(ExamResult examResult,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
        QueryWrapper<ExamResult> queryWrapper = QueryGenerator.initQueryWrapper(examResult, req.getParameterMap());
		Page<ExamResult> page = new Page<ExamResult>(pageNo, pageSize);
		IPage<ExamResult> pageList = examResultService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param examResult
	 * @return
	 */
	@AutoLog(value = "考试结果表-添加")
	@ApiOperation(value="考试结果表-添加", notes="考试结果表-添加")
	@RequiresPermissions("examResult:exam_result:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody ExamResult examResult) {
		examResultService.save(examResult);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param examResult
	 * @return
	 */
	@AutoLog(value = "考试结果表-编辑")
	@ApiOperation(value="考试结果表-编辑", notes="考试结果表-编辑")
	@RequiresPermissions("examResult:exam_result:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody ExamResult examResult) {
		examResultService.updateById(examResult);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "考试结果表-通过id删除")
	@ApiOperation(value="考试结果表-通过id删除", notes="考试结果表-通过id删除")
	@RequiresPermissions("examResult:exam_result:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		examResultService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "考试结果表-批量删除")
	@ApiOperation(value="考试结果表-批量删除", notes="考试结果表-批量删除")
	@RequiresPermissions("examResult:exam_result:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.examResultService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "考试结果表-通过id查询")
	@ApiOperation(value="考试结果表-通过id查询", notes="考试结果表-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<ExamResult> queryById(@RequestParam(name="id",required=true) String id) {
		ExamResult examResult = examResultService.getById(id);
		if(examResult==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(examResult);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param examResult
    */
    @RequiresPermissions("examResult:exam_result:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, ExamResult examResult) {
        return super.exportXls(request, examResult, ExamResult.class, "考试结果表");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequiresPermissions("examResult:exam_result:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, ExamResult.class);
    }

}
