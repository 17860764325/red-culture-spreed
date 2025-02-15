package org.jeecg.modules.demo.redSpirit.mapper;

import java.util.List;
import org.jeecg.modules.demo.redSpirit.entity.PageInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Description: 文章正文
 * @Author: jeecg-boot
 * @Date:   2025-02-15
 * @Version: V1.0
 */
public interface PageInfoMapper extends BaseMapper<PageInfo> {

	/**
	 * 通过主表id删除子表数据
	 *
	 * @param mainId 主表id
	 * @return boolean
	 */
	public boolean deleteByMainId(@Param("mainId") String mainId);

   /**
    * 通过主表id查询子表数据
    *
    * @param mainId 主表id
    * @return List<PageInfo>
    */
	public List<PageInfo> selectByMainId(@Param("mainId") String mainId);

}
