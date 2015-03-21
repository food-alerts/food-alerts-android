package foodalert.food_alert;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class ImgLoader {

    public static Bitmap readImgClasspath(String fileName) {
        return BitmapFactory.decodeStream(MainActivity.class.getClassLoader().getResourceAsStream("res/drawable" + fileName));
    }
}
