package com.nlx.ggstreams.rest;


import com.google.gson.*;
import com.nlx.ggstreams.models.EmoteIcon;
import com.nlx.ggstreams.models.EmoteIconUrls;


import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class GGSmileDeserializer implements JsonDeserializer<Map<String, EmoteIcon>> {
    private static final String SMILE_KEY = "key";
    private static final String SMILE_DONATE_LVL = "donate_lvl";
    private static final String SMILE_PREMIUM = "is_premium";
    private static final String SMILE_PAID = "is_paid";
    private static final String SMILE_CHANNEL_ID = "channel_id";
    private static final String SMILE_URLS = "urls";

    private static final String SMILE_URLS_IMG = "img";
    private static final String SMILE_URLS_BIG = "big";
    private static final String SMILE_URLS_GIF = "gif";

    public static final CharSequence PATTERN_COLUMN = ":";

    @Override
    public Map<String, EmoteIcon> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Map<String, EmoteIcon> smileys = new HashMap<>();
        JsonArray smileysJson = json.getAsJsonArray();

        for (int i = 0; i < smileysJson.size(); i++) {
            JsonObject smileyJson = smileysJson.get(i).getAsJsonObject();
            EmoteIcon emoteIcon = new EmoteIcon();
            emoteIcon.setKey(PATTERN_COLUMN + smileyJson.get(SMILE_KEY).getAsString() + PATTERN_COLUMN);
            emoteIcon.setDonateLvl(smileyJson.get(SMILE_DONATE_LVL).getAsInt());
            emoteIcon.setPremium(smileyJson.get(SMILE_PREMIUM).getAsBoolean());
            emoteIcon.setPaid(smileyJson.get(SMILE_PAID).getAsBoolean());
            emoteIcon.setChannelId(smileyJson.get(SMILE_CHANNEL_ID).getAsString());

            JsonObject urlsJson = smileyJson.get(SMILE_URLS).getAsJsonObject();
            EmoteIconUrls smileUrls = new EmoteIconUrls();
            smileUrls.setImg(urlsJson.get(SMILE_URLS_IMG).getAsString());
            smileUrls.setBig(urlsJson.get(SMILE_URLS_BIG).getAsString());
            smileUrls.setGif(urlsJson.get(SMILE_URLS_GIF).getAsString());

            emoteIcon.setUrls(smileUrls);


            smileys.put(emoteIcon.getKey(), emoteIcon);
        }

        return smileys;
    }
}
