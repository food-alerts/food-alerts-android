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

        return FoodItemFetchedEvent.builder(barCode, "Produit inconnu")
                .withPictureUri(ImgLoader.readImgClasspath("/notfound.png"))
                .withFound(false)
                .build();
    }

    private void populate() {
        FoodItemFetchedEvent dolipraneWithoutLactose = FoodItemFetchedEvent.builder("3400938200577", "Paracétamol (sans lactose)")
                .withPictureUri(ImgLoader.readImgClasspath("/paracetamol.jpg"))
                .build();
        foodItems.put("3400955473350", FoodItemFetchedEvent.builder("3400955473350", "Doliprane")
                .withPictureUri(ImgLoader.readImgClasspath("/doliprane.jpg"))
                .withElements("Lactose")
                .withAlternative(dolipraneWithoutLactose)
                .build());
        foodItems.put("3400938200577", dolipraneWithoutLactose);
        FoodItemFetchedEvent choucrouteWithoutLactose = FoodItemFetchedEvent.builder("3245390063441", "Choucroute (sans lactose)")
                .withPictureUri(ImgLoader.readImgClasspath("/choucroute_without_lactose.jpg"))
                .build();
        foodItems.put("3245390063441", choucrouteWithoutLactose);
        foodItems.put("3237550029213", FoodItemFetchedEvent.builder("3237550029213", "Choucroute")
                .withPictureUri(ImgLoader.readImgClasspath("/choucroute.jpg"))
                .withAlternative(choucrouteWithoutLactose)
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
