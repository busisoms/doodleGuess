package com.doodleguess;

import io.javalin.Javalin;
import io.javalin.config.RoutesConfig;
import io.javalin.http.Context;
import io.javalin.http.staticfiles.Location;

public class Main {
    private final String DEFAULT_PORT = "7000";
    private final Javalin app;

    public Main(){
        this.app = Javalin.create(Config -> {
            Config.staticFiles.add("/public", Location.CLASSPATH);
            register(Config.routes);
        });
    }

    public void start(){
        String envPort = env("PORT", DEFAULT_PORT);
        app.start(Integer.parseInt(envPort));
    }

    public static void register(RoutesConfig routes){
        routes.get("/health", Main::health);
    }

    public static void health(Context ctx){
        ctx.status(200).result("OK");
    }

    private static String env(String key, String fallback) {
        String value = System.getenv(key);
        return value != null ? value : fallback;
    }

    public static void main(String[] args) {
        Main service = new Main();
        service.start();
    }

}
