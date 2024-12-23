package iqro.mobil.cryptocurrency.data.main

import iqro.mobil.cryptocurrency.data.CoinPaprikaApi
import iqro.mobil.cryptocurrency.data.models.CoinById
import iqro.mobil.cryptocurrency.data.models.CoinpaprikaMod
import iqro.mobil.cryptocurrency.utils.CoinListResource
import iqro.mobil.cryptocurrency.utils.CoinResource
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(val api:CoinPaprikaApi):MainRepository {
    override suspend fun getCoinpaprika(): CoinListResource<CoinpaprikaMod> {
        return try {
            val response=api.getCoinList()
            if (response.isSuccessful&&response.body()!=null){
                CoinListResource.Success(response.body())
            }else{
                CoinListResource.Error(response.message())
            }

        }catch (e:Exception){
            CoinListResource.Error(e.message)
        }
    }

    override suspend fun getCOinById(coinId: String): CoinResource<CoinById> {
       return try {
           val response=api.getCoinById(coinId)
           if (response.isSuccessful&&response.body()!=null){
               CoinResource.Success(response.body())
           }else{
               CoinResource.Error(response.message())
           }
       }catch (e:Exception){
           CoinResource.Error(e.message)
       }
    }
}