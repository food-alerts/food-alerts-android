package foodalert.food_alert.tasks;

import android.os.AsyncTask;

import foodalert.food_alert.model.FoodItemFetchedEvent;
import foodalert.food_alert.services.FoodService;
import foodalert.food_alert.services.RemoteFoodService;

import static foodalert.food_alert.services.FoodAlertsBus.SHARED;

public class FoodInformationRetriever extends AsyncTask<String, Void, FoodItemFetchedEvent> {

    private FoodService foodService;
    private RemoteFoodService remoteFoodService;

    @Override
    protected void onPreExecute() {
        foodService = new FoodService();
        remoteFoodService = new RemoteFoodService();
    }

    protected FoodItemFetchedEvent doInBackground(String... barCodes) {
        final FoodItemFetchedEvent fetch = foodService.fetch(barCodes[0]);
        return fetch.isFound() ? fetch : remoteFoodService.fetch(barCodes[0]);
    }

    @Override
    protected void onPostExecute(FoodItemFetchedEvent foodItemFetchedEvent) {
        SHARED.bus().post(foodItemFetchedEvent);
    }
}
