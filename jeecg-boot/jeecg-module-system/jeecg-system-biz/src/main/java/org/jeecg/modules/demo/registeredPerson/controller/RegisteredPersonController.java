package org.jeecg.modules.demo.registeredPerson.controller;

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
import org.jeecg.modules.demo.registeredPerson.entity.RegisteredPerson;
import org.jeecg.modules.demo.registeredPerson.service.IRegisteredPersonService;

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
 * @Description: 报名人物
 * @Author: jeecg-boot
 * @Date:   2025-04-09
 * @Version: V1.0
 */
@Api(tags="报名人物")
@RestController
@RequestMapping("/registeredPerson/registeredPerson")
@Slf4j
public class RegisteredPersonController extends JeecgController<RegisteredPerson, IRegisteredPersonService> {
	@Autowired
	private IRegisteredPersonService registeredPersonService;

	/**
	 * 分页列表查询
	 *
	 * @param registeredPerson
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "报名人物-分页列表查询")
	@ApiOperation(value="报名人物-分页列表查询", notes="报名人物-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<RegisteredPerson>> queryPageList(RegisteredPerson registeredPerson,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
        QueryWrapper<RegisteredPerson> queryWrapper = QueryGenerator.initQueryWrapper(registeredPerson, req.getParameterMap());
		Page<RegisteredPerson> page = new Page<RegisteredPerson>(pageNo, pageSize);
		IPage<RegisteredPerson> pageList = registeredPersonService.page(page, queryWrapper);
		return Result.OK(pageList);
	}

	/**
	 *   添加
	 *
	 * @param registeredPerson
	 * @return
	 */
	@AutoLog(value = "报名人物-添加")
	@ApiOperation(value="报名人物-添加", notes="报名人物-添加")
	@RequiresPermissions("registeredPerson:registered_person:add")
	@PostMapping(value = "/add")
	public Result<Map<String, Object>> add(@RequestBody RegisteredPerson registeredPerson) {
//		registeredPersonService.save(registeredPerson);
		return registeredPersonService.add(registeredPerson);
	}

	/**
	 *  编辑
	 *
	 * @param registeredPerson
	 * @return
	 */
	@AutoLog(value = "报名人物-编辑")
	@ApiOperation(value="报名人物-编辑", notes="报名人物-编辑")
	@RequiresPermissions("registeredPerson:registered_person:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody RegisteredPerson registeredPerson) {
		registeredPersonService.updateById(registeredPerson);
		return Result.OK("编辑成功!");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "报名人物-通过id删除")
	@ApiOperation(value="报名人物-通过id删除", notes="报名人物-通过id删除")
	@RequiresPermissions("registeredPerson:registered_person:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		registeredPersonService.removeById(id);
		return Result.OK("删除成功!");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "报名人物-批量删除")
	@ApiOperation(value="报名人物-批量删除", notes="报名人物-批量删除")
	@RequiresPermissions("registeredPerson:registered_person:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.registeredPersonService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "报名人物-通过id查询")
	@ApiOperation(value="报名人物-通过id查询", notes="报名人物-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<RegisteredPerson> queryById(@RequestParam(name="id",required=true) String id) {
		RegisteredPerson registeredPerson = registeredPersonService.getById(id);
		if(registeredPerson==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(registeredPerson);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param registeredPerson
    */
    @RequiresPermissions("registeredPerson:registered_person:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, RegisteredPerson registeredPerson) {
        return super.exportXls(request, registeredPerson, RegisteredPerson.class, "报名人物");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequiresPermissions("registeredPerson:registered_person:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, RegisteredPerson.class);
    }

}
