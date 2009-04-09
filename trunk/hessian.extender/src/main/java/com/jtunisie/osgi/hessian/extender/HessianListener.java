package com.jtunisie.osgi.hessian.extender;

import com.caucho.hessian.server.HessianServlet;
import javax.servlet.Servlet;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.http.HttpService;
import org.osgi.util.tracker.ServiceTracker;


public class HessianListener {

    private HttpService httpService;
    private ServiceTracker servletsTracker;

    public HessianListener(BundleContext context) {
        this.servletsTracker = new CustomTracker(context, HessianServlet.class.getName()) {

            @Override
            public void serviceRegistered(ServiceReference reference) throws Exception {
                Servlet servlet = (Servlet) context.getService(reference);
                String url = (String) reference.getProperty("url");
                httpService.registerServlet(url, servlet, null, null);
                System.out.println("we get servlet");
            }

            @Override
            public void serviceUnregistered(ServiceReference reference, Object service) throws Exception {
                System.out.println("Unregister " + reference);
                httpService.unregister((String) reference.getProperty("url"));
            }
        };
    }

    public void setHttpService(HttpService httpService) throws Exception {
        this.httpService = httpService;
        this.servletsTracker.open();
    }

    public void unsetHttpService(ServiceReference reference) {
        this.servletsTracker.close();
    }
}
