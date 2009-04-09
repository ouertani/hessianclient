/*
 * @name: Slim OUERTANI
 * @site : http://jroller.com/ouertani
 * @mail : ouertani@gmail.com
 */
package com.jtunisie.osgi.hessian.client;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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

/**
 *
 * @author slim
 */
public class Parser {


    public List<Pair> parseRemoteconfig(){
          // Create event reader
        FileReader reader = null;
        try {
            XMLInputFactory factory = XMLInputFactory.newInstance();
            reader = new FileReader("/home/slim/svn/2main/hessianextender-read-only/client.test/src/main/resources/OSGI-INF/remote-service/remote-services.xml");
            XMLEventReader eventReader = factory.createXMLEventReader(reader);
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
        } catch (XMLStreamException ex) {
            ex.printStackTrace();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException ex) {
                Logger.getLogger(Parser.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        throw  new RuntimeException("Parse Exception");
    }

    public static void main(String[] args) {
        // Create event reader
        FileReader reader = null;
        try {
            XMLInputFactory factory = XMLInputFactory.newInstance();
            reader = new FileReader("/home/slim/svn/2main/hessianextender-read-only/client.test/src/main/resources/OSGI-INF/remote-service/remote-services.xml");
            XMLEventReader eventReader = factory.createXMLEventReader(reader);
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
                        for (Pair pair : remotes) {
                            System.out.println("---");
                            System.out.println("" + pair.getRemoteInterface() + "---" + pair.getRemoteAdress());
                        }
                        System.out.println("END");
                        return;
                    }
                    if (event.asEndElement().getName().getLocalPart().equals("service-description")) {
                        Pair p = Pair.getInstence(remotInterface, remoteAdress);
                        remotes.add(p);
                    }
                }
            }
        } catch (XMLStreamException ex) {
            ex.printStackTrace();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException ex) {
                Logger.getLogger(Parser.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    static class Pair {

        String remoteInterface;
        String remoteAdress;
        Class<?> clazz;

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
//            this.clazz = Class.forName(remotInterface);
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
    }
}
