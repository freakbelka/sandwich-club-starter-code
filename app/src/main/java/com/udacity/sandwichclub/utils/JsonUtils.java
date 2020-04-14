package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        try {
            JSONObject sandwichJson = new JSONObject(json);
            JSONObject nameObj = sandwichJson.getJSONObject("name");
            JSONArray alsoKnownAs = nameObj.getJSONArray("alsoKnownAs");
            JSONArray ingredients = sandwichJson.getJSONArray("ingredients");

            List<String> parsedAlsoKnownAs = new ArrayList<>();
            for (int i = 0; i < alsoKnownAs.length(); i++) {
                parsedAlsoKnownAs.add((String) alsoKnownAs.get(i));
            }

            List<String> parsedIngredients = new ArrayList<>();
            for (int i = 0; i < ingredients.length(); i++) {
                parsedIngredients.add((String) ingredients.get(i));
            }
            return new Sandwich(nameObj.getString("mainName"),
                    parsedAlsoKnownAs,
                    sandwichJson.getString("placeOfOrigin"),
                    sandwichJson.getString("description"),
                    sandwichJson.getString("image"),
                    parsedIngredients);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}
