package com.example.greedygameassesment.di

import androidx.lifecycle.AndroidViewModel
import com.example.greedygameassesment.network.NetworkApi
import com.example.greedygameassesment.utils.Constant
import com.example.greedygameassesment.viewmodel.DataRepository
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val retrofitModule = module {
    single {//Singleton Component
        getNetworkApi()
    }
}

/**
 * Used to get the instance of Network Api
 */
private fun getNetworkApi(): NetworkApi {
    return Retrofit.Builder().baseUrl(Constant.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create()).build().create(NetworkApi::class.java)
}