package com.oc.controller;

import com.rometools.rome.feed.synd.*;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedOutput;
import org.jasig.cas.client.authentication.AttributePrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 自定义类，可根据需要调整
 */
import com.oc.domain.Notification;

/**
 * @author wyj
 * @date 2018/10/24
 */
@RestController
@RequestMapping("/rss")
public class NotificationController {
    private static final String RSS_TYPE = "rss_2.0";
    private static final String MIME_TYPE = "application/rss+xml; charset=UTF-8";
    private DateFormat dcDate = new SimpleDateFormat("yyyy-MM-dd");

    @GetMapping("/notification")
    public void getNotificationChannel(HttpServletRequest request,HttpServletResponse response) {
        /**
         * 获取从代理端登录的用户信息，将其属性放入map中
         * 根据cas server验证返回属性分别为accountId、realname、name，其中name为登录用户名
         * 所有属性以key-value方式存放至map，根据用户名验证可以通过attributes.get("name").toString()取得用户名
         */
        AttributePrincipal principal = (AttributePrincipal) request.getUserPrincipal();
        Map<String, Object> attributes = principal.getAttributes();


        /**
         * 获取parameter
         */
        String from = request.getParameter("from");
        Date date = null;
        try {
            date = dcDate.parse(from);
        } catch (Exception e){
            e.printStackTrace();
        }

        /**
         * 静态数据，仅为演示使用
         */
        List<Notification> notificationList = getData();

        List<SyndEntry> itemList = getEntries(notificationList);

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
     * 创建一个feed实例
     *
     * @param itemList
     * @return
     */
    private SyndFeed createFeed(List<SyndEntry> itemList) {
        SyndFeed syndFeed = new SyndFeedImpl();

        syndFeed.setFeedType(RSS_TYPE);
        syndFeed.setTitle("通知频道");
        syndFeed.setDescription("通知公告列表");
        syndFeed.setLink("https://www.baidu.com/baidu?wd=通知频道");
        syndFeed.setEntries(itemList);

        return syndFeed;
    }

    /**
     * 获取item列表
     *
     * @param notifications
     * @return
     */
    private List<SyndEntry> getEntries(List<Notification> notifications) {
        List<SyndEntry> items = new ArrayList<>();

        for (Notification notification : notifications) {
            SyndEntry syndEntry = new SyndEntryImpl();

            syndEntry.setTitle(notification.getTitle());
            syndEntry.setLink(notification.getUrl());
            syndEntry.setPublishedDate(notification.getUpdatedAt());

            SyndContent description = new SyndContentImpl();
            description.setType("text/html");
            description.setValue(notification.getContent());

            syndEntry.setDescription(description);

            items.add(syndEntry);

        }

        return items;
    }

    /**
     * 静态数据生成
     *
     * @return
     */
    private List<Notification> getData() {
        List<Notification> notificationList = new ArrayList<>();

        Notification notification1 = new Notification();
        notification1.setId(1001);
        notification1.setUpdatedAt(new Date());
        notification1.setTitle("通知9");
        notification1.setContent("通知9/通知9/通知9/通知9");
        notification1.setUrl("https://www.baidu.com/baidu?wd=通知9");
        notification1.setUsername("jcz");

        Notification notification2 = new Notification();
        notification2.setId(1010);
        notification2.setUpdatedAt(new Date());
        notification2.setTitle("通知10");
        notification2.setContent("通知10/通知10/通知10/通知10");
        notification2.setUrl("https://www.baidu.com/baidu?wd=通知10");
        notification2.setUsername("gj");

        /**
         * ...
         * 此处仅为示例，可根据需求添加新的数据
         */

        notificationList.add(notification1);
        notificationList.add(notification2);

        return notificationList;
    }
}
