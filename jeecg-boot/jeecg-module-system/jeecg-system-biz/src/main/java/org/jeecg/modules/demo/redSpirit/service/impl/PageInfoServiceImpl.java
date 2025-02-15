package org.jeecg.modules.demo.redSpirit.service.impl;

import org.jeecg.modules.demo.redSpirit.entity.PageInfo;
import org.jeecg.modules.demo.redSpirit.mapper.PageInfoMapper;
import org.jeecg.modules.demo.redSpirit.service.IPageInfoService;
import org.springframework.stereotype.Service;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description: 文章正文
 * @Author: jeecg-boot
 * @Date:   2025-02-15
 * @Version: V1.0
 */
@Service
public class PageInfoServiceImpl extends ServiceImpl<PageInfoMapper, PageInfo> implements IPageInfoService {
	
	@Autowired
	private PageInfoMapper pageInfoMapper;
	
	@Override
	public List<PageInfo> selectByMainId(String mainId) {
		return pageInfoMapper.selectByMainId(mainId);
	}
}
