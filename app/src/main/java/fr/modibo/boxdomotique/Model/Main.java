package fr.modibo.boxdomotique.Model;

public class Main {

    private String title;
    private String info;
    private int image;

    public Main(String title, String info, int image) {
        this.title = title;
        this.info = info;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
