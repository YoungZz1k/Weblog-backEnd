package com.youngzz1k.weblog.web.model.VO.archive;

import com.youngzz1k.weblog.common.model.BasePageQuery;
import io.swagger.annotations.ApiModel;
import lombok.*;

@Data
@Builder
@ApiModel(value = "文章归档分页 VO")
public class FindArchiveArticlePageListReqVO extends BasePageQuery {
}