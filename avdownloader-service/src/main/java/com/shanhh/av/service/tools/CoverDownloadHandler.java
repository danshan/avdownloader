package com.shanhh.av.service.tools;

import org.apache.http.HttpEntity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author dan.shan
 * @since 2014-11-28 4:27 PM
 */
public class CoverDownloadHandler extends AbstractResponseHandler<String> {

    private String filepath;

    public CoverDownloadHandler(String filepath) {
        this.filepath = filepath;
    }

    @Override
    public String handle(HttpEntity entity) throws IOException {
        File file = new File(filepath);
        FileOutputStream output = new FileOutputStream(file);
        try {
            output = new FileOutputStream(file);
            byte buffer[] = new byte[4 * 1024];
            int len;
            while ((len = entity.getContent().read(buffer)) != -1) {
                output.write(buffer, 0, len);
            }
            output.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                entity.getContent().close();
                output.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return this.filepath;
    }

}
