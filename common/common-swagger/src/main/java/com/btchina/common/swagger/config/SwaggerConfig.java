package com.btchina.common.swagger.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.servlet.mvc.method.RequestMappingInfoHandlerMapping;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.service.Contact;
import springfox.documentation.spring.web.plugins.WebFluxRequestHandlerProvider;
import springfox.documentation.spring.web.plugins.WebMvcRequestHandlerProvider;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
//@EnableSwagger2	   //开启 Swagger2
@EnableOpenApi     //开启 Swagger3 ，可不写
@EnableKnife4j     //开启 knife4j ，可不写
public class SwaggerConfig {



	@Bean
	public Docket createRestApi() {
		// Swagger 2 使用的是：DocumentationType.SWAGGER_2
		// Swagger 3 使用的是：DocumentationType.OAS_30
		return new Docket(DocumentationType.OAS_30)
				// 定义是否开启swagger，false为关闭，可以通过变量控制
				.enable(true)
				// 将api的元信息设置为包含在json ResourceListing响应中。
				.apiInfo(new ApiInfoBuilder()
						.title("小红书接口文档")
						// 描述
						.description("平台服务管理api")
						.contact(new Contact("franky", "", "fenggi123@gmail.com"))
						.version("1.0.0")
						.build())
				// 分组名称
				.groupName("1.0")
				// 选择哪些接口作为swagger的doc发布
				.select()
				// 要扫描的API(Controller)基础包
				.apis(RequestHandlerSelectors.basePackage("com.example.red.book"))
				//                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
				.paths(PathSelectors.any())
				.build();
	}

	/**
	 * 修复 Failed to start bean ‘ documentationPluginsBootstrapper ‘ ; nested exception 的问题
	 * springfox 假设 Spring MVC 的路径匹配策略是 ant-path-matcher，而 Spring Boot 2.6.x版本的默认匹配策略是 path-pattern-matcher，这就造成了上面的报错。
	 *
	 * @return
	 */
	@Bean
	public static BeanPostProcessor springfoxHandlerProviderBeanPostProcessor() {
		return new BeanPostProcessor() {

			@Override
			public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
				if (bean instanceof WebMvcRequestHandlerProvider || bean instanceof WebFluxRequestHandlerProvider) {
					customizeSpringfoxHandlerMappings(getHandlerMappings(bean));
				}
				return bean;
			}

			private <T extends RequestMappingInfoHandlerMapping> void customizeSpringfoxHandlerMappings(List<T> mappings) {
				List<T> copy = mappings.stream()
						.filter(mapping -> mapping.getPatternParser() == null)
						.collect(Collectors.toList());
				mappings.clear();
				mappings.addAll(copy);
			}

			@SuppressWarnings("unchecked")
			private List<RequestMappingInfoHandlerMapping> getHandlerMappings(Object bean) {
				try {
					Field field = ReflectionUtils.findField(bean.getClass(), "handlerMappings");
					field.setAccessible(true);
					return (List<RequestMappingInfoHandlerMapping>) field.get(bean);
				} catch (IllegalArgumentException | IllegalAccessException e) {
					throw new IllegalStateException(e);
				}
			}
		};
	}

}
