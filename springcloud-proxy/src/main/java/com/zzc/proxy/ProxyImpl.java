package com.zzc.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class ProxyImpl implements InvocationHandler {
    private Object subject;
    public ProxyImpl(Object subject){
        this.subject = subject;
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("before send");
        System.out.println("method=" + method);
        method.invoke(subject, args);
        System.out.println("after send");
        return null;
    }
}
