package com.example.greedygameassesment.model

import com.google.gson.annotations.SerializedName

data class Data(@SerializedName("children")
var children : List<Children>)