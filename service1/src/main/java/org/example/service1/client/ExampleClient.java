package org.example.service1.client;

import cn.hutool.json.JSONObject;
import org.example.service1.client.config.ExampleConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author dlz
 * @since 2024/03/04
 */
@FeignClient(name = "service2", configuration = ExampleConfiguration.class)
public interface ExampleClient {
    @GetMapping("/service2/test")
    JSONObject getService();

}
