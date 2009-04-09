/*
 * @name: Slim OUERTANI
 * @site : http://jroller.com/ouertani
 * @mail : ouertani@gmail.com
 */
package com.jtunisie.osgi.hessian.client.test;

import com.jtunisie.osgi.hessian.IService;
import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;

/**
 *
 * @author slim
 */
public class RemoteServieTracker extends ServiceTracker implements IService {

    public RemoteServieTracker(BundleContext context) {
        super(context, IService.class.getName(), null);
    }

    @Override
    public String execute() {
        IService service = (IService) getService();
        String execute = service.execute();        
        return execute;
    }
}
