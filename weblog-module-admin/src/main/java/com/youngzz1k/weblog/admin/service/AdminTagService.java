package com.youngzz1k.weblog.admin.service;

import com.youngzz1k.weblog.admin.model.vo.category.AddCategoryReqVO;
import com.youngzz1k.weblog.admin.model.vo.category.DeleteCategoryReqVO;
import com.youngzz1k.weblog.admin.model.vo.category.FindCategoryPageListReqVO;
import com.youngzz1k.weblog.admin.model.vo.tag.AddTagReqVO;
import com.youngzz1k.weblog.admin.model.vo.tag.DeleteTagReqVO;
import com.youngzz1k.weblog.admin.model.vo.tag.FindTagPageListReqVO;
import com.youngzz1k.weblog.admin.model.vo.tag.SearchTagReqVO;
import com.youngzz1k.weblog.common.utils.PageResponse;
import com.youngzz1k.weblog.common.utils.Response;


public interface AdminTagService {

    /**
     * 添加标签集合
     * @param addTagReqVO
     * @return
     */
    Response addTags(AddTagReqVO addTagReqVO);

    /**
     * 标签分页查询
     * @param findTagPageListReqVO
     * @return
     */
    PageResponse findTagPageList(FindTagPageListReqVO findTagPageListReqVO);

    /**
     * 删除标签
     * @param deleteTagReqVO
     * @return
     */
    Response deleteTag(DeleteTagReqVO deleteTagReqVO);

    /**
     * 根据标签关键词模糊查询
     * @param searchTagReqVO
     * @return
     */
    Response searchTag(SearchTagReqVO searchTagReqVO);

    /**
     * 查询标签 Select 列表数据
     * @return
     */
    Response findTagSelectList();
}
