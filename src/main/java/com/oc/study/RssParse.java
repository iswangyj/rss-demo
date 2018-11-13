package com.oc.study;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.zip.GZIPInputStream;

/**
 * @author wyj
 * @date 2018/10/25
 */
public class RssParse {
    public void parseXml(URL url) throws IOException, FeedException {
        SyndFeedInput syndFeedInput = new SyndFeedInput();
        SyndFeed feed = null;
        URLConnection connection = url.openConnection();
        String content_encoding = connection.getHeaderField("Content-Encoding");
        if (content_encoding != null && content_encoding.contains("gzip")) {
            System.out.println("content encoding is gzip");
            GZIPInputStream gzin = new GZIPInputStream(connection.getInputStream());
            feed = syndFeedInput.build(new XmlReader(gzin));
        } else {
            feed = syndFeedInput.build(new XmlReader(connection.getInputStream()));
        }

        List<SyndEntry> entries = feed.getEntries();
        for (SyndEntry entry : entries) {
            System.out.println(entry.getTitle());
            System.out.println(entry.getLink());
            System.out.println(entry.getAuthor());
        }

        System.out.println("feed size: " + feed.getEntries().size());

    }

    public static void main(String[] args) {
        RssParse rssParse = new RssParse();
        try {
            rssParse.parseXml(new URL("http://news.qq.com/newsgn/rss_newsgn.xml"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (FeedException e) {
            e.printStackTrace();
        }

    }

}
