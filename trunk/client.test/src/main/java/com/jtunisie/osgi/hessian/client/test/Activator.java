/*
 * @name: Slim OUERTANI
 * @site : http://jroller.com/ouertani
 * @mail : ouertani@gmail.com
 */
package com.jtunisie.osgi.hessian.client.test;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

/**
 *
 * @author slim
 */
public class Activator implements BundleActivator {

    private RemoteServieTracker remoteServiceTracker;

    @Override
    public void start(BundleContext context) throws Exception {
        remoteServiceTracker = new RemoteServieTracker(context);
        remoteServiceTracker.open();
        String execute = remoteServiceTracker.execute();
        System.out.println(execute);
    }

    @Override
    public void stop(BundleContext context) throws Exception {
        if (remoteServiceTracker != null) {
            remoteServiceTracker.close();
        }
    }
}
