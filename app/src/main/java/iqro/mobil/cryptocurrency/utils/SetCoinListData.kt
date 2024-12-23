package iqro.mobil.cryptocurrency.utils

import iqro.mobil.cryptocurrency.data.models.CoinpaprikaMod

sealed class SetCoinListData {
    data class Success(val data:List<CoinpaprikaMod>?):SetCoinListData()
    data class Error(val message:String?):SetCoinListData()
    object Loading:SetCoinListData()
    object Empty:SetCoinListData()
}