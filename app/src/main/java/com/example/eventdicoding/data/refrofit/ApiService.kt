package com.example.eventdicoding.data.refrofit

import com.example.eventdicoding.data.response.EventDicodingResponse
import com.example.eventdicoding.data.response.ListEventsItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService{
    @GET("events")
    fun getEvent(
        @Query("active") active: Int
    ) : Call<EventDicodingResponse>
    @GET("events/{id}")
    fun getEventDetail(
        @Path("id") eventId:Int
    ): Call<EventDicodingResponse>
    @GET("events")
    fun getEventSearch(
        @Query("active") active: Int,
        @Query("q") query: String
    ): Call<EventDicodingResponse>
}