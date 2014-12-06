package com.shanhh.av.service.service.impl;

import com.shanhh.av.service.bean.MagnetItem;
import com.shanhh.av.service.service.MagnetService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.LinkedList;
import java.util.List;

/**
 * @author dan.shan
 * @since 2014-12-04 7:29 PM
 */
@Service
public class CilizhuMagnetServiceImpl implements MagnetService {

    private static final Logger LOG = LoggerFactory.getLogger(CilizhuMagnetServiceImpl.class);

    @Value("${magnet.timeout}")
    private int timeout;

    private static final String SEARCH_URL_TEMPLATE = "http://cilizhu.com/{0}-hot-desc-1";

    @Override
    public List<MagnetItem> search(String keyword) {
        String searchUrl = buildSearchUrl(keyword);
        try {
            Document document = Jsoup.connect(searchUrl)
                    .header("User-Agent", buildUserAgent())
                    .timeout(timeout)
                    .get();
            return parseDocument(document);
        } catch (IOException e) {
            LOG.error("search {} failed, {}", keyword, e.getMessage());
            return null;
        }
    }

    private List<MagnetItem> parseDocument(Document doc) {
        List<MagnetItem> list = new LinkedList<MagnetItem>();
        Elements searchItems = doc.getElementsByClass("search-item");
        for (Element item : searchItems) {
            MagnetItem magnetItem = new MagnetItem();

            magnetItem.setTitle(item.getElementsByClass("item-title").get(0).text());
            magnetItem.setLink(item.getElementsByClass("download").get(0).attr("href"));

            Element itembars = item.getElementsByClass("item-bar").get(0);
            for (Element itembar : itembars.getElementsByTag("span")) {
                if (itembar.text().startsWith("热度")) {
                    magnetItem.setHot(Integer.parseInt(itembar.getElementsByTag("b").text()));
                } else if (itembar.text().startsWith("大小")) {
                    magnetItem.setSize(itembar.getElementsByTag("b").text());
                }
            }

            list.add(magnetItem);
        }
        return list;
    }


    private String buildSearchUrl(String keyword) {
        try {
            return MessageFormat.format(SEARCH_URL_TEMPLATE, new String[]{
                    URLEncoder.encode(keyword, "utf-8")
            });
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    private static final String USER_AGENT = "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2";
    private String buildUserAgent() {
        return USER_AGENT;
    }

}
