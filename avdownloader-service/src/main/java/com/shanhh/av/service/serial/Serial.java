package com.shanhh.av.service.serial;

import java.util.Collection;

/**
 * @author dan.shan
 * @since 2014-11-27 9:25 PM
 */
public interface Serial {

    String buildCoverUrl(String serialId);
    String buildCoverSavedName(String serialId);

    Collection<String> getSerialIdPack();

}
