package com.nlx.ggstreams

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.nlx.ggstreams.models.StreamListResponse
import com.nlx.ggstreams.rest.GGStreamDeserializer.parseGGStream
import java.lang.reflect.Type


class StreamListDeserializer : JsonDeserializer<StreamListResponse> {

    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): StreamListResponse {
        val response = StreamListResponse()

        val jsonObject = json!!.asJsonObject

        if (jsonObject.has("page_count") && !jsonObject.get("page_count").isJsonNull) {
            response.pageCount = jsonObject.get("page_count").asInt
        }

        if (jsonObject.has("page_size") && !jsonObject.get("page_size").isJsonNull) {
            response.pageSize = jsonObject.get("page_size").asInt
        }

        if (jsonObject.has("total_items") && !jsonObject.get("total_items").isJsonNull) {
            response.totalItems = jsonObject.get("total_items").asInt
        }

        if (jsonObject.has("page") && !jsonObject.get("page").isJsonNull) {
            response.page = jsonObject.get("page").asInt
        }

        if (jsonObject.has("_embedded") && !jsonObject.get("_embedded").isJsonNull) {
            val streamsObject = jsonObject.getAsJsonObject("_embedded")

            val jsonArray = streamsObject.get("streams").asJsonArray

            val streamList = jsonArray.map { parseGGStream(it.asJsonObject) }

            response.streams = streamList
        }


        return response
    }

}