package com.atguigu.guli.service.edu.service.impl;

import com.atguigu.guli.common.base.result.R;
import com.atguigu.guli.service.edu.entity.Teacher;
import com.atguigu.guli.service.edu.entity.vo.TeacherQueryVo;
import com.atguigu.guli.service.edu.feign.OssFileService;
import com.atguigu.guli.service.edu.mapper.TeacherMapper;
import com.atguigu.guli.service.edu.service.TeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements TeacherService {

    @Autowired
    private OssFileService ossFileService;

    @Override
    public IPage<Teacher> selectPage(Page<Teacher> pageParam, TeacherQueryVo teacherQueryVo) {
        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("sort");

        if (teacherQueryVo == null) {
            return baseMapper.selectPage(pageParam, queryWrapper);
        }

        String name = teacherQueryVo.getName();
        Integer level = teacherQueryVo.getLevel();
        String begin = teacherQueryVo.getJoinDateBegin();
        String end = teacherQueryVo.getJoinDateEnd();

        if (!StringUtils.isEmpty(name)) {
            System.out.println(name + "==============");
            //左%会使索引失效
            queryWrapper.likeRight("name", name);
        }

        if (level != null) {
            queryWrapper.eq("level", level);
        }

        if (!StringUtils.isEmpty(begin)) {
            queryWrapper.ge("join_date", begin);
        }

        if (!StringUtils.isEmpty(end)) {
            queryWrapper.le("join_date", end);
        }

        return baseMapper.selectPage(pageParam, queryWrapper);
    }

    @Override
    public List<Map<String, Object>> selectNameListByKey(String key) {
        QueryWrapper<Teacher> teacherQueryWrapper = new QueryWrapper<>();
        teacherQueryWrapper.select("name");
        teacherQueryWrapper.likeRight("name", key);
        List<Map<String, Object>> maps = baseMapper.selectMaps(teacherQueryWrapper);
        return maps;
    }

    @Override
    public boolean removeAvatarById(String id) {
        Teacher teacher = baseMapper.selectById(id);
        if(teacher != null) {
            String avatar = teacher.getAvatar();
            if(!StringUtils.isEmpty(avatar)){
                //删除图片
                R r = ossFileService.removeFile(avatar);
                return r.getSuccess();
            }
        }
        return false;
    }
}