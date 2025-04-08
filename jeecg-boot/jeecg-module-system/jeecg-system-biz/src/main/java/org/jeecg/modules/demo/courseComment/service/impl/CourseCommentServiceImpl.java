package org.jeecg.modules.demo.courseComment.service.impl;

import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.demo.courseComment.entity.CourseComment;
import org.jeecg.modules.demo.courseComment.mapper.CourseCommentMapper;
import org.jeecg.modules.demo.courseComment.service.ICourseCommentService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description: course_comment
 * @Author: jeecg-boot
 * @Date:   2025-04-04
 * @Version: V1.0
 */
@Service
public class CourseCommentServiceImpl extends ServiceImpl<CourseCommentMapper, CourseComment> implements ICourseCommentService {
    @Resource
    private CourseCommentMapper courseCommentMapper;

    @Override
    public Result<List<CourseComment>> selectByObjectCodeAndChapterCode(CourseComment courseComment) {
        // 根据课程编码和章节编码查询数据
        List<CourseComment> courseComments = courseCommentMapper.selectByObjectCodeAndChapterCode(courseComment);
        // 先查询所有replyToId为0的数据，这里是找出所有一级评论
        List<CourseComment> filterList = courseComments.stream().filter(item -> item.getReplyToId().equals("0")).collect(Collectors.toList());
        for (CourseComment comment : filterList) {
            // 先查询所有replyToId为0的数据，这里是找出所有一级评论
            List<CourseComment> filterByTopToIdList = courseComments.stream().filter(item -> item.getTopToId() != null && item.getTopToId().equals(comment.getId())).collect(Collectors.toList());
            List<CourseComment> replies = new ArrayList<>();
            for (CourseComment childComment : filterByTopToIdList) {
                // 先查询被评论的数据
                List<CourseComment> filterByReplyToIdList = courseComments.stream().filter(item -> item.getId().equals(childComment.getReplyToId())).collect(Collectors.toList());
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
