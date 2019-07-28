package com.mgp.ddemo.commons.exceptionhandler;


import com.mgp.ddemo.commons.util.ReturnInfo;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 全局异常处理类
 */
//组合注解，里面含有Component
@ControllerAdvice
public class GlobalDefultExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public Object defultExcepitonHandler(HttpServletRequest request, HttpServletResponse response, Exception e) throws IOException {
        e.printStackTrace();
        if (e instanceof GlobalException) {
            //自定义异常
            GlobalException exception = (GlobalException) e;
            //如果需要跳转
            //response.sendRedirect(request.getContextPath()+"/noPriv.html");
            return ReturnInfo.error(exception.getCode(),exception.getMessage());
        }
        //系统异常
        return ReturnInfo.error(e.getMessage());
    }
}
