package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    private static final String NAME = "name";
    private static final String MAIN_NAME = "mainName";
    private static final String ALSO_KNOWN_AS = "alsoKnownAs";
    private static final String PLACE_OF_ORIGIN = "placeOfOrigin";
    private static final String DESCRIPTION = "description";
    private static final String IMAGE = "image";
    private static final String INGREDIENTS = "ingredients";

    public static Sandwich parseSandwichJson(String json) {
        if (json == null) {
            return null;
        }

        Sandwich sandwich = new Sandwich();

        try {
            JSONObject sandwichJson = new JSONObject(json);

            if (sandwichJson != null) {
                JSONObject name = sandwichJson.getJSONObject(NAME);
                if (name != null) {
                    sandwich.setMainName(name.getString(MAIN_NAME));
                    sandwich.setAlsoKnownAs(jsonArrayToList(name.getJSONArray(ALSO_KNOWN_AS)));
                }

                sandwich.setPlaceOfOrigin(sandwichJson.getString(PLACE_OF_ORIGIN));
                sandwich.setDescription(sandwichJson.getString(DESCRIPTION));
                sandwich.setImage(sandwichJson.getString(IMAGE));
                sandwich.setIngredients(jsonArrayToList(sandwichJson.getJSONArray(INGREDIENTS)));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d(JsonUtils.class.getName(), "returning: "  + sandwich.getMainName());
        return sandwich;
    }

    private static List<String> jsonArrayToList(JSONArray jsonArray) throws JSONException {
        List<String> list = new ArrayList<String>();
        for(int i = 0; i < jsonArray.length(); i++){
            list.add(jsonArray.getString(i));
        }
        return list;
    }
}
