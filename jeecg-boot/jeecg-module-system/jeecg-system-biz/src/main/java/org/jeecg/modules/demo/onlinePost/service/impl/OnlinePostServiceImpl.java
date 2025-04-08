package org.jeecg.modules.demo.onlinePost.service.impl;

import org.jeecg.modules.demo.onlinePost.entity.OnlinePost;
import org.jeecg.modules.demo.onlinePost.mapper.OnlinePostMapper;
import org.jeecg.modules.demo.onlinePost.service.IOnlinePostService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 帖子表
 * @Author: jeecg-boot
 * @Date:   2025-04-08
 * @Version: V1.0
 */
@Service
public class OnlinePostServiceImpl extends ServiceImpl<OnlinePostMapper, OnlinePost> implements IOnlinePostService {

}
