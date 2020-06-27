package com.example.greedygameassesment.model

sealed class BaseResult {
    data class Success(var baseResponse: BaseResponse) : BaseResult()
    data class Failure(var error: String) : BaseResult()
}