package iqro.mobil.cryptocurrency.utils

sealed class CoinResource<T>(data:T?,message:String?=null) {
    data class Success<T>(val data: T?):CoinResource<T>(data)
    data class Error<T>(val message:String?):CoinResource<T>(null,message)
}