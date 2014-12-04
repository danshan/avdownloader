package com.shanhh.av.service.tools;

import com.shanhh.av.service.job.CoverJob;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.methods.HttpGet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

/**
 * @author dan.shan
 * @since 2014-11-29 12:15 AM
 */
public class CoverDownloader implements Runnable {

    private static final Logger LOG = LoggerFactory.getLogger(CoverDownloader.class);

    private CoverJob job;
    private String folder;
    private CustomHttpComponent httpComponent;

    @Override
    public void run() {
        if (checkCoverExists(job)) {
            LOG.info("{} exists, skip job", parseSavedPath(job));
        }

        HttpGet httpGet = new HttpGet(this.job.getCoverUrl());
        try {
            boolean success = httpComponent.execute(httpGet, new CoverDownloadHandler(this.parseFolder(), job.getCoverSavedName()));
            if (success) {
                LOG.info("download success, {}, {}", this.parseSavedPath(job), this.job.getCoverUrl());
            }
        } catch (HttpResponseException e1) {
            LOG.info(e1.getMessage());
        } catch (IOException e) {
        }
    }

    private String parseFolder() {
        if (folder.endsWith("/")) {
            return folder;
        } else {
            return folder + "/";
        }
    }

    private String parseSavedPath(CoverJob job) {
        return this.parseFolder() + job.getCoverSavedName();
    }

    private boolean checkCoverExists(CoverJob job) {
        File cover = new File(parseSavedPath(job));
        return cover.exists();
    }

    public CoverJob getJob() {
        return job;
    }

    public void setJob(CoverJob job) {
        this.job = job;
    }

    public String getFolder() {
        return folder;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }

    public CustomHttpComponent getHttpComponent() {
        return httpComponent;
    }

    public void setHttpComponent(CustomHttpComponent httpComponent) {
        this.httpComponent = httpComponent;
    }
}
