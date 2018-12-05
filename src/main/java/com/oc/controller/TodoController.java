package com.oc.controller;

import com.oc.enums.StateEnum;
import com.rometools.rome.feed.synd.*;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedOutput;
import org.jasig.cas.client.authentication.AttributePrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.text.SimpleDateFormat;

/**
 * 自定义类，可根据需要调整
 */
import com.oc.domain.Todo;

import javax.servlet.http.HttpServletRequest;
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
    private DateFormat dcDate = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * 获取待办频道
     *
     * @param state
     * @param response
     * @throws Exception
     */
    @GetMapping("/todo")
    public void getTodoChannel(@RequestParam(value = "state")String state, HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (!stateExists(state)) {
            throw new Exception("state is not exists!");
        }
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
        } catch (Exception e) {
            e.printStackTrace();
        }


        /**
         * 静态数据，仅为演示使用
         */
        List<Todo> todoList = getData();
        List<SyndEntry> itemList;
        if (state.equals(StateEnum.PENDING.getStateName())){
            itemList = getEntries(todoList, StateEnum.PENDING);
        } else {
            itemList = getEntries(todoList, StateEnum.RESOLVED);
        }

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
    private List<SyndEntry> getEntries(List<Todo> todoList, StateEnum state) {
        List<SyndEntry> items = new ArrayList<>();

        for (Todo todo : todoList) {
            if (todo.getState().equals(state.getStateName())) {
                SyndEntry syndEntry = new SyndEntryImpl();

                syndEntry.setTitle(todo.getTitle());
                syndEntry.setLink(todo.getUrl());
                syndEntry.setPublishedDate(todo.getUpdatedAt());

                SyndContent description = new SyndContentImpl();
                description.setType("text/html");
                description.setValue(getDescription(state));
                syndEntry.setDescription(description);

                items.add(syndEntry);
            }
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
        todo1.setUsername("gj");


        Todo todo2 = new Todo();
        todo2.setId(110);
        todo2.setTitle("待办事件6");
        todo2.setContent("待办事件6/待办事件6/待办事件6");
        todo2.setUrl("https://www.baidu.com/baidu?wd=待办事件6");
        todo2.setState("resolved");
        todo2.setUpdatedAt(new Date());
        todo2.setUsername("jcz");


        Todo todo3 = new Todo();
        todo3.setId(111);
        todo3.setTitle("待办事件7");
        todo3.setContent("待办事件7/待办事件7/待办事件7");
        todo3.setUrl("https://www.baidu.com/baidu?wd=待办事件7");
        todo3.setState("resolved");
        todo3.setUpdatedAt(new Date());
        todo3.setUsername("jcz");

        Todo todo4 = new Todo();
        todo4.setId(112);
        todo4.setTitle("待办事件8");
        todo4.setContent("待办事件8/待办事件8/待办事件8");
        todo4.setUrl("https://www.baidu.com/baidu?wd=待办事件8");
        todo4.setState("pending");
        todo4.setUpdatedAt(new Date());
        todo4.setUsername("gj");


        Todo todo5 = new Todo();
        todo5.setId(115);
        todo5.setTitle("待办事件9");
        todo5.setContent("待办事件9/待办事件9/待办事件9");
        todo5.setUrl("https://www.baidu.com/baidu?wd=待办事件6");
        todo5.setState("resolved");
        todo5.setUpdatedAt(new Date());
        todo5.setUsername("jcz");


        Todo todo6 = new Todo();
        todo6.setId(116);
        todo6.setTitle("待办事件10");
        todo6.setContent("待办事件10/待办事件10/待办事件10");
        todo6.setUrl("https://www.baidu.com/baidu?wd=待办事件10");
        todo6.setState("resolved");
        todo6.setUpdatedAt(new Date());
        todo6.setUsername("jcz");

        /**
         * ...
         * 此处仅为示例，可根据需求添加新的数据
         */

        todoList.add(todo1);
        todoList.add(todo2);
        todoList.add(todo3);
        todoList.add(todo4);
        todoList.add(todo5);
        todoList.add(todo6);

        return todoList;
    }

    /**
     * 待办事件状态信息以JSON形式生成
     *
     * @param state
     * @return
     */
    private String getDescription(StateEnum state) {
        return "{\"state\":\"" + state.getStateName() + "\"}";
    }

    private boolean stateExists(String state) {
        return StateEnum.PENDING.getStateName().equals(state) || StateEnum.RESOLVED.getStateName().equals(state);
    }
}
