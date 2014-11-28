package com.shanhh.av;

import com.shanhh.av.serial.HuntSerial;
import com.shanhh.av.serial.Serial;
import com.shanhh.av.tools.CustomHttpComponent;

/**
 * @author dan.shan
 * @since 2014-11-27 10:11 PM
 */
public class Job implements Runnable {
    private SerialFactory.SerialName serialName;
    private String serialId;
    private String coverUrl;
    private String coverSavedName;

    public Job(SerialFactory.SerialName serialName, String serialId) {
        this.serialName = serialName;
        this.serialId = serialId;

        Serial serial = HuntSerial.getInstance();
        this.coverUrl = serial.buildCoverUrl(serialId);
        this.coverSavedName = serial.buildCoverSavedName(serialId);
    }

    @Override
    public void run() {
        System.out.println(this.coverUrl);
    }

    public SerialFactory.SerialName getSerialName() {
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
