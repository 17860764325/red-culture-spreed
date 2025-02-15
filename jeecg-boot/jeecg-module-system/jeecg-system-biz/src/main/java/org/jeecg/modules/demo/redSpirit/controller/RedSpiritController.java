package org.jeecg.modules.demo.redSpirit.controller;

import org.jeecg.common.system.query.QueryGenerator;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.jeecg.common.system.query.QueryRuleEnum;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.common.api.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import java.util.Arrays;
import java.util.HashMap;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.demo.redSpirit.entity.PageInfo;
import org.jeecg.modules.demo.redSpirit.entity.RedSpirit;
import org.jeecg.modules.demo.redSpirit.service.IRedSpiritService;
import org.jeecg.modules.demo.redSpirit.service.IPageInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.shiro.authz.annotation.RequiresPermissions;
 /**
 * @Description: 红色精神
 * @Author: jeecg-boot
 * @Date:   2025-02-15
 * @Version: V1.0
 */
@Api(tags="红色精神")
@RestController
@RequestMapping("/redSpirit/redSpirit")
@Slf4j
public class RedSpiritController extends JeecgController<RedSpirit, IRedSpiritService> {

	@Autowired
	private IRedSpiritService redSpiritService;

	@Autowired
	private IPageInfoService pageInfoService;


	/*---------------------------------主表处理-begin-------------------------------------*/

	/**
	 * 分页列表查询
	 * @param redSpirit
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "红色精神-分页列表查询")
	@ApiOperation(value="红色精神-分页列表查询", notes="红色精神-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<RedSpirit>> queryPageList(RedSpirit redSpirit,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
      	QueryWrapper<RedSpirit> queryWrapper = QueryGenerator.initQueryWrapper(redSpirit, req.getParameterMap());
		Page<RedSpirit> page = new Page<RedSpirit>(pageNo, pageSize);
		IPage<RedSpirit> pageList = redSpiritService.page(page, queryWrapper);
		return Result.OK(pageList);
	}

	/**
     *   添加
     * @param redSpirit
     * @return
     */
    @AutoLog(value = "红色精神-添加")
    @ApiOperation(value="红色精神-添加", notes="红色精神-添加")
    @RequiresPermissions("redSpirit:red_spirit:add")
    @PostMapping(value = "/add")
    public Result<String> add(@RequestBody RedSpirit redSpirit) {
        redSpiritService.save(redSpirit);
        return Result.OK("添加成功！");
    }

    /**
     *  编辑
     * @param redSpirit
     * @return
     */
    @AutoLog(value = "红色精神-编辑")
    @ApiOperation(value="红色精神-编辑", notes="红色精神-编辑")
    @RequiresPermissions("redSpirit:red_spirit:edit")
    @RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
    public Result<String> edit(@RequestBody RedSpirit redSpirit) {
        redSpiritService.updateById(redSpirit);
        return Result.OK("编辑成功!");
    }

    /**
     * 通过id删除
     * @param id
     * @return
     */
    @AutoLog(value = "红色精神-通过id删除")
    @ApiOperation(value="红色精神-通过id删除", notes="红色精神-通过id删除")
    @RequiresPermissions("redSpirit:red_spirit:delete")
    @DeleteMapping(value = "/delete")
    public Result<String> delete(@RequestParam(name="id",required=true) String id) {
        redSpiritService.delMain(id);
        return Result.OK("删除成功!");
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @AutoLog(value = "红色精神-批量删除")
    @ApiOperation(value="红色精神-批量删除", notes="红色精神-批量删除")
    @RequiresPermissions("redSpirit:red_spirit:deleteBatch")
    @DeleteMapping(value = "/deleteBatch")
    public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
        this.redSpiritService.delBatchMain(Arrays.asList(ids.split(",")));
        return Result.OK("批量删除成功!");
    }

    /**
     * 导出
     * @return
     */
    @RequiresPermissions("redSpirit:red_spirit:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, RedSpirit redSpirit) {
        return super.exportXls(request, redSpirit, RedSpirit.class, "红色精神");
    }

    /**
     * 导入
     * @return
     */
    @RequiresPermissions("redSpirit:red_spirit:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, RedSpirit.class);
    }
	/*---------------------------------主表处理-end-------------------------------------*/
	

    /*--------------------------------子表处理-文章正文-begin----------------------------------------------*/
	/**
	 * 通过主表ID查询
	 * @return
	 */
	//@AutoLog(value = "文章正文-通过主表ID查询")
	@ApiOperation(value="文章正文-通过主表ID查询", notes="文章正文-通过主表ID查询")
	@GetMapping(value = "/listPageInfoByMainId")
    public Result<IPage<PageInfo>> listPageInfoByMainId(PageInfo pageInfo,
                                                    @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                    @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                    HttpServletRequest req) {
        QueryWrapper<PageInfo> queryWrapper = QueryGenerator.initQueryWrapper(pageInfo, req.getParameterMap());
        Page<PageInfo> page = new Page<PageInfo>(pageNo, pageSize);
        IPage<PageInfo> pageList = pageInfoService.page(page, queryWrapper);
        return Result.OK(pageList);
    }

