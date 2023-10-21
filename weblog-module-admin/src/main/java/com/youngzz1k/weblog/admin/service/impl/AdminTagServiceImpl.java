package com.youngzz1k.weblog.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youngzz1k.weblog.admin.model.vo.category.AddCategoryReqVO;
import com.youngzz1k.weblog.admin.model.vo.category.DeleteCategoryReqVO;
import com.youngzz1k.weblog.admin.model.vo.category.FindCategoryPageListReqVO;
import com.youngzz1k.weblog.admin.model.vo.category.FindCategoryPageListRspVO;
import com.youngzz1k.weblog.admin.model.vo.tag.AddTagReqVO;
import com.youngzz1k.weblog.admin.model.vo.tag.DeleteTagReqVO;
import com.youngzz1k.weblog.admin.model.vo.tag.FindTagPageListReqVO;
import com.youngzz1k.weblog.admin.model.vo.tag.FindTagPageListRspVO;
import com.youngzz1k.weblog.admin.service.AdminCategoryService;
import com.youngzz1k.weblog.admin.service.AdminTagService;
import com.youngzz1k.weblog.common.domain.dos.CategoryDO;
import com.youngzz1k.weblog.common.domain.dos.TagDO;
import com.youngzz1k.weblog.common.domain.mapper.CategoryMapper;
import com.youngzz1k.weblog.common.domain.mapper.TagMapper;
import com.youngzz1k.weblog.common.enums.ResponseCodeEnum;
import com.youngzz1k.weblog.common.exception.BizException;
import com.youngzz1k.weblog.common.model.vo.SelectRspVO;
import com.youngzz1k.weblog.common.utils.PageResponse;
import com.youngzz1k.weblog.common.utils.Response;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AdminTagServiceImpl extends ServiceImpl<TagMapper, TagDO> implements AdminTagService {

    @Autowired
    private TagMapper tagMapper;

    @Override
    public Response addTags(AddTagReqVO addTagReqVO) {

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

    @Override
    public Response deleteTag(DeleteTagReqVO deleteTagReqVO) {

        //标签Id
        Long id = deleteTagReqVO.getId();

        int count = tagMapper.deleteById(id);

        return count == 1 ? Response.success() : Response.fail(ResponseCodeEnum.TAG_NOT_EXISTED);
    }
}
