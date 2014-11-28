package com.shanhh.av.service.job;

import com.shanhh.av.service.serial.HuntSerial;
import com.shanhh.av.service.serial.Serial;
import com.shanhh.av.service.serial.SerialName;
import com.shanhh.av.service.tools.CoverDownloadHandler;
import com.shanhh.av.service.tools.CustomHttpComponent;
import org.apache.http.client.methods.HttpGet;

import java.io.IOException;

/**
 * @author dan.shan
 * @since 2014-11-27 10:11 PM
 */
public class CoverJob implements Runnable {
    private SerialName serialName;
    private String serialId;
    private String coverUrl;
    private String coverSavedName;

    public CoverJob(SerialName serialName, String serialId) {
        this.serialName = serialName;
        this.serialId = serialId;

        Serial serial = HuntSerial.getInstance();
        this.coverUrl = serial.buildCoverUrl(serialId);
        this.coverSavedName = serial.buildCoverSavedName(serialId);
    }

    @Override
    public void run() {
        System.out.println(this.coverUrl);
        /*
        HttpGet httpGet = new HttpGet(this.coverUrl);
        try {
            String filepath = CustomHttpComponent.getInstance().execute(httpGet, new CoverDownloadHandler("/tmp/" + this.coverSavedName));
            System.out.println("download " + this.coverUrl + " success, " + filepath);
        } catch (IOException e) {
            e.printStackTrace();
        }
         */
    }

    public SerialName getSerialName() {
        return serialName;
    }

    public String getSerialId() {
        return serialId;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public String getCoverSavedName() {
        return coverSavedName;
    }

}
