package com.demir.yasin.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.demir.yasin.databinding.AmountDetailItemBinding
import com.demir.yasin.model.Invoice

class AmountAdapter:RecyclerView.Adapter<AmountAdapter.AmountHolder>() {
    class AmountHolder(val binding: AmountDetailItemBinding):RecyclerView.ViewHolder(binding.root)
    private val diffUtil=object : DiffUtil.ItemCallback<Invoice>(){
        override fun areItemsTheSame(oldItem: Invoice, newItem: Invoice): Boolean {
            return oldItem==newItem
        }

        override fun areContentsTheSame(oldItem: Invoice, newItem: Invoice): Boolean {
            return oldItem.installationNumber==newItem.installationNumber
        }

    }
    val differ= AsyncListDiffer(this,diffUtil)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AmountHolder {
        val view=AmountDetailItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return AmountHolder(view)
    }

    override fun onBindViewHolder(holder: AmountHolder, position: Int) {1
        holder.binding.amountPayable=differ.currentList[position]
        holder.binding.pay.setOnClickListener {
            onClickPay?.let {
                it.invoke(differ.currentList[position])
            }
        }
        holder.binding.invoice.setOnClickListener {
            onClickDocument?.let {
                it.invoke(differ.currentList[position])
            }
        }


    }
    var onClickDocument:((Invoice)-> Unit)? = null
    var onClickPay:((Invoice)-> Unit)? = null


    override fun getItemCount(): Int {
       return differ.currentList.size
    }
}