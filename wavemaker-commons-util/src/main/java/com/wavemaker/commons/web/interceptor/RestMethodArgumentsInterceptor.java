package com.wavemaker.commons.web.interceptor;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;

import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotatedElementUtils;

/**
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 2/8/18
 */
public interface RestMethodArgumentsInterceptor {

    default boolean supports(Method method, final MethodParameter[] parameters) {
        return true;
    }

    Object[] intercept(Method method, MethodParameter[] parameters, Object[] arguments);

    default <A extends Annotation> A getAnnotation(AnnotatedElement element, Class<A> annotationType) {
        return AnnotatedElementUtils.findMergedAnnotation(element, annotationType);
    }
}
