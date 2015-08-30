package me.alexeyterekhov.susaninsearch.Client.API2Gis;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.HttpRequest;
import me.alexeyterekhov.susaninsearch.Data.Firm;

import java.util.List;

public class API2GisFactory {
    private static API2Gis api = null;

    public static API2Gis getApi() {
        if (api == null)
            api = createApi();
        return api;
    }

    private static API2Gis createApi() {
        return new API2Gis() {
            private static final String key = "ruuxah6217";

            @Override
            public List<Firm> searchPopularFirms(String what, String where) {
                List<Firm> out = null;

                try {
                    HttpResponse<JsonNode> response = getRequestBase("http://catalog.api.2gis.ru/search")
                            .queryString("what", what)
                            .queryString("where", where)
                            .queryString("sort", "rating")
                            .asJson();

                    String textResponse = response.getBody().toString();
                    out = JsonParser2Gis.parseFirms(textResponse);
                } catch (UnirestException e) {
                    e.printStackTrace();
                }

                return out;
            }

            @Override
            public String getFirmDetails(String firmId, String firmHash) {
                String out = null;

                try {
                    HttpResponse<JsonNode> response = getRequestBase("http://catalog.api.2gis.ru/profile")
                            .queryString("id", firmId)
                            .queryString("hash", firmHash)
                            .asJson();
                    out = response.getBody().toString();
                } catch (UnirestException e) {
                    e.printStackTrace();
                }

                return out;
            }

            private HttpRequest getRequestBase(String url) {
                return Unirest.get(url)
                        .queryString("key", key)
                        .queryString("version", "1.3");
            }
        };
    }
}
