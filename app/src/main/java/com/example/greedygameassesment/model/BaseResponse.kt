package com.example.greedygameassesment.model

import com.google.gson.annotations.SerializedName

data class BaseResponse(
    @SerializedName("data")
    var data: Data
) {

}
