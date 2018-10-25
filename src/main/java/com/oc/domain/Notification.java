package com.oc.domain;

import lombok.Data;

import java.util.Date;

/**
 * @author wyj
 * @date 2018/10/23
 */
@Data
public class Notification {
    private Integer id;
    private String title;
    private String content;
    private String url;
    private Date createdAt;
    private Date updatedAt;
    private Date deletedAt;
}
