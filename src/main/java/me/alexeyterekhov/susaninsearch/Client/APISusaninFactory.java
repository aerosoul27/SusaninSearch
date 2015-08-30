package me.alexeyterekhov.susaninsearch.Client;

import me.alexeyterekhov.susaninsearch.Client.API2Gis.API2Gis;
import me.alexeyterekhov.susaninsearch.Client.API2Gis.API2GisFactory;
import me.alexeyterekhov.susaninsearch.Client.API2Gis.JsonParser2Gis;
import me.alexeyterekhov.susaninsearch.Data.Firm;

import java.util.List;

public class APISusaninFactory {
    private static APISusanin api = null;

    public static APISusanin getApi() {
        if (api == null)
            api = createApi();
        return api;
    }

    private static APISusanin createApi() {
        return new APISusanin() {
            @Override
            public Firm findMostPopular(String what, String where) {
                API2Gis api2Gis = API2GisFactory.getApi();

                List<Firm> popularFirms = api2Gis.searchPopularFirms(what, where);
                Firm mostPopular = null;

                if (popularFirms != null && !popularFirms.isEmpty()) {
                    mostPopular = popularFirms.get(0);
                    String details = api2Gis.getFirmDetails(mostPopular.getId(), mostPopular.getHash());
                    if (details != null) {
                        Float rating = JsonParser2Gis.extractRating(details);
                        if (rating != null)
                            mostPopular.setFlampRating(rating);
                    }
                }

                return mostPopular;
            }
        };
    }
}
