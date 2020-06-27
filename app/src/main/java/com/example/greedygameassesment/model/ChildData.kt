package com.example.greedygameassesment.model

import com.google.gson.annotations.SerializedName

data class ChildData(
    @SerializedName("preview")
    var preview: Preview,
    @SerializedName("thumbnail")
    var thumnailUrl: String,
    @SerializedName("url_overridden_by_dest")
    var largeUrl:String
)
