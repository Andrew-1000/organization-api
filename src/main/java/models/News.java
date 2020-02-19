package models;

import java.util.Objects;

public class News {


    private int news_id;
    private String news_details;

    public News(String news_details) {
        this.news_details = news_details;
    }

    public int getNews_id() {
        return news_id;
    }

    public void setNews_id(int news_id) {
        this.news_id = news_id;
    }

    public String getNews_details() {
        return news_details;
    }

    public void setNews_details(String news_details) {
        this.news_details = news_details;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof News)) return false;
        News news = (News) o;
        return news_id == news.news_id &&
                Objects.equals(news_details, news.news_details);
    }

    @Override
    public int hashCode() {
        return Objects.hash(news_id, news_details);
    }
}
