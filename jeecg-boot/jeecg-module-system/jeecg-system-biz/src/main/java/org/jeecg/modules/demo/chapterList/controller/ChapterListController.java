package org.jeecg.modules.demo.chapterList.controller;

import org.jeecg.common.system.query.QueryGenerator;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.jeecg.common.system.query.QueryRuleEnum;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.demo.objectList.entity.ObjectList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.demo.chapterList.entity.ObjectData;
import org.jeecg.modules.demo.chapterList.entity.ChapterList;
import org.jeecg.modules.demo.chapterList.service.IChapterListService;
import org.jeecg.modules.demo.chapterList.service.IObjectDataService;
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
import java.util.stream.Collectors;
import org.apache.shiro.authz.annotation.RequiresPermissions;
 /**
 * @Description: 章节列表
 * @Author: jeecg-boot
 * @Date:   2025-02-16
 * @Version: V1.0
 */
@Api(tags="章节列表")
@RestController
@RequestMapping("/chapterList/chapterList")
@Slf4j
public class ChapterListController extends JeecgController<ChapterList, IChapterListService> {

	@Autowired
	private IChapterListService chapterListService;

	@Autowired
	private IObjectDataService objectDataService;

	@Value("${jeecg.path.upload}")
	private String filePath;


	/*---------------------------------主表处理-begin-------------------------------------*/

	/**
	 * 分页列表查询
	 * @param chapterList
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "章节列表-分页列表查询")
	@ApiOperation(value="章节列表-分页列表查询", notes="章节列表-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<ChapterList>> queryPageList(ChapterList chapterList,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
      	QueryWrapper<ChapterList> queryWrapper = QueryGenerator.initQueryWrapper(chapterList, req.getParameterMap());
		Page<ChapterList> page = new Page<ChapterList>(pageNo, pageSize);
		IPage<ChapterList> pageList = chapterListService.page(page, queryWrapper);
		return Result.OK(pageList);
	}

	/**
     *   添加
     * @param chapterList
     * @return
     */
    @AutoLog(value = "章节列表-添加")
    @ApiOperation(value="章节列表-添加", notes="章节列表-添加")
    @RequiresPermissions("chapterList:chapter_list:add")
    @PostMapping(value = "/add")
    public Result<String> add(@RequestBody ChapterList chapterList) {
        chapterListService.save(chapterList);
        return Result.OK("添加成功！");
    }

    /**
     *  编辑
     * @param chapterList
     * @return
     */
    @AutoLog(value = "章节列表-编辑")
    @ApiOperation(value="章节列表-编辑", notes="章节列表-编辑")
    @RequiresPermissions("chapterList:chapter_list:edit")
    @RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
    public Result<String> edit(@RequestBody ChapterList chapterList) {
        chapterListService.updateById(chapterList);
        return Result.OK("编辑成功!");
    }

    /**
     * 通过id删除
     * @param id
     * @return
     */
    @AutoLog(value = "章节列表-通过id删除")
    @ApiOperation(value="章节列表-通过id删除", notes="章节列表-通过id删除")
    @RequiresPermissions("chapterList:chapter_list:delete")
    @DeleteMapping(value = "/delete")
    public Result<String> delete(@RequestParam(name="id",required=true) String id) {
        chapterListService.delMain(id);
        return Result.OK("删除成功!");
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @AutoLog(value = "章节列表-批量删除")
    @ApiOperation(value="章节列表-批量删除", notes="章节列表-批量删除")
    @RequiresPermissions("chapterList:chapter_list:deleteBatch")
    @DeleteMapping(value = "/deleteBatch")
    public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
        this.chapterListService.delBatchMain(Arrays.asList(ids.split(",")));
        return Result.OK("批量删除成功!");
    }

    /**
     * 导出
     * @return
     */
    @RequiresPermissions("chapterList:chapter_list:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, ChapterList chapterList) {
        return super.exportXls(request, chapterList, ChapterList.class, "章节列表");
    }

    /**
     * 导入
     * @return
     */
    @RequiresPermissions("chapterList:chapter_list:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, ChapterList.class);
    }
	/*---------------------------------主表处理-end-------------------------------------*/
	

    /*--------------------------------子表处理-课程资料-begin----------------------------------------------*/
	/**
	 * 通过主表ID查询
	 * @return
	 */
	//@AutoLog(value = "课程资料-通过主表ID查询")
	@ApiOperation(value="课程资料-通过主表ID查询", notes="课程资料-通过主表ID查询")
	@GetMapping(value = "/listObjectDataByMainId")
    public Result<IPage<ObjectData>> listObjectDataByMainId(ObjectData objectData,
                                                    @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                    @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                    HttpServletRequest req) {
        QueryWrapper<ObjectData> queryWrapper = QueryGenerator.initQueryWrapper(objectData, req.getParameterMap());
        Page<ObjectData> page = new Page<ObjectData>(pageNo, pageSize);
        IPage<ObjectData> pageList = objectDataService.page(page, queryWrapper);
        return Result.OK(pageList);
    }

