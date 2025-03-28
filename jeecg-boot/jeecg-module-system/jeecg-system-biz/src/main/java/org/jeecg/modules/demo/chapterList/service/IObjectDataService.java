package org.jeecg.modules.demo.chapterList.service;

import org.jeecg.modules.demo.chapterList.entity.ObjectData;
import com.baomidou.mybatisplus.extension.service.IService;
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
}
