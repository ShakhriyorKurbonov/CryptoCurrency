package iqro.mobil.cryptocurrency

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.ActionBar.LayoutParams
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import iqro.mobil.cryptocurrency.data.main.CoinViewModel
import iqro.mobil.cryptocurrency.data.models.CoinById
import iqro.mobil.cryptocurrency.databinding.CoinFragmentBinding
import iqro.mobil.cryptocurrency.other.Constants
import iqro.mobil.cryptocurrency.utils.SetCoinData
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CoinFragment:Fragment() {
    val TAG="TAG"
    private var _binding: CoinFragmentBinding?= null
    private val binding get() = _binding!!
    private val coinViewModel:CoinViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding=CoinFragmentBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val coinId=arguments?.getString(Constants.CoinId)
        if (coinId!=null)
        coinViewModel.getCoin(coinId)

        lifecycleScope.launch {
            coinViewModel.coinFlow.collectLatest {
                when(it){
                    SetCoinData.Empty -> Unit
                    is SetCoinData.Error -> {
                        binding.progressBar.isVisible=false
                        binding.errorMessageTv.text=it.message
                        binding.scrollView.isVisible=false
                        binding.errorMessageTv.isVisible=true
                    }
                    SetCoinData.Loading -> {
                        binding.progressBar.isVisible=true
                        binding.scrollView.isVisible=false
                        binding.errorMessageTv.isVisible=false
                    }
                    is SetCoinData.Success -> {
                        binding.progressBar.isVisible=false
                        binding.errorMessageTv.isVisible=false
                        binding.scrollView.isVisible=true
                        val coinById=it.data
                        populateData(coinById)
                    }
                }
            }
        }

    }

    @SuppressLint("SetTextI18n")
    fun populateData(coinById: CoinById?){
        if (coinById!=null){
            Glide.with(requireContext()).load(coinById.logo).into(binding.cryptoImg)
            binding.coinNameTv.text="${coinById.rank}. ${coinById.name} ${coinById.symbol}"
            binding.descriptionTv.text=coinById.description
            binding.isActiveTv.text=if(coinById.is_active) "Active" else "Inactive"
            binding.isActiveTv.setTextColor(if (coinById.is_active) Color.GREEN else Color.RED)
            coinById.tags.forEach {
                binding.flowLayout.addView(getTextView(it.name))
            }
        }
    }

    fun getTextView(text:String):TextView{
        val textView=TextView(requireContext())
        textView.text=text
        textView.setTextColor(Color.GREEN)
        textView.layoutParams= ViewGroup.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT)
        textView.setBackgroundResource(R.drawable.rectangle)
        textView.setPadding(30,20,30,20)
        return textView

    }

}