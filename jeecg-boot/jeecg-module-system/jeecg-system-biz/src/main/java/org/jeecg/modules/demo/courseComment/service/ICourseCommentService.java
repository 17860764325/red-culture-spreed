package org.jeecg.modules.demo.courseComment.service;

import io.swagger.annotations.ApiOperation;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.modules.demo.courseComment.entity.CourseComment;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @Description: course_comment
 * @Author: jeecg-boot
 * @Date:   2025-04-04
 * @Version: V1.0
 */
public interface ICourseCommentService extends IService<CourseComment> {
    /**
     *  根据课程编码和章节编码查询所有评论
     *
     * @param courseComment
     * @return
     */
    Result<List<CourseComment>> selectByObjectCodeAndChapterCode(CourseComment courseComment);
}
