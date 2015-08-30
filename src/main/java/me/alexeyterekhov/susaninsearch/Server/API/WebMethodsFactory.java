package me.alexeyterekhov.susaninsearch.Server.API;

import com.sun.net.httpserver.HttpHandler;
import me.alexeyterekhov.susaninsearch.Server.API.RequestHandlers.FirmSearch;

import java.util.ArrayList;
import java.util.List;

public class WebMethodsFactory {
    private static String apiPrefix = "/api/";
    private static List<WebMethod> methods = null;

    public static List<WebMethod> getMethods() {
        if (methods == null) {
            methods = new ArrayList<>();
            fillMethods();
        }
        return methods;
    }

    public static void setApiPrefix(String prefix) {
        apiPrefix = prefix;
    }

    private static void fillMethods() {
        addMethod("firm_search", new FirmSearch());
    }

    private static void addMethod(String name, HttpHandler handler) {
        WebMethod method = new WebMethod(apiPrefix + name, handler);
        methods.add(method);
    }
}