	/**
	 * 添加
	 * @param pageInfo
	 * @return
	 */
	@AutoLog(value = "文章正文-添加")
	@ApiOperation(value="文章正文-添加", notes="文章正文-添加")
	@PostMapping(value = "/addPageInfo")
	public Result<String> addPageInfo(@RequestBody PageInfo pageInfo) {
		pageInfoService.save(pageInfo);
		return Result.OK("添加成功！");
	}

    /**
	 * 编辑
	 * @param pageInfo
	 * @return
	 */
	@AutoLog(value = "文章正文-编辑")
	@ApiOperation(value="文章正文-编辑", notes="文章正文-编辑")
	@RequestMapping(value = "/editPageInfo", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> editPageInfo(@RequestBody PageInfo pageInfo) {
		pageInfoService.updateById(pageInfo);
		return Result.OK("编辑成功!");
	}

	/**
	 * 通过id删除
	 * @param id
	 * @return
	 */
	@AutoLog(value = "文章正文-通过id删除")
	@ApiOperation(value="文章正文-通过id删除", notes="文章正文-通过id删除")
	@DeleteMapping(value = "/deletePageInfo")
	public Result<String> deletePageInfo(@RequestParam(name="id",required=true) String id) {
		pageInfoService.removeById(id);
		return Result.OK("删除成功!");
	}

	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "文章正文-批量删除")
	@ApiOperation(value="文章正文-批量删除", notes="文章正文-批量删除")
	@DeleteMapping(value = "/deleteBatchPageInfo")
	public Result<String> deleteBatchPageInfo(@RequestParam(name="ids",required=true) String ids) {
	    this.pageInfoService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}

    /**
     * 导出
     * @return
     */
    @RequestMapping(value = "/exportPageInfo")
    public ModelAndView exportPageInfo(HttpServletRequest request, PageInfo pageInfo) {
		 // Step.1 组装查询条件
		 QueryWrapper<PageInfo> queryWrapper = QueryGenerator.initQueryWrapper(pageInfo, request.getParameterMap());
		 LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

		 // Step.2 获取导出数据
		 List<PageInfo> pageList = pageInfoService.list(queryWrapper);
		 List<PageInfo> exportList = null;

		 // 过滤选中数据
		 String selections = request.getParameter("selections");
		 if (oConvertUtils.isNotEmpty(selections)) {
			 List<String> selectionList = Arrays.asList(selections.split(","));
			 exportList = pageList.stream().filter(item -> selectionList.contains(item.getId())).collect(Collectors.toList());
		 } else {
			 exportList = pageList;
		 }

		 // Step.3 AutoPoi 导出Excel
		 ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
		 //此处设置的filename无效,前端会重更新设置一下
		 mv.addObject(NormalExcelConstants.FILE_NAME, "文章正文");
		 mv.addObject(NormalExcelConstants.CLASS, PageInfo.class);
		 mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("文章正文报表", "导出人:" + sysUser.getRealname(), "文章正文"));
		 mv.addObject(NormalExcelConstants.DATA_LIST, exportList);
		 return mv;
    }

    /**
     * 导入
     * @return
     */
    @RequestMapping(value = "/importPageInfo/{mainId}")
    public Result<?> importPageInfo(HttpServletRequest request, HttpServletResponse response, @PathVariable("mainId") String mainId) {
		 MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		 Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		 for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
       // 获取上传文件对象
			 MultipartFile file = entity.getValue();
			 ImportParams params = new ImportParams();
			 params.setTitleRows(2);
			 params.setHeadRows(1);
			 params.setNeedSave(true);
			 try {
				 List<PageInfo> list = ExcelImportUtil.importExcel(file.getInputStream(), PageInfo.class, params);
				 for (PageInfo temp : list) {
                    temp.setPageNumber(mainId);
				 }
				 long start = System.currentTimeMillis();
				 pageInfoService.saveBatch(list);
				 log.info("消耗时间" + (System.currentTimeMillis() - start) + "毫秒");
				 return Result.OK("文件导入成功！数据行数：" + list.size());
			 } catch (Exception e) {
				 log.error(e.getMessage(), e);
				 return Result.error("文件导入失败:" + e.getMessage());
			 } finally {
				 try {
					 file.getInputStream().close();
				 } catch (IOException e) {
					 e.printStackTrace();
				 }
			 }
		 }
		 return Result.error("文件导入失败！");
    }

    /*--------------------------------子表处理-文章正文-end----------------------------------------------*/




}
