/*
 * @name: Slim OUERTANI
 * @site : http://jroller.com/ouertani
 * @mail : ouertani@gmail.com
 */
package com.jtunisie.osgi.hessian.client;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

/**
 *
 * @author slim
 */
public class Activator implements BundleActivator {

    @Override
    public void start(BundleContext context) throws Exception {
        context.addBundleListener(new BundleListner(context));
    }

    @Override
    public void stop(BundleContext context) throws Exception {
        //TODO
    }
}
