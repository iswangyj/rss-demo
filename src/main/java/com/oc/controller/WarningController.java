package com.oc.controller;


import com.rometools.rome.feed.synd.*;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedOutput;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 自定义类，可根据需要调整
 */
import com.oc.domain.Warning;

/**
 * @author wyj
 * @date 2018/10/24
 */
@RequestMapping("/rss")
@RestController
public class WarningController {
    private static final String RSS_TYPE = "rss_2.0";
    private static final String MIME_TYPE = "application/rss+xml; charset=UTF-8";

    @GetMapping("/warning")
    public void getWarningChannel(HttpServletResponse response) {
        /**
         * 静态数据，仅为演示使用
         */
        List<Warning> warningList = getData();

        List<SyndEntry> itemList = getEntries(warningList);

        SyndFeed feed = createFeed(itemList);

        SyndFeedOutput output = new SyndFeedOutput();

        try {
            response.setContentType(MIME_TYPE);
            output.output(feed, response.getWriter());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (FeedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建feed实例
     *
     * @param itemList
     * @return
     */
    private SyndFeed createFeed(List<SyndEntry> itemList) {
        SyndFeed syndFeed = new SyndFeedImpl();

        syndFeed.setFeedType(RSS_TYPE);
        syndFeed.setTitle("预警频道");
        syndFeed.setDescription("预警信息列表");
        syndFeed.setLink("https://www.baidu.com/baidu?wd=预警频道");
        syndFeed.setEntries(itemList);

        return syndFeed;
    }

    /**
     * 获取item列表
     *
     * @param warnings
     * @return
     */
    private List<SyndEntry> getEntries(List<Warning> warnings) {
        List<SyndEntry> items = new ArrayList<>();

        for (Warning warning : warnings) {
            SyndEntry syndEntry = new SyndEntryImpl();

            syndEntry.setTitle(warning.getTitle());
            syndEntry.setPublishedDate(warning.getUpdatedAt());
            syndEntry.setLink(warning.getUrl());

            SyndContent description = new SyndContentImpl();
            description.setType("text/html");
            description.setValue(warning.getContent());
            syndEntry.setDescription(description);

            items.add(syndEntry);
        }

        return items;
    }

    /**
     * 生成静态数据
     *
     * @return
     */
    private List<Warning> getData() {
        List<Warning> warningList = new ArrayList<>();

        Warning warning1 = new Warning();
        warning1.setId(1);
        warning1.setTitle("预警信息1");
        warning1.setUrl("http://www.baidu.com");
        warning1.setContent("预警信息1/预警信息1/预警信息1");
        warning1.setUpdatedAt(new Date());

        Warning warning2 = new Warning();
        warning2.setId(2);
        warning2.setTitle("预警信息2");
        warning2.setUrl("http://www.github.com");
        warning2.setContent("预警信息2/预警信息2/预警信息2");
        warning2.setUpdatedAt(new Date());

        /**
         * ......
         *  此处仅为示例，可根据需求添加新的数据
         */

        warningList.add(warning1);
        warningList.add(warning2);

        return warningList;
    }
}
