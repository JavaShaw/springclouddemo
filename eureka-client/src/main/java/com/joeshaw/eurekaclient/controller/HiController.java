package com.joeshaw.eurekaclient.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HiController {
    private static final Logger LOG = LoggerFactory.getLogger(HiController.class);

    @Value("${server.port}")
    String port;

    @RequestMapping("/hi")
    public String home(@RequestParam(value = "name", defaultValue = "张三") String name) {
        LOG.info("calling trace service-hi ",Level.INFO);
        return "hi " + name + " ,i am from port:" + port;
    }
}
