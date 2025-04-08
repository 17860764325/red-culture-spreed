package org.jeecg.modules.demo.historicalFigure.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.demo.historicalFigure.entity.HistoricalFigure;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: 历史人物
 * @Author: jeecg-boot
 * @Date:   2025-04-02
 * @Version: V1.0
 */
public interface HistoricalFigureMapper extends BaseMapper<HistoricalFigure> {
    /**
     * 查询所有
     * @return
     */
    List<HistoricalFigure> listAll();
}
