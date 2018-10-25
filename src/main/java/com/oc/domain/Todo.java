package com.oc.domain;

import lombok.Data;

import java.util.Date;

/**
 * @author wyj
 * @date 2018/10/23
 */
@Data
public class Todo {
    private Integer id;
    private String title;
    /**
     * 待办事件内容
     */
    private String content;
    private String yUrl;
    private String refer;
    /**
     * 待办事件状态
     */
    private String state;
    private Date createdAt;
    private Date updatedAt;
    private Date deletedAt;
}
