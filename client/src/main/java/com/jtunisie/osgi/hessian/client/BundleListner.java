/*
 * @name: Slim OUERTANI
 * @site : http://jroller.com/ouertani
 * @mail : ouertani@gmail.com
 */
package com.jtunisie.osgi.hessian.client;

import com.jtunisie.osgi.hessian.client.Parser.Pair;
import java.net.URL;
import java.util.Dictionary;
import java.util.List;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.SynchronousBundleListener;
import static org.osgi.framework.BundleEvent.*;

/**
 *
 * @author slim
 */
public class BundleListner implements SynchronousBundleListener {

    private static final String HESSIAN_HEADER = "Hessian-File";
    private final BundleContext context;

    public BundleListner(BundleContext context) {
        this.context = context;
    }

    @Override
    public void bundleChanged(BundleEvent event) {   
        if (event.getType() == UPDATED || event.getType() == STARTED) {

            try {             
                addBundle(event.getBundle());
            } catch (ClassNotFoundException ex) {
               ex.printStackTrace();
            }
        }
        if (BundleEvent.STOPPED == event.getType()) {
            removeBundle(event.getBundle());
        }
    }

    private void addBundle(Bundle bundle) throws ClassNotFoundException {
        @SuppressWarnings("unchecked")
        Dictionary<String, String> headers = bundle.getHeaders();
        String indexPath = headers.get(HESSIAN_HEADER);
        
        if (indexPath == null) {
            return;
        }      
        URL resource = bundle.getResource(indexPath);
        if (resource == null) {
            return;
        }
        
        List<Pair> remotes = Parser.parseRemoteconfig(resource);
        if (remotes != null) {
            for (Pair pair : remotes) {          
                pair.setServiceRegistration(new ClientExtender().registerService(context, pair));
            }
        }
    }

    private void removeBundle(Bundle bundle) {
        // TODO
    }
}
