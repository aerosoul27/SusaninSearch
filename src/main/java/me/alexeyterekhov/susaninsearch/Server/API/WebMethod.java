package me.alexeyterekhov.susaninsearch.Server.API;

import com.sun.net.httpserver.HttpHandler;

public class WebMethod {
    private String methodName = null;
    private HttpHandler methodHandler = null;

    public WebMethod(String methodName, HttpHandler methodHandler) {
        this.methodName = methodName;
        this.methodHandler = methodHandler;
    }

    public String getMethodName() {
        return methodName;
    }

    public HttpHandler getMethodHandler() {
        return methodHandler;
    }
}
