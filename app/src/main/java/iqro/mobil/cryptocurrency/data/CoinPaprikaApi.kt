package iqro.mobil.cryptocurrency.data

import iqro.mobil.cryptocurrency.data.models.CoinById
import iqro.mobil.cryptocurrency.data.models.CoinpaprikaMod
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface CoinPaprikaApi{

    @GET("coins")
    suspend fun getCoinList():Response<List<CoinpaprikaMod>>

    @GET("coins/{coinId}")
    suspend fun getCoinById(@Path("coinId") coinId:String):Response<CoinById>

}