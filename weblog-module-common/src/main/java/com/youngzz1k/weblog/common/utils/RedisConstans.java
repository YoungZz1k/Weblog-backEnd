package com.youngzz1k.weblog.common.utils;

import lombok.Data;

@Data
public class RedisConstans {

    public static final String ARTICLE_KEY = "article:";

    public static final String ARTICLE_PAGE = "article:page:";

    public static final String ARTICLE_LOCK = "article:lock:";

    public static final String ARTICLE_CONTENT = "article:content:";

    public static final String ARTICLE_BLOGSETTINGS = "article:blogsettings:";

    public static final String BLOGSETTINGS_LOCK = "blogsettings:lock:";

    public static final String ARTICLE_CATEGORY = "article:category:";

    public static final String CATEGORY_LOCK = "category:lock:";

    public static final String ARTICLE_TAG = "article:tag:";

    public static final String TAG_LOCK = "tag:lock:";
}
