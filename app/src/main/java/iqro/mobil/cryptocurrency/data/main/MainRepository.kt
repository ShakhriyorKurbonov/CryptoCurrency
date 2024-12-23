package iqro.mobil.cryptocurrency.data.main

import iqro.mobil.cryptocurrency.data.models.CoinById
import iqro.mobil.cryptocurrency.data.models.CoinpaprikaMod
import iqro.mobil.cryptocurrency.utils.CoinListResource
import iqro.mobil.cryptocurrency.utils.CoinResource

interface MainRepository {
    suspend fun getCoinpaprika():CoinListResource<CoinpaprikaMod>
    suspend fun getCOinById(coinId:String):CoinResource<CoinById>
}