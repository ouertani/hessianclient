/*
 * @name: Slim OUERTANI
 * @site : http://jroller.com/ouertani
 * @mail : ouertani@gmail.com
 */
package com.jtunisie.osgi.hessian.client;


import java.lang.reflect.Proxy;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

/**
 *
 * @author slim
 */
public class DraftExtender implements BundleActivator {

    @Override
    public void start(BundleContext context) throws Exception {
        String _interface = "com.jtunisie.osgi.hessian.IService";
        GenericClientProxy clientProxy = new GenericClientProxy(_interface, "http://localhost:8082/jtunisie");
        Class<?> clazz=Class.forName(_interface);
        Class [] interfaces={clazz};
        Object newProxyInstance = Proxy.newProxyInstance(this.getClass().getClassLoader(), interfaces, clientProxy);
        context.registerService(_interface,  newProxyInstance, null);
    }

    @Override
    public void stop(BundleContext context) throws Exception {   }
}
