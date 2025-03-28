package org.jeecg.modules.demo.chapterList.service;

import org.jeecg.modules.demo.chapterList.entity.ObjectData;
import org.jeecg.modules.demo.chapterList.entity.ChapterList;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @Description: 章节列表
 * @Author: jeecg-boot
 * @Date:   2025-02-16
 * @Version: V1.0
 */
public interface IChapterListService extends IService<ChapterList> {

	/**
	 * 删除一对多
	 *
	 * @param id
	 */
	public void delMain (String id);
	
	/**
	 * 批量删除一对多
	 *
	 * @param idList
	 */
	public void delBatchMain (Collection<? extends Serializable> idList);


}
