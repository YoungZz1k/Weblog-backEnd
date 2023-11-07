package com.youngzz1k.weblog.web.model.VO.tag;

import com.youngzz1k.weblog.common.model.BasePageQuery;
import lombok.*;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FindTagArticlePageListReqVO extends BasePageQuery {

    /**
     * 标签 ID
     */
    @NotNull(message = "标签 ID 不能为空")
    private Long id;

}