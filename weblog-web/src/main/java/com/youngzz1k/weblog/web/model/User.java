package com.youngzz1k.weblog.web.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import javax.validation.constraints.*;

@Data
@ApiModel(value = "用户实体类")
public class User {

    @NotBlank(message = "用户名不能为空！")
    @ApiModelProperty(value = "用户名")
    private String userName;

    @NotNull(message = "性别不能为空！")
    @ApiModelProperty(value = "用户性别")
    private String sex;

    @NotNull(message = "年龄不能为空！")
    @Min(value = 18, message = "年龄必须大于等于18！")
    @Max(value = 100, message = "年龄必须小于等于100！")
    @ApiModelProperty(value = "用户年龄")
    private Integer age;

    @NotNull(message = "邮箱不能为空！")
    @Email(message = "邮箱格式不正确！")
    @ApiModelProperty(value = "用户邮箱")
    private String email;

    // 创建时间
    private LocalDateTime createTime;
    // 更新日期
    private LocalDate updateDate;
    // 时间
    private LocalTime time;
}
