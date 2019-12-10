package com.cryptodev.cryptopedia.NewsBackEnd;

public class News {

    private String title;
    private String description;
    private String imageLink;
    private String link;
    public String getLink() {
        return link;
    }

    public News(String title, String description, String link,String imageLink) {
        this.title = title;
        this.description = description;
        this.imageLink= imageLink;
        this.link = link;
    }


    public String getImageLink(){return imageLink;}


    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

}
