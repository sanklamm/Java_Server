package com.sean;

import org.apache.ftpserver.ftplet.FtpException;

import java.io.IOException;

public class App
{
    public static void main( String[] args ) throws FtpException, IOException {

        Ftp myFTPServer = new Ftp(8123);
        User testUser = new User("test", "test", "/home/sean/Documents/FTP");
        myFTPServer.setUserFile("/home/sean/Documents/FTP/myusers.properties");
        myFTPServer.addUser(testUser);
        myFTPServer.createFtpServer();
    }
}
