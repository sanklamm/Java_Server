package com.sean;

import org.apache.ftpserver.FtpServer;
import org.apache.ftpserver.FtpServerFactory;
import org.apache.ftpserver.ftplet.FtpException;
import org.apache.ftpserver.listener.ListenerFactory;

/**
 * Hello world!
 *
 */
public class App
{
    public static void main( String[] args ) throws FtpException {
        FtpServerFactory serverFactory = new FtpServerFactory();
        ListenerFactory factory = new ListenerFactory();
        // set the port of the listener
        factory.setPort(2221);
        // replace the default listener
        serverFactory.addListener("default", factory.createListener());
        // start the server
        FtpServer server = serverFactory.createServer();
        server.start();
    }
}
