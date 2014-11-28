package com.shanhh.av;

import com.shanhh.av.serial.Serial;

import java.util.Collection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author dan.shan
 * @since 2014-11-27 9:29 PM
 */
public class Main {

    public static void main(String[] args) {

        ExecutorService pool = Executors.newFixedThreadPool(30);

        SerialFactory factory = SerialFactory.getInstance();

        for (SerialFactory.SerialName name : SerialFactory.SerialName.values()) {
            Serial serial = factory.getSerial(name);
            Collection<String> serialIdPack = serial.getSerialIdPack();
            for (String serialId : serialIdPack) {
                pool.execute(new Job(name, serialId));
            }
        }

        pool.shutdown();

    }
}