	/**
	 * 添加
	 * @param objectData
	 * @return
	 */
	@AutoLog(value = "课程资料-添加")
	@ApiOperation(value="课程资料-添加", notes="课程资料-添加")
	@PostMapping(value = "/addObjectData")
	public Result<String> addObjectData(@RequestBody ObjectData objectData) {
//		objectDataService.save(objectData);
		objectDataService.addObjectData(objectData);
		return Result.OK("添加成功！");
	}

    /**
	 * 编辑
	 * @param objectData
	 * @return
	 */
	@AutoLog(value = "课程资料-编辑")
	@ApiOperation(value="课程资料-编辑", notes="课程资料-编辑")
	@RequestMapping(value = "/editObjectData", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> editObjectData(@RequestBody ObjectData objectData) {
//		objectDataService.updateById(objectData);
		objectDataService.editObjectData(objectData);
		return Result.OK("编辑成功!");
	}

	 /**
	  * 编辑视频内容(无返回信息)
	  * @param objectData
	  * @return
	  */
	 @AutoLog(value = "课程资料视频-编辑")
	 @RequestMapping(value = "/editObjectDataWatchTime", method = {RequestMethod.PUT,RequestMethod.POST})
	 public Result<String> editObjectDataWatchTime(@RequestBody ObjectData objectData) {
		 objectDataService.editObjectData(objectData);
		 return Result.OK();
	 }

	/**
	 * 通过id删除
	 * @param id
	 * @return
	 */
	@AutoLog(value = "课程资料-通过id删除")
	@ApiOperation(value="课程资料-通过id删除", notes="课程资料-通过id删除")
	@DeleteMapping(value = "/deleteObjectData")
	public Result<String> deleteObjectData(@RequestParam(name="id",required=true) String id) {
		objectDataService.removeById(id);
		return Result.OK("删除成功!");
	}

	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "课程资料-批量删除")
	@ApiOperation(value="课程资料-批量删除", notes="课程资料-批量删除")
	@DeleteMapping(value = "/deleteBatchObjectData")
	public Result<String> deleteBatchObjectData(@RequestParam(name="ids",required=true) String ids) {
	    this.objectDataService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}

    /**
     * 导出
     * @return
     */
    @RequestMapping(value = "/exportObjectData")
    public ModelAndView exportObjectData(HttpServletRequest request, ObjectData objectData) {
		 // Step.1 组装查询条件
		 QueryWrapper<ObjectData> queryWrapper = QueryGenerator.initQueryWrapper(objectData, request.getParameterMap());
		 LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

		 // Step.2 获取导出数据
		 List<ObjectData> pageList = objectDataService.list(queryWrapper);
		 List<ObjectData> exportList = null;

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
		 mv.addObject(NormalExcelConstants.FILE_NAME, "课程资料");
		 mv.addObject(NormalExcelConstants.CLASS, ObjectData.class);
		 mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("课程资料报表", "导出人:" + sysUser.getRealname(), "课程资料"));
		 mv.addObject(NormalExcelConstants.DATA_LIST, exportList);
		 return mv;
    }

    /**
     * 导入
     * @return
     */
    @RequestMapping(value = "/importObjectData/{mainId}")
    public Result<?> importObjectData(HttpServletRequest request, HttpServletResponse response, @PathVariable("mainId") String mainId) {
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
				 List<ObjectData> list = ExcelImportUtil.importExcel(file.getInputStream(), ObjectData.class, params);
				 for (ObjectData temp : list) {
                    temp.setChapterCode(mainId);
				 }
				 long start = System.currentTimeMillis();
				 objectDataService.saveBatch(list);
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

    /*--------------------------------子表处理-课程资料-end----------------------------------------------*/


	 /**
	  * 根据课程编码查询所有的章节数据
	  * @param chapterList
	  * @return
	  */
	 @RequestMapping(value = "/selectDataByObjectCode", method = RequestMethod.POST)
	 public Result<List<ChapterList>> selectDataByObjectCode(@RequestBody ChapterList chapterList) {
		 return chapterListService.selectDataByObjectCode(chapterList);
	 }

	 /**
	  * 根据课程编码更新课程
	  * @param chapterList
	  * @return
	  */
	 @RequestMapping(value = "/updateWatch", method = RequestMethod.POST)
	 public Result<ChapterList> updateWatch(@RequestBody ChapterList chapterList) {
		 chapterListService.updateWatch(chapterList);
		 return Result.OK(chapterList);
	 }


}
