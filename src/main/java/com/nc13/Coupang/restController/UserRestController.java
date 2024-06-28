package com.nc13.Coupang.restController;

import com.nc13.Coupang.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user/")
public class UserRestController {
    @Autowired
    UserService userService;

    @GetMapping("validateUsername")
    public Map<String, Object> validateUsername(String username) {
        Map<String, Object> resultMap = new HashMap<>();
        boolean result = userService.validateUsername(username);
        System.out.println("username: " + username);
        if (result) {
            resultMap.put("result", "success");
        } else {
            resultMap.put("result", "fail");
        }

        return resultMap;
    }

    @GetMapping("validateNickname")
    public Map<String, Object> validateNickname(String nickname) {
        Map<String, Object> resultMap = new HashMap<>();
        boolean result = userService.validateNickname(nickname);
        System.out.println("nickname: "+nickname);
        if (result) {
            resultMap.put("result", "success");
        } else {
            resultMap.put("result","fail");
        }

        return resultMap;
    }

    @GetMapping("validateAll")
    public Map<String, Object> validateAll(String username, String nickname){
        Map<String, Object> resultMap=new HashMap<>();
        boolean result = userService.validateAll(username,nickname);
        System.out.println("username: " + username);
        System.out.println("nickname: "+nickname);
        if (result) {
            resultMap.put("result", "success");
        } else {
            resultMap.put("result","fail");
        }
        return resultMap;
    }

}
