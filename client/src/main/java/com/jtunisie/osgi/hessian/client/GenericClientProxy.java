/*
 * @name: Slim OUERTANI
 * @site : http://jroller.com/ouertani
 * @mail : ouertani@gmail.com
 */
package com.jtunisie.osgi.hessian.client;

import com.caucho.hessian.client.HessianProxyFactory;
import com.jtunisie.osgi.hessian.client.Parser.Pair;
import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 *
 * @author slim
 */
public class GenericClientProxy implements InvocationHandler, Serializable {

    private Pair pair;

    public GenericClientProxy(Pair pair) throws ClassNotFoundException {
        this.pair = pair;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        HessianProxyFactory factory = new HessianProxyFactory();
        Serializable service = (Serializable) factory.create(pair.getClazz(), pair.getRemoteAdress());
        return method.invoke(service, args);
    }
}
