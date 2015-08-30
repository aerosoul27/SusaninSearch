package me.alexeyterekhov.susaninsearch;

import com.sun.net.httpserver.HttpServer;
import me.alexeyterekhov.susaninsearch.Server.ServerFactory;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        int port = -1;
        if (args.length > 0) {
            try {
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                System.out.println("Wrong port parameter, used default value");
            }
        }

        try {
            if (port > 0)
                ServerFactory.setServerPort(port);
            HttpServer server = ServerFactory.getSusaninServer();
            server.start();
            System.out.println("Server started on port " + ServerFactory.getServerPort());
        } catch (IOException e) {
            System.out.println("Unable to start server :(");
            e.printStackTrace();
        }
    }
}
