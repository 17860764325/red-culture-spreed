package org.jeecg.modules.demo.courseExam.service;

import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.demo.courseExam.entity.CourseExam;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @Description: course_exam
 * @Author: jeecg-boot
 * @Date:   2025-04-05
 * @Version: V1.0
 */
public interface ICourseExamService extends IService<CourseExam> {

    /**
     * 随机查询(查询50条)
     *
     * @param courseExam
     * @return
     */
    Result<List<CourseExam>> randomQuery(@RequestBody CourseExam courseExam);
}
