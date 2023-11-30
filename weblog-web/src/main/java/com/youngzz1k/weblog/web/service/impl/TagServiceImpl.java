package com.youngzz1k.weblog.web.service.impl;

import cn.hutool.core.lang.TypeReference;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youngzz1k.weblog.common.domain.dos.ArticleDO;
import com.youngzz1k.weblog.common.domain.dos.ArticleTagRelDO;
import com.youngzz1k.weblog.common.domain.dos.TagDO;
import com.youngzz1k.weblog.common.domain.mapper.ArticleMapper;
import com.youngzz1k.weblog.common.domain.mapper.ArticleTagRelMapper;
import com.youngzz1k.weblog.common.domain.mapper.TagMapper;
import com.youngzz1k.weblog.common.enums.ResponseCodeEnum;
import com.youngzz1k.weblog.common.exception.BizException;
import com.youngzz1k.weblog.common.utils.PageResponse;
import com.youngzz1k.weblog.common.utils.RedisConstans;
import com.youngzz1k.weblog.common.utils.Response;
import com.youngzz1k.weblog.common.utils.nxLock;
import com.youngzz1k.weblog.web.convert.ArticleConvert;
import com.youngzz1k.weblog.web.model.VO.tag.FindTagArticlePageListReqVO;
import com.youngzz1k.weblog.web.model.VO.tag.FindTagArticlePageListRspVO;
import com.youngzz1k.weblog.web.model.VO.tag.FindTagListRspVO;
import com.youngzz1k.weblog.web.service.TagService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.youngzz1k.weblog.common.utils.RedisConstans.ARTICLE_TAG;
import static com.youngzz1k.weblog.common.utils.RedisConstans.TAG_LOCK;

@Service
@Slf4j
public class TagServiceImpl implements TagService {

    @Autowired
    private TagMapper tagMapper;
    @Autowired
    private ArticleTagRelMapper articleTagRelMapper;
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private nxLock nxLock;

    /**
     * 获取标签列表
     *
     * @return
     */
    @Override
    public Response findTagList() {

        // 先查询缓存
        String jsonStr = redisTemplate.opsForValue().get(ARTICLE_TAG);
        if (StringUtils.isNotBlank(jsonStr)){
            // 有缓存直接返回
            List<FindTagListRspVO> vos = JSONUtil.toBean(jsonStr, new TypeReference<List<FindTagListRspVO>>() {
            }, false);

            return Response.success(vos);
        }
        // 查询所有标签
        List<TagDO> tagDOS = tagMapper.selectList(Wrappers.emptyWrapper());

        // DO 转 VO
        List<FindTagListRspVO> vos = null;
        if (!CollectionUtils.isEmpty(tagDOS)) {
            vos = tagDOS.stream()
                    .map(tagDO -> FindTagListRspVO.builder()
                            .id(tagDO.getId())
                            .name(tagDO.getName())
                            .build())
                    .collect(Collectors.toList());
        }
        // 写入缓存
        // 先获取锁
        try{
            if (!nxLock.tryLock(TAG_LOCK)){
                // 等待重试
                Thread.sleep(50);
                findTagList();
            }
            // 写缓存
            String json = JSONUtil.toJsonStr(vos);
            // 缓存30min
            redisTemplate.opsForValue().set(ARTICLE_TAG,json,30L, TimeUnit.MINUTES);
        }catch (InterruptedException e){
            throw new RuntimeException(e);
        }finally {
            nxLock.unLock(TAG_LOCK);
        }

        return Response.success(vos);
    }

    /**
     * 获取标签下文章分页列表
     *
     * @param findTagArticlePageListReqVO
     * @return
     */
    @Override
    public Response findTagPageList(FindTagArticlePageListReqVO findTagArticlePageListReqVO) {
        Long current = findTagArticlePageListReqVO.getCurrent();
        Long size = findTagArticlePageListReqVO.getSize();
        // 标签 ID
        Long tagId = findTagArticlePageListReqVO.getId();

        // 判断该标签是否存在
        TagDO tagDO = tagMapper.selectById(tagId);
        if (Objects.isNull(tagDO)) {
            log.warn("==> 该标签不存在, tagId: {}", tagId);
            throw new BizException(ResponseCodeEnum.TAG_NOT_EXISTED);
        }

        // 先查询该标签下所有关联的文章 ID
        List<ArticleTagRelDO> articleTagRelDOS = articleTagRelMapper.selectByTagId(tagId);

        // 若该标签下未发布任何文章
        if (CollectionUtils.isEmpty(articleTagRelDOS)) {
            log.info("==> 该标签下还未发布任何文章, tagId: {}", tagId);
            return PageResponse.success(null, null);
        }

        // 提取所有文章 ID
        List<Long> articleIds = articleTagRelDOS.stream().map(ArticleTagRelDO::getArticleId).collect(Collectors.toList());

        // 根据文章 ID 集合查询文章分页数据
        Page<ArticleDO> page = articleMapper.selectPageListByArticleIds(current, size, articleIds);
        List<ArticleDO> articleDOS = page.getRecords();

        // DO 转 VO
        List<FindTagArticlePageListRspVO> vos = null;
        if (!CollectionUtils.isEmpty(articleDOS)) {
            vos = articleDOS.stream()
                    .map(articleDO -> ArticleConvert.INSTANCE.convertDO2TagArticleVO(articleDO))
                    .collect(Collectors.toList());
        }

        return PageResponse.success(page, vos);
    }
}