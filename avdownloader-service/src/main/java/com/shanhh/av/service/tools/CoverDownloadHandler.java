package com.shanhh.av.service.tools;

import org.apache.http.HttpEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author dan.shan
 * @since 2014-11-28 4:27 PM
 */
public class CoverDownloadHandler extends AbstractResponseHandler<Boolean> {

    private static final Logger LOG = LoggerFactory.getLogger(CoverDownloadHandler.class);

    private String folder;
    private String filename;

    public CoverDownloadHandler(String folder, String filename) {
        this.folder = folder;
        this.filename = filename;

        this.checkFolder(this.folder);
    }

    private void checkFolder(String folder) {
        File file = new File(folder);
        if (!file.exists()) {
            file.mkdirs();
        } else if (!file.isDirectory()) {
            LOG.error("{} exists but not a folder.", folder);
        }
    }

    @Override
    public Boolean handle(HttpEntity entity) throws IOException {
        File file = new File(this.folder + this.filename);
        FileOutputStream output = new FileOutputStream(file);
        try {
            output = new FileOutputStream(file);
            byte buffer[] = new byte[4 * 1024];
            int len;
            while ((len = entity.getContent().read(buffer)) != -1) {
                output.write(buffer, 0, len);
            }
            output.flush();
            return true;
        } catch (Exception e) {
            LOG.warn("save cover failed, {}, {}", this.folder + this.filename, e.getMessage());
            return false;
        } finally {
            try {
                entity.getContent().close();
                output.close();
            } catch (Exception e) {
            }
        }
    }

}
