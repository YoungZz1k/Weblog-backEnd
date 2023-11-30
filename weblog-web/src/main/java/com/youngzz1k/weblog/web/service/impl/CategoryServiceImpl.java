package com.youngzz1k.weblog.web.service.impl;

import cn.hutool.core.lang.TypeReference;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youngzz1k.weblog.common.domain.dos.ArticleCategoryRelDO;
import com.youngzz1k.weblog.common.domain.dos.ArticleDO;
import com.youngzz1k.weblog.common.domain.dos.CategoryDO;
import com.youngzz1k.weblog.common.domain.mapper.ArticleCategoryRelMapper;
import com.youngzz1k.weblog.common.domain.mapper.ArticleMapper;
import com.youngzz1k.weblog.common.domain.mapper.CategoryMapper;
import com.youngzz1k.weblog.common.enums.ResponseCodeEnum;
import com.youngzz1k.weblog.common.exception.BizException;
import com.youngzz1k.weblog.common.utils.PageResponse;
import com.youngzz1k.weblog.common.utils.RedisConstans;
import com.youngzz1k.weblog.common.utils.Response;
import com.youngzz1k.weblog.common.utils.nxLock;
import com.youngzz1k.weblog.web.convert.ArticleConvert;
import com.youngzz1k.weblog.web.model.VO.category.FindCategoryArticlePageListReqVO;
import com.youngzz1k.weblog.web.model.VO.category.FindCategoryArticlePageListRspVO;
import com.youngzz1k.weblog.web.model.VO.category.FindCategoryListRspVO;
import com.youngzz1k.weblog.web.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.youngzz1k.weblog.common.utils.RedisConstans.ARTICLE_CATEGORY;
import static com.youngzz1k.weblog.common.utils.RedisConstans.CATEGORY_LOCK;

@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private ArticleCategoryRelMapper articleCategoryRelMapper;
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private nxLock nxLock;

    /**
     * 获取分类列表
     *
     * @return
     */
    @Override
    public Response findCategoryList() {

        // 查询缓存
        String jsonStr = redisTemplate.opsForValue().get(ARTICLE_CATEGORY);
        // 缓存不为空 直接返回
        if (StringUtils.isNotBlank(jsonStr)){
            List<FindCategoryListRspVO> vos = JSONUtil.toBean(jsonStr, new TypeReference<List<FindCategoryListRspVO>>() {
            }, false);

            return Response.success(vos);
        }

        // 查询所有分类
        List<CategoryDO> categoryDOS = categoryMapper.selectList(Wrappers.emptyWrapper());

        // DO 转 VO
        List<FindCategoryListRspVO> vos = null;
        if (!CollectionUtils.isEmpty(categoryDOS)) {
            vos = categoryDOS.stream()
                    .map(categoryDO -> FindCategoryListRspVO.builder()
                            .id(categoryDO.getId())
                            .name(categoryDO.getName())
                            .build())
                    .collect(Collectors.toList());
        }
        // 写入缓存
        try{
            // 获取锁
            if (!nxLock.tryLock(CATEGORY_LOCK)){
                // 失败 等待重试
                Thread.sleep(50);
                findCategoryList();
            }
            // 成功 写入缓存
            String json = JSONUtil.toJsonStr(vos);
            // 缓存时间30min
            redisTemplate.opsForValue().set(ARTICLE_CATEGORY,json,30L, TimeUnit.MINUTES);
        }catch (InterruptedException e){
            throw new RuntimeException(e);
        }
        finally {
            nxLock.unLock(CATEGORY_LOCK);
        }

        return Response.success(vos);
    }

    /**
     * 获取分类下文章分页数据
     *
     * @param findCategoryArticlePageListReqVO
     * @return
     */
    @Override
    public Response findCategoryArticlePageList(FindCategoryArticlePageListReqVO findCategoryArticlePageListReqVO) {
        Long current = findCategoryArticlePageListReqVO.getCurrent();
        Long size = findCategoryArticlePageListReqVO.getSize();
        Long categoryId = findCategoryArticlePageListReqVO.getId();

        CategoryDO categoryDO = categoryMapper.selectById(categoryId);

        // 判断该分类是否存在
        if (Objects.isNull(categoryDO)) {
            log.warn("==> 该分类不存在, categoryId: {}", categoryId);
            throw new BizException(ResponseCodeEnum.CATEGORY_NOT_EXISTED);
        }

        // 先查询该分类下所有关联的文章 ID
        List<ArticleCategoryRelDO> articleCategoryRelDOS = articleCategoryRelMapper.selectListByCategoryId(categoryId);

        // 若该分类下未发布任何文章
        if (CollectionUtils.isEmpty(articleCategoryRelDOS)) {
            log.info("==> 该分类下还未发布任何文章, categoryId: {}", categoryId);
            return PageResponse.success(null, null);
        }

        List<Long> articleIds = articleCategoryRelDOS.stream().map(ArticleCategoryRelDO::getArticleId).collect(Collectors.toList());

        // 根据文章 ID 集合查询文章分页数据
        Page<ArticleDO> page = articleMapper.selectPageListByArticleIds(current, size, articleIds);
        List<ArticleDO> articleDOS = page.getRecords();

        // DO 转 VO
        List<FindCategoryArticlePageListRspVO> vos = null;
        if (!CollectionUtils.isEmpty(articleDOS)) {
            vos = articleDOS.stream()
                    .map(articleDO -> ArticleConvert.INSTANCE.convertDO2CategoryArticleVO(articleDO))
                    .collect(Collectors.toList());
        }

        return PageResponse.success(page, vos);
    }

}