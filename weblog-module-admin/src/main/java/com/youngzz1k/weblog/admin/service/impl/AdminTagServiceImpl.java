package com.youngzz1k.weblog.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youngzz1k.weblog.admin.model.vo.category.AddCategoryReqVO;
import com.youngzz1k.weblog.admin.model.vo.category.DeleteCategoryReqVO;
import com.youngzz1k.weblog.admin.model.vo.category.FindCategoryPageListReqVO;
import com.youngzz1k.weblog.admin.model.vo.category.FindCategoryPageListRspVO;
import com.youngzz1k.weblog.admin.model.vo.tag.*;
import com.youngzz1k.weblog.admin.service.AdminCategoryService;
import com.youngzz1k.weblog.admin.service.AdminTagService;
import com.youngzz1k.weblog.common.domain.dos.ArticleTagRelDO;
import com.youngzz1k.weblog.common.domain.dos.CategoryDO;
import com.youngzz1k.weblog.common.domain.dos.TagDO;
import com.youngzz1k.weblog.common.domain.mapper.ArticleTagRelMapper;
import com.youngzz1k.weblog.common.domain.mapper.CategoryMapper;
import com.youngzz1k.weblog.common.domain.mapper.TagMapper;
import com.youngzz1k.weblog.common.enums.ResponseCodeEnum;
import com.youngzz1k.weblog.common.exception.BizException;
import com.youngzz1k.weblog.common.model.vo.SelectRspVO;
import com.youngzz1k.weblog.common.utils.PageResponse;
import com.youngzz1k.weblog.common.utils.RedisConstans;
import com.youngzz1k.weblog.common.utils.Response;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.youngzz1k.weblog.common.utils.RedisConstans.ARTICLE_TAG;

@Service
@Slf4j
public class AdminTagServiceImpl extends ServiceImpl<TagMapper, TagDO> implements AdminTagService {

    @Autowired
    private TagMapper tagMapper;

    @Autowired
    private ArticleTagRelMapper articleTagRelMapper;
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public Response addTags(AddTagReqVO addTagReqVO) {

        // 删除缓存
        redisTemplate.delete(ARTICLE_TAG);

        // Vo转Do
        List<TagDO> tagDos = addTagReqVO.getTags()
                .stream().map(tagName -> TagDO.builder()
                        .name(tagName.trim())// 去掉前后空格
                        .createTime(LocalDateTime.now())
                        .updateTime(LocalDateTime.now())
                        .build())
                .collect(Collectors.toList());

        // 批量插入
        try {
            saveBatch(tagDos);
        } catch (Exception e){
            log.warn("该标签已存在",e);
        }

        return Response.success();
    }

    @Override
    public PageResponse findTagPageList(FindTagPageListReqVO findTagPageListReqVO) {

        //分页参数和条件参数
        String name = findTagPageListReqVO.getName();
        Long current = findTagPageListReqVO.getCurrent();
        Long size = findTagPageListReqVO.getSize();
        LocalDate startDate = findTagPageListReqVO.getStartDate();
        LocalDate endDate = findTagPageListReqVO.getEndDate();

        //分页查询
        Page<TagDO> page = tagMapper.selectPageList(current, size, name, startDate, endDate);

        List<TagDO> tagDOS = page.getRecords();

        //DO转VO
        List<FindTagPageListRspVO> vos = null;

        if(!CollectionUtils.isEmpty(tagDOS)){
            vos = tagDOS.stream().map(tagDo -> FindTagPageListRspVO.builder()
                    .id(tagDo.getId())
                    .name(tagDo.getName())
                    .createTime(tagDo.getCreateTime())
                    .build())
                    .collect(Collectors.toList());
        }
        return PageResponse.success(page, vos);
    }

    /**
     * 删除标签
     *
     * @param deleteTagReqVO
     * @return
     */
    @Override
    public Response deleteTag(DeleteTagReqVO deleteTagReqVO) {

        // 删除缓存
        redisTemplate.delete(ARTICLE_TAG);

        // 标签 ID
        Long tagId = deleteTagReqVO.getId();

        // 校验该标签下是否有关联的文章，若有，则不允许删除，提示用户需要先删除标签下的文章
        ArticleTagRelDO articleTagRelDO = articleTagRelMapper.selectOneByTagId(tagId);

        if (Objects.nonNull(articleTagRelDO)) {
            log.warn("==> 此标签下包含文章，无法删除，tagId: {}", tagId);
            throw new BizException(ResponseCodeEnum.TAG_CAN_NOT_DELETE);
        }

        // 根据标签 ID 删除
        int count = tagMapper.deleteById(tagId);

        return count == 1 ? Response.success() : Response.fail(ResponseCodeEnum.TAG_NOT_EXISTED);
    }

    @Override
    public Response searchTag(SearchTagReqVO searchTagReqVO) {
        String key = searchTagReqVO.getKey();

        //  执行模糊查询
        List<TagDO> tagDOS = tagMapper.selectByKey(key);

        List<SelectRspVO> rspVOS = null;

        if(!CollectionUtils.isEmpty(tagDOS)){
            //DO转VO
            rspVOS =  tagDOS.stream().map(tagDO -> SelectRspVO
                            .builder()
                            .label(tagDO.getName())
                            .value(tagDO.getId())
                            .build())
                    .collect(Collectors.toList());
        }
        return Response.success(rspVOS);
    }

    @Override
    public Response findTagSelectList() {
        // 查询所有标签, Wrappers.emptyWrapper() 表示查询条件为空
        List<TagDO> tagDOS = tagMapper.selectList(Wrappers.emptyWrapper());

        // DO 转 VO
        List<SelectRspVO> vos = null;
        if (!CollectionUtils.isEmpty(tagDOS)) {
            vos = tagDOS.stream()
                    .map(tagDO -> SelectRspVO.builder()
                            .label(tagDO.getName())
                            .value(tagDO.getId())
                            .build())
                    .collect(Collectors.toList());
        }

        return Response.success(vos);
    }
}
