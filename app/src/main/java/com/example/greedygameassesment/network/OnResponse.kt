package com.example.greedygameassesment.network

import com.example.greedygameassesment.model.BaseResponse

interface OnResponse {
    fun onSuccess(response:BaseResponse)
    fun onFailure(error:String)
}