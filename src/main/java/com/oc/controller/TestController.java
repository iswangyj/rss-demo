package com.oc.controller;

import com.oc.study.RssParse;
import com.rometools.rome.io.FeedException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URL;

/**
 * @author wyj
 * @date 2018/10/25
 */
@RestController
@RequestMapping("/parse")
public class TestController {
    @GetMapping("/rss/{channel}")
    public void parseResult(@PathVariable("channel") String channelName) throws IOException, FeedException {
        RssParse rssParse = new RssParse();

        String url = "http://localhost:8080/rss/" + channelName;

        rssParse.parseXml(new URL(url));
    }
}
