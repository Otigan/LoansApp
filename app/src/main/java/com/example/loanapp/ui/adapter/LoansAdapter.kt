package com.example.loanapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.loanapp.databinding.ItemLoanBinding
import com.example.loanapp.domain.entity.Loan

class LoansAdapter : RecyclerView.Adapter<LoansAdapter.LoanViewHolder>() {

    var loans: List<Loan> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class LoanViewHolder(private val binding: ItemLoanBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(loanItem: Loan) {
            binding.txtLoanId.text = loanItem.id.toString()
            binding.txtLoanAmount.text = loanItem.amount.toString()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LoanViewHolder {
        val binding = ItemLoanBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LoanViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LoanViewHolder, position: Int) {
        val currentItem = loans[position]
        holder.bind(currentItem)
    }

    override fun getItemCount(): Int =
        loans.size
}