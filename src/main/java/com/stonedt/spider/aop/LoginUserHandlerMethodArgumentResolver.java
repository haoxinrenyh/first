package com.stonedt.spider.aop;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.stonedt.spider.entity.StonedtUser;


@Component
public class LoginUserHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver
{
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.getParameterType().isAssignableFrom(StonedtUser.class)&&methodParameter.hasParameterAnnotation(LoginUser.class);
    }
 
    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        //获取登陆用户信息
        Object object = nativeWebRequest.getAttribute(AuthorizationInterceptor.USER_INFO, RequestAttributes.SCOPE_REQUEST);
        if(object == null){
            return null;
        }
        return (StonedtUser)object;
    }
}