package com.youngzz1k.weblog.common.domain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.youngzz1k.weblog.common.domain.dos.ArticleDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ArticleMapper extends BaseMapper<ArticleDO> {
}
