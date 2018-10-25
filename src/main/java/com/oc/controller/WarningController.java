package com.oc.controller;

import com.rometools.rome.feed.rss.Channel;
import com.rometools.rome.feed.rss.Description;
import com.rometools.rome.feed.rss.Item;
import com.oc.domain.Warning;
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
@RequestMapping("/rss")
@RestController
public class WarningController {

    @GetMapping("/warning")
    public Channel getWarningChannel() {
        List<Item> items = getItemList(getData());

        Channel channel = new Channel("rss_2.0");
        channel.setTitle("预警频道");
        channel.setLink("https://www.baidu.com/baidu?wd=预警信息");
        channel.setDescription("预警信息显示频道");
        channel.setItems(items);

        return channel;
    }

    private List<Item> getItemList(List<Warning> warnings) {
        List<Item> itemList = new ArrayList<>();

        for (Warning warning : warnings) {
            Item item = new Item();
            item.setTitle(warning.getTitle());
            item.setLink(warning.getUrl());
            item.setPubDate(warning.getUpdatedAt());
            Description description = new Description();
            description.setValue(warning.getContent());
            item.setDescription(description);

            itemList.add(item);
        }

        return itemList;
    }

    private List<Warning> getData() {
        List<Warning> warningList = new ArrayList<>();

        Warning warning1 = new Warning();
        warning1.setId(1);
        warning1.setTitle("预警信息1");
        warning1.setUrl("http://www.baidu.com");
        warning1.setContent("预警信息1/预警信息1/预警信息1");
        warning1.setCreatedAt(new Date());
        warning1.setUpdatedAt(new Date());

        Warning warning2 = new Warning();
        warning2.setId(2);
        warning2.setTitle("预警信息2");
        warning2.setUrl("http://www.github.com");
        warning2.setContent("预警信息2/预警信息2/预警信息2");
        warning2.setCreatedAt(new Date());
        warning2.setUpdatedAt(new Date());

        warningList.add(warning1);
        warningList.add(warning2);

        return warningList;
    }
}
