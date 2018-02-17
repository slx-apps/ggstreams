package com.nlx.ggstreams.rest

import com.nlx.ggstreams.models.AuthResponse
import com.nlx.ggstreams.models.ChatProfile
import com.nlx.ggstreams.rest.GGRestClient.Companion.GOODGAME_API_AUTH
import com.nlx.ggstreams.rest.GGRestClient.Companion.GOODGAME_API_CHAT_AUTH
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface GGV1Api {

    @FormUrlEncoded
    @POST(GOODGAME_API_AUTH)
    fun login(@Field("username") username: String,
              @Field("password") password: String): Observable<AuthResponse>

    @FormUrlEncoded
    @POST(GOODGAME_API_CHAT_AUTH)
    fun chatLogin(@Field("login") login: String,
                  @Field("password") password: String): Observable<ChatProfile>


}
