package com.atguigu.guli.service.edu.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.atguigu.guli.service.edu.entity.Subject;
import com.atguigu.guli.service.edu.entity.excel.ExcelSubjectData;
import com.atguigu.guli.service.edu.mapper.SubjectMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
//自动添加有参无参构造
@AllArgsConstructor
@NoArgsConstructor
public class ExcelSubjectDataListener extends AnalysisEventListener<ExcelSubjectData> {

    private SubjectMapper subjectMapper;


    /**
     * 这个每一条数据解析都会来调用
     */
    @Override
    public void invoke(ExcelSubjectData data, AnalysisContext context) {
//        log.info("解析到一条数据:{}", data);
        //处理读取的数据
        String levelOneTitle = data.getLevelOneTitle();
        String levelTwoTitle = data.getLevelTwoTitle();
        Subject subject;

        //判断一级分类是否重复
        Subject subjectLevelOneTitle = getByTitle(levelOneTitle);
        String SubParentId = null;
        if(subjectLevelOneTitle == null){
            //存储一级类别
            subject = new Subject();
            subject.setParentId("0");
            subject.setTitle(levelOneTitle);
            subjectMapper.insert(subject);
            SubParentId = subject.getId();
        }else {
            SubParentId = subjectLevelOneTitle.getId();
        }

        //判断二级分类是否重复
        Subject subByTitle = getSubByTitle(levelTwoTitle,SubParentId);
        if(subByTitle == null){
            //存储二级类别
            subject = new Subject();
            subject.setTitle(levelTwoTitle);
            subject.setParentId(SubParentId);
            subjectMapper.insert(subject);
        }

    }

    /**
     * 所有数据解析完成了 都会来调用
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        log.info("所有数据解析完成！");
    }

    /**
     *  判断一级类别是否重复
     * @param title
     * @return
     */
    private Subject getByTitle(String title){
        QueryWrapper<Subject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("title",title);
        queryWrapper.eq("parent_id","0");
        return subjectMapper.selectOne(queryWrapper);
    }

    /**
     *  根据分类名称和父id是否存在
     * @param title
     * @param parentId
     * @return
     */
    private Subject getSubByTitle(String title,String parentId){
        QueryWrapper<Subject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("title",title);
        queryWrapper.eq("parent_id",parentId);
        return subjectMapper.selectOne(queryWrapper);
    }

}
