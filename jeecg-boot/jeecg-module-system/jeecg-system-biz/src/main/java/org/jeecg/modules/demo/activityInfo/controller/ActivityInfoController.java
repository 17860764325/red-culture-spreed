package org.jeecg.modules.demo.activityInfo.controller;

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
import org.jeecg.modules.demo.activityInfo.entity.ActivityInfo;
import org.jeecg.modules.demo.activityInfo.service.IActivityInfoService;

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
 * @Description: 活动信息
 * @Author: jeecg-boot
 * @Date:   2025-04-08
 * @Version: V1.0
 */
@Api(tags="活动信息")
@RestController
@RequestMapping("/activityInfo/activityInfo")
@Slf4j
public class ActivityInfoController extends JeecgController<ActivityInfo, IActivityInfoService> {
	@Autowired
	private IActivityInfoService activityInfoService;

	/**
	 * 分页列表查询
	 *
	 * @param activityInfo
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "活动信息-分页列表查询")
	@ApiOperation(value="活动信息-分页列表查询", notes="活动信息-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<ActivityInfo>> queryPageList(ActivityInfo activityInfo,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
        QueryWrapper<ActivityInfo> queryWrapper = QueryGenerator.initQueryWrapper(activityInfo, req.getParameterMap());
		Page<ActivityInfo> page = new Page<ActivityInfo>(pageNo, pageSize);
		IPage<ActivityInfo> pageList = activityInfoService.page(page, queryWrapper);
		return Result.OK(pageList);
	}

	/**
	 *   添加
	 *
	 * @param activityInfo
	 * @return
	 */
	@AutoLog(value = "活动信息-添加")
	@ApiOperation(value="活动信息-添加", notes="活动信息-添加")
	@RequiresPermissions("activityInfo:activity_info:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody ActivityInfo activityInfo) {
		activityInfoService.save(activityInfo);
		return Result.OK("添加成功！");
	}

	/**
	 *  编辑
	 *
	 * @param activityInfo
	 * @return
	 */
	@AutoLog(value = "活动信息-编辑")
	@ApiOperation(value="活动信息-编辑", notes="活动信息-编辑")
	@RequiresPermissions("activityInfo:activity_info:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody ActivityInfo activityInfo) {
		activityInfoService.updateById(activityInfo);
		return Result.OK("编辑成功!");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "活动信息-通过id删除")
	@ApiOperation(value="活动信息-通过id删除", notes="活动信息-通过id删除")
	@RequiresPermissions("activityInfo:activity_info:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		activityInfoService.removeById(id);
		return Result.OK("删除成功!");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "活动信息-批量删除")
	@ApiOperation(value="活动信息-批量删除", notes="活动信息-批量删除")
	@RequiresPermissions("activityInfo:activity_info:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.activityInfoService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "活动信息-通过id查询")
	@ApiOperation(value="活动信息-通过id查询", notes="活动信息-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<ActivityInfo> queryById(@RequestParam(name="id",required=true) String id) {
		ActivityInfo activityInfo = activityInfoService.getById(id);
		if(activityInfo==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(activityInfo);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param activityInfo
    */
    @RequiresPermissions("activityInfo:activity_info:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, ActivityInfo activityInfo) {
        return super.exportXls(request, activityInfo, ActivityInfo.class, "活动信息");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequiresPermissions("activityInfo:activity_info:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, ActivityInfo.class);
    }

}
