package com.example.greedygameassesment.viewmodel

import com.example.greedygameassesment.model.BaseResponse
import com.example.greedygameassesment.network.NetworkApi
import com.example.greedygameassesment.network.OnResponse
import retrofit2.Call
import retrofit2.Response

class DataRepository(var networkApi: NetworkApi) {

    fun fetchImages(onResponse: OnResponse) {
        val call = networkApi.getImages()
        call.enqueue(object : retrofit2.Callback<BaseResponse> {
            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                onResponse.onFailure(t.message.toString())
            }

            override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                if (response.isSuccessful)
                    onResponse.onSuccess(response = response.body() as BaseResponse)
                else
                    onResponse.onFailure(response.message())
            }

        })

    }

}