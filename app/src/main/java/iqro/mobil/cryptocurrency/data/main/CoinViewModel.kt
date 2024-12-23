package iqro.mobil.cryptocurrency.data.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import iqro.mobil.cryptocurrency.utils.CoinResource
import iqro.mobil.cryptocurrency.utils.SetCoinData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoinViewModel @Inject constructor(val mainRepository: MainRepository):ViewModel() {

   private val _coinFlow= MutableStateFlow<SetCoinData>(SetCoinData.Empty)
    val coinFlow:StateFlow<SetCoinData> get() = _coinFlow

    fun getCoin(coinId:String){
        _coinFlow.value=SetCoinData.Loading
        viewModelScope.launch {
           val result= mainRepository.getCOinById(coinId)
            when(result){
                is CoinResource.Error -> {
                    _coinFlow.value=SetCoinData.Error(result.message)
                }
                is CoinResource.Success -> {
                    _coinFlow.value=SetCoinData.Success(result.data)
                }
            }
        }
    }

}