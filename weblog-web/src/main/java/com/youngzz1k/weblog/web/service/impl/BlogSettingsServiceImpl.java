package com.youngzz1k.weblog.web.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.youngzz1k.weblog.common.domain.dos.BlogSettingsDO;
import com.youngzz1k.weblog.common.domain.mapper.BlogSettingsMapper;
import com.youngzz1k.weblog.common.utils.RedisConstans;
import com.youngzz1k.weblog.common.utils.Response;
import com.youngzz1k.weblog.common.utils.nxLock;
import com.youngzz1k.weblog.web.convert.BlogSettingsConvert;
import com.youngzz1k.weblog.web.model.VO.blogsettings.FindBlogSettingsDetailRspVO;
import com.youngzz1k.weblog.web.service.BlogSettingsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

import static com.youngzz1k.weblog.common.utils.RedisConstans.ARTICLE_BLOGSETTINGS;
import static com.youngzz1k.weblog.common.utils.RedisConstans.BLOGSETTINGS_LOCK;

/**
 *  博客设置
 **/
@Service
@Slf4j
public class BlogSettingsServiceImpl implements BlogSettingsService {

    @Autowired
    private BlogSettingsMapper blogSettingsMapper;
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private nxLock nxLock;

    /**
     * 获取博客设置信息
     *
     * @return
     */
    @Override
    public Response findDetail() {
        // 从缓存中查 有直接返回
        String jsonStr = redisTemplate.opsForValue().get(ARTICLE_BLOGSETTINGS);
        if (StringUtils.isNotBlank(jsonStr)){
            FindBlogSettingsDetailRspVO rspVO = JSONUtil.toBean(jsonStr, FindBlogSettingsDetailRspVO.class);
            return Response.success(rspVO);
        }
        // 查询博客设置信息（约定的 ID 为 1）
        BlogSettingsDO blogSettingsDO = blogSettingsMapper.selectById(1L);
        // DO 转 VO
        FindBlogSettingsDetailRspVO vo = BlogSettingsConvert.INSTANCE.convertDO2VO(blogSettingsDO);
        // 放入缓存
        // 获取锁
        try{
           if (!nxLock.tryLock(RedisConstans.BLOGSETTINGS_LOCK)){
               // 未获取到锁 等待并重试
               Thread.sleep(50);
               findDetail();
           }
           // 获取到锁 写缓存
            String json = JSONUtil.toJsonStr(vo);
           // 缓存时间30min
           redisTemplate.opsForValue().set(ARTICLE_BLOGSETTINGS,json,30L, TimeUnit.MINUTES);

        }catch (InterruptedException e){
            throw new RuntimeException(e);
        }
        finally {
            nxLock.unLock(BLOGSETTINGS_LOCK);
        }

        return Response.success(vo);
    }
}
