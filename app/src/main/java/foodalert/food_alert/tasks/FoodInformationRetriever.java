package foodalert.food_alert.tasks;

import android.os.AsyncTask;

import foodalert.food_alert.model.FoodItemFetchedEvent;
import foodalert.food_alert.services.FoodService;

import static foodalert.food_alert.services.FoodAlertsBus.SHARED;

/**
* Created by mgazanayi on 21/03/15.
*/
public class FoodInformationRetriever extends AsyncTask<String, Void, FoodItemFetchedEvent> {

    private FoodService foodService;

    @Override
    protected void onPreExecute() {
        foodService = new FoodService();
    }

    protected FoodItemFetchedEvent doInBackground(String... barCodes) {
        return foodService.fetch(barCodes[0]);
    }

    @Override
    protected void onPostExecute(FoodItemFetchedEvent foodItemFetchedEvent) {
        SHARED.bus().post(foodItemFetchedEvent);
    }
}
