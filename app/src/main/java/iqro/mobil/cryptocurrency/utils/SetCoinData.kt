package iqro.mobil.cryptocurrency.utils

import iqro.mobil.cryptocurrency.data.models.CoinById

sealed class SetCoinData {
    data class Success(val data:CoinById?):SetCoinData()
    data class Error(val message:String?):SetCoinData()
    object Loading:SetCoinData()
    object Empty:SetCoinData()
}