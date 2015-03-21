package foodalert.food_alert.model;

import android.graphics.Bitmap;

import foodalert.food_alert.ImgLoader;

public class AllergyResult {
    private boolean status;
    private String name;
    private Bitmap icon;

    public AllergyResult(String name, boolean isOk) {
        this.name = name;
        this.status = isOk;
        this.icon = isOk ? ImgLoader.readImgClasspath("/tick.png") : ImgLoader.readImgClasspath("/tack.png");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Bitmap getIcon() {
        return icon;
    }

    public void setIcon(Bitmap icon) {
        this.icon = icon;
    }

    public boolean isOk() {
        return status;
    }
}
