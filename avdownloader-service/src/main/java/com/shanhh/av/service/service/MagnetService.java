package com.shanhh.av.service.service;

import com.shanhh.av.service.bean.MagnetItem;

import java.util.List;

/**
 * @author dan.shan
 * @since 2014-12-04 7:29 PM
 */
public interface MagnetService {

    List<MagnetItem> search(String keyword);

}
