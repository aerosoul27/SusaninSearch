package me.alexeyterekhov.susaninsearch.Client.API2Gis;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import me.alexeyterekhov.susaninsearch.Data.Firm;

import java.util.ArrayList;
import java.util.List;

public class JsonParser2Gis {
    public static Firm parseFirm(JsonObject firmJson) {
        if (!firmJson.has("id")
                || !firmJson.has("hash")
                || !firmJson.has("name"))
            return null;

        String firmId = firmJson.get("id").getAsString();
        String firmHash = firmJson.get("hash").getAsString();
        String firmName = firmJson.get("name").getAsString();
        String firmCity = null;
        if (firmJson.has("city_name"))
            firmCity = firmJson.get("city_name").getAsString();
        String firmAddress = null;
        if (firmJson.has("address"))
            firmAddress = firmJson.get("address").getAsString();

        Firm firm = new Firm(firmId, firmHash);
        firm.setName(firmName);
        StringBuilder name = new StringBuilder("");
        if (firmCity != null)
            name.append(firmCity);
        if (firmAddress != null) {
            if (name.length() > 0)
                name.append(", ");
            name.append(firmAddress);
        }
        firm.setAddress(name.toString());

        return firm;
    }

    public static List<Firm> parseFirms(String response) {
        List<Firm> out = new ArrayList<>();

        JsonObject json = new JsonParser().parse(response).getAsJsonObject();
        JsonArray firms = json.getAsJsonArray("result");
        if (firms != null && firms.size() > 0)
            for (JsonElement el : firms) {
                JsonObject firmJson = el.getAsJsonObject();
                out.add(parseFirm(firmJson));
            }

        return out;
    }

    public static Float extractRating(String firmDetails) {
        JsonObject json = new JsonParser().parse(firmDetails).getAsJsonObject();
        if (json.has("rating"))
            return json.get("rating").getAsFloat();
        return null;
    }
}
