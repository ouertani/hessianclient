/*
 * @name: Slim OUERTANI
 * @site : http://jroller.com/ouertani
 * @mail : ouertani@gmail.com
 */
package com.jtunisie.osgi.hessian.client;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Dictionary;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.SynchronousBundleListener;

/**
 *
 * @author slim
 */
public class BundleListner implements SynchronousBundleListener {

    private static final String HESSIAN_HEADER = "Hessian-File";

    @Override
    public void bundleChanged(BundleEvent event) {
        if (BundleEvent.STARTED == event.getType()) {
            addBundle(event.getBundle());
        }
        if (BundleEvent.STOPPED == event.getType()) {
            removeBundle(event.getBundle());
        }
    }

    private void addBundle(Bundle bundle) {
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
    }

    private void removeBundle(Bundle bundle) {
    }

    /**
     * Draft cimplementation
     * @param url
     * @return
     * @throws java.io.IOException
     * @throws javax.xml.stream.XMLStreamException
     */
    private Pair readConfig(URL url) throws IOException, XMLStreamException {

        XMLInputFactory inputFactory = XMLInputFactory.newInstance();
        // Setup a new eventReader
        InputStream openStream = url.openStream();

        XMLEventReader eventReader = inputFactory.createXMLEventReader(openStream);

        // Read the XML document
        while (eventReader.hasNext()) {

            XMLEvent event = eventReader.nextEvent();

            if (event.isStartElement()) {
                if (event.asStartElement().getName().getLocalPart().equals("provide")) {
                    event = eventReader.nextEvent();
                    System.out.println(event.asCharacters().getData());
                    continue;
                }
                if (event.asStartElement().getName().getLocalPart().equals("baud")) {
                    event = eventReader.nextEvent();
                    System.out.println(event.asCharacters().getData());
                    continue;
                }

                if (event.asStartElement().getName().getLocalPart().equals("bit")) {
                    event = eventReader.nextEvent();
                    System.out.println(event.asCharacters().getData());
                    continue;
                }

                if (event.asStartElement().getName().getLocalPart().equals("parity")) {
                    event = eventReader.nextEvent();
                    System.out.println(event.asCharacters().getData());
                    continue;
                }
            }
        }



        return null;
    }

    private boolean isHessianRemoting(Bundle bundle) {
        @SuppressWarnings("unchecked")
        Dictionary<String, String> headers = bundle.getHeaders();
        String indexPath = headers.get(HESSIAN_HEADER);
        return indexPath != null;

    }

    class Pair {

        String _interface;
        String _address;
        Class clazz;

        public Pair(String _interface, String _address) throws ClassNotFoundException {
            this._interface = _interface;
            this._address = _address;
            this.clazz = Class.forName(_interface);
        }

        public String getRemoteAdress() {
            return _address;
        }

        public Class<?> getService() {
            return clazz;
        }
    }
}
