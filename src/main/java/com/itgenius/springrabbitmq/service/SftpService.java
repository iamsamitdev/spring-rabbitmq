package com.itgenius.springrabbitmq.service;

import org.springframework.stereotype.Service;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

import lombok.Data;
import lombok.ToString;

@Service
@Data
@ToString
public class SftpService {
    
    private static final String hostname = "xxx";
    private static final int port = 22;
    private static final String username = "xxx";
    private static final String password = "xxx";
    // private static final String remoteDir = "xxx";
    private Session session;

    private static final String remoteFile = "/home/aaaa/remote/afile.txt";
    private static final String localFile = "/home/bbb/local/";

    // Connect method
    public void connect() throws SftpException {

        try{
            JSch jsch = new JSch();

            session = jsch.getSession(username, hostname, port);
            session.setPassword(password);

            session.connect(10000);

            Channel sftp = session.openChannel("sftp");
            ChannelSftp channelSftp = (ChannelSftp) sftp;

            // download file from remote server to local
            channelSftp.get(remoteFile, localFile);

        }catch (JSchException e){
            e.printStackTrace();
        }finally{
            if(session != null){
                session.disconnect();
            }
        }

    }

}
