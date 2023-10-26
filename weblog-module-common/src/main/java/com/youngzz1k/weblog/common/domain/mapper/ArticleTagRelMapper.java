package com.youngzz1k.weblog.common.domain.mapper;


import com.youngzz1k.weblog.common.config.InsertBatchMapper;
import com.youngzz1k.weblog.common.domain.dos.ArticleTagRelDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ArticleTagRelMapper extends InsertBatchMapper<ArticleTagRelDO> {
}
