package me.alexeyterekhov.susaninsearch.Common;

import java.util.concurrent.*;

public class ExecutorFactory {
    private static BlockingQueue<Runnable> serverQueue = null;
    private static BlockingQueue<Runnable> requestQueue = null;
    private static Executor serverExecutor = null;
    private static Executor requestExecutor = null;

    public static Executor getServerExecutor() {
        if (serverExecutor == null) {
            serverExecutor = new ThreadPoolExecutor(
                    10,
                    40,
                    30,
                    TimeUnit.SECONDS,
                    getServerQueue());
        }
        return serverExecutor;
    }

    public static Executor getRequestExecutor() {
        if (requestExecutor == null) {
            requestExecutor = new ThreadPoolExecutor(
                    5,
                    10,
                    0,
                    TimeUnit.SECONDS,
                    getRequestQueue());
        }
        return requestExecutor;
    }

    private static BlockingQueue<Runnable> getServerQueue() {
        if (serverQueue == null)
            serverQueue = new ArrayBlockingQueue<>(100);
        return serverQueue;
    }

    private static BlockingQueue<Runnable> getRequestQueue() {
        if (requestQueue == null)
            requestQueue = new ArrayBlockingQueue<>(50);
        return requestQueue;
    }
}
