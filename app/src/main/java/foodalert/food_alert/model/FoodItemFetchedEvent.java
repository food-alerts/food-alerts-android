package foodalert.food_alert.model;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class FoodItemFetchedEvent {
    private final boolean found;
    private final String barCode;
    private final String name;
    private final Bitmap pictureUri;
    private final Collection<String> traces;
    private final Collection<String> ingredients;
    private final List<String> elements;
    private final FoodItemFetchedEvent alternative;

    private FoodItemFetchedEvent(Builder builder) {
        this.barCode = builder.barCode;
        this.name = builder.name;
        this.pictureUri = builder.pictureUri;
        this.traces = builder.traces;
        this.ingredients = builder.ingredients;
        this.elements = Arrays.asList(builder.elements);
        this.found = builder.found;
        this.alternative = builder.alternative;
    }

    public static Builder builder(String barCode, String name) {
        return new Builder(barCode, name);
    }

    public String getBarCode() {
        return barCode;
    }

    public String getName() {
        return name;
    }

    public Bitmap getPictureUri() {
        return pictureUri;
    }

    public Collection<String> getTraces() {
        return traces;
    }

    public Collection<String> getIngredients() {
        return ingredients;
    }

    public List<String> getElements() {
        return elements;
    }

    public boolean isFound() {
        return found;
    }

    public FoodItemFetchedEvent getAlternative() {
        return alternative;
    }

    @Override
    public String toString() {
        return "FoodItem{" +
                "barCode='" + barCode + '\'' +
                ", name='" + name + '\'' +
                ", pictureUri='" + pictureUri + '\'' +
                ", traces=" + traces +
                ", ingredients=" + ingredients +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FoodItemFetchedEvent foodItemFetchedEvent = (FoodItemFetchedEvent) o;

        if (barCode != null ? !barCode.equals(foodItemFetchedEvent.barCode) : foodItemFetchedEvent.barCode != null)
            return false;
        if (ingredients != null ? !ingredients.equals(foodItemFetchedEvent.ingredients) : foodItemFetchedEvent.ingredients != null)
            return false;
        if (name != null ? !name.equals(foodItemFetchedEvent.name) : foodItemFetchedEvent.name != null) return false;
        if (pictureUri != null ? !pictureUri.equals(foodItemFetchedEvent.pictureUri) : foodItemFetchedEvent.pictureUri != null)
            return false;
        if (traces != null ? !traces.equals(foodItemFetchedEvent.traces) : foodItemFetchedEvent.traces != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = barCode != null ? barCode.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (pictureUri != null ? pictureUri.hashCode() : 0);
        result = 31 * result + (traces != null ? traces.hashCode() : 0);
        result = 31 * result + (ingredients != null ? ingredients.hashCode() : 0);
        return result;
    }

    public static class Builder {
        private boolean found = true;
        private String barCode;
        private String name;
        private Bitmap pictureUri; //TODO: default pic
        private Collection<String> traces = new ArrayList<>();
        private Collection<String> ingredients = new ArrayList<>();
        private String[] elements = new String[0];
        private FoodItemFetchedEvent alternative;

        private Builder(String barCode, String name) {
            this.barCode = barCode;
            this.name = name;
        }
    
        public Builder withPictureUri(Bitmap pictureUri) {
            this.pictureUri = pictureUri;
            return this;
        }
    
        public Builder withTraces(Collection<String> traces) {
            this.traces = traces;
            return this;
        }

        public Builder withIngredients(Collection<String> ingredients) {
            this.ingredients = ingredients;
            return this;
        }

        public Builder withElements(String... elements) {
            this.elements = elements;
            return this;
        }

        public Builder withFound(boolean found) {
            this.found = found;
            return this;
        }

        public Builder withAlternative(FoodItemFetchedEvent element) {
            this.alternative = element;
            return this;
        }

        public FoodItemFetchedEvent build() {
            if (barCode == null) {
                throw new IllegalStateException("null barCode");
            }
            if (name == null) {
                throw new IllegalStateException("null name");
            }
            if (traces == null) {
                throw new IllegalStateException("null traces");
            }
            if (ingredients == null) {
                throw new IllegalStateException("null ingredients");
            }
            return new FoodItemFetchedEvent(this);
        }
    }
}
