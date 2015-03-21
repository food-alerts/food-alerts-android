package foodalert.food_alert.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import foodalert.food_alert.R;
import foodalert.food_alert.model.AllergyResult;

public class AllergyAdapter extends ArrayAdapter {
    Context c;

    List<AllergyResult> content;

    public AllergyAdapter(Context c, List<AllergyResult> content) {
        super(c, R.layout.allergie_list_item, content);
        this.c = c;
        this.content = content;
    }

    @Override
    public View getView(int itempos, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View listrowView = inflater.inflate(R.layout.allergie_list_item, parent, false);

        TextView txtcontent = (TextView) listrowView.findViewById(R.id.list_item_allergies_textview);
        ImageView imgcontent = (ImageView) listrowView.findViewById(R.id.list_item_allergies_imgview);

        AllergyResult item = content.get(itempos);

        txtcontent.setText(item.getName());
        imgcontent.setImageBitmap(item.getIcon());

        return listrowView;
    }
}