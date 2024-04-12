package com.hacheery.bookstorebackend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.config.PageableHandlerMethodArgumentResolverCustomizer;

/*
    AS-IS: đối với các request liên quan đến paging thì mặc định page number = 0
    TO-BE: sau khi config như dưới đây thì mặc định page number = 1
    Note: nếu config bằng cách viết trong file properties như dưới đây thì sẽ không hoạt động
    spring.data.web.pageable.one-indexed-parameters=true
    Lý do là vì: đây là 1 bug của Spring boot. SpringDataWebAutoConfiguration được config sau
    RepositoryRestMvcAutoConfiguration và trên bean SpringData... không tồn tại PageableHandlerMethodArgumentResolver.
    Tuy nhiên RepositoryRestMvcAutoConfiguration có bean PageableHandlerMethodArgumentResolver thông qua
    phương thức pageableResolver(), do đó SpringDataWebProperties không được sử dụng. Tuy nhiên phương thức
    pageableResolver gọi phương thức customizePageableResolver, và nó có thể được cấu hình thông qua
    PageableHandlerMethodArgumentResolverCustomizer để tùy chỉnh PageableHandlerMethodArgumentResolver
 */

@Configuration
public class PageablePageNumberConfig {
    @Bean
    public PageableHandlerMethodArgumentResolverCustomizer customizer() {
        return p -> p.setOneIndexedParameters(true);
    }
}
