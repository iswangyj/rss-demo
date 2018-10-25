package com.test.controller;

import com.rometools.rome.feed.rss.Channel;
import com.rometools.rome.feed.rss.Description;
import com.rometools.rome.feed.rss.Item;
import com.test.domain.Notification;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author wyj
 * @date 2018/10/24
 */
@RestController
@RequestMapping("/rss")
public class NotificationController {
    @GetMapping("/notification")
    public Channel getNotificationChannel() {
        List<Item> items = getItemList(getData());

        Channel channel = new Channel("rss_2.0");
        channel.setTitle("通知频道");
        channel.setDescription("通知公告列表");
        channel.setLink("https://www.baidu.com/baidu?wd=通知公告");
        channel.setItems(items);

        return channel;
    }

    private List<Item> getItemList(List<Notification> notifications) {
        List<Item> items = new ArrayList<>();
        for (Notification notification : notifications) {
            Item item = new Item();
            item.setTitle(notification.getTitle());
            item.setLink(notification.getUrl());
            item.setPubDate(notification.getUpdatedAt());
            Description description = new Description();
            description.setValue(notification.getTitle());
            item.setDescription(description);

            items.add(item);
        }
        return items;
    }

    private List<Notification> getData() {
        List<Notification> notificationList = new ArrayList<>();

        Notification notification1 = new Notification();
        notification1.setId(1001);
        notification1.setUpdatedAt(new Date());
        notification1.setTitle("通知9");
        notification1.setUrl("https://www.baidu.com/baidu?wd=通知9");
        
        Notification notification2 = new Notification();
        notification2.setId(1010);
        notification2.setUpdatedAt(new Date());
        notification2.setTitle("通知10");
        notification2.setUrl("https://www.baidu.com/baidu?wd=通知10");

        notificationList.add(notification1);
        notificationList.add(notification2);

        return notificationList;
    }
}
