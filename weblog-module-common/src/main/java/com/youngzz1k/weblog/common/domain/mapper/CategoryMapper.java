package com.youngzz1k.weblog.common.domain.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youngzz1k.weblog.common.domain.dos.CategoryDO;
import com.youngzz1k.weblog.common.domain.dos.TagDO;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDate;
import java.util.Objects;

@Mapper
public interface CategoryMapper extends BaseMapper<CategoryDO> {
    /**
     * 根据用户名查询
     * @param categoryName
     * @return
     */
    default CategoryDO selectByName(String categoryName) {
        // 构建查询条件
        LambdaQueryWrapper<CategoryDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CategoryDO::getName, categoryName);

        // 执行查询
        return selectOne(wrapper);
    }

    /**
     * 分类分页数据查询
     * @param current
     * @param size
     * @param name
     * @param startDate
     * @param endDate
     * @return
     */
    default Page<CategoryDO> selectPageList(long current, long size, String name, LocalDate startDate, LocalDate endDate){

        //分页对象
        Page<CategoryDO> page = new Page<>(current,size);

        //构建查询条件
        LambdaQueryWrapper<CategoryDO> wrapper = new LambdaQueryWrapper<>();

        wrapper
                .like(Objects.nonNull(name), CategoryDO::getName, name) // 模糊匹配
                .ge(Objects.nonNull(startDate), CategoryDO::getCreateTime, startDate) // 大于等于开始时间
                .le(Objects.nonNull(endDate), CategoryDO::getCreateTime, endDate) // 小于等于结束时间
                .orderByDesc(CategoryDO::getCreateTime); // order by createTime DESC

        return selectPage(page, wrapper);
    }
}
