package foodalert.food_alert.services;


import com.fasterxml.jackson.jr.ob.JSON;
import com.google.common.base.Optional;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import foodalert.food_alert.ImgLoader;
import foodalert.food_alert.model.Cip13;
import foodalert.food_alert.model.FoodItem;
import foodalert.food_alert.model.FoodItemFetchedEvent;

public final class RemoteFoodService {

    private final OkHttpClient httpClient = new OkHttpClient();

    public FoodItemFetchedEvent fetch(String barCode) {
        final Optional<FoodItem> foodItem = search(new Cip13(barCode));
        if (foodItem.isPresent()) {
            final FoodItem item = foodItem.get();
            Collection<String> ingredients = new ArrayList<>();
            ingredients.addAll(item.ingredients());
            ingredients.addAll(item.allergens());
            return FoodItemFetchedEvent.builder(item.cip13(), item.name())
                    .withPictureUri(ImgLoader.readImgClasspath("/notfound.png"))
                    .withFound(true)
                    .withIngredients(ingredients)
                    .build();
        }
        return FoodItemFetchedEvent.builder(barCode, "Produit inconnu")
                    .withPictureUri(ImgLoader.readImgClasspath("/notfound.png"))
                    .withFound(false)
                    .build();
    }

    public Optional<FoodItem> search(Cip13 cip13) {
        String url = "http://fr.openŒfoodfacts.org/api/v0/produit/" + cip13.code() + ".json";
        try {
            Optional<String> json = call(url);
            if (json.isPresent()) {
                return foodItem(json.get());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.absent();
    }

    private Optional<String> call(String url) throws IOException {
        Request request = new Request.Builder().get().url(url).build();
        Response response = httpClient.newCall(request).execute();
        return response.code() == 200 ? Optional.of(response.body().string()) : Optional.<String>absent();
    }

    private static Optional<FoodItem> foodItem(String json) throws Exception {
        final Map<String, Object> map = JSON.std.mapFrom(json);
        if (map.containsValue("product not found")) {
            return Optional.absent();
        }
        final Map<String, String> product = (Map<String, String>) map.get("product");
        return Optional.of(new FoodItem(
                fromString(product, "_id"),
                fromString(product, "product_name"),
                fromStrings(product, "ingredients_text"),
                fromStrings(product, "allergens"),
                fromString(product, "image_url")));
    }

    private static String fromString(final Map<String, String> product, final String key) {
        return product.containsKey("_id") ? product.get("_id") : "";
    }

    private static List<String> fromStrings(Map<String, String> product, final String key) {
        final List<String> allergens = new ArrayList<>();
        final String allergensAsString = product.containsKey(key) ? product.get(key) : "";
        final Scanner allergensScanner = new Scanner(allergensAsString);
        allergensScanner.useDelimiter(", ");
        while (allergensScanner.hasNext()) {
            allergens.add(allergensScanner.next());
        }
        return allergens;
    }

}
