package org.jeecg.modules.demo.chapterList.service;

import io.swagger.annotations.ApiOperation;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.modules.demo.chapterList.entity.ObjectData;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * @Description: 课程资料
 * @Author: jeecg-boot
 * @Date:   2025-02-16
 * @Version: V1.0
 */
public interface IObjectDataService extends IService<ObjectData> {

  /**
   * 通过主表id查询子表数据
   *
   * @param mainId
   * @return List<ObjectData>
   */
	public List<ObjectData> selectByMainId(String mainId);

    /**
     * 编辑
     * @param objectData
     * @return
     */
    Result<String> editObjectData(ObjectData objectData);

    /**
     * 添加
     * @param objectData
     * @return
     */
    Result<String> addObjectData(@RequestBody ObjectData objectData);
}
