package com.namnx.spring_redis.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import springfox.documentation.annotations.ApiIgnore;

@Controller
public class SystemController {

    @ApiIgnore
    @RequestMapping("/")
    public String redirectToSwaggerPage() {
        return "redirect:swagger-ui.html";
    }
}
