package cn.vpclub.cmhk.attachment.consumer.util;

import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import java.io.*;
import java.net.ConnectException;

public class FtpTemplate {

    /**
     * ftp服务器地址
     */
    private String server;
    /**
     * 端口
     */
    private int port;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;

    public FtpTemplate(String server, int port, String username, String password) {
        this.server = server;
        this.port = port;
        this.username = username;
        this.password = password;
    }

    /**
     * @param ftp
     */
    private void connect(FTPClient ftp) throws Exception {
        // 连接服务器
        ftp.connect(server, port);
        int reply = ftp.getReplyCode();
        // 是否连接成功
        if (!FTPReply.isPositiveCompletion(reply)) {
            throw new ConnectException(server + " 服务器拒绝连接");
        }
        // 登陆
        if (!ftp.login(username, password)) {
            throw new ConnectException("用户名或密码错误");
        }
    }


    /**
     * @param ftp
     * @throws Exception
     */
    private void setProperty(FTPClient ftp) throws Exception {
        ftp.enterLocalPassiveMode();
        // 二进制传输,默认为ASCII
        ftp.setFileType(FTP.BINARY_FILE_TYPE);
    }

    /**
     * @param ftp
     */
    private void logout(FTPClient ftp) throws Exception {
        ftp.noop();
        ftp.logout();
    }

    /**
     * @param ftp
     * @param remoteFileName
     * @param locaFileName
     */
    private void upload(FTPClient ftp, String remoteFileName,
                        String locaFileName) throws Exception {
        //上传
        InputStream input;

        input = new FileInputStream(locaFileName);

        ftp.storeFile(remoteFileName, input);

        input.close();
    }

    /**
     * @param ftp
     * @param remoteFileName
     * @param locaFileName
     */
    private void download(FTPClient ftp, String remoteFileName,
                          String locaFileName) throws Exception {
        OutputStream output = null;
        output = new FileOutputStream(locaFileName);
        ftp.retrieveFile(remoteFileName, output);
        output.close();
    }

    /**
     * @param ftp
     * @param remotePathName
     */
    private void mkdir(FTPClient ftp, String remotePathName) throws Exception {
        ftp.makeDirectory(remotePathName);
    }


    private void handle(FtpInvoke ftpInvoke, String remoteFileName,
                        String locaFileName) {
        FTPClient ftp = null;
        try {
            ftp = new FTPClient();
            ftp.addProtocolCommandListener(new PrintCommandListener(
                    new PrintWriter(System.out), true));
            // 连接ftp服务器
            connect(ftp);
            // 设置属性
            setProperty(ftp);
            // 具体操作方法
            ftpInvoke.doHandle(ftp, remoteFileName, locaFileName);
            // 退出
            logout(ftp);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException f) {
                }
            }
        }
    }

    /**
     * 上传文件
     *
     * @param remoteFileName 远程文件名称
     * @param locaFileName   本地文件名称
     */
    public void upload(String remoteFileName, String locaFileName) {
        handle(new FtpInvoke() {

            @Override
            public void doHandle(FTPClient ftp, String remoteFileName,
                                 String locaFileName) throws Exception {
                //上传文件
                upload(ftp, remoteFileName, locaFileName);
            }
        }, remoteFileName, locaFileName);

    }

    /**
     * 下载文件
     *
     * @param remoteFileName 远程文件名称
     * @param locaFileName   本地文件名称
     */
    public void download(String remoteFileName, String locaFileName) {
        handle(new FtpInvoke() {

            @Override
            public void doHandle(FTPClient ftp, String remoteFileName,
                                 String locaFileName) throws Exception {
                // 下载文件
                download(ftp, remoteFileName, locaFileName);
            }
        }, remoteFileName, locaFileName);
    }

    /**
     * 创建文件夹
     *
     * @param remotePathName 远程文件夹名称
     */
    public void mkdir(String remotePathName) {
        handle(new FtpInvoke() {

            @Override
            public void doHandle(FTPClient ftp, String remoteFileName,
                                 String locaFileName) throws Exception {
                //创建文件夹
                mkdir(ftp, remoteFileName);
            }
        }, remotePathName, null);
    }

}
