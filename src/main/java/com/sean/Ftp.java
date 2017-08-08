package com.sean;

import org.apache.ftpserver.FtpServer;
import org.apache.ftpserver.FtpServerFactory;
import org.apache.ftpserver.ftplet.*;
import org.apache.ftpserver.listener.ListenerFactory;
import org.apache.ftpserver.usermanager.PasswordEncryptor;
import org.apache.ftpserver.usermanager.PropertiesUserManagerFactory;
import org.apache.ftpserver.usermanager.impl.BaseUser;
import org.apache.ftpserver.usermanager.impl.WritePermission;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Created by sean on 11.07.17.
 */
public class Ftp {
    Properties defaultProps = new Properties();
    int port = 8123;
    String userFile = "/home/sean/Documents/FTP/myusers.properties";
    PropertiesUserManagerFactory userManagerFactory;
    FtpServerFactory serverFactory;


    public Ftp() {
        defaultProps.setProperty("port", "8123");
        defaultProps.setProperty("name", "default");
        this.serverFactory = new FtpServerFactory();
        ListenerFactory factory = new ListenerFactory();
        factory.setPort(this.port);// set the port of the listener
        // replace the default listener to the serverFactory (for network management). multiple listeners are possible.
        this.serverFactory.addListener("default", factory.createListener());
        this.userManagerFactory = new PropertiesUserManagerFactory();
        this.setupUsers();
    }
    public Ftp(int port) {
        this.port = port;
        this.serverFactory = new FtpServerFactory();
        ListenerFactory factory = new ListenerFactory();
        factory.setPort(port);
        this.serverFactory.addListener("default", factory.createListener());
        this.userManagerFactory = new PropertiesUserManagerFactory();
        this.setupUsers();
    }

    public void setPort(int port) {
        this.port = port;
    }
    public void setUserFile(String userFile) {
        this.userFile = userFile;
    }
    public int getPort() {
        return port;
    }
    public String getUserFile() {
        return userFile;
    }

    private void setupUsers(){
        //choose any. We're telling the FTP-server where to read it's user list
        this.userManagerFactory.setFile(new File(this.userFile));
        this.userManagerFactory.setPasswordEncryptor(new PasswordEncryptor()
                //We store clear-text passwords in this example
        {

            @Override
            public String encrypt(String password) {
                return password;
            }

            @Override
            public boolean matches(String passwordToCheck, String storedPassword) {
                return passwordToCheck.equals(storedPassword);
            }
        });
    }

    public void addUser(User u) throws FtpException {
        BaseUser user = new BaseUser();
        user.setName(u.getUsername());
        user.setPassword(u.getPassword());
        user.setHomeDirectory(u.getUserDir());
        List<Authority> authorities = new ArrayList<Authority>();
        authorities.add(new WritePermission());
        user.setAuthorities(authorities);
        UserManager um = this.userManagerFactory.createUserManager();
        try {
            um.save(user);// Save the user to the user list on the filesystem
        }
        catch (FtpException e1) {
            // Deal with exception as needed
        }
        this.serverFactory.setUserManager(um);
    }

    public void createFtpServer(){
        Map<String, Ftplet> m = new HashMap<String, Ftplet>();
        m.put("myFtplet", new Ftplet()
        {

            @Override
            public void init(FtpletContext ftpletContext) throws FtpException {
                //System.out.println("init");
                //System.out.println("Thread #" + Thread.currentThread().getId());
            }

            @Override
            public void destroy() {
                //System.out.println("destroy");
                //System.out.println("Thread #" + Thread.currentThread().getId());
            }

            @Override
            public FtpletResult beforeCommand(FtpSession session, FtpRequest request) throws FtpException, IOException
            {
                //System.out.println("beforeCommand " + session.getUserArgument() + " : " + session.toString() + " | " + request.getArgument() + " : " + request.getCommand() + " : " + request.getRequestLine());
                //System.out.println("Thread #" + Thread.currentThread().getId());

                //do something
                return FtpletResult.DEFAULT;//...or return accordingly
            }

            @Override
            public FtpletResult afterCommand(FtpSession session, FtpRequest request, FtpReply reply) throws FtpException, IOException
            {
                //System.out.println("afterCommand " + session.getUserArgument() + " : " + session.toString() + " | " + request.getArgument() + " : " + request.getCommand() + " : " + request.getRequestLine() + " | " + reply.getMessage() + " : " + reply.toString());
                //System.out.println("Thread #" + Thread.currentThread().getId());

                //do something
                return FtpletResult.DEFAULT;//...or return accordingly
            }

            @Override
            public FtpletResult onConnect(FtpSession session) throws FtpException, IOException
            {
                //System.out.println("onConnect " + session.getUserArgument() + " : " + session.toString());
                //System.out.println("Thread #" + Thread.currentThread().getId());

                //do something
                return FtpletResult.DEFAULT;//...or return accordingly
            }

            @Override
            public FtpletResult onDisconnect(FtpSession session) throws FtpException, IOException
            {
                //System.out.println("onDisconnect " + session.getUserArgument() + " : " + session.toString());
                //System.out.println("Thread #" + Thread.currentThread().getId());

                //do something
                return FtpletResult.DEFAULT;//...or return accordingly
            }
        });
        serverFactory.setFtplets(m);

        //Map<String, Ftplet> mappa = serverFactory.getFtplets();
        //System.out.println(mappa.size());
        //System.out.println("Thread #" + Thread.currentThread().getId());
        //System.out.println(mappa.toString());
        FtpServer server = serverFactory.createServer();
        try
        {
            server.start();//Your FTP server starts listening for incoming FTP-connections, using the configuration options previously set
        }
        catch (FtpException ex)
        {
            //Deal with exception as you need
        }
    }
}
