package com.shanhh.av.service.serial;

/**
 * @author dan.shan
 * @since 2014-11-28 10:59 PM
 */
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
