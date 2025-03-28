package org.jeecg.modules.demo.chapterList.service.impl;

import org.jeecg.modules.demo.chapterList.entity.ChapterList;
import org.jeecg.modules.demo.chapterList.entity.ObjectData;
import org.jeecg.modules.demo.chapterList.mapper.ObjectDataMapper;
import org.jeecg.modules.demo.chapterList.mapper.ChapterListMapper;
import org.jeecg.modules.demo.chapterList.service.IChapterListService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.io.Serializable;
import java.util.List;
import java.util.Collection;

/**
 * @Description: 章节列表
 * @Author: jeecg-boot
 * @Date:   2025-02-16
 * @Version: V1.0
 */
@Service
public class ChapterListServiceImpl extends ServiceImpl<ChapterListMapper, ChapterList> implements IChapterListService {

	@Autowired
	private ChapterListMapper chapterListMapper;
	@Autowired
	private ObjectDataMapper objectDataMapper;
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void delMain(String id) {
		objectDataMapper.deleteByMainId(id);
		chapterListMapper.deleteById(id);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void delBatchMain(Collection<? extends Serializable> idList) {
		for(Serializable id:idList) {
			objectDataMapper.deleteByMainId(id.toString());
			chapterListMapper.deleteById(id);
		}
	}
	
}
