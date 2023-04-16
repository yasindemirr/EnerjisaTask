package com.demir.yasin.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.demir.yasin.databinding.InvoiceItemBinding
import com.demir.yasin.model.ListModel

class InvoiceAdapter:RecyclerView.Adapter<InvoiceAdapter.IncoiceHolder>() {
    class IncoiceHolder(val binding: InvoiceItemBinding):RecyclerView.ViewHolder(binding.root)
     private val diffUtil=object :DiffUtil.ItemCallback<ListModel>(){
         override fun areItemsTheSame(oldItem: ListModel, newItem: ListModel): Boolean {
             return oldItem==newItem
         }

         override fun areContentsTheSame(oldItem: ListModel, newItem: ListModel): Boolean {
             return oldItem.contractAccountNumber==newItem.contractAccountNumber
         }

     }
    val differ=AsyncListDiffer(this,diffUtil)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IncoiceHolder {
       val view=InvoiceItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return IncoiceHolder(view)
    }

    override fun onBindViewHolder(holder: IncoiceHolder, position: Int) {
        holder.binding.invoice=differ.currentList[position]
        holder.binding.apply {
            installationNumber.setOnClickListener {
                onClickInstallationCopyBoard?.let {
                    it.invoke(differ.currentList[position])
                }
            }
            agreementAccountNumber.setOnClickListener {
                onClickContractCopyBoard?.let {
                    it.invoke(differ.currentList[position])
                }
            }
        }
        holder.binding.button.setOnClickListener {
            onClickButton?.let {
                it.invoke(differ.currentList[position])
            }

        }
    }
    var onClickButton:((ListModel)-> Unit)? = null
    var onClickInstallationCopyBoard:((ListModel)-> Unit)? = null
    var onClickContractCopyBoard:((ListModel)-> Unit)? = null


    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}