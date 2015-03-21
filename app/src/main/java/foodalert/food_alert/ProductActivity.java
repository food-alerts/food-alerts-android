package foodalert.food_alert;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import foodalert.food_alert.model.FoodItem;
import foodalert.food_alert.services.FoodService;


public class ProductActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        String product = getIntent().getStringExtra("product");

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        //Recup pref utilisateur
        Boolean isAllergiqueLactose = preferences.getBoolean("pref_lactose", false);
        Boolean isAllergiqueGluten = preferences.getBoolean("pref_gluten", false);

        ListView listView = (ListView) findViewById(R.id.allergies_list);
        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<>(this, R.layout.allergie_list_item);
        if (isAllergiqueGluten) {
            stringArrayAdapter.add("gluten");
        }
        listView.setAdapter(stringArrayAdapter);


        new AsyncTask<String, Void, FoodItem>() {

            private FoodService foodService;

            @Override
            protected void onPreExecute() {
                foodService = new FoodService();
            }

            protected FoodItem doInBackground(String... barCodes) {
                return foodService.fetch(barCodes[0]);
            }

            @Override
            protected void onPostExecute(FoodItem foodItem) {
                ((TextView) findViewById(R.id.product_name)).setText(foodItem.getName());
                ((TextView) findViewById(R.id.barcode)).setText(foodItem.getBarCode());
                ((ImageView) findViewById(R.id.imageView)).setImageBitmap(foodItem.getPictureUri());
            }
        }.execute(product);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_product, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
