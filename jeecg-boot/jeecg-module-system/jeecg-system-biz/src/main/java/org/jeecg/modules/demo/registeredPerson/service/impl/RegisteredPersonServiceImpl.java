package org.jeecg.modules.demo.registeredPerson.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.demo.activityInfo.entity.ActivityInfo;
import org.jeecg.modules.demo.activityInfo.mapper.ActivityInfoMapper;
import org.jeecg.modules.demo.activityInfo.service.IActivityInfoService;
import org.jeecg.modules.demo.registeredPerson.entity.RegisteredPerson;
import org.jeecg.modules.demo.registeredPerson.mapper.RegisteredPersonMapper;
import org.jeecg.modules.demo.registeredPerson.service.IRegisteredPersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 报名人物
 * @Author: jeecg-boot
 * @Date:   2025-04-09
 * @Version: V1.0
 */
@Service
public class RegisteredPersonServiceImpl extends ServiceImpl<RegisteredPersonMapper, RegisteredPerson> implements IRegisteredPersonService {

    @Autowired
    private RegisteredPersonMapper registeredPersonMapper;

    @Autowired
    private ActivityInfoMapper activityInfoMapper;

    @Override
    public Result<Map<String, Object>> add(RegisteredPerson registeredPerson) {
        Map<String, Object> resMap = new HashMap<>(16);
        // 查询活动详情
        ActivityInfo activityInfo = activityInfoMapper.selectById(registeredPerson.getActivityId());
        // 查询该活动的报名人数
        QueryWrapper<RegisteredPerson> wrapper = new QueryWrapper<>();
        wrapper.eq("activity_id", registeredPerson.getActivityId());
        List<RegisteredPerson> personList = registeredPersonMapper.selectList(wrapper);
        String message = "";
        boolean isRegistered = false;
        if (personList.size() >= activityInfo.getMaxPeople()) {
            message = "活动报名人数已满！";
        } else {
            save(registeredPerson);
            isRegistered = true;
            message = "报名成功！";
        }
        resMap.put("message", message);
        resMap.put("isRegistered", isRegistered);
        return Result.OK(resMap);
    }
}
