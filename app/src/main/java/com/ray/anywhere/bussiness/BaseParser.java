package com.ray.anywhere.bussiness;

import com.ray.anywhere.AppApplication;
import com.ray.anywhere.util.ACache;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.URL;

public abstract class BaseParser {
    public static final int timeoutMillis = 20 * 1000;
    public String path;
    public Document doc;

    public BaseParser() {

    }

    public BaseParser(String path) {
        this.path = path;
    }

    public abstract boolean startParse(boolean fromCache);

    public Document parse(URL url) throws IOException {
        return Jsoup.parse(url, timeoutMillis);
    }

    public Document parse(String path, boolean fromCache) throws IOException {
        Document d = null;
        ACache ac = ACache.get(AppApplication.getInstance());
        if (fromCache) {
            String docStr = ac.getAsString(path);
            if (docStr == null) {
                d = parse(new URL(path));
                ac.put(path, d.toString());
            } else {
                d = getDoc(docStr);
            }
        } else {
            d = parse(new URL(path));
            ac.put(path, d.toString());
        }
        return d;
    }

    public Document getDoc(byte[] responseBody) {
        return getDoc(new String(responseBody));
    }

    public Document getDoc(String html) {
        return Jsoup.parse(html);
    }
}
