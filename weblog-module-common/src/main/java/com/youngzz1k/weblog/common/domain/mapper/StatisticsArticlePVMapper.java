package com.youngzz1k.weblog.common.domain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.youngzz1k.weblog.common.domain.dos.StatisticsArticlePVDO;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface StatisticsArticlePVMapper extends BaseMapper<StatisticsArticlePVDO> {
    /**
     * 对指定日期的文章 PV 访问量进行 +1
     * @param date
     * @return
     */
    default int increasePVCount(LocalDate date) {
        return update(null, Wrappers.<StatisticsArticlePVDO>lambdaUpdate()
                .setSql("pv_count = pv_count + 1")
                .eq(StatisticsArticlePVDO::getPvDate, date));
    }

    /**
     * 查询最近一周的文章 PV 访问量记录
     * @return
     */
    default List<StatisticsArticlePVDO> selectLatestWeekRecords() {
        return selectList(Wrappers.<StatisticsArticlePVDO>lambdaQuery()
                .le(StatisticsArticlePVDO::getPvDate, LocalDate.now()) // 小于等于当前日期
                .orderByDesc(StatisticsArticlePVDO::getUpdateTime)// 根据更新时间降序
                .last("limit 7")// 仅查询七条
                );
    }
}