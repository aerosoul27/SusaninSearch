package me.alexeyterekhov.susaninsearch.Server;

import com.sun.net.httpserver.HttpServer;
import me.alexeyterekhov.susaninsearch.Common.ExecutorFactory;
import me.alexeyterekhov.susaninsearch.Server.API.WebMethod;
import me.alexeyterekhov.susaninsearch.Server.API.WebMethodsFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.List;
import java.util.concurrent.Executor;

public class ServerFactory {
    private static int backlog = 0;
    private static int serverPort = 8080;
    private static InetSocketAddress inetSocketAddress = null;
    private static HttpServer httpServer = null;

    public static HttpServer getSusaninServer() throws IOException {
        if (httpServer == null) {
            httpServer = HttpServer.create(getInetSocketAddress(), getBacklog());
            httpServer.setExecutor(getExecutor());

            // Add methods to server
            List<WebMethod> webMethods = WebMethodsFactory.getMethods();
            for (WebMethod method : webMethods)
                httpServer.createContext(method.getMethodName(), method.getMethodHandler());
        }
        return httpServer;
    }

    public static int getServerPort() {
        return serverPort;
    }

    public static void setServerPort(int port) {
        serverPort = port;
    }

    private static InetSocketAddress getInetSocketAddress() {
        if (inetSocketAddress == null)
            inetSocketAddress = new InetSocketAddress(getServerPort());
        return inetSocketAddress;
    }

    private static int getBacklog() {
        return backlog;
    }

    private static Executor getExecutor() {
        return ExecutorFactory.getServerExecutor();
    }
}