package org.jeecg.modules.demo.redSpirit.service;

import org.jeecg.modules.demo.redSpirit.entity.PageInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * @Description: 文章正文
 * @Author: jeecg-boot
 * @Date:   2025-02-15
 * @Version: V1.0
 */
public interface IPageInfoService extends IService<PageInfo> {

  /**
   * 通过主表id查询子表数据
   *
   * @param mainId
   * @return List<PageInfo>
   */
	public List<PageInfo> selectByMainId(String mainId);
}
