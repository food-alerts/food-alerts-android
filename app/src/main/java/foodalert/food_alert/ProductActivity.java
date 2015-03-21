package foodalert.food_alert;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import foodalert.food_alert.model.FoodItem;


public class ProductActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        String product = getIntent().getStringExtra("product");
        new AsyncTask<String, Void, FoodItem>() {

            @Override
            protected FoodItem doInBackground(String... strings) {
                return FoodItem.builder(strings[0], "Biscotte de ouf")
                        .build();
            }

            @Override
            protected void onPostExecute(FoodItem foodItem) {
                ((TextView) findViewById(R.id.textView2)).setText(foodItem.toString());
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
