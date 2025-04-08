package org.jeecg.modules.demo.courseExam.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.demo.courseExam.entity.CourseExam;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @Description: course_exam
 * @Author: jeecg-boot
 * @Date:   2025-04-05
 * @Version: V1.0
 */
public interface CourseExamMapper extends BaseMapper<CourseExam> {

    /**
     * 随机查询(查询50条)
     *
     * @param courseExam
     * @return
     */
    List<CourseExam> randomQuery(@RequestBody CourseExam courseExam);
}
