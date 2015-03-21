package foodalert.food_alert.services;

import com.squareup.otto.Bus;

public enum FoodAlertsBus {

    SHARED(new Bus());

    private final Bus bus;
    private FoodAlertsBus(Bus bus) {
        this.bus = bus;
    }

    public Bus bus() {
        return bus;
    }
}
