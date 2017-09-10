/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.squadd.batch.util;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Vector;
import java.util.logging.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author smorcja
 */
public class FtpSession {

    private static final Logger LOGGER = LoggerFactory.getLogger(FtpSession.class);
    private String protocol;
    private String url;
    private String username;
    private String password;
    private JSch jsch;
    private Session session;
    private ChannelSftp csftp;
    private boolean connected;

    private void init(String config) {
        this.url = "170.127.114.148";
        this.username = "hades";
        this.password = "hades1";
    }

    public boolean connect() {
        this.connected = false;
        Properties config = new Properties();
        config.put("StrictHostKeyChecking", "no");
        try {
            jsch = new JSch();
            //jsch.setKnownHosts(knownHostsPath);
            session = jsch.getSession(this.username, this.url, 22);
            session.setConfig(config);
            session.setPassword(this.password);
            session.connect();

            Channel channel = session.openChannel("sftp");
            channel.connect();
            csftp = (ChannelSftp) channel;
            this.connected = true;
        } catch (JSchException ex) {
            LOGGER.error(ex.getMessage(), Level.SEVERE, ex);
        }
        return connected;
    }

    public void disconnect() {
        this.csftp.quit();
        this.session.disconnect();
        this.jsch = null;
        this.connected = false;
    }

    public String printWorkingDir() {
        try {
            return csftp.pwd();
        } catch (SftpException ex) {
            LOGGER.error(ex.getMessage(), Level.SEVERE, ex);
            return ex.getMessage();
        }
    }

    public List<String> listFiles(String sourcePath) {
        List<String> list = new ArrayList<String>();
        if (this.connected) {
            try {
                Vector vv = csftp.ls(sourcePath);
                if (vv != null) {
                    for (int ii = 0; ii < vv.size(); ii++) {
                        Object obj = vv.elementAt(ii);
                        if (obj instanceof ChannelSftp.LsEntry) {
                            list.add(((ChannelSftp.LsEntry) obj).getLongname());
                        }
                    }
                }
            } catch (SftpException ex) {
                LOGGER.error(ex.getMessage(), Level.SEVERE, ex);
            }
        } else {
            LOGGER.error("Login failed, cannot proceed", Level.SEVERE, null);
        }
        return list;
    }
    
    public List<String> listFiles() {
        return this.listFiles("/");
    }
    
    public boolean upload(String sourcePath, String destPath) {
        boolean success = false;
        try {
            int mode = ChannelSftp.OVERWRITE;
            csftp.put(sourcePath, destPath, null, mode);
            success = true;
        } catch (SftpException ex) {
            LOGGER.error(ex.getMessage(), Level.SEVERE, ex);
        }
        return success;
    }
    
    public boolean download(String sourcePath, String destPath) {
        boolean success = false;
        try {
            int mode = ChannelSftp.OVERWRITE;
            csftp.get(sourcePath, destPath, null, mode);
            success = true;
        } catch (SftpException ex) {
            LOGGER.error(ex.getMessage(), Level.SEVERE, ex);
        }
        return success;
    }
}
