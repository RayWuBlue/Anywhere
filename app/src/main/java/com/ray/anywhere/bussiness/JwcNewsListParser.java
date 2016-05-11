package com.ray.anywhere.bussiness;

import com.ray.anywhere.entity.News;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JwcNewsListParser extends BaseParser {
    public Document doc;
    public List<News> newslist;
    public String catalog;

    public JwcNewsListParser() {
        super();
    }

    public JwcNewsListParser(String path, String catalog) {
        super(path);
        this.catalog = catalog;
    }

    @Override
    public boolean startParse(boolean fromCache) {
        try {
            doc = parse(path, fromCache);
            Elements lis = doc.select("#list_r ul li");
            newslist = new ArrayList<News>();
            News news;
            for (Element e : lis) {
                news = new News(e.select("a").get(0).attr("href"), this.catalog, e.select("a").get(0).text(), e.select("span").get(0).text(),News.TYPE_JWC);
                newslist.add(news);
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

}
