package org.jeecg.modules.demo.historicalFigure.service.impl;

import org.jeecg.modules.demo.historicalFigure.entity.HistoricalFigure;
import org.jeecg.modules.demo.historicalFigure.mapper.HistoricalFigureMapper;
import org.jeecg.modules.demo.historicalFigure.service.IHistoricalFigureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

/**
 * @Description: 历史人物
 * @Author: jeecg-boot
 * @Date:   2025-04-02
 * @Version: V1.0
 */
@Service
public class HistoricalFigureServiceImpl extends ServiceImpl<HistoricalFigureMapper, HistoricalFigure> implements IHistoricalFigureService {

    @Autowired
    private HistoricalFigureMapper historicalFigureMapper;

    @Override
    public List<HistoricalFigure> listAll() {
        return historicalFigureMapper.listAll();
    }
}
