package soap;

import javax.xml.ws.Endpoint;


import java.io.IOException;


public class LogServicePublisher {

    static int port = 8090;
    static String servicePath = "/logs";
    static String BASE_URI = "http://localhost:" + port + servicePath;


    public static void main(String[] args) throws IOException {
        System.out.println("SOAP Service listening on " + BASE_URI + "?wsdl");

        Endpoint.publish(BASE_URI, new LogServiceImpl());
        System.out.println("Server is running");

    }
}