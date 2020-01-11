package com.godfunc.interceptor;

import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Invocation;

import java.util.Properties;

public class CustomerInterceptor implements Interceptor {
    public Object intercept(Invocation invocation) throws Throwable {
        return invocation.proceed();
    }

    public Object plugin(Object target) {
        return target;
    }

    public void setProperties(Properties properties) {

    }
}
