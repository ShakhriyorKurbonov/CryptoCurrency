package iqro.mobil.cryptocurrency

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import iqro.mobil.cryptocurrency.data.main.MainViewModel
import iqro.mobil.cryptocurrency.data.models.CoinpaprikaMod
import iqro.mobil.cryptocurrency.databinding.FragmentCoinListBinding
import iqro.mobil.cryptocurrency.utils.SetCoinListData
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CoinListFragment : Fragment() {
  private var _binding: FragmentCoinListBinding?=null
    private lateinit var coinListAdapter: CoinListAdapter
    val TAG="TAG"
    private val binding get() = _binding!!
    private val  mainViewModel: MainViewModel by viewModels()




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel.getCoinPaprika()
        coinListAdapter= CoinListAdapter(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding=FragmentCoinListBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.apply {
            adapter=coinListAdapter
            layoutManager=LinearLayoutManager(requireContext())
        }



        lifecycleScope.launch {
            mainViewModel.flow.collectLatest {
                when(it){
                    SetCoinListData.Empty -> Unit
                    is SetCoinListData.Error -> {
                        binding.progressBar.isVisible=false
                        binding.recyclerView.isVisible=false
                        binding.errorMessage.isVisible=true
                        binding.errorMessage.text=it.message
                    }
                    SetCoinListData.Loading -> {
                        binding.progressBar.isVisible=true
                        binding.recyclerView.isVisible=false
                        binding.errorMessage.isVisible=false
                    }
                    is SetCoinListData.Success -> {
                        binding.progressBar.isVisible=false
                        binding.recyclerView.isVisible=true
                        binding.errorMessage.isVisible=false
                        val coinList=it.data as List<CoinpaprikaMod>
                        coinListAdapter.submitList(coinList)

                    }
                }
            }
        }


    }




}