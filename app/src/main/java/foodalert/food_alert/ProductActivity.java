package foodalert.food_alert;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import foodalert.food_alert.adapter.AllergyAdapter;
import foodalert.food_alert.model.AllergyResult;
import foodalert.food_alert.model.FoodItemFetchedEvent;
import foodalert.food_alert.tasks.FoodInformationRetriever;

import static foodalert.food_alert.services.FoodAlertsBus.SHARED;


public class ProductActivity extends ActionBarActivity {

    private boolean ok = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SHARED.bus().register(this);
        setContentView(R.layout.activity_product);

        new FoodInformationRetriever().execute(getIntent().getStringExtra("product"));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_product, menu);
        menu.findItem(R.id.action_settings).setVisible(false);
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

        if (!event.isFound()) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("http://fr.openfoodfacts.org/"));
            startActivity(Intent.createChooser(intent, "Choisir le navigateur"));
        }

        ((TextView) findViewById(R.id.product_name)).setText(event.getName());
        ((TextView) findViewById(R.id.barcode)).setText(event.getBarCode());
        ((ImageView) findViewById(R.id.imageView)).setImageBitmap(event.getPictureUri());

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

        List<AllergyResult> result = new ArrayList<>();
        if (isAllergiqueGluten) {
            result.add(new AllergyResult("Gluten", isOk("Gluten", event)));
        }
        if (isAllergiqueLactose) {
            result.add(new AllergyResult("Lactose", isOk("Lactose", event)));
        }
        if (isAllergiqueOeuf) {
            result.add(new AllergyResult("Oeuf", isOk("Oeuf", event)));
        }
        if (isAllergiqueCrustaces) {
            result.add(new AllergyResult("Crustacés", isOk("Crustacés", event)));
        }
        if (isAllergiquePoisson) {
            result.add(new AllergyResult("Poisson", isOk("Poisson", event)));
        }
        if (isAllergiqueArachide) {
            result.add(new AllergyResult("Arachide", isOk("Arachide", event)));
        }
        if (isAllergiqueSesame) {
            result.add(new AllergyResult("Sésame", isOk("Sésame", event)));
        }
        View viewById = findViewById(R.id.backTitle);

        // hide alternative
        findViewById(R.id.txtAlternativesTitle).setAlpha(0);
        findViewById(R.id.layAlter).setAlpha(0);

        if (ok) {
            viewById.setBackgroundColor(Color.parseColor("#62C747"));

        } else {
            if (event.getAlternative() != null) {
                findViewById(R.id.txtAlternativesTitle).setAlpha(1);
                findViewById(R.id.layAlter).setAlpha(1);
                ImageView imageAlter = (ImageView) findViewById(R.id.imgAlter);
                imageAlter.setImageBitmap(event.getAlternative().getPictureUri());
                ((TextView)findViewById(R.id.txtAlter)).setText(event.getAlternative().getName());

            }
            viewById.setBackgroundColor(Color.parseColor("#ff2a15"));
        }

        Collections.sort(result, new Comparator<AllergyResult>() {
            @Override
            public int compare(AllergyResult lhs, AllergyResult rhs) {
                if (lhs.isOk() == rhs.isOk()) {
                    return 0;
                }
                if (rhs.isOk() == true) {
                    return -1;
                }
                return 1;
            }
        });

        listView.setAdapter(new AllergyAdapter(this, result));
    }

    private boolean isOk(String element, FoodItemFetchedEvent event) {
        if (event.getElements().contains(element)) {
            ok = false;
            return false;
        }
        return true;
    }
}
