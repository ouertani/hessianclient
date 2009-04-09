package com.jtunisie.osgi.hessian.impl;

import com.caucho.hessian.server.HessianServlet;
import com.jtunisie.osgi.hessian.IService;



/**
 *
 * @author Slim OUERTANI
 */
public class Service extends HessianServlet implements IService {

    public void publish() {
        this.setAPIClass(IService.class);
    }

    @Override
    public String execute()  {
        return "jTunisie";
    }
}

 
