package com.example.greedygameassesment.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.greedygameassesment.model.BaseResponse
import com.example.greedygameassesment.model.BaseResult
import com.example.greedygameassesment.network.OnResponse

class ActivityViewModel(private var dataRepository: DataRepository) : ViewModel() {
    var baseResult: MutableLiveData<BaseResult> = MutableLiveData()

    fun getApiImages() {
        dataRepository.fetchImages(object : OnResponse {
            override fun onSuccess(response: BaseResponse) {
                baseResult.value = BaseResult.Success(response)
            }

            override fun onFailure(error: String) {
                baseResult.value = BaseResult.Failure(error)
            }

        })
    }

}