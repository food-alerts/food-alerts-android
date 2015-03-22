package foodalert.food_alert.model;

import java.util.Collection;

public final class FoodItem {

    private final String cip13;
    private final String name;
    private final Collection<String> ingredients;
    private final Collection<String> allergens;
    private final String imageUrl;

    public FoodItem(String cip13, String name, Collection<String> ingredients, Collection<String> allergens, String imageUrl) {
        this.cip13 = cip13;
        this.imageUrl = imageUrl;
        this.allergens = allergens;
        this.name = name;
        this.ingredients = ingredients;
    }

    public String cip13() {
        return cip13;
    }

    public String name() {
        return name;
    }

    public Collection<String> ingredients() {
        return ingredients;
    }

    public Collection<String> allergens() {
        return allergens;
    }

    public String imageUrl() {
        return imageUrl;
    }


}
