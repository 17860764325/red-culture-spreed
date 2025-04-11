package org.jeecg.modules.demo.registeredPerson.service;

import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.demo.registeredPerson.entity.RegisteredPerson;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * @Description: 报名人物
 * @Author: jeecg-boot
 * @Date:   2025-04-09
 * @Version: V1.0
 */
public interface IRegisteredPersonService extends IService<RegisteredPerson> {

    /**
     * 活动报名
     * @param registeredPerson
     * @return
     */
    Result<Map<String, Object>> add(RegisteredPerson registeredPerson);
}
