package com.ray.anywhere.bussiness;

import com.ray.anywhere.entity.News;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 * Created by ray on 16-5-7.
 */
public class YUNewsParser extends BaseNewsParser {

    public YUNewsParser(News news){
        super(news);
    }
    @Override
    public boolean startParse(Document doc) {
        content = doc.select(".content_c").first();
        clearContent();
        footer = doc.select(".footer").first();
        Element infoElement = doc.select("h5").first();
        Element titleElement = doc.select("h4").first();
        info = infoElement.text();
        title = titleElement.text();
        return true;
    }
}
