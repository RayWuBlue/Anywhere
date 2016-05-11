package com.ray.anywhere.bussiness;

import com.ray.anywhere.entity.News;
import com.ray.anywhere.util.N;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ray on 16-5-7.
 */
public class YUNewsListParser extends BaseParser {
    public Document doc;
    public List<News> newslist;
    public String catalog;

    public YUNewsListParser() {
        super();
    }

    public YUNewsListParser(String path, String catalog) {
        super(path);
        this.catalog = catalog;
    }

    @Override
    public boolean startParse(boolean fromCache) {
        try {
            doc = parse(path, fromCache);
            Element ul = doc.select("#content_list ul").first();
            Elements lis = ul.select("li");
            newslist = new ArrayList<News>();
            News news;
            for (Element e : lis) {
                news = new News(N.YUNEWS_DOMAIN+e.select("a").get(0).attr("href"), this.catalog, e.select("a").get(0).text(), e.select("span").get(0).text(),News.TYPE_YU);
                newslist.add(news);
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
