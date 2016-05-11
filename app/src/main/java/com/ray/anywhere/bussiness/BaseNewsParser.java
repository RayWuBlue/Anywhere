package com.ray.anywhere.bussiness;

import com.ray.anywhere.entity.News;

import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Created by ray on 16-5-7.
 */
public abstract class BaseNewsParser extends BaseParser {

    public String catalog;
    public String time;
    public String title;

    public Element content;
    public String info;
    public Element footer;


    public String css = "<style type=\"text/css\" >"
            + "*{color:#444 !important}"+"body{padding:15px}" + "p{word-wrap: break-word;word-break: normal;}"+"h1{color:#333 !important}"+"img{width:100% !important}"
            + "</style>";
    public String divider = "<hr style=\"height:1px;border:none;border-top:1px solid #00aaee;\" />";

    public String createTag(String html, String tag, String attr) {
        return "<" + tag + " " + attr + ">" + html + "</" + tag + ">";
    }

    public BaseNewsParser() {
        super();
    }

    public BaseNewsParser(News news) {
        super(news.getPath());
        this.catalog = news.getCataloge();
        this.title = news.getTitle();
        this.time = news.getTime();
    }

    abstract public boolean startParse(Document doc);

    @Override
    public boolean startParse(boolean fromCache) {
        Document doc = null;
        try {
            doc = parse(path, fromCache);
            if (doc == null)
                return false;
            else
                return startParse(doc);

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String toWap() {
        String body = "";
        body += css;
        if (title != null) {
            body += createTag(
                    createTag(createTag(title, "h3", null), "center", null),
                    "div", null);
            body += divider;
        }
        if (info != null) {
            body += createTag(createTag(info, "p", null), "center",
                    "style=\"font-size:12px !important;\"");
            body += divider;
        }
        if (content != null) {
            body += content.toString().replaceAll("</*span>", "");
        }
        if (footer != null) {
            body += divider;
            body += createTag(createTag(footer.toString(), "center", null),
                    "div", "style=\"font-size:12px !important\"");
        }
        return body;
    }

    public void clearContent() {
        clearBg(content);
        clearP(content);
        clearTable(content);
        clearSpan(content);
        clearImage(content);
    }

    public void clearTable(Element e) {
        // 去掉table的所有属性
        Elements tables = e.select("table");
        Element table = null;
        Elements trs = null;
        Element tr = null;
        Elements tds = null;
        Element td = null;
        for (int i = 0; i < tables.size(); i++) {
            table = tables.get(i);
            removeAttr(table);
            table.attr(
                    "style",
                    "border-collapse:collapse;border-spacing:0;border-left:1px solid #888;border-top:1px solid #888;background:#efefef;");
            trs = table.select("tr");
            for (int j = 0; j < trs.size(); j++) {
                tr = trs.get(j);
                removeAttr(tr);
                tds = tr.select("td");
                td = null;
                for (int k = 1; k < tds.size(); k++) {
                    td = tds.get(k);
                    clearTd(td);
                }
            }
        }
        Elements tds2 = e.select("td");
        for (int i = 0; i < tds2.size(); i++) {
            clearTd(tds2.get(i));
            tds2.get(i)
                    .attr("style",
                            "border-right:1px solid #888;border-bottom:1px solid #888;");
        }
    }

    public void clearImage(Element e) {
        Elements images = e.select("img");
        for (int i = 0; i < images.size(); i++) {
            Element img = images.get(i);
            if (img != null) {
                Attributes attrs = img.attributes();
                if (attrs != null) {
                    for (Attribute attr : attrs.asList()) {
                        if (!attr.getKey().equals("src"))
                            img.removeAttr(attr.getKey());
                    }
                }
            }
        }
    }

    public void clearBg(Element element) {
        // 去掉所有background属性
        Elements bkcs = element.getElementsByAttribute("background-color");
        for (int i = 0; i < bkcs.size(); i++) {
            bkcs.get(i).removeAttr("background-color");
        }
    }

    public void clearP(Element e) {
        Elements ps = e.select("p");
        Element p = null;
        // 去掉p的所有属性
        for (int i = 0; i < ps.size(); i++) {
            p = ps.get(i);
            removeAttr(p);
        }
    }

    public void clearSpan(Element e) {
        Elements sps2 = e.select("span");
        for (int i = 0; i < sps2.size(); i++) {
            removeAttr(sps2.get(i));
        }
    }

    public void clearTd(Element td) {
        // 记住该格子占用几行
        String rowspan = td.attr("rowSpan");
        String colspan = td.attr("colspan");
        removeAttr(td);
        if (!"".equals(rowspan) && rowspan != null)
            td.attr("rowspan", rowspan);
        if (!"".equals(colspan) && colspan != null)
            td.attr("colspan", colspan);
        td.attr("style", "border:1px solid black");
        Elements spans = td.select("span");
        Element span = null;
        for (int l = 0; l < spans.size(); l++) {
            span = spans.get(l);
            removeAttr(span);
        }
        Elements tdds = td.select("td");
    }

    public void removeAttr(Element e) {
        if (e != null) {
            Attributes attrs = e.attributes();
            if (attrs != null) {
                for (Attribute attr : attrs.asList()) {

                    e.removeAttr(attr.getKey());
                }
            }
        }
    }

    @Override
    public String toString() {
        return "News [time=" + time + ", path=" + path + ", title=" + title
                + ", content=" + content + ", info=" + info + ", footer="
                + footer + "]";
    }
}
