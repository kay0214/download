package com.sandman.download.entity.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by sunpeikai on 2018/4/17.
 */
@Component
public class SftpParam {
    private static String USERNAME;
    private static String PASSWORD;
    private static String HOST;
    private static int SSH_PORT;
    private static String PATH_PREFIX;
    private static String TEMP_FILE_PATH;
    private static String BLOGGER_DEFAULT_IMG;
    private static String TEMPLATES_PATH;

    @Value("${sftpServer.userName}")
    private void setUSERNAME(String USERNAME) {
        SftpParam.USERNAME = USERNAME;
    }

    @Value("${sftpServer.password}")
    private void setPASSWORD(String PASSWORD) {
        SftpParam.PASSWORD = PASSWORD;
    }

    @Value("${sftpServer.host}")
    private void setHOST(String HOST) {
        SftpParam.HOST = HOST;
    }

    @Value("${sftpServer.sshPort}")
    private void setSshPort(int sshPort) {
        SSH_PORT = sshPort;
    }

    @Value("${sftpServer.prefix}")
    public void setPathPrefix(String pathPrefix) {
        PATH_PREFIX = pathPrefix;
    }

    @Value("${sftpServer.tempFilePath}")
    public void setTempFilePath(String tempFilePath) {
        TEMP_FILE_PATH = tempFilePath;
    }
    @Value("${sftpServer.bloggerDefaultImg}")
    public void setBloggerDefaultImg(String bloggerDefaultImg) {
        BLOGGER_DEFAULT_IMG = bloggerDefaultImg;
    }

    public static String getUSERNAME() {
        return USERNAME;
    }

    public static String getPASSWORD() {
        return PASSWORD;
    }

    public static String getHOST() {
        return HOST;
    }

    public static int getSshPort() {
        return SSH_PORT;
    }

    public static String getPathPrefix() {
        return PATH_PREFIX;
    }

    public static String getTempFilePath() {
        return TEMP_FILE_PATH;
    }
    public static String getLinePathPrefix(){
        return "http://" + getHOST();
    }

    public static String getBloggerDefaultImg() {
        return BLOGGER_DEFAULT_IMG;
    }

    public static String getTemplatesPath() {
        return TEMPLATES_PATH;
    }
    @Value("${sftpServer.templatesPath}")
    public void setTemplatesPath(String templatesPath) {
        TEMPLATES_PATH = templatesPath;
    }
}
