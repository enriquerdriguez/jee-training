package com.bmw.logger;

import java.lang.reflect.Method;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.logging.Logger;

import javax.annotation.Priority;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

@Logged
@Interceptor
@Priority(value = Interceptor.Priority.APPLICATION)
public class LoggerInterceptor {    
    @AroundInvoke
    public Object applyLog(InvocationContext ctx) throws Exception{
        Method calledMethod = ctx.getMethod();
        Class clazzCalled = calledMethod.getDeclaringClass();

        LocalDateTime iniTime = LocalDateTime.now();
        Object result = ctx.proceed();
        Duration timeElapsed = Duration.between(iniTime, LocalDateTime.now());

        Logger logger = Logger.getLogger(clazzCalled.getName());
        String mensaje = "[Method Executed: "+calledMethod.getName()+"] Time elapsed: "+timeElapsed.toMillis() + " ms"; 
        logger.info(mensaje);
        return result;
    }
}