package com.shanhh.av.service.serial;

import java.text.MessageFormat;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * @author dan.shan
 * @since 2014-11-27 9:28 PM
 */
public class ChnSerial implements Serial {

    private static final String COVER_URL_TEMPLATE = "http://pics.dmm.co.jp/digital/video/118chn00{0}/118chn00{1}pl.jpg";
    private static final String SERIAL_ID_TEMPLATE = "CHN-{0,number,000}";

    private static Serial instance = new ChnSerial();
    private ChnSerial() {}

    public static Serial getInstance() {
        return instance;
    }

    @Override
    public String buildCoverUrl(String serialId) {
        String name = serialId.toLowerCase().replace("-", "");
        return MessageFormat.format(COVER_URL_TEMPLATE, new String[]{ name, name });
    }

    @Override
    public Collection<String> getSerialIdPack() {
        List<String> serialIdPack = new LinkedList<String>();
        for (int id = 1; id < 1000; id++) {
            serialIdPack.add(MessageFormat.format(SERIAL_ID_TEMPLATE, id));
        }
        return serialIdPack;
    }

    @Override
    public String buildCoverSavedName(String serialId) {
        return serialId + ".jpg";
    }

}
