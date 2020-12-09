package com.joeshaw.servicefegin.service;

import com.joeshaw.servicefegin.controller.hystrix.SchedualServiceHiHystric;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "SERVICE-HI",fallback = SchedualServiceHiHystric.class)
public interface SchedualServiceHi {
    @GetMapping(value = "/hi")
    String sayHiFromClientOne(@RequestParam(value = "name") String name);
}
