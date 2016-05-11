package com.ray.anywhere.entity;

import java.io.Serializable;

/**
 * Created by ray on 16-5-7.
 */
public class News implements Serializable {
    public static final String SERIALIZE_KEY = "news";
    public static final int TYPE_YU = 0;
    public static final int TYPE_JWC = 1;
    private String path;
    private String cataloge;
    private String title;
    private String time;
    private int type;

    public News(String path, String cataloge, String title, String time, int type) {
        this.path = path;
        this.cataloge = cataloge;
        this.title = title;
        this.time = time;
        this.type = type;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getCataloge() {
        return cataloge;
    }

    public void setCataloge(String cataloge) {
        this.cataloge = cataloge;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
