package iqro.mobil.cryptocurrency.utils

sealed class CoinListResource<T>(data:List<T>?, message:String?=null) {
    data class Success<T>(val data: List<T>?):CoinListResource<T>(data)
    data class Error<T>(val message: String?):CoinListResource<T>(null,message)
}