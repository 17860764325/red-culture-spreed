package org.jeecg.modules.demo.courseExam.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.demo.courseExam.entity.CourseExam;
import org.jeecg.modules.demo.courseExam.mapper.CourseExamMapper;
import org.jeecg.modules.demo.courseExam.service.ICourseExamService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description: course_exam
 * @Author: jeecg-boot
 * @Date:   2025-04-05
 * @Version: V1.0
 */
@Service
public class CourseExamServiceImpl extends ServiceImpl<CourseExamMapper, CourseExam> implements ICourseExamService {

    @Resource
    private CourseExamMapper courseExamMapper;

    @Override
    public Result<List<CourseExam>> randomQuery(CourseExam courseExam) {
        List<CourseExam> courseExamList = courseExamMapper.randomQuery(courseExam);
        for (CourseExam exam : courseExamList) {
            List<String> optionList = new ArrayList<>();
            if (!StringUtils.isEmpty(exam.getOptionA())) {
                optionList.add(exam.getOptionA());
            }
            if (!StringUtils.isEmpty(exam.getOptionB())) {
                optionList.add(exam.getOptionB());
            }
            if (!StringUtils.isEmpty(exam.getOptionC())) {
                optionList.add(exam.getOptionC());
            }
            if (!StringUtils.isEmpty(exam.getOptionD())) {
                optionList.add(exam.getOptionD());
            }
            if (!optionList.isEmpty()) {
                exam.setOptions(optionList);
            }
        }
        return Result.OK(courseExamList);
    }
}
