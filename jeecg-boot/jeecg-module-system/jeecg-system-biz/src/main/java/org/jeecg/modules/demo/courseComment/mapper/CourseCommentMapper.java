package org.jeecg.modules.demo.courseComment.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.demo.courseComment.entity.CourseComment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: course_comment
 * @Author: jeecg-boot
 * @Date:   2025-04-04
 * @Version: V1.0
 */
public interface CourseCommentMapper extends BaseMapper<CourseComment> {

    /**
     *  根据课程编码和章节编码查询所有评论
     *
     * @param courseComment
     * @return
     */
    List<CourseComment> selectByObjectCodeAndChapterCode(CourseComment courseComment);
}
