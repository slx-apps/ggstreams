package com.nlx.ggstreams.rest

import com.nlx.ggstreams.models.EmoteIcon
import com.nlx.ggstreams.models.EmoteIconsResponse
import com.nlx.ggstreams.models.GGStream
import com.nlx.ggstreams.models.StreamListResponse
import com.nlx.ggstreams.rest.GGRestClient
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface GGApi {

    @Headers(GGRestClient.HEADER_API_V2_VERSION)
    @GET(GGRestClient.GOODGAME_API_V2_STREAMS)
    fun streams(@Query("page") page: Int): Single<StreamListResponse>

    @GET(GGRestClient.CHANNEL_STATUS)
    fun getStreamByName(@Query("id") id: String,
                        @Query("fmt") format: String): Call<GGStream>

    @Headers(GGRestClient.HEADER_API_V2_VERSION)
    @GET(GGRestClient.GOODGAME_API_V2_ALL_SMILES)
    fun allSmiles(@Query("page") page: Int): Call<Map<String, EmoteIcon>>

    @Headers(GGRestClient.HEADER_API_V2_VERSION)
    @GET(GGRestClient.GOODGAME_API_V2_ALL_SMILES)
    fun allSmilesObservable(@Query("page") page: Int): Observable<EmoteIconsResponse>

    @Headers(GGRestClient.HEADER_API_V2_VERSION)
    @GET(GGRestClient.GOODGAME_API_V2_CHANNEL_SMILES)
    fun channelSmiles(@Path("channelId") channelId: Int,
                      @Query("page") page: Int): Call<Map<String, EmoteIcon>>

    @Headers(GGRestClient.HEADER_API_V2_VERSION)
    @GET(GGRestClient.GOODGAME_API_V2_CHANNEL_SMILES)
    fun channelSmilesObservable(@Path("channelId") channelId: String,
                                @Query("page") page: Int): Observable<EmoteIconsResponse>


}