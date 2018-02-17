package com.nlx.ggstreams.rest;


import com.google.gson.*;
import com.nlx.ggstreams.models.GGStream;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.Set;

public class GGStreamDeserializer implements JsonDeserializer<GGStream> {

    public static final String STREAM_ID = "stream_id";
    public static final String KEY = "key";
    public static final String TITLE = "title";
	public static final String VIEWERS = "viewers";
    public static final String USER_IN_CHAT = "usersinchat";
    public static final String THUMB = "thumb";

    @Override
    public GGStream deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject stream = json.getAsJsonObject();
        GGStream ggStream = null;

        Set<Map.Entry<String, JsonElement>> entrySet = stream.entrySet();
        for (Map.Entry<String, JsonElement> entry : entrySet) {
            ggStream = parseGGStream(stream.get(entry.getKey()).getAsJsonObject());
        }

        return ggStream;
    }

    public static GGStream parseGGStream(JsonObject jsonStream) {
        GGStream ggStream = new GGStream();

        if (jsonStream.has(STREAM_ID) && !jsonStream.get(STREAM_ID).isJsonNull()) {
            ggStream.setStreamId(jsonStream.get(STREAM_ID).getAsInt());
        }

        if (jsonStream.has("id") && !jsonStream.get("id").isJsonNull()) {
            ggStream.setStreamId(jsonStream.get("id").getAsInt());
        }

        if (jsonStream.has("channel") && !jsonStream.get("channel").isJsonNull()) {
            JsonObject jsonChannel = jsonStream.get("channel").getAsJsonObject();

            if (jsonChannel.has("thumb") && !jsonChannel.get("thumb").isJsonNull()) {
                ggStream.setThumb(jsonChannel.get("thumb").getAsString());
            }
            if (jsonChannel.has("title") && !jsonChannel.get("title").isJsonNull()) {
                ggStream.setTitle(jsonChannel.get("title").getAsString());
            }

            if (jsonChannel.has("gg_player_src") && !jsonChannel.get("gg_player_src").isJsonNull()) {
                ggStream.setPlayerSrc(jsonChannel.get("gg_player_src").getAsString());
            }
        }

        if (jsonStream.has(KEY) && !jsonStream.get(KEY).isJsonNull()) {
            ggStream.setKey(jsonStream.get(KEY).getAsString());
        }

        if (jsonStream.has(TITLE) && !jsonStream.get(TITLE).isJsonNull()) {
            ggStream.setTitle(jsonStream.get(TITLE).getAsString());
        }

        if (jsonStream.has(VIEWERS) && !jsonStream.get(VIEWERS).isJsonNull()) {
            ggStream.setViewers(jsonStream.get(VIEWERS).getAsInt());
        }

        if (jsonStream.has(USER_IN_CHAT) && !jsonStream.get(USER_IN_CHAT).isJsonNull()) {
            ggStream.setUsersInChat(jsonStream.get(USER_IN_CHAT).getAsInt());
        }

        if (jsonStream.has(THUMB) && !jsonStream.get(THUMB).isJsonNull()) {
            ggStream.setThumb(jsonStream.get(THUMB).getAsString());
        }

        return ggStream;
    }
}
