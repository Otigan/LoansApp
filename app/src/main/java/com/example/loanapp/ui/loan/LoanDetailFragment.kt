package com.example.loanapp.ui.loan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.loanapp.R
import com.example.loanapp.databinding.FragmentLoanDetailBinding

class LoanDetailFragment : Fragment(R.layout.fragment_loan_detail) {

    private var _binding: FragmentLoanDetailBinding? = null
    private val binding get() = _binding!!
    private val args by navArgs<LoanDetailFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoanDetailBinding.inflate(inflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val loan = args.loan

        binding.apply {
            when (loan.state) {
                "APPROVED" -> {
                    txtLoanDetailStatus.text =
                        getString(R.string.txt_loan_detail_status, "одобрена")
                    txtLoanDetailDescription.text =
                        getString(R.string.txt_loan_detail_success_description)
                }
                "REJECTED" -> {
                    txtLoanDetailStatus.text =
                        getString(R.string.txt_loan_detail_status, "отклонена")
                    txtLoanDetailDescription.text =
                        getString(R.string.txt_loan_detail_reject_description)
                }
                "REGISTERED" -> {
                    txtLoanDetailStatus.text =
                        getString(R.string.txt_loan_detail_status, "на рассмотрении")
                    txtLoanDetailDescription.text = "Ожидайте рассмотрения заявки"
                }
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}