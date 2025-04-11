package org.jeecg.modules.demo.activityComment.service;

import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.demo.activityComment.entity.ActivityComment;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.demo.courseComment.entity.CourseComment;

import java.util.List;

/**
 * @Description: 活动评论表
 * @Author: jeecg-boot
 * @Date:   2025-04-09
 * @Version: V1.0
 */
public interface IActivityCommentService extends IService<ActivityComment> {

    /**
     *  根据活动ID查询所有评论
     *
     * @param activityComment
     * @return
     */
    Result<List<ActivityComment>> selectByActivityId(ActivityComment activityComment);
}
