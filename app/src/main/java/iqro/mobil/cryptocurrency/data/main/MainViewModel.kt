package iqro.mobil.cryptocurrency.data.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import iqro.mobil.cryptocurrency.utils.CoinListResource
import iqro.mobil.cryptocurrency.utils.SetCoinListData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(val mainRepository: MainRepository):ViewModel() {

    private val _flow= MutableStateFlow<SetCoinListData>(SetCoinListData.Empty)
    val flow:StateFlow<SetCoinListData> get() =_flow

    fun getCoinPaprika(){
        _flow.value=SetCoinListData.Loading
        viewModelScope.launch {
            val respose=mainRepository.getCoinpaprika()
            when(respose){
                is CoinListResource.Error -> {
                    _flow.value=SetCoinListData.Error(respose.message)
                }
                is CoinListResource.Success -> {
                    _flow.value=SetCoinListData.Success(respose.data)
                }
            }
        }
    }
}