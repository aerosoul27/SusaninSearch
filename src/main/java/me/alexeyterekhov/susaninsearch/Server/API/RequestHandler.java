package me.alexeyterekhov.susaninsearch.Server.API;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

public abstract class RequestHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        Map<String, String> params = extractHttpParams(httpExchange);

        String encoding = "UTF-8";
        WebMethodResponse response = handleRequest(params);
        byte[] bytes = response.getResponse().getBytes(Charset.forName(encoding));

        httpExchange.getResponseHeaders().set("Content-Type", "text/html; charset=" + encoding);

        httpExchange.sendResponseHeaders(response.getCode(), bytes.length);
        OutputStream os = httpExchange.getResponseBody();
        os.write(bytes);
        os.flush();
        os.close();
    }

    private Map<String, String> extractHttpParams(HttpExchange exchange) {
        Map<String, String> out = new HashMap<>();

        String GETQuery = exchange.getRequestURI().getQuery();
        if (GETQuery != null && !GETQuery.equals("")) {
            for (String param : GETQuery.split("&")) {
                String pair[] = param.split("=");
                if (pair.length > 1)
                    out.put(pair[0], pair[1]);
                else
                    out.put(pair[0], "");
            }
        }

        return out;
    }

    protected abstract WebMethodResponse handleRequest(Map<String, String> params);
}
