package com.shanhh.av.service.job;

import com.shanhh.av.service.serial.HuntSerial;
import com.shanhh.av.service.serial.Serial;
import com.shanhh.av.service.serial.SerialName;
import lombok.Getter;

/**
 * @author dan.shan
 * @since 2014-11-27 10:11 PM
 */
public class CoverJob {

    @Getter
    private SerialName serialName;
    @Getter
    private String serialId;
    @Getter
    private String coverUrl;
    @Getter
    private String coverSavedName;

    public CoverJob(SerialName serialName, String serialId) {
        this.serialName = serialName;
        this.serialId = serialId;

        Serial serial = HuntSerial.getInstance();
        this.coverUrl = serial.buildCoverUrl(serialId);
        this.coverSavedName = serial.buildCoverSavedName(serialId);
    }

}
