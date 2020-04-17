package com.springcloud.common.config;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import com.netflix.loadbalancer.RetryRule;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration  // 相当于 spring 的application.xml
public class BeanConfig {
    /**
     * 相当于 <bean id="restTemplate" class = "xxx.xxx.RestTemplate"></bean>
     * @return
     */
    @LoadBalanced  // 使用 Ribbon 负载均衡调用
    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    /**
     * 覆盖掉原来ribbon默认的轮询负载均衡策略
     * @return
     */
    @Bean
    public IRule iRule(){
        //return new RandomRule(); // 采用随机的负载均衡策略
        return new RetryRule();  //先按照RoundRobinRule(轮询)分发，若不能访问分发服务，则在指定时间内重试，分发其他可用服务
    }
}
