package org.jeecg.modules.demo.chapterList.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FrameGrabber;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.demo.chapterList.entity.ObjectData;
import org.jeecg.modules.demo.chapterList.mapper.ObjectDataMapper;
import org.jeecg.modules.demo.chapterList.service.IObjectDataService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description: 课程资料
 * @Author: jeecg-boot
 * @Date:   2025-02-16
 * @Version: V1.0
 */
@Service
public class ObjectDataServiceImpl extends ServiceImpl<ObjectDataMapper, ObjectData> implements IObjectDataService {
	
	@Autowired
	private ObjectDataMapper objectDataMapper;
	@Value("${jeecg.path.upload}")
	private String filePath;
	
	@Override
	public List<ObjectData> selectByMainId(String mainId) {
		return objectDataMapper.selectByMainId(mainId);
	}

	@Override
	public Result<String> addObjectData(ObjectData objectData) {
		ObjectData selectData = objectDataMapper.selectById(objectData.getId());
		// 视频时长处理, 判断视频链接是否改变，如果改变就重新计算总时长
		if (!StringUtils.isEmpty(objectData.getVedio()) && !objectData.getVedio().equals(selectData.getVedio())) {
			// 获取视频文件路径
			String vedioPath = filePath + "\\" + objectData.getVedio();
			double totalDuration = getTotalVideoDuration(vedioPath);
			objectData.setTotalWatchTime(totalDuration);
			objectData.setWatchTime(0d);
		}
		save(objectData);
		return Result.OK("添加成功！");
	}

	@Override
	public Result<String> editObjectData(ObjectData objectData) {
		ObjectData selectData = objectDataMapper.selectById(objectData.getId());
		// 视频时长处理, 判断视频链接是否改变，如果改变就重新计算总时长
		if (!StringUtils.isEmpty(objectData.getVedio()) && !objectData.getVedio().equals(selectData.getVedio())) {
			// 获取视频文件路径
			String vedioPath = filePath + "\\" + objectData.getVedio();
			double totalDuration = getTotalVideoDuration(vedioPath);
			objectData.setTotalWatchTime(totalDuration);
			objectData.setWatchTime(0d);
		}
		if (objectData.getWatchTime() != null) {
			// 将观看时长转换成分钟
			// 一分钟有 60 秒, 为了避免精度丢失，这里准换成BigDecimal类型进行转换
			BigDecimal divisor = new BigDecimal(60);
			BigDecimal secondsBigDecimal = new BigDecimal(objectData.getWatchTime());
			// 进行除法运算，保留适当的小数位数，这里选择保留 6 位小数
			BigDecimal secondsDivBigDecimal = secondsBigDecimal.divide(divisor, 6, RoundingMode.HALF_UP);
			objectData.setWatchTime(Double.valueOf(String.valueOf(secondsDivBigDecimal)));
		}
		updateById(objectData);
		return Result.OK("编辑成功!");
	}

	// 获取视频的总时长
	public double getTotalVideoDuration(String videoPath) {
		// 创建 FFmpegFrameGrabber对象
		FFmpegFrameGrabber grabber = null;
		try {
			grabber = new FFmpegFrameGrabber(videoPath);
			// 启动FFmpegFrameGrabber对象
			grabber.start();
			// 通过getLengthInTime() 方法获取视频时长，单位为微秒。
			long durationInMicroseconds = grabber.getLengthInTime();
			// 将微秒转换为分钟
			// 一分钟有 60 秒，一秒有 1000000 微秒, 为了避免精度丢失，这里准换成BigDecimal类型进行转换
			BigDecimal divisor = new BigDecimal(60 * 1000000);
			BigDecimal microsecondsBigDecimal = new BigDecimal(durationInMicroseconds);
			// 进行除法运算，保留适当的小数位数，这里选择保留 6 位小数
			BigDecimal microsecondsDivBigDecimal = microsecondsBigDecimal.divide(divisor, 6, RoundingMode.HALF_UP);
			double durationInMinutes = Double.parseDouble(String.valueOf(microsecondsDivBigDecimal));
			return durationInMinutes;
		} catch (FrameGrabber.Exception e) {
			System.err.println("获取视频时长时出现错误: " + e.getMessage());
			return -1;
		} finally {
			if (grabber != null) {
				try {
					grabber.stop();
				} catch (FrameGrabber.Exception e) {
					System.err.println("关闭 FFmpegFrameGrabber 时出现错误: " + e.getMessage());
				}
			}
		}
	}
}
