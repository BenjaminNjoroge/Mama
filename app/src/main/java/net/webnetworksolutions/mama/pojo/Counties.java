package net.webnetworksolutions.mama.pojo;

/**
 * Created by Benja on 4/4/2018.
 */

public class Counties {
    private String title, hospitals;
    private int img;

    public Counties(){
        //Empty constructor
    }
    public Counties(String title, String hospitals, int img) {
        this.title = title;
        this.hospitals = hospitals;
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHospitals() {
        return hospitals;
    }

    public void setHospitals(String hospitals) {
        this.hospitals = hospitals;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }
}
