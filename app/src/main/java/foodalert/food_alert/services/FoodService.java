package foodalert.food_alert.services;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;

import foodalert.food_alert.model.FoodItem;

public class FoodService {

    private final HashMap<String, FoodItem> foodItems = new HashMap<>();

    public FoodItem fetch(String barCode) {
        populate();
        FoodItem foodItem = foodItems.get(barCode);
        if (foodItem != null) {
            return foodItem;
        }

        return FoodItem.builder(barCode, "Confiture de cheval")
                .withPictureUri(decodePicture("http://jeremybyrne.info/Areas/Science/Images/imgUnavailable.png"))
                .build();
    }

    private void populate() {
        foodItems.put("3400955473350", FoodItem.builder("3400955473350", "Doliprane")
                .withPictureUri(decodePicture("http://www.parapharmadirect.com/files/catalog/products/wmproductbig/doliprane-1000-8cps.jpg"))
                .build());
        foodItems.put("3400938200577", FoodItem.builder("3400938200577", "Doliprane sans lactose")
                .withPictureUri(decodePicture("http://www.parapharmadirect.com/files/catalog/products/wmproductbig/doliprane-sirop.jpg"))
                .build());
        foodItems.put("3245390063441", FoodItem.builder("3245390063441", "Choucroute sans lactose")
                .withPictureUri(decodePicture("http://fr.openfoodfacts.org/images/products/324/539/006/3441/front.4.200.jpg"))
                .build());
        foodItems.put("3237550029213", FoodItem.builder("3245390063441", "Choucroute")
                .withPictureUri(decodePicture("http://fr.openfoodfacts.org/images/products/324/539/006/3441/front.4.200.jpg"))
                .build());
    }

    private Bitmap decodePicture(String uri) {
        try {
            return BitmapFactory.decodeStream((InputStream) new URL(uri).getContent());
        } catch (IOException e) {
            return null;
        }
    }
}
