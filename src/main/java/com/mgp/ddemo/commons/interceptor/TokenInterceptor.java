package com.mgp.ddemo.commons.interceptor;
import com.alibaba.fastjson.JSONObject;
import com.mgp.ddemo.commons.redis.RedisUtil;
import com.mgp.ddemo.commons.threadbind.ThreadLocalUtil;
import com.mgp.ddemo.commons.threadbind.ThreadUserInfo;
import com.mgp.ddemo.commons.token.JwtUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Configuration
public class TokenInterceptor extends HandlerInterceptorAdapter {

    @Autowired(required = false)@Qualifier("redisUtil")
    private RedisUtil redisUtil;

    /**
     * 调用时间：Controller方法处理之前
     * 执行顺序：链式Intercepter情况下，Intercepter按照声明的顺序一个接一个执行
     * 若返回false，则中断执行，注意：不会进入afterCompletion
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("preHandle: ");
        if(handler instanceof HandlerMethod){
            HandlerMethod hm = (HandlerMethod)handler;
            NeedLogin needLogin =  hm.getMethodAnnotation(NeedLogin.class);
            System.out.println("needLogin : " + needLogin);
            if(needLogin != null){
               //获取token，将token数据注入user线程
                String token = request.getHeader("token");
                if(StringUtils.isNotBlank(token)){
                    //redis实现token的方式，我没写完
                    /*ThreadUserInfo threadUserInfo = JSONObject.parseObject(redisUtil.get(token).toString(),ThreadUserInfo.class);
                    ThreadLocalUtil.setThreadLocalUserInfo(threadUserInfo);*/
                    //jwt版的实现token 的方式
                    ThreadUserInfo threadUserInfo = JwtUtils.decode(token,ThreadUserInfo.class);
                    ThreadLocalUtil.setThreadLocalUserInfo(threadUserInfo);
                    return true;
                }

               return false;
            }else{
                //返回true继续往下走
                return true;
            }
        }
        else{
            return false;
        }
    }

    /**
     * 调用前提：preHandle返回true
     * 调用时间：Controller方法处理完之后，DispatcherServlet进行视图的渲染之前，也就是说在这个方法中你可以对ModelAndView进行操作
     * 执行顺序：链式Intercepter情况下，Intercepter按照声明的顺序倒着执行。
     * 备注：postHandle虽然post打头，但post、get方法都能处理
     *
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("postHandle: " + ThreadLocalUtil.getThreadLocalUserInfo());
        super.postHandle(request, response, handler, modelAndView);
    }

    /**
     * 调用前提：preHandle返回true
     * 调用时间：DispatcherServlet进行视图的渲染之后
     * 多用于清理资源
     *
     * @param request
     * @param response
     * @param handler
     * @param ex
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("afterCompletion: " + ThreadLocalUtil.getThreadLocalUserInfo());
        super.afterCompletion(request, response, handler, ex);
    }

    /**
     * 这个方法会在Controller方法异步执行时开始执行, 而Interceptor的postHandle方法则是需要等到Controller的异步执行完才能执行
     *
     * @param request
     * @param response
     * @param handler
     */
    @Override
    public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        super.afterConcurrentHandlingStarted(request, response, handler);
    }
}
