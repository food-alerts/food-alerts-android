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
                .withPictureUri(decodePicture("http://www.pharma-medicaments.com/media/3638186__043056800_1650_25042013.jpg"))
                .build();
        foodItems.put("3400955473350", FoodItemFetchedEvent.builder("3400955473350", "Doliprane")
                .withPictureUri(decodePicture("http://www.parapharmadirect.com/files/catalog/products/wmproductbig/doliprane-1000-8cps.jpg"))
                .withElements("Lactose")
                .withAlternative(dolipraneWithoutLactose)
                .build());
        foodItems.put("3400938200577", dolipraneWithoutLactose);
        FoodItemFetchedEvent choucrouteWithoutLactose = FoodItemFetchedEvent.builder("3245390063441", "Choucroute (sans lactose)")
                .withPictureUri(decodePicture("http://fr.openfoodfacts.org/images/products/324/539/006/3441/front.4.200.jpg"))
                .build();
        foodItems.put("3245390063441", choucrouteWithoutLactose);
        foodItems.put("3237550029213", FoodItemFetchedEvent.builder("3237550029213", "Choucroute")
                .withPictureUri(decodePicture("http://fr.openfoodfacts.org/images/products/323/755/002/9213/front.6.200.jpg"))
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
