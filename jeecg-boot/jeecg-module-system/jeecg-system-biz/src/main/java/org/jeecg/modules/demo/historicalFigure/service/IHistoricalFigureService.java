package org.jeecg.modules.demo.historicalFigure.service;

import org.jeecg.modules.demo.historicalFigure.entity.HistoricalFigure;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @Description: 历史人物
 * @Author: jeecg-boot
 * @Date:   2025-04-02
 * @Version: V1.0
 */
public interface IHistoricalFigureService extends IService<HistoricalFigure> {
    /**
     * 查询所有
     * @return
     */
    List<HistoricalFigure> listAll();
}
