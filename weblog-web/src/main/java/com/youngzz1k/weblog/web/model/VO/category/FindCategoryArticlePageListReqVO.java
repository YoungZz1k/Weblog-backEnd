package com.youngzz1k.weblog.web.model.VO.category;

import com.youngzz1k.weblog.common.model.BasePageQuery;
import lombok.*;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FindCategoryArticlePageListReqVO extends BasePageQuery {
    /**
     * 分类 ID
     */
	@NotNull(message = "分类 ID 不能为空")
    private Long id;

}