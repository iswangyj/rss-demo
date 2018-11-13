package com.oc.controller;

import com.rometools.rome.feed.synd.*;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedOutput;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 自定义类，可根据需要调整
 */
import com.oc.domain.Todo;

import javax.servlet.http.HttpServletResponse;

/**
 * @author wyj
 * @date 2018/10/24
 */
@RestController
@RequestMapping("/rss")
public class TodoController {
    private static final String RSS_TYPE = "rss_2.0";
    private static final String MIME_TYPE = "application/rss+xml;charset=utf-8";

    @GetMapping("/todo")
    public void getTodoChannel(HttpServletResponse response) {
        /**
         * 静态数据，仅为演示使用
         */
        List<Todo> todoList = getData();

        List<SyndEntry> itemList = getEntries(todoList);

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
        syndFeed.setTitle("待办频道");
        syndFeed.setDescription("待办事件列表");
        syndFeed.setLink("https://www.baidu.com/baidu?wd=待办频道");
        syndFeed.setEntries(itemList);

        return syndFeed;
    }

    /**
     * 获取item列表
     *
     * @param todoList
     * @return
     */
    private List<SyndEntry> getEntries(List<Todo> todoList) {
        List<SyndEntry> items = new ArrayList<>();

        for (Todo todo : todoList) {
            SyndEntry syndEntry = new SyndEntryImpl();

            syndEntry.setTitle(todo.getTitle());
            syndEntry.setLink(todo.getUrl());
            syndEntry.setPublishedDate(todo.getUpdatedAt());
            syndEntry.setComments(todo.getState());

            SyndContent description = new SyndContentImpl();
            description.setType("text/html");
            description.setValue(todo.getContent());
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
    private List<Todo> getData() {
        List<Todo> todoList = new ArrayList<>();

        Todo todo1 = new Todo();
        todo1.setId(101);
        todo1.setTitle("待办事件5");
        todo1.setContent("待办事件5/待办事件5/待办事件5");
        todo1.setUrl("https://www.baidu.com/baidu?wd=待办事件5");
        todo1.setState("pending");
        todo1.setUpdatedAt(new Date());


        Todo todo2 = new Todo();
        todo2.setId(110);
        todo2.setTitle("待办事件6");
        todo2.setContent("待办事件6/待办事件6/待办事件6");
        todo2.setUrl("https://www.baidu.com/baidu?wd=待办事件6");
        todo2.setState("processed");
        todo2.setUpdatedAt(new Date());


        Todo todo3 = new Todo();
        todo3.setId(111);
        todo3.setTitle("待办事件7");
        todo3.setContent("待办事件7/待办事件7/待办事件7");
        todo3.setUrl("https://www.baidu.com/baidu?wd=待办事件7");
        todo3.setState("processed");
        todo3.setUpdatedAt(new Date());

        /**
         * ...
         * 此处仅为示例，可根据需求添加新的数据
         */

        todoList.add(todo1);
        todoList.add(todo2);
        todoList.add(todo3);

        return todoList;
    }
}
