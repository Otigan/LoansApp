package com.example.loanapp.ui.loan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.loanapp.R
import com.example.loanapp.databinding.FragmentLoanDetailBinding
import com.example.loanapp.domain.entity.LoanState.*
import com.example.loanapp.ui.adapter.formatDate

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
            val state = values().find { it.name == loan.state } ?: UNKNOWN
            when (state) {
                APPROVED -> {
                    txtStatusLoanDetail.text = getString(R.string.txt_status_approved_loan_detail)
                    txtLoanDetailStatus.text =
                        getString(R.string.txt_loan_detail_status_approved)
                    txtLoanDetailDescription.text =
                        getString(R.string.txt_loan_detail_success_description)
                }
                REJECTED -> {
                    txtStatusLoanDetail.text = getString(R.string.txt_status_registered_loan_detail)
                    txtLoanDetailStatus.text =
                        getString(R.string.txt_loan_detail_status_rejected)
                    txtLoanDetailDescription.text =
                        getString(R.string.txt_loan_detail_reject_description)
                }
                REGISTERED -> {
                    txtStatusLoanDetail.text = getString(R.string.txt_status_registered_loan_detail)
                    txtLoanDetailStatus.text =
                        getString(R.string.txt_loan_detail_status_registered)
                    txtLoanDetailDescription.text =
                        getString(R.string.txt_loan_detail_registered_description)
                }
                UNKNOWN -> throw IllegalStateException("Unknown state of loan")
            }
            txtAmountLoanDetail.text = getString(R.string.txt_amount_loan_detail, loan.amount)
            txtPercentLoanDetail.text =
                getString(R.string.txt_percent_loan_detail, loan.percent)
            val formattedDate = formatDate(loan.date)
            txtDateLoanDetail.text = getString(R.string.txt_date_loan_detail, formattedDate)
            txtPeriodLoanDetail.text = getString(R.string.txt_period_loan_detail, loan.period)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}