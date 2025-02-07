package xyz.qweru.api.config;

import com.google.gson.JsonObject;

public interface Jsonable {
    void addJson(JsonObject root);
    void readJson(JsonObject json);
}
