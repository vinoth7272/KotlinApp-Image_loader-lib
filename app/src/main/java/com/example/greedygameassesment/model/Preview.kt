package com.example.greedygameassesment.model

import com.google.gson.annotations.SerializedName

data class Preview (@SerializedName("images")
var images:List<ApiImages>)
