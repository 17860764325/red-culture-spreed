package org.jeecg.modules.demo.chapterList.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.demo.chapterList.entity.ChapterList;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.jeecg.modules.demo.objectList.entity.ObjectList;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @Description: 章节列表
 * @Author: jeecg-boot
 * @Date:   2025-02-16
 * @Version: V1.0
 */
public interface ChapterListMapper extends BaseMapper<ChapterList> {
    /**
     * 根据课程编码查询所有的章节数据
     * @param chapterList
     * @return
     */
    List<ChapterList> selectDataByObjectCode(ChapterList chapterList);

    /**
     * 查询所有的patientid不等于0的章节
     * @return
     */
    List<ChapterList> selectAllData();
}
