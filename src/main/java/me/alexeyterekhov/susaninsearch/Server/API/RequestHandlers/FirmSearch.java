package me.alexeyterekhov.susaninsearch.Server.API.RequestHandlers;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import me.alexeyterekhov.susaninsearch.Client.APISusanin;
import me.alexeyterekhov.susaninsearch.Client.APISusaninFactory;
import me.alexeyterekhov.susaninsearch.Common.ExecutorFactory;
import me.alexeyterekhov.susaninsearch.Data.Firm;
import me.alexeyterekhov.susaninsearch.Server.API.RequestHandler;
import me.alexeyterekhov.susaninsearch.Server.API.WebMethodResponse;

import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

public class FirmSearch extends RequestHandler {
    @Override
    protected WebMethodResponse handleRequest(Map<String, String> params) {
        if (!params.containsKey("service"))
            return new WebMethodResponse(400, "Parameter 'service' must be specified");
        final String service = params.get("service");

        final APISusanin api = APISusaninFactory.getApi();
        final Vector<Firm> results = new Vector<>();
        final ArrayList<String> places = new ArrayList<>();
        places.add("Новосибирск");
        places.add("Омск");
        places.add("Томск");
        places.add("Кемерово");
        places.add("Новокузнецк");

        final CountDownLatch latch = new CountDownLatch(places.size());
        Executor executor = ExecutorFactory.getRequestExecutor();
        for (final String place : places) {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    Firm mostPopular = api.findMostPopular(service, place);
                    if (mostPopular != null)
                        results.add(mostPopular);
                    latch.countDown();
                }
            });
        }

        try {
            latch.await(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Gson gson = new Gson();
        JsonObject response = new JsonObject();
        response.addProperty("count", results.size());

        if (results.size() > 0) {
            Collections.sort(results, new Comparator<Firm>() {
                @Override
                public int compare(Firm o1, Firm o2) {
                    float r1 = 0;
                    float r2 = 0;

                    if (o1.getFlampRating() != null)
                        r1 = o1.getFlampRating();
                    if (o2.getFlampRating() != null)
                        r2 = o2.getFlampRating();

                    if (r1 == r2)
                        return 0;
                    else if (r1 > r2)
                        return -1;
                    else
                        return 1;
                }
            });

            JsonElement firms = gson.toJsonTree(results);
            response.add("firms", firms);
        } else {
            response.add("firms", new JsonArray());
        }
        return new WebMethodResponse(200, response.toString());
    }
}
