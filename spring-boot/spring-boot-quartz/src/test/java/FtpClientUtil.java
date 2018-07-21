package cn.vpclub.cmhk.attachment.consumer.util;


/**
 *
 */
public class FtpClientUtil {

    FtpTemplate fpFtpTemplate;

    public FtpClientUtil() {
        String server = "172.16.5.218";
        int port = 21;
        String username = "administrator";
        String password = "VPclub123";
        fpFtpTemplate = new FtpTemplate(server, port, username, password);
    }

    public FtpClientUtil(String server, int port, String username,
                         String password) {
        fpFtpTemplate = new FtpTemplate(server, port, username, password);
    }

    /**
     * 上传文件
     *
     * @param remoteFileName 远程文件名称
     * @param locaFileName   本地文件名称
     */
    public void upload(String remoteFileName, String locaFileName) {
        fpFtpTemplate.upload(remoteFileName, locaFileName);
    }

    /**
     * 下载文件
     *
     * @param remoteFileName 远程文件名称
     * @param locaFileName   本地文件名称
     */
    public void download(String remoteFileName, String locaFileName) {
        fpFtpTemplate.download(remoteFileName, locaFileName);
    }

    /**
     * 创建文件夹
     *
     * @param remotePathName 远程文件夹名称
     */
    public void mkdir(String remotePathName) {
        fpFtpTemplate.mkdir(remotePathName);
    }
}
