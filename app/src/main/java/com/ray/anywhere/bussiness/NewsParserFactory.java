package com.ray.anywhere.bussiness;

import com.ray.anywhere.entity.News;

/**
 * Created by ray on 16-5-7.
 */
public class NewsParserFactory {
    private static NewsParserFactory instance = new NewsParserFactory();
    private NewsParserFactory(){}
    public static NewsParserFactory getInstance(){
        return instance;
    }
    public BaseNewsParser getNewsParser(News news){
        BaseNewsParser parser = null;
        switch (news.getType()){
            case News.TYPE_YU:
                parser = new YUNewsParser(news);
                break;
            case News.TYPE_JWC:
                parser = new JwcNewsParser(news);
                break;

            default:break;
        }
        return parser;
    }
}
