package com.atguigu.guli.service.edu.entity;

import com.atguigu.guli.service.base.model.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 课程收藏
 * </p>
 *
 * @author Helen
 * @since 2020-04-12
 */
@Data
@Accessors(chain = true)
@TableName("edu_course_collect")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value="CourseCollect对象", description="课程收藏")
public class CourseCollect extends BaseEntity {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "课程ID")
    private String courseId;

    @ApiModelProperty(value = "用户ID")
    private String memberId;


}
