package com.oc.controller;

import com.rometools.rome.feed.rss.Channel;
import com.rometools.rome.feed.rss.Description;
import com.rometools.rome.feed.rss.Item;
import com.oc.domain.Todo;
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
public class TodoController {

    @GetMapping("/todo")
    public Channel getTodoChannel() {
        List<Item> items = getItemList(getData());

        Channel channel = new Channel("rss_2.0");
        channel.setTitle("待办频道");
        channel.setLink("https://www.baidu.com/baidu?wd=待办事件");
        channel.setDescription("待办事件列表");
        channel.setItems(items);

        return channel;
    }

    private List<Item> getItemList(List<Todo> todoList) {
        List<Item> items = new ArrayList<>();

        for (Todo todo : todoList) {

            Item item = new Item();
            item.setTitle(todo.getTitle());
            item.setLink(todo.getYUrl());
            item.setPubDate(todo.getUpdatedAt());
            item.setComments(todo.getState());

            Description description = new Description();
            description.setValue(todo.getContent());
            item.setDescription(description);

            items.add(item);

        }

        return items;
    }

    private  List<Todo> getData() {
        List<Todo> todoList = new ArrayList<>();

        Todo todo1 = new Todo();
        todo1.setId(101);
        todo1.setTitle("待办事件5");
        todo1.setContent("待办事件5/待办事件5/待办事件5");
        todo1.setYUrl("https://www.baidu.com/baidu?wd=待办事件5");
        todo1.setState("pending");
        todo1.setUpdatedAt(new Date());
        todoList.add(todo1);

        Todo todo2 = new Todo();
        todo2.setId(110);
        todo2.setTitle("待办事件6");
        todo2.setContent("待办事件6/待办事件6/待办事件6");
        todo2.setYUrl("https://www.baidu.com/baidu?wd=待办事件6");
        todo2.setState("processed");
        todo2.setUpdatedAt(new Date());
        todoList.add(todo2);

        Todo todo3 = new Todo();
        todo3.setId(111);
        todo3.setTitle("待办事件7");
        todo3.setContent("待办事件7/待办事件7/待办事件7");
        todo3.setYUrl("https://www.baidu.com/baidu?wd=待办事件7");
        todo3.setRefer("bing");
        todo3.setState("processed");
        todo3.setUpdatedAt(new Date());
        todoList.add(todo3);

        return todoList;
    }
}
