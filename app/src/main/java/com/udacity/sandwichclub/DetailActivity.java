package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        populateView(sandwich.getPlaceOfOrigin(), (TextView) findViewById(R.id.origin_tv), (LinearLayout) findViewById(R.id.origin_ll));
        populateView(sandwich.getAlsoKnownAs(), (TextView) findViewById(R.id.also_known_tv), (LinearLayout) findViewById(R.id.also_known_ll));
        populateView(sandwich.getDescription(), (TextView) findViewById(R.id.description_tv), (LinearLayout) findViewById(R.id.description_ll));
        populateView(sandwich.getIngredients(), (TextView) findViewById(R.id.ingredients_tv), (LinearLayout) findViewById(R.id.ingredients_ll));
    }

    private void populateView(Object value, TextView valueTV, LinearLayout valueLL) {
        if (value == null
                || (value instanceof String && value.equals(""))
                || (value instanceof List && ((List) value).size() == 0)) {
            valueLL.setVisibility(View.GONE);
        } else {
            valueLL.setVisibility(View.VISIBLE);
            if (value instanceof String) {
                valueTV.setText((String) value);
            }
            if (value instanceof List) {
                List<String> values = (List<String>) value;
                String prefix = "";
                if (values.size() > 1) {
                    prefix = "\u2022 ";
                }
                for (int i = 0; i < values.size(); i++) {
                    valueTV.append(prefix + values.get(i));
                    if (i != values.size() - 1) {
                        valueTV.append("\n");
                    }
                }
            }
        }
    }
}
