package net.webnetworksolutions.mama.slideUpPanel;

/**
 * Created by Benja on 4/4/2018.
 */

public class Counties {
    private String title, countyDesc;
    private int img;

    public Counties() {
    }

    public Counties(String title, String countyDesc, int img) {
        this.title = title;
        this.countyDesc = countyDesc;
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCountyDesc() {
        return countyDesc;
    }

    public void setCountyDesc(String countyDesc) {
        this.countyDesc = countyDesc;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }
}
