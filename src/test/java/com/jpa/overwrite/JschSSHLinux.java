package com.jpa.overwrite;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import org.apache.logging.log4j.core.util.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class JschSSHLinux {

    public static void main(String[] args) throws Exception {

        String host = "154.8.162.93";
        int port = 22;
        String user = "root";
        String password = "Madness1111";
        String command = "ifconfig";

        Session session = openSession(host,port,user,password);
        String res = exeCommand(session,command);
        System.out.print(res);

        command = "cd /home";
        res = exeCommand(session,command);
        System.out.print(res);

        command = "ls";
        res = exeCommand(session,command);
        System.out.print(res);

        closeSession(session);
    }

    public static void closeSession(Session session){
       session.disconnect();
    }

    public static Session openSession(String host, int port, String user, String password) throws JSchException {
        JSch jsch = new JSch();
        Session session = jsch.getSession(user, host, port);
        session.setConfig("StrictHostKeyChecking", "no");

        session.setPassword(password);
        session.connect();
        if(session.isConnected()){
            System.out.println("session connected");
        }
        return session;
    }
    public static String exeCommand(Session session, String command) throws Exception {

        InputStream in = null;
        try{


            ChannelExec channelExec = (ChannelExec) session.openChannel("exec");
            in = channelExec.getInputStream();
            channelExec.setCommand(command);
            channelExec.setErrStream(System.err);
            channelExec.connect();
            String out = IOUtils.toString(new InputStreamReader(in));

            return out;
        }finally {
          in.close();
        }

    }

}
