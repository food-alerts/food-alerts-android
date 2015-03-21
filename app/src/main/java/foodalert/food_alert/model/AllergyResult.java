package foodalert.food_alert.model;

import android.graphics.Bitmap;

public class AllergyResult {
    private String name;
    private Bitmap icon;

    public AllergyResult(String name, Bitmap icon) {
        this.name = name;
        this.icon = icon;
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
}
