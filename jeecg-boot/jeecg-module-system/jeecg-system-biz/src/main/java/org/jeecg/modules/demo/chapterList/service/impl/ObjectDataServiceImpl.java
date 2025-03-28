package org.jeecg.modules.demo.chapterList.service.impl;

import org.jeecg.modules.demo.chapterList.entity.ObjectData;
import org.jeecg.modules.demo.chapterList.mapper.ObjectDataMapper;
import org.jeecg.modules.demo.chapterList.service.IObjectDataService;
import org.springframework.stereotype.Service;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description: 课程资料
 * @Author: jeecg-boot
 * @Date:   2025-02-16
 * @Version: V1.0
 */
@Service
public class ObjectDataServiceImpl extends ServiceImpl<ObjectDataMapper, ObjectData> implements IObjectDataService {
	
	@Autowired
	private ObjectDataMapper objectDataMapper;
	
	@Override
	public List<ObjectData> selectByMainId(String mainId) {
		return objectDataMapper.selectByMainId(mainId);
	}
}
