package com.thc.projectcd_spring.controller.page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/tbuser")
@Controller
public class TbuserController {

    // {} 부분이 PathVariable을 나타낸다.
    @GetMapping("/{page}")
    // @PathVariable 은 url중 일부를 파라미터로 사용하고 싶을 때 사용한다.
    public String testPage(@PathVariable String page) {

        // /tbuser/PathVariable.html을 찾아서 리턴
        return "/tbuser/" + page;
    }
}
