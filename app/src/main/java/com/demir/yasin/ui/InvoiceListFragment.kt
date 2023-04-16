package com.demir.yasin.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.demir.yasin.R
import com.demir.yasin.adapter.InvoiceAdapter
import com.demir.yasin.databinding.FragmentInvoiceListBinding
import com.demir.yasin.util.LoadingHelper
import com.demir.yasin.util.copy
import com.demir.yasin.util.setTotalPrice
import com.demir.yasin.viewmodel.InvoiceViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InvoiceListFragment : Fragment() {
    private lateinit var binding: FragmentInvoiceListBinding
    private val invoiceViewModel: InvoiceViewModel by viewModels()
    private val invoiceAdapter = InvoiceAdapter()

    private lateinit var loadingHelper: LoadingHelper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadingHelper = LoadingHelper.getInstance(requireActivity())

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentInvoiceListBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUi()
        obserLiveData()
        seletInvoiceDetail()
        copyClipBoard()
        binding.toolBar.back.visibility = View.GONE
    }

    private fun copyClipBoard() {
        invoiceAdapter.onClickInstallationCopyBoard={
            requireContext().copy(it.installationNumber)
        }
        invoiceAdapter.onClickContractCopyBoard={
            requireContext().copy(it.contractAccountNumber)
        }
    }

    private fun seletInvoiceDetail() {
        invoiceAdapter.onClickButton = {
            val bundle = Bundle().apply {
                putParcelable("list", it)
            }
            findNavController().navigate(R.id.invoiceListFragmentToDetail, bundle)
        }
    }

    private fun setUi() {
        binding.invoiceRec.adapter = invoiceAdapter
        binding.invoiceRec.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    }

    private fun obserLiveData() {
        invoiceViewModel.invoiceData.observe(viewLifecycleOwner, Observer {
            it?.let { result ->
                binding.apply {
                    totalPriceAccount.text = result.totalPrice.setTotalPrice()
                }
                val sozlesmeText =
                    String.format(getString(R.string.fatura_bilgisi), result.totalPriceCount)
                binding.sozlesmeDetail.text = sozlesmeText
                invoiceAdapter.differ.submitList(result.list)
            }
        })

        invoiceViewModel.isLoading.observe(viewLifecycleOwner){ isLoading->
            if (isLoading){
              loadingHelper.showDialog()
            }else{
                loadingHelper.hideDialog()
            }
        }

        invoiceViewModel.errorMessage.observe(viewLifecycleOwner){ errorMessage->
            //error popup ya da ekrana boş datası ekle
        }

    }
}