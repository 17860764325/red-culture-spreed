package org.jeecg.modules.demo.historicalFigure.controller;

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
import org.jeecg.modules.demo.historicalFigure.entity.HistoricalFigure;
import org.jeecg.modules.demo.historicalFigure.service.IHistoricalFigureService;

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
 * @Description: 历史人物
 * @Author: jeecg-boot
 * @Date:   2025-04-02
 * @Version: V1.0
 */
@Api(tags="历史人物")
@RestController
@RequestMapping("/historicalFigure/historicalFigure")
@Slf4j
public class HistoricalFigureController extends JeecgController<HistoricalFigure, IHistoricalFigureService> {
	@Autowired
	private IHistoricalFigureService historicalFigureService;
	
	/**
	 * 分页列表查询
	 *
	 * @param historicalFigure
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "历史人物-分页列表查询")
	@ApiOperation(value="历史人物-分页列表查询", notes="历史人物-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<HistoricalFigure>> queryPageList(HistoricalFigure historicalFigure,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
        QueryWrapper<HistoricalFigure> queryWrapper = QueryGenerator.initQueryWrapper(historicalFigure, req.getParameterMap());
		Page<HistoricalFigure> page = new Page<HistoricalFigure>(pageNo, pageSize);
		IPage<HistoricalFigure> pageList = historicalFigureService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param historicalFigure
	 * @return
	 */
	@AutoLog(value = "历史人物-添加")
	@ApiOperation(value="历史人物-添加", notes="历史人物-添加")
	@RequiresPermissions("historicalFigure:historical_figure:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody HistoricalFigure historicalFigure) {
		historicalFigureService.save(historicalFigure);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param historicalFigure
	 * @return
	 */
	@AutoLog(value = "历史人物-编辑")
	@ApiOperation(value="历史人物-编辑", notes="历史人物-编辑")
	@RequiresPermissions("historicalFigure:historical_figure:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody HistoricalFigure historicalFigure) {
		historicalFigureService.updateById(historicalFigure);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "历史人物-通过id删除")
	@ApiOperation(value="历史人物-通过id删除", notes="历史人物-通过id删除")
	@RequiresPermissions("historicalFigure:historical_figure:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		historicalFigureService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "历史人物-批量删除")
	@ApiOperation(value="历史人物-批量删除", notes="历史人物-批量删除")
	@RequiresPermissions("historicalFigure:historical_figure:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.historicalFigureService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "历史人物-通过id查询")
	@ApiOperation(value="历史人物-通过id查询", notes="历史人物-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<HistoricalFigure> queryById(@RequestParam(name="id",required=true) String id) {
		HistoricalFigure historicalFigure = historicalFigureService.getById(id);
		if(historicalFigure==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(historicalFigure);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param historicalFigure
    */
    @RequiresPermissions("historicalFigure:historical_figure:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, HistoricalFigure historicalFigure) {
        return super.exportXls(request, historicalFigure, HistoricalFigure.class, "历史人物");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequiresPermissions("historicalFigure:historical_figure:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, HistoricalFigure.class);
    }

	 /**
	  * 查询所有
	  * @return
	  */
	 @ApiOperation(value="历史人物-查询所有", notes="历史人物-查询所有")
	 @GetMapping(value = "/listAll")
	 public Result<List<HistoricalFigure>> listAll() {
		 List<HistoricalFigure> dataAll = historicalFigureService.listAll();
		 return Result.OK(dataAll);
	 }

}
