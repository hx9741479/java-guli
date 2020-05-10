package com.atguigu.guli.service.statistics.service.impl;

import com.atguigu.guli.common.base.result.R;
import com.atguigu.guli.service.statistics.entity.Daily;
import com.atguigu.guli.service.statistics.fegin.UcenterMemberService;
import com.atguigu.guli.service.statistics.mapper.DailyMapper;
import com.atguigu.guli.service.statistics.service.DailyService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author Helen
 * @since 2020-05-10
 */
@Service
public class DailyServiceImpl extends ServiceImpl<DailyMapper, Daily> implements DailyService {

    @Autowired
    private UcenterMemberService ucenterMemberService;

    @Override
    public void createStatisticsByDay(String day) {
        //如果当日的统计记录已存在，则删除重新统计|或 提示用户当日记录已存在
        QueryWrapper<Daily> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("date_calculated", day);
        baseMapper.delete(queryWrapper);
        //生成统计记录
        R r = ucenterMemberService.countRegisterNum(day);
        Integer registerNum = (Integer) r.getData().get("registerNum");
        int loginNum = RandomUtils.nextInt(100, 200);
        int videoViewNum = RandomUtils.nextInt(100, 200);
        int courseNum = RandomUtils.nextInt(100, 200);

        //在本地数据库创建统计信息
        Daily daily = new Daily();
        daily.setRegisterNum(registerNum);
        daily.setLoginNum(loginNum);
        daily.setVideoViewNum(videoViewNum);
        daily.setCourseNum(courseNum);
        daily.setDateCalculated(day);

        baseMapper.insert(daily);
    }

    /**
     * 获取所有类别数据
     *
     * @param begin
     * @param end
     * @return
     */
    @Override
    public Map<String, Map<String, Object>> getChartData(String begin, String end) {
        Map<String, Map<String, Object>> map = new HashMap<>();
        Map<String, Object> registerNum = this.getChartDataByType(begin, end, "register_num");
        Map<String, Object> loginNum = this.getChartDataByType(begin, end, "login_num");
        Map<String, Object> videoViewNum = this.getChartDataByType(begin, end, "video_view_num");
        Map<String, Object> courseNum = this.getChartDataByType(begin, end, "course_num");

        map.put("registerNum", registerNum);
        map.put("loginNum", loginNum);
        map.put("videoViewNum", videoViewNum);
        map.put("courseNum", courseNum);
        return map;
    }

    /**
     * 辅助方法：根据类别获取数据
     *
     * @param begin
     * @param end
     * @param type
     * @return
     */
    private Map<String, Object> getChartDataByType(String begin, String end, String type) {

        Map<String, Object> map = new HashMap<>();

        //根据前端要的数据：我们要给它横轴和纵轴的数据
        List<String> xAxis = new ArrayList<>();//日期列表
        List<Integer> yAxis = new ArrayList<>();//数据列表

        QueryWrapper queryWrapper = new QueryWrapper<>();
        queryWrapper.select("date_calculated",type);
        queryWrapper.between("date_calculated",begin,end);
        List<Map<String, Object>> mapsData = baseMapper.selectMaps(queryWrapper);
        for (Map<String, Object> mapsDatum : mapsData) {
            String calculated = (String)mapsDatum.get("date_calculated");
            Integer count = (Integer)mapsDatum.get(type);
            xAxis.add(calculated);
            yAxis.add(count);
        }
        map.put("xAxis",xAxis);
        map.put("yAxis",yAxis);
        return map;
    }

}
