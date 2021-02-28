package soap;

import impl.RepositoryManagerImpl;
import org.glassfish.grizzly.http.server.HttpHandler;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.NetworkListener;
import org.glassfish.grizzly.jaxws.JaxwsHandler;


import java.io.IOException;


public class LogServicePublisher {

    static int port = 8090;
    static String servicePath = "/logs";
    static String BASE_URI = "http://localhost:" + port + servicePath;


    public static void main(String[] args) throws IOException {
        System.out.println("SOAP Service listening on " + BASE_URI + "?wsdl");

        NetworkListener networkListener = new NetworkListener("jaxws-listener", "0.0.0.0", port);
        HttpHandler httpHandler = new JaxwsHandler(new LogServiceImpl());

        HttpServer httpServer = new HttpServer();
        httpServer.getServerConfiguration().addHttpHandler(httpHandler, servicePath);
        httpServer.addListener(networkListener);

        httpServer.start();
        System.in.read();
        httpServer.stop();
    }
}