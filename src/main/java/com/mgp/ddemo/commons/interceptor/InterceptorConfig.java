package com.mgp.ddemo.commons.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.nio.charset.Charset;
import java.util.List;

/**
 * @author:mgp，
 * 我没有配置包拦截，手动配置controller拦截
 */
@Configuration
public class InterceptorConfig extends WebMvcConfigurationSupport {

    @Autowired(required = false)@Qualifier("tokenInterceptor")
    TokenInterceptor tokenInterceptor;

    //    以下WebMvcConfigurerAdapter 比较常用的重写接口
    //    /** 解决跨域问题 **/
    //    public void addCorsMappings(CorsRegistry registry) ;
    //    /** 添加拦截器 **/
    //    void addInterceptors(InterceptorRegistry registry);
    //    /** 这里配置视图解析器 **/
    //    /** 视图跳转控制器 **/
    //    void addViewControllers(ViewControllerRegistry registry);
    //    void configureViewResolvers(ViewResolverRegistry registry);
    //    /** 配置内容裁决的一些选项 **/
    //    void configureContentNegotiation(ContentNegotiationConfigurer configurer);
    //    /** 视图跳转控制器 **/
    //    void addViewControllers(ViewControllerRegistry registry);
    //    /** 静态资源处理 **/
    //    void addResourceHandlers(ResourceHandlerRegistry registry);
    //    /** 默认静态资源处理器 **/
    //    void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer);

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tokenInterceptor).addPathPatterns("/**","/**/**")
                .excludePathPatterns("/login","/filedir/**","/resources/**","/static/**","/public/**");
        super.addInterceptors(registry);
    }

    /**
     * 配置静态访问资源:http://127.0.0.1:8012/ddemo/static/1.jpg,
     * http://127.0.0.1:8012/ddemo/filedir/1.jpg指向外部路径也可以访问，但如果不是浏览器可以解析的，需要做处理，比如txt文件下载
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("classpath:/resources/");
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/public/**").addResourceLocations("classpath:/public/");
        //访问外部目录
        registry.addResourceHandler("/filedir/**").addResourceLocations("file:c:/logs/");
        super.addResourceHandlers(registry);
    }

    /**
     * 以前要访问一个页面需要先创建个Controller控制类，再写方法跳转到页面
     * 在这里配置后就不需要那么麻烦了，直接访问http://localhost:8080/toLogin就跳转到login.jsp页面了
     * @param registry
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/main").setViewName("main");
        super.addViewControllers(registry);
    }

    /**
     * 编码配置，会导致异常，待处理
     */
    /*@Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        super.configureMessageConverters(converters);
        converters.add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
    }*/
}
