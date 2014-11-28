package com.shanhh.av.service.serial;

import org.springframework.stereotype.Component;

/**
 * @author dan.shan
 * @since 2014-11-27 9:24 PM
 */
@Component
public class SerialFactory {

    public Serial getSerial(SerialName serial) {
        switch (serial) {
            case HUNT:
                return HuntSerial.getInstance();
            case CHN:
                return ChnSerial.getInstance();
            default:
                throw new IllegalArgumentException("unsupportted serial: " + serial.getName());
        }
    }

}
