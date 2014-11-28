package com.shanhh.av.service;

import com.shanhh.av.service.serial.ChnSerial;
import com.shanhh.av.service.serial.HuntSerial;
import com.shanhh.av.service.serial.Serial;

/**
 * @author dan.shan
 * @since 2014-11-27 9:24 PM
 */
public class SerialFactory {

    private static SerialFactory instance = new SerialFactory();
    private SerialFactory() {}
    public static SerialFactory getInstance() {
        return instance;
    }

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

    public enum SerialName {

        HUNT("HUNT"),
        CHN("CHN");

        private final String name;

        private SerialName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

    }

}
