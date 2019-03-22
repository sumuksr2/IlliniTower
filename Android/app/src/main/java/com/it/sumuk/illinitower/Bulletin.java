package com.it.sumuk.illinitower;

public class Bulletin {
    public Bulletin(String description, String image, String title) {
        this.title = title;
        this.description = description;
        this.image = image;
    }

    public Bulletin() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    private String title, description, image;


}
