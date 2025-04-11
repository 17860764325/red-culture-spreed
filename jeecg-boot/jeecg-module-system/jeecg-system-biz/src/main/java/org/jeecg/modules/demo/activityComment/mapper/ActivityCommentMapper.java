package org.jeecg.modules.demo.activityComment.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.demo.activityComment.entity.ActivityComment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.jeecg.modules.demo.courseComment.entity.CourseComment;

/**
 * @Description: 活动评论表
 * @Author: jeecg-boot
 * @Date:   2025-04-09
 * @Version: V1.0
 */
public interface ActivityCommentMapper extends BaseMapper<ActivityComment> {

    /**
     *  根据课活动ID查询所有评论
     *
     * @param activityComment
     * @return
     */
    List<ActivityComment> selectByActivityId(ActivityComment activityComment);
}
