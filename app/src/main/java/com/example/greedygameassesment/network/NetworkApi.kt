package com.example.greedygameassesment.network

import com.example.greedygameassesment.model.BaseResponse
import retrofit2.Call
import retrofit2.http.GET

interface NetworkApi {
    @GET("r/images/hot.json")
    fun getImages() : Call<BaseResponse>

}