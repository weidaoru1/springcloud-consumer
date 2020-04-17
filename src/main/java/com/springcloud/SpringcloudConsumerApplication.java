package com.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

//@SpringBootApplication  //开启 springboot注解
//@EnableEurekaClient // 开启 eureka 客户端功能
//@EnableCircuitBreaker  // 开启断路由功能

@SpringCloudApplication  // 代替以上三类注解
public class SpringcloudConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringcloudConsumerApplication.class, args);
    }

}
