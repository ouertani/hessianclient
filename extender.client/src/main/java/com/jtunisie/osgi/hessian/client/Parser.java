/*
 * @name: Slim OUERTANI
 * @site : http://jroller.com/ouertani
 * @mail : ouertani@gmail.com
 */
package com.jtunisie.osgi.hessian.client;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.XMLEvent;
import org.osgi.framework.ServiceRegistration;

/**
 *
 * @author slim
 */
public class Parser {

    public static List<Pair> parseRemoteconfig(URL url) {

       
        // Create event reader
        InputStream openStream = null;
        try {
            XMLInputFactory factory = XMLInputFactory.newInstance();
            // Setup a new eventReader
            openStream = url.openStream();

            XMLEventReader eventReader = factory.createXMLEventReader(openStream);
            QName name = new QName("name");
            QName iname = new QName("interface");
            List<Pair> remotes = null;
            String remotInterface = "";
            String remoteAdress = "";

            while (eventReader.hasNext()) {
                XMLEvent event = eventReader.nextEvent();



                if (event.isStartElement()) {

                    if (event.asStartElement().getName().getLocalPart().equals("service-descriptions")) {
                        remotes = new ArrayList<Pair>();
                    }
                    if (event.asStartElement().getName().getLocalPart().equals("service-description")) {
                    }
                    if (event.asStartElement().getName().getLocalPart().equals("provide")) {
                        Attribute attributeByName = event.asStartElement().getAttributeByName(iname);
                        if (attributeByName != null) {
                            remotInterface = attributeByName.getValue();
                        }
                    }
                    if (event.asStartElement().getName().getLocalPart().equals("property")) {

                        Attribute attributeByName = event.asStartElement().getAttributeByName(name);
                        if (attributeByName != null && attributeByName.getValue().equals("osgi.remote.configuration.pojo.address")) {
                            remoteAdress = eventReader.nextEvent().asCharacters().getData();
                        }
                    }


                }
                if (event.isEndElement()) {
                    if (event.asEndElement().getName().getLocalPart().equals("service-descriptions")) {
                        return remotes;
                    }
                    if (event.asEndElement().getName().getLocalPart().equals("service-description")) {
                        Pair p = Pair.getInstence(remotInterface, remoteAdress);
                        remotes.add(p);
                    }
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (XMLStreamException ex) {
            ex.printStackTrace();
        } finally {
            try {
                openStream.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        throw new RuntimeException("Parse Exception");
    }

    public static class Pair {

        private String remoteInterface;
        private String remoteAdress;
        private Class<?> clazz;
        private ServiceRegistration serviceRegistration;

        static Pair getInstence(String remotInterface, String remoteAdress) {
            try {
                return new Pair(remotInterface, remoteAdress);
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
                return null;
            }
        }

        private Pair(String remotInterface, String remoteAdress) throws ClassNotFoundException {
            this.remoteInterface = remotInterface;
            this.remoteAdress = remoteAdress;
            this.clazz = Class.forName(remotInterface);
        }

        public Class<?> getClazz() {
            return clazz;
        }

        public String getRemoteInterface() {
            return remoteInterface;
        }

        public String getRemoteAdress() {
            return remoteAdress;
        }

        public ServiceRegistration getServiceRegistration() {
            System.out.println("Service registred !!");
            return serviceRegistration;
        }

        public void setServiceRegistration(ServiceRegistration serviceRegistration) {
            this.serviceRegistration = serviceRegistration;
        }
    }
}
