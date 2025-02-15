package org.jeecg.modules.demo.personStudy.controller;

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
import org.jeecg.modules.demo.personStudy.entity.PersonStudy;
import org.jeecg.modules.demo.personStudy.service.IPersonStudyService;

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
 * @Description: 用户课程学习信息
 * @Author: jeecg-boot
 * @Date:   2025-02-16
 * @Version: V1.0
 */
@Api(tags="用户课程学习信息")
@RestController
@RequestMapping("/personStudy/personStudy")
@Slf4j
public class PersonStudyController extends JeecgController<PersonStudy, IPersonStudyService> {
	@Autowired
	private IPersonStudyService personStudyService;
	
	/**
	 * 分页列表查询
	 *
	 * @param personStudy
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "用户课程学习信息-分页列表查询")
	@ApiOperation(value="用户课程学习信息-分页列表查询", notes="用户课程学习信息-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<PersonStudy>> queryPageList(PersonStudy personStudy,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
        QueryWrapper<PersonStudy> queryWrapper = QueryGenerator.initQueryWrapper(personStudy, req.getParameterMap());
		Page<PersonStudy> page = new Page<PersonStudy>(pageNo, pageSize);
		IPage<PersonStudy> pageList = personStudyService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param personStudy
	 * @return
	 */
	@AutoLog(value = "用户课程学习信息-添加")
	@ApiOperation(value="用户课程学习信息-添加", notes="用户课程学习信息-添加")
	@RequiresPermissions("personStudy:person_study:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody PersonStudy personStudy) {
		personStudyService.save(personStudy);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param personStudy
	 * @return
	 */
	@AutoLog(value = "用户课程学习信息-编辑")
	@ApiOperation(value="用户课程学习信息-编辑", notes="用户课程学习信息-编辑")
	@RequiresPermissions("personStudy:person_study:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody PersonStudy personStudy) {
		personStudyService.updateById(personStudy);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "用户课程学习信息-通过id删除")
	@ApiOperation(value="用户课程学习信息-通过id删除", notes="用户课程学习信息-通过id删除")
	@RequiresPermissions("personStudy:person_study:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		personStudyService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "用户课程学习信息-批量删除")
	@ApiOperation(value="用户课程学习信息-批量删除", notes="用户课程学习信息-批量删除")
	@RequiresPermissions("personStudy:person_study:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.personStudyService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "用户课程学习信息-通过id查询")
	@ApiOperation(value="用户课程学习信息-通过id查询", notes="用户课程学习信息-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<PersonStudy> queryById(@RequestParam(name="id",required=true) String id) {
		PersonStudy personStudy = personStudyService.getById(id);
		if(personStudy==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(personStudy);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param personStudy
    */
    @RequiresPermissions("personStudy:person_study:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, PersonStudy personStudy) {
        return super.exportXls(request, personStudy, PersonStudy.class, "用户课程学习信息");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequiresPermissions("personStudy:person_study:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, PersonStudy.class);
    }

}
