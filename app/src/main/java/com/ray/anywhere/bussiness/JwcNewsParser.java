package com.ray.anywhere.bussiness;

import com.ray.anywhere.entity.News;

import org.jsoup.nodes.Document;

public class JwcNewsParser extends BaseNewsParser {

    public JwcNewsParser(News news) {
        super(news);
    }

    @Override
    public boolean startParse(Document doc) {
        content = doc.select("#arc_word").get(0);
        clearContent();
        footer = doc.select("#footer").get(0);
        info = doc.select("h3").get(0).text();
        title = doc.select("h1").get(0).text();
        return true;

    }

}
