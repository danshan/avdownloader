package com.shanhh.av.service.job;

import com.shanhh.av.service.serial.HuntSerial;
import com.shanhh.av.service.serial.Serial;
import com.shanhh.av.service.serial.SerialName;

/**
 * @author dan.shan
 * @since 2014-11-27 10:11 PM
 */
public class CoverJob {

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
