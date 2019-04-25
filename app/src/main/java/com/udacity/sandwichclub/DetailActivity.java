package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
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

        populateUI();
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        // populate AlsoKnownAs text view
        TextView alsoKnownTV = findViewById(R.id.also_known_tv);
        alsoKnownTV.setText(listToCommaString(sandwich.getAlsoKnownAs()));

        // populate Ingredients text view
        TextView ingredientsTV = findViewById(R.id.ingredients_tv);
        ingredientsTV.setText(listToCommaString(sandwich.getIngredients()));

        // populate Description text view
        TextView descriptionTV = findViewById(R.id.description_tv);
        descriptionTV.setText(sandwich.getDescription());

        // populate Origin text view
        TextView originTV = findViewById(R.id.origin_tv);
        originTV.setText(sandwich.getPlaceOfOrigin());

        setTitle(sandwich.getMainName());
    }

    private String listToCommaString(List<String> list) {
        StringBuilder builder = new StringBuilder();
        for (String li:list) {
            builder.append(li).append(", ");
        }
        String s = builder.toString();
        return s.replaceAll(", $", "");
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {

    }
}
