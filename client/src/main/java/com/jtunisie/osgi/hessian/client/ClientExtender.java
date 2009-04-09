/*
 * @name: Slim OUERTANI
 * @site : http://jroller.com/ouertani
 * @mail : ouertani@gmail.com
 */
package com.jtunisie.osgi.hessian.client;

import com.jtunisie.osgi.hessian.client.Parser.Pair;
import java.lang.reflect.Proxy;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

/**
 *
 * @author slim
 */
public class ClientExtender {

    ServiceRegistration registerService(BundleContext context, Pair pair) throws ClassNotFoundException {
        String _interface = pair.getRemoteInterface();
        Class[] interfaces = {pair.getClazz()};
        GenericClientProxy clientProxy = new GenericClientProxy(pair);
        Object newProxyInstance = Proxy.newProxyInstance(this.getClass().getClassLoader(), interfaces, clientProxy);
        return context.registerService(_interface, newProxyInstance, null);
    }

    void unregisterService(BundleContext context, Pair pair) {
        //TODO
    }
}
