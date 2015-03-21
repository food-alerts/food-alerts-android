package foodalert.food_alert.services;

import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;

import foodalert.food_alert.model.FoodItem;

public class FoodService {

    private final HashMap<String, FoodItem> foodItems = new HashMap<>();

    public FoodService() {
        foodItems.put("3400955473350", FoodItem.builder("3400955473350", "Doliprane").build());
        foodItems.put("3400938200577", FoodItem.builder("3400938200577", "Doliprane sans lactose").build());
        foodItems.put("3245390063441", FoodItem.builder("3245390063441", "Choucroute sans lactose").build());
        foodItems.put("3237550029213", FoodItem.builder("324590063441", "Choucroute").build());
    }

    public FoodItem fetch(String barCode) {
        FoodItem foodItem = foodItems.get(barCode);
        if (foodItem != null) {
            return foodItem;
        }

        FoodItem.Builder builder = FoodItem.builder(barCode, "Confiture de cheval");
        try {
            builder.withPictureUri(BitmapFactory.decodeStream((InputStream) new URL("http://fr.openfoodfacts.org/images/products/344/878/110/0002/front.10.200.jpg").getContent()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return builder.build();
    }
}
