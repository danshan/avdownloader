package com.shanhh.av.service.service.impl;

import com.shanhh.av.service.job.CoverJob;
import com.shanhh.av.service.service.CoverService;
import com.shanhh.av.service.tools.CoverDownloader;
import com.shanhh.av.service.tools.CustomHttpComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author dan.shan
 * @since 2014-11-29 12:21 AM
 */
@Service
public class CoverServiceImpl implements CoverService {

    @Value("${saved.folder}")
    private String savedFolder;

    @Autowired
    private CustomHttpComponent httpComponent;

    @Override
    public void download(CoverJob job) {
        CoverDownloader downloader = new CoverDownloader();
        downloader.setJob(job);
        downloader.setFolder(this.savedFolder);
        downloader.setHttpComponent(httpComponent);
        downloader.run();
    }

}
