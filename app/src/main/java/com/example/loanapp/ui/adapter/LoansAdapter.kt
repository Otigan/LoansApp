package com.example.loanapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.loanapp.R
import com.example.loanapp.databinding.ItemLoanBinding
import com.example.loanapp.domain.entity.Loan

class LoansAdapter : ListAdapter<Loan, LoansAdapter.LoanViewHolder>(LoanComparator()) {

    class LoanViewHolder(private val binding: ItemLoanBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(loanItem: Loan) {
            binding.apply {
                root.context.apply {
                    txtLoanId.text = getString(R.string.txt_view_loan_id, loanItem.id)
                    txtLoanStatus.text = getString(R.string.txt_view_loan_status, loanItem.state)
                    txtViewAmount.text = getString(R.string.txt_view_amount, loanItem.amount)
                    when (loanItem.state) {
                        "REGISTERED" -> {
                            imgLoanStatus.setImageDrawable(
                                AppCompatResources.getDrawable(
                                    this,
                                    R.drawable.icon_loan_registered
                                )
                            )
                        }
                        "APPROVED" -> {
                            imgLoanStatus.setImageDrawable(
                                AppCompatResources.getDrawable(
                                    this,
                                    R.drawable.icon_loan_approved
                                )
                            )
                        }
                        "REJECTED" -> {
                            imgLoanStatus.setImageDrawable(
                                AppCompatResources.getDrawable(
                                    this,
                                    R.drawable.icon_loan_rejected
                                )
                            )
                        }
                    }
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LoanViewHolder {
        val binding = ItemLoanBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LoanViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LoanViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }
}

private class LoanComparator : DiffUtil.ItemCallback<Loan>() {
    override fun areItemsTheSame(oldItem: Loan, newItem: Loan): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Loan, newItem: Loan): Boolean =
        oldItem == newItem
}