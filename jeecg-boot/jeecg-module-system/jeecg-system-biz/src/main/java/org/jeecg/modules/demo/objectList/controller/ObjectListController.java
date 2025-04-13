package org.jeecg.modules.demo.objectList.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.query.QueryRuleEnum;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.demo.chapterList.entity.ChapterList;
import org.jeecg.modules.demo.chapterList.mapper.ChapterListMapper;
import org.jeecg.modules.demo.courseComment.entity.CourseComment;
import org.jeecg.modules.demo.courseComment.mapper.CourseCommentMapper;
import org.jeecg.modules.demo.objectList.entity.ObjectList;
import org.jeecg.modules.demo.objectList.service.IObjectListService;

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
 * @Description: 课程列表
 * @Author: jeecg-boot
 * @Date:   2025-02-15
 * @Version: V1.0
 */
@Api(tags="课程列表")
@RestController
@RequestMapping("/objectList/objectList")
@Slf4j
public class ObjectListController extends JeecgController<ObjectList, IObjectListService> {
	@Autowired
	private IObjectListService objectListService;
	@Autowired
	private ChapterListMapper chapterListMapper;
	@Autowired
	private CourseCommentMapper courseCommentMapper;
	
	/**
	 * 分页列表查询
	 *
	 * @param objectList
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "课程列表-分页列表查询")
	@ApiOperation(value="课程列表-分页列表查询", notes="课程列表-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<ObjectList>> queryPageList(ObjectList objectList,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
        QueryWrapper<ObjectList> queryWrapper = QueryGenerator.initQueryWrapper(objectList, req.getParameterMap());
		Page<ObjectList> page = new Page<ObjectList>(pageNo, pageSize);
		IPage<ObjectList> pageList = objectListService.page(page, queryWrapper);
		// 查询所有的parentId不等于0的章节
		List<ChapterList> chapterLists = chapterListMapper.selectAllData();
		for (ObjectList record : pageList.getRecords()) {
			// 查询属于该课程的章节
			List<ChapterList> objectListFilterList =
					chapterLists.stream().filter(item -> item.getObjectCode().equals(record.getObjectCode())).collect(Collectors.toList());

			// 计算课件学习字段
			// 查询该课程已经看过的章节
			List<ChapterList> objectFilterList =
					objectListFilterList.stream().filter(item -> "true".equals(item.getWatch())).collect(Collectors.toList());
			String coursewareLearning = objectFilterList.size() + "/" + objectListFilterList.size();
			record.setCoursewareLearning(coursewareLearning);

			// 计算学习总时长字段
			// 记录视频总时长
			BigDecimal totalWatchTime = BigDecimal.ZERO;
			// 记录视频观看时长
			BigDecimal watchTime = BigDecimal.ZERO;
			for (ChapterList chapterList : objectListFilterList) {
				if (chapterList.getTotalWatchTime() != null) {
					// 统计所有视频的观看总时长，并保留两位小数，使用四舍五入的舍入模式
					totalWatchTime = totalWatchTime.add(BigDecimal.valueOf(chapterList.getTotalWatchTime()).setScale(2, RoundingMode.HALF_UP));
				}
				if (chapterList.getWatchTime() != null) {
					// 统计所有视频的观看时长，并保留两位小数，使用四舍五入的舍入模式
					watchTime = watchTime.add(BigDecimal.valueOf(chapterList.getWatchTime()).setScale(2, RoundingMode.HALF_UP));
				}
			}
			// 转换成正数
//			long totalWatchTimeLong = totalWatchTime.setScale(0, RoundingMode.HALF_UP).longValue();
//			long watchTimeLong = watchTime.setScale(0, RoundingMode.HALF_UP).longValue();
			String totalTime = watchTime + "/" + totalWatchTime;
			record.setTotalTime(totalTime);

			// 计算发帖条数
			// 根据课程编码和章节编码查询数据
			CourseComment courseComment = new CourseComment();
			courseComment.setObjectCode(record.getObjectCode());
			List<CourseComment> courseComments = courseCommentMapper.selectByObjectCodeAndChapterCode(courseComment);
			// 先查询所有replyToId为0的数据，这里是找出所有一级评论
			List<CourseComment> filterList = courseComments.stream().filter(item -> item.getReplyToId().equals("0")).collect(Collectors.toList());
			String postNumber = String.valueOf(filterList.size());
			record.setPostNumber(postNumber);
		}
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param objectList
	 * @return
	 */
	@AutoLog(value = "课程列表-添加")
	@ApiOperation(value="课程列表-添加", notes="课程列表-添加")
	@RequiresPermissions("objectList:object_list:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody ObjectList objectList) {
		objectListService.save(objectList);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param objectList
	 * @return
	 */
	@AutoLog(value = "课程列表-编辑")
	@ApiOperation(value="课程列表-编辑", notes="课程列表-编辑")
	@RequiresPermissions("objectList:object_list:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody ObjectList objectList) {
		objectListService.updateById(objectList);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "课程列表-通过id删除")
	@ApiOperation(value="课程列表-通过id删除", notes="课程列表-通过id删除")
	@RequiresPermissions("objectList:object_list:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		objectListService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "课程列表-批量删除")
	@ApiOperation(value="课程列表-批量删除", notes="课程列表-批量删除")
	@RequiresPermissions("objectList:object_list:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.objectListService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "课程列表-通过id查询")
	@ApiOperation(value="课程列表-通过id查询", notes="课程列表-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<ObjectList> queryById(@RequestParam(name="id",required=true) String id) {
		ObjectList objectList = objectListService.getById(id);
		if(objectList==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(objectList);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param objectList
    */
    @RequiresPermissions("objectList:object_list:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, ObjectList objectList) {
        return super.exportXls(request, objectList, ObjectList.class, "课程列表");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequiresPermissions("objectList:object_list:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, ObjectList.class);
    }

}
