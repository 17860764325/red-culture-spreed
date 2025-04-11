package org.jeecg.modules.demo.activityComment.service.impl;

import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.demo.activityComment.entity.ActivityComment;
import org.jeecg.modules.demo.activityComment.mapper.ActivityCommentMapper;
import org.jeecg.modules.demo.activityComment.service.IActivityCommentService;
import org.jeecg.modules.demo.courseComment.entity.CourseComment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description: 活动评论表
 * @Author: jeecg-boot
 * @Date:   2025-04-09
 * @Version: V1.0
 */
@Service
public class ActivityCommentServiceImpl extends ServiceImpl<ActivityCommentMapper, ActivityComment> implements IActivityCommentService {

    @Autowired
    private ActivityCommentMapper activityCommentMapper;

    @Override
    public Result<List<ActivityComment>> selectByActivityId(ActivityComment activityComment) {
        // 根据课程编码和章节编码查询数据
        List<ActivityComment> activityComments = activityCommentMapper.selectByActivityId(activityComment);
        // 先查询所有replyToId为0的数据，这里是找出所有一级评论
        List<ActivityComment> filterList = activityComments.stream().filter(item -> item.getReplyToId().equals("0")).collect(Collectors.toList());
        for (ActivityComment comment : filterList) {
            // 先查询所有replyToId为0的数据，这里是找出所有一级评论
            List<ActivityComment> filterByTopToIdList = activityComments.stream().filter(item -> item.getTopToId() != null && item.getTopToId().equals(comment.getId())).collect(Collectors.toList());
            List<ActivityComment> replies = new ArrayList<>();
            for (ActivityComment childComment : filterByTopToIdList) {
                // 先查询被评论的数据
                List<ActivityComment> filterByReplyToIdList = activityComments.stream().filter(item -> item.getId().equals(childComment.getReplyToId())).collect(Collectors.toList());
                if (!filterByReplyToIdList.isEmpty()) {
                    childComment.setReplyToName(filterByReplyToIdList.get(0).getReplyToName());
                }
                replies.add(childComment);
            }
            comment.setReplies(replies);
        }
        return Result.OK(filterList);
    }
}
