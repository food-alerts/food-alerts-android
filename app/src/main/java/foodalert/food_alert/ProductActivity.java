package foodalert.food_alert;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

import foodalert.food_alert.adapter.AllergyAdapter;
import foodalert.food_alert.model.AllergyResult;
import foodalert.food_alert.model.FoodItemFetchedEvent;
import foodalert.food_alert.tasks.FoodInformationRetriever;

import static foodalert.food_alert.services.FoodAlertsBus.SHARED;


public class ProductActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SHARED.bus().register(this);
        setContentView(R.layout.activity_product);

        String product = getIntent().getStringExtra("product");

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);

        //Recup pref utilisateur
        Boolean isAllergiqueLactose = preferences.getBoolean("pref_lactose", false);
        Boolean isAllergiqueGluten = preferences.getBoolean("pref_gluten", false);
        Boolean isAllergiqueOeuf = preferences.getBoolean("pref_oeuf", false);
        Boolean isAllergiqueCrustaces = preferences.getBoolean("pref_crustaces", false);
        Boolean isAllergiquePoisson = preferences.getBoolean("pref_poisson", false);
        Boolean isAllergiqueArachide = preferences.getBoolean("pref_arachide", false);
        Boolean isAllergiqueSesame = preferences.getBoolean("pref_sesame", false);

        final ListView listView = (ListView) findViewById(R.id.allergies_list);

        List<AllergyResult> allergies = new ArrayList<>();
        if (isAllergiqueGluten) {
            allergies.add(new AllergyResult("Gluten", ImgLoader.readImgClasspath("/tick.png")));
        }
        if (isAllergiqueLactose) {
            allergies.add(new AllergyResult("Lactose", ImgLoader.readImgClasspath("/tack.png")));
        }
        if (isAllergiqueOeuf) {
            allergies.add(new AllergyResult("Oeuf", ImgLoader.readImgClasspath("/tick.png")));
        }
        if (isAllergiqueCrustaces) {
            allergies.add(new AllergyResult("Crustacés", ImgLoader.readImgClasspath("/tick.png")));
        }
        if (isAllergiquePoisson) {
            allergies.add(new AllergyResult("Poisson", ImgLoader.readImgClasspath("/tack.png")));
        }
        if (isAllergiqueArachide) {
            allergies.add(new AllergyResult("Arachide", ImgLoader.readImgClasspath("/tick.png")));
        }
        if (isAllergiqueSesame) {
            allergies.add(new AllergyResult("Sésame", ImgLoader.readImgClasspath("/tack.png")));
        }
        listView.setAdapter(new AllergyAdapter(this, allergies));

        new FoodInformationRetriever().execute(product);
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

    @Subscribe
    public void foodItemFetched(FoodItemFetchedEvent event) {
        ((TextView) findViewById(R.id.product_name)).setText(event.getName());
        ((TextView) findViewById(R.id.barcode)).setText(event.getBarCode());
        ((ImageView) findViewById(R.id.imageView)).setImageBitmap(event.getPictureUri());
    }

}
