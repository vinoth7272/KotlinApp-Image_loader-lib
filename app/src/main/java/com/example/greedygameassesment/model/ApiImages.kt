package com.example.greedygameassesment.model

import com.google.gson.annotations.SerializedName

data class ApiImages(
    @SerializedName("source")
    var source: ImageSource?,
    @SerializedName("resolutions")
    var resolutions:List<ImageSource>?
)
