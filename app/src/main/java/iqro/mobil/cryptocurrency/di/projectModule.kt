package iqro.mobil.cryptocurrency.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import iqro.mobil.cryptocurrency.data.CoinPaprikaApi
import iqro.mobil.cryptocurrency.data.main.MainRepository
import iqro.mobil.cryptocurrency.data.main.MainRepositoryImpl
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object projectModule {

    @Singleton
    @Provides
    fun getApi():CoinPaprikaApi{
        return Retrofit.Builder()
            .baseUrl("https://api.coinpaprika.com/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CoinPaprikaApi::class.java)
    }

    @Singleton
    @Provides
    fun getRepository(api: CoinPaprikaApi):MainRepository{
        return MainRepositoryImpl(api)
    }

}