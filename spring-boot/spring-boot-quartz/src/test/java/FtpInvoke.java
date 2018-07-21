package cn.vpclub.cmhk.attachment.consumer.util;

import org.apache.commons.net.ftp.FTPClient;

public interface FtpInvoke {

    /**
     * @param ftp
     * @param remoteFileName
     * @param locaFileName
     */
    void doHandle(FTPClient ftp, String remoteFileName, String locaFileName) throws Exception;
}
