package org.jeecg.modules.demo.redSpirit.service.impl;

import org.jeecg.modules.demo.redSpirit.entity.RedSpirit;
import org.jeecg.modules.demo.redSpirit.entity.PageInfo;
import org.jeecg.modules.demo.redSpirit.mapper.PageInfoMapper;
import org.jeecg.modules.demo.redSpirit.mapper.RedSpiritMapper;
import org.jeecg.modules.demo.redSpirit.service.IRedSpiritService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.io.Serializable;
import java.util.List;
import java.util.Collection;

/**
 * @Description: 红色精神
 * @Author: jeecg-boot
 * @Date:   2025-02-15
 * @Version: V1.0
 */
@Service
public class RedSpiritServiceImpl extends ServiceImpl<RedSpiritMapper, RedSpirit> implements IRedSpiritService {

	@Autowired
	private RedSpiritMapper redSpiritMapper;
	@Autowired
	private PageInfoMapper pageInfoMapper;
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void delMain(String id) {
		pageInfoMapper.deleteByMainId(id);
		redSpiritMapper.deleteById(id);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void delBatchMain(Collection<? extends Serializable> idList) {
		for(Serializable id:idList) {
			pageInfoMapper.deleteByMainId(id.toString());
			redSpiritMapper.deleteById(id);
		}
	}
	
}
