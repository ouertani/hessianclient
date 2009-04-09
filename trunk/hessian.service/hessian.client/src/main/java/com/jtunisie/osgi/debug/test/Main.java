/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jtunisie.osgi.debug.test;

import com.caucho.hessian.client.HessianProxyFactory;
import com.jtunisie.osgi.hessian.IService;

import java.net.MalformedURLException;

/**
 *
 * @author slim
 */
public class Main {

    public static void main(String[] args) throws MalformedURLException  {
        HessianProxyFactory factory = new HessianProxyFactory();
        IService service = (IService) factory.create(IService.class, "http://localhost:8082/jtunisie");
        System.out.println(service.execute());
    }
}
