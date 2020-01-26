package com.godfunc.interceptor;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

@Intercepts(@Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}))
public class CustomerInterceptor implements Interceptor {

    private Logger log = LoggerFactory.getLogger(CustomerInterceptor.class);

    public Object intercept(Invocation invocation) throws Throwable {
        log.info("intercept start .....");
        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        Object parameter = invocation.getArgs()[1];
        RowBounds rowBounds = (RowBounds) invocation.getArgs()[2];
        ResultHandler resultHandler = (ResultHandler) invocation.getArgs()[3];

        BoundSql sql = mappedStatement.getBoundSql(parameter);
        log.info("sql: {}", sql.getSql());
        log.info("params: {}", parameter.toString());
        Object object = invocation.proceed();
        log.info("intercept end .....");
        return object;
    }

    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    public void setProperties(Properties properties) {

    }
}
