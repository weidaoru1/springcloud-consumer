package com.springcloud.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.springcloud.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
public class TestController {
    @Autowired
    private RestTemplate restTemplate;
    @RequestMapping("/consumer/test")
    public String consumerTest(){
        System.out.println("==================");
        String str = restTemplate.getForEntity("http://SPRINGCLOUD-PROVIDER/provider/test",String.class).getBody();
        return str;
    }
    @GetMapping("/consumer/user")
    public User user(){
        ResponseEntity<User> entity = restTemplate.getForEntity("http://SPRINGCLOUD-PROVIDER/provider/user",User.class);
        User user = entity.getBody();
        System.out.println("id:" + user.getId() + ",name:" + user.getName());
        return user;
    }
    @GetMapping("/consumer/getUser")
    public User getUser(){
        // 第一种方式
        String[] params = {"10001","中国电信"};
        //ResponseEntity<User> entity = restTemplate.getForEntity("http://SPRINGCLOUD-PROVIDER/provider/getUser?id={0}&name={1}",User.class,params);

        // 第二种方式
        Map<String,Object> map = new HashMap<>();
        map.put("id",10010);
        map.put("name","中国联通");
        ResponseEntity<User> entity = restTemplate.getForEntity("http://SPRINGCLOUD-PROVIDER/provider/getUser?id={id}&name={name}",User.class,map);

        User user = entity.getBody();
        System.out.println("id:" + user.getId() + ",name:" + user.getName());
        return user;
    }
    @PostMapping("/consumer/addUser")
    public User addUser(){
        MultiValueMap<String,Object> dataMap = new LinkedMultiValueMap<String,Object>();
        dataMap.add("id","10001");
        dataMap.add("name","中国电信");
        ResponseEntity<User> entity = restTemplate.postForEntity("http://SPRINGCLOUD-PROVIDER/provider/addUser",dataMap,User.class);
        User user = entity.getBody();
        System.out.println("addUser:===id:" + user.getId() + ",name:" + user.getName());
        return user;
    }
    @PutMapping("/consumer/updateUser")
    public String updateUser(){
        MultiValueMap<String,Object> dataMap = new LinkedMultiValueMap<String,Object>();
        dataMap.add("id","10001");
        dataMap.add("name","中国电信");
        restTemplate.put("http://SPRINGCLOUD-PROVIDER/provider/updateUser",dataMap);
        return "success";
    }
    @DeleteMapping("/consumer/deleteUser")
    public String deleteUser(){
        // 第一种方式
        String[] params = {"10001","中国电信"};
        restTemplate.delete("http://SPRINGCLOUD-PROVIDER/provider/deleteUser?id={0}&name={1}",params);

        // 第二种方式
        Map<String,Object> map = new HashMap<>();
        map.put("id",10010);
        map.put("name","中国联通");
        restTemplate.delete("http://SPRINGCLOUD-PROVIDER/provider/deleteUser?id={id}&name={name}",map);

        return "success";
    }
    @RequestMapping("/consumer/hystrix")
    @HystrixCommand(fallbackMethod = "error",commandProperties = {@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",
            value = "1500")})  // 设置默认超时时间
    public String hystrix(){
        return restTemplate.getForEntity("http://SPRINGCLOUD-PROVIDER/provider/test",String.class).getBody();
    }
    public String error(){
        // 访问远程服务失败时，进行处理逻辑返回异常信息
        return "error";
    }
}
