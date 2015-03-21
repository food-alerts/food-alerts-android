package foodalert.food_alert.services;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;

import foodalert.food_alert.ImgLoader;
import foodalert.food_alert.model.FoodItemFetchedEvent;

public class FoodService {

    private final HashMap<String, FoodItemFetchedEvent> foodItems = new HashMap<>();

    public FoodItemFetchedEvent fetch(String barCode) {
        populate();
        FoodItemFetchedEvent foodItemFetchedEvent = foodItems.get(barCode);
        if (foodItemFetchedEvent != null) {
            return foodItemFetchedEvent;
        }

        return FoodItemFetchedEvent.builder(barCode, "Confiture de cheval")
                .withPictureUri(ImgLoader.readImgClasspath("/notfound.png"))
                .build();
    }

    private void populate() {
        foodItems.put("3400955473350", FoodItemFetchedEvent.builder("3400955473350", "Doliprane")
                .withPictureUri(decodePicture("http://www.parapharmadirect.com/files/catalog/products/wmproductbig/doliprane-1000-8cps.jpg"))
                .withElements("Lactose")
                .build());
        foodItems.put("3400938200577", FoodItemFetchedEvent.builder("3400938200577", "Doliprane sans lactose")
                .withPictureUri(decodePicture("http://www.parapharmadirect.com/files/catalog/products/wmproductbig/doliprane-sirop.jpg"))
                .build());
        foodItems.put("3245390063441", FoodItemFetchedEvent.builder("3245390063441", "Choucroute sans lactose")
                .withPictureUri(decodePicture("http://fr.openfoodfacts.org/images/products/324/539/006/3441/front.4.200.jpg"))
                .build());
        foodItems.put("3237550029213", FoodItemFetchedEvent.builder("3245390063441", "Choucroute")
                .withPictureUri(decodePicture("http://fr.openfoodfacts.org/images/products/324/539/006/3441/front.4.200.jpg"))
                .withElements("Lactose")
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
