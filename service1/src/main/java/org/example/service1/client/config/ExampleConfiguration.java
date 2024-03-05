package org.example.service1.client.config;

import feign.*;
import feign.codec.ErrorDecoder;
import org.apache.http.HttpStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * @author dlz
 * @since 2024/03/05
 */
@Configuration
public class ExampleConfiguration {

    /**
     * 请求拦截.
     */
    @Bean
    public RequestInterceptor requestInterceptor() {
        return new ExampleInterceptor();
    }

    /**
     * 异常处理.
     */
    @Bean
    public ErrorDecoder errorDecoder() {
        return new ExampleErrorDecoder();
    }

    /**
     * 超时配置.
     */
    @Bean
    public Request.Options options() {
        return new Request.Options(5, TimeUnit.SECONDS, 10, TimeUnit.SECONDS, true);
    }

    private static class ExampleInterceptor implements RequestInterceptor {
        @Override
        public void apply(RequestTemplate template) {
            template.header("Authorization", "Bearer token");
        }
    }

    private static class ExampleErrorDecoder implements ErrorDecoder {

        @Override
        public Exception decode(String methodKey, Response response) {
            final int status = response.status();
            return switch (status) {
                case HttpStatus.SC_NOT_FOUND -> throw new RuntimeException("资源服务器-未找到");
                case HttpStatus.SC_INTERNAL_SERVER_ERROR -> throw new RuntimeException("资源服务器-异常");
                default -> FeignException.errorStatus(methodKey, response);
            };

        }
    }

}
