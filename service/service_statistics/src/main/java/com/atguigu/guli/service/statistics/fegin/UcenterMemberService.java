package com.atguigu.guli.service.statistics.fegin;

import com.atguigu.guli.common.base.result.R;
import com.atguigu.guli.service.statistics.fegin.fallback.UcenterMemberServiceFallBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "service-ucenter",fallback = UcenterMemberServiceFallBack.class)
public interface UcenterMemberService {

    @GetMapping(value = "count-register-num/{day}")
    R countRegisterNum(@PathVariable("day") String day);
}
