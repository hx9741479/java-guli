package com.atguigu.guli.service.edu.controller.admin;


import com.atguigu.guli.common.base.result.R;
import com.atguigu.guli.service.edu.entity.form.CourseInfoForm;
import com.atguigu.guli.service.edu.service.CourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author hhxx
 * @since 2020-04-12
 */
@Api(tags = {"课程管理"})
@CrossOrigin //跨域
@RestController
@RequestMapping("/admin/edu/course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @ApiOperation("新增课程")
    @PostMapping("save-course-info")
    public R saveCourseInfo(@ApiParam(value = "课程的详细信息", required = true)
                            @RequestBody() CourseInfoForm courseInfoForm) {
        String courseId = courseService.saveCourseInfo(courseInfoForm);
        return R.ok().data("courseId", courseId);
    }

    @ApiOperation("根据ID查询课程")
    @GetMapping("course-info/{id}")
    public R getById(@ApiParam(value = "课程Id", required = true)
                     @PathVariable String id) {

        CourseInfoForm courseInfoForm = courseService.getCourseInfoById(id);
        if (courseInfoForm == null) {
            return R.error().message("数据不存在！");
        }
        return R.ok().data("item", courseInfoForm);
    }

    @ApiOperation("更新课程")
    @PutMapping("update-course-info")
    public R updateCourseInfoById(@ApiParam(value = "课程基本信息", required = true)
                                  @RequestBody CourseInfoForm courseInfoForm) {
       courseService.updateCourseInfoById(courseInfoForm);
       return R.ok().message("更新成功！");
    }

}

