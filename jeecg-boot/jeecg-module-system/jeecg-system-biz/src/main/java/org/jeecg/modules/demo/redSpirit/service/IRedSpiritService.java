package org.jeecg.modules.demo.redSpirit.service;

import org.jeecg.modules.demo.redSpirit.entity.PageInfo;
import org.jeecg.modules.demo.redSpirit.entity.RedSpirit;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @Description: 红色精神
 * @Author: jeecg-boot
 * @Date:   2025-02-15
 * @Version: V1.0
 */
public interface IRedSpiritService extends IService<RedSpirit> {

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
