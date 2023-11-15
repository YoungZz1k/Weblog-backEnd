package com.youngzz1k.weblog.common.domain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.youngzz1k.weblog.common.domain.dos.StatisticsArticlePVDO;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDate;

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
}