/*
 * @name: Slim OUERTANI
 * @site : http://jroller.com/ouertani
 * @mail : ouertani@gmail.com
 */
package com.jtunisie.osgi.hessian.client;

import com.caucho.hessian.client.HessianProxyFactory;
import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 *
 * @author slim
 */
public class GenericClientProxy implements InvocationHandler,Serializable {

    String _interface;
    String _address;
     Class clazz;

    public GenericClientProxy(String _interface, String _address) throws ClassNotFoundException {
        this._interface = _interface;
        this._address = _address;
        this.clazz=Class.forName(_interface);
        System.out.println("Clazz "+clazz);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("Generic Logger Entry: Invoking " +
                method.getName());

        HessianProxyFactory factory = new HessianProxyFactory();

        Serializable service = (Serializable) factory.create(clazz, _address);

        return method.invoke(service, args);
    }
}
