package com.example.loanapp.ui.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.loanapp.R
import com.example.loanapp.databinding.ItemLoanBinding
import com.example.loanapp.domain.entity.Loan
import com.example.loanapp.domain.entity.LoanState
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class LoansAdapter(private val onClick: (Loan) -> Unit) :
    ListAdapter<Loan, LoansAdapter.LoanViewHolder>(LoanComparator()) {

    class LoanViewHolder(
        private val binding: ItemLoanBinding,
        private val onClick: (Loan) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(loanItem: Loan) {
            binding.apply {
                root.setOnClickListener { onClick(loanItem) }
                root.context.apply {
                    val formattedDate = formatDate(loanItem.date)
                    txtLoanDate.text = getString(R.string.txt_view_loan_date, formattedDate)
                    txtViewAmount.text = getString(R.string.txt_view_amount, loanItem.amount)
                    val state =
                        LoanState.values().find { it.name == loanItem.state } ?: LoanState.UNKNOWN
                    when (state) {
                        LoanState.APPROVED -> {
                            txtLoanStatus.text = getString(R.string.txt_view_loan_status_approved)
                            imgLoanStatus.setImageDrawable(
                                AppCompatResources.getDrawable(
                                    this,
                                    R.drawable.icon_loan_approved
                                )
                            )
                        }
                        LoanState.REJECTED -> {
                            txtLoanStatus.text = getString(R.string.txt_view_loan_status_rejected)
                            imgLoanStatus.setImageDrawable(
                                AppCompatResources.getDrawable(
                                    this,
                                    R.drawable.icon_loan_rejected
                                )
                            )
                        }
                        LoanState.REGISTERED -> {
                            txtLoanStatus.text = getString(R.string.txt_view_loan_status_registered)
                            imgLoanStatus.setImageDrawable(
                                AppCompatResources.getDrawable(
                                    this,
                                    R.drawable.icon_loan_registered
                                )
                            )
                        }
                        else -> {
                            throw IllegalStateException("Unknown state of loan")
                        }
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LoanViewHolder {
        val binding =
            ItemLoanBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LoanViewHolder(binding, onClick)
    }

    override fun onBindViewHolder(holder: LoanViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }
}

fun formatDate(loanDate: String): String {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val parsedDate =
            LocalDateTime.parse(loanDate, DateTimeFormatter.ISO_DATE_TIME)
        parsedDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
    } else {
        val format = SimpleDateFormat(
            "dd-MM-yyyy", Locale.ROOT
        )
        val date = format.parse(loanDate)
        return date.toString()
    }

}

private class LoanComparator : DiffUtil.ItemCallback<Loan>() {
    override fun areItemsTheSame(oldItem: Loan, newItem: Loan): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Loan, newItem: Loan): Boolean =
        oldItem == newItem
}