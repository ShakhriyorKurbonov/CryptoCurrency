package iqro.mobil.cryptocurrency

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.findFragment
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import iqro.mobil.cryptocurrency.data.models.CoinpaprikaMod
import iqro.mobil.cryptocurrency.databinding.CoinListBinding
import iqro.mobil.cryptocurrency.other.Constants
import kotlin.concurrent.fixedRateTimer


class MyDiff:DiffUtil.ItemCallback<CoinpaprikaMod>(){
    override fun areItemsTheSame(oldItem: CoinpaprikaMod, newItem: CoinpaprikaMod): Boolean {
return oldItem==newItem
    }

    override fun areContentsTheSame(oldItem: CoinpaprikaMod, newItem: CoinpaprikaMod): Boolean {
        return oldItem==newItem
    }
}

class CoinListAdapter(val fragment:Fragment):ListAdapter<CoinpaprikaMod,CoinListAdapter.CoinListViewHolder>(MyDiff()) {
    inner class CoinListViewHolder(val binding: CoinListBinding):ViewHolder(binding.root){
        fun setBind(coins:CoinpaprikaMod){
            binding.root.setOnClickListener {
               fragment.findNavController().navigate(
                   R.id.action_coinListFragment_to_coinFragment,
                   bundleOf(Constants.CoinId to coins.id)
               )
            }
            binding.coinName.text="${coins.rank} ${coins.name}"
            binding.textView2.text=if (coins.is_active) "Active" else "Inactive"
            binding.textView2.setTextColor(if (coins.is_active) Color.GREEN else Color.RED)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinListViewHolder {
        return CoinListViewHolder(CoinListBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: CoinListViewHolder, position: Int) {
        holder.setBind(getItem(position))
    }
}