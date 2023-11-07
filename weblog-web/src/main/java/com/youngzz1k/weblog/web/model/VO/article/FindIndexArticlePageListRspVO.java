package com.youngzz1k.weblog.web.model.VO.article;

import com.youngzz1k.weblog.web.model.VO.category.FindCategoryListRspVO;
import com.youngzz1k.weblog.web.model.VO.tag.FindTagListRspVO;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FindIndexArticlePageListRspVO {
    private Long id;
    private String cover;
    private String title;
    private LocalDate createDate;
    private String summary;
    /**
     * 文章分类
     */
    private FindCategoryListRspVO category;

    /**
     * 文章标签
     */
    private List<FindTagListRspVO> tags;
}