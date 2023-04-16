package com.demir.yasin.ui

import android.os.Bundle
import android.text.InputFilter
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.demir.yasin.R
import com.demir.yasin.adapter.AmountAdapter
import com.demir.yasin.databinding.FragmentInvoiceDetailBinding
import com.demir.yasin.model.Invoice
import com.demir.yasin.model.ListModel
import com.demir.yasin.util.*
import com.demir.yasin.viewmodel.InvoiceViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InvoiceDetailFragment : Fragment() {
    private lateinit var binding: FragmentInvoiceDetailBinding
    private val invoiceViewModel: InvoiceViewModel by viewModels()
    val args by navArgs<InvoiceDetailFragmentArgs>()
    private lateinit var sortedInvoiceList: List<Invoice>
    private val amountAdapter = AmountAdapter()

    private lateinit var loadingHelper: LoadingHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadingHelper = LoadingHelper.getInstance(requireActivity())

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentInvoiceDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val list = args.list
        observeData(list)
        setUi(list)
        binding.toolBar.back.setOnClickListener {
            findNavController().popBackStack()
        }
        lastPaymentDialog()
        lastDocumentDialog()
        val NumberValidator = NumberValidator(
            binding.tcNumber,
            binding.phoneNumber,
            binding.mail,
            binding.tcErrorText,
            binding.mailErrorText,
            binding.phoneErrorText
        )
        binding.tcNumber.addTextChangedListener(NumberValidator)
        binding.phoneNumber.addTextChangedListener(NumberValidator)
        binding.mail.addTextChangedListener(NumberValidator)
        //bu kod ile kullanıcı isim ve soyismini girerken sadece harflere ve boşluk tuşuna basabilecek.
        val filter = InputFilter { source, _, _, _, _, _ ->
            source.filter { it.isLetter() || it.isWhitespace() }
        }
        binding.name.filters = arrayOf(filter)
        copyClipBoard(list)
    }
    private fun copyClipBoard(list: ListModel) {
            binding.detailInstallationNumber.setOnClickListener {
                requireContext().copy(list.installationNumber)
            }
        binding.detailAgreementAccountNumber.setOnClickListener {
            requireContext().copy(list.contractAccountNumber)
        }
    }

    private fun lastDocumentDialog() {
        amountAdapter.onClickDocument = {
            val descripton = String.format(getString(R.string.dokuman_bilgi), it.documentNumber)
            requireContext().showDialog("Doküman Numarası", descripton)
        }
    }

    private fun lastPaymentDialog() {
        amountAdapter.onClickPay = {
            val descripton = String.format(getString(R.string.son_odeme), it.dueDate)
            requireContext().showDialog("Son Ödeme Tarihi", descripton)

        }
    }

    private fun setUi(listModel: ListModel) {
        binding.apply {
            detailInformation1.text = listModel.company
            detailAdress.text = listModel.address
            detailInstallationNumber.text = listModel.installationNumber
            detailAgreementAccountNumber.text = listModel.contractAccountNumber
            binding.totalPriceAccount.text = listModel.amount.setTotalPrice()
        }

        binding.amountDetailRec.adapter = amountAdapter
        binding.amountDetailRec.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    }

    private fun observeData(listModel: ListModel) {
        invoiceViewModel.invoiceData.observe(viewLifecycleOwner, Observer {

            it?.let { result ->
                sortedInvoiceList =
                    result.invoices.filter { it.installationNumber == listModel.installationNumber }
                amountAdapter.differ.submitList(sortedInvoiceList)
                val sozlesmeText = String.format(
                    getString(R.string.secili_sozlesme_fatura_adet),
                    sortedInvoiceList.size
                )
                binding.detailSozlesmeDetail.text = sozlesmeText
            }
        })

        invoiceViewModel.isLoading.observe(viewLifecycleOwner){ isLoading->
            if (isLoading){
                loadingHelper.showDialog()
            }else{
                loadingHelper.hideDialog()
            }
        }

        invoiceViewModel.errorMessage.observe(viewLifecycleOwner) { errorMessage ->
            //error popup ya da ekrana boş datası ekle
        }
    }
}