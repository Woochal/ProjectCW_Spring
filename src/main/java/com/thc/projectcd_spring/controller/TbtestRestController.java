package com.thc.projectcd_spring.controller;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

// @RestController은 @Controller와 @ResponseBody의 조합.
// 컨트롤러에서는 데이터를 반환하기 위해 @ResponseBody 어노테이션을 활용해주어야 하는데, @RestController는 없이도 가능하다.
@RequestMapping("/api/test")
@RestController
public class TbtestRestController {

    @GetMapping("/alert")
    // @RequestParam 은 쿼리스트링으로 전달된 값을 받는다.
    // 파라미터가 여러개라면 여러개를 각각 받아도 되고, map을 사용하여 한번에 받아도 된다.
    public String userData(@RequestParam Map<String, Object> param){

        System.out.println(param);

        Map<String,Object> userData = new HashMap<>();
        userData.put("user_name",param.get("user_name"));
        userData.put("user_age",param.get("user_age"));
        System.out.println(userData.get("user_name"));
        System.out.println(userData.get("user_age"));

        // html이 아닌 값을 반환. 즉 "test" 라는 string을 반환. RestController이니까.
        return "test";
    }

}
