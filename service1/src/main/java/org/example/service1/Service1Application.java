package org.example.service1;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import jakarta.annotation.Resource;
import org.example.service1.client.ExampleClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@RestController
@RequestMapping("service1")
@RefreshScope
public class Service1Application {
    @Resource
    private ExampleClient exampleClient;

    @Value("${sign}")
    private String sign;

    public static void main(String[] args) {
        SpringApplication.run(Service1Application.class, args);
    }

    @GetMapping("test")
    public Object test() {
        final JSONObject resultJsonObj = exampleClient.getService();
        return JSONUtil.createObj().set("status", "ok")
                .set("message", "请求成功")
                .set("result", resultJsonObj);
    }

    @GetMapping("sign")
    public Object sign() {
        return JSONUtil.createObj().set("status", "ok")
                .set("message", "请求成功")
                .set("result", sign);
    }
}
