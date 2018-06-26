package com.sandman.download.utils;

import com.sandman.download.entity.common.SftpParam;
import com.sandman.download.service.common.SendEmailService;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

/**
 * Created by sunpeikai on 2018/4/27.
 */
public class TemplateUtils {
    /**
     * 获取到指定模板内容,用于给用户发邮件
     * */
    public static String getTemplateByName(String templateName){
        String classPath = SftpParam.getTemplatesPath();
        String filePath = classPath + "/" + templateName + ".html";
        StringBuffer sb = new StringBuffer();
        Reader reader = null;
        BufferedReader br = null;
        try {
            reader = new FileReader(filePath);
            br = new BufferedReader(reader);
            String data = null;
            while ((data = br.readLine()) != null) {
                sb.append(data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
                br.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}
