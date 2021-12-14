package com.example.loanapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.loanapp.databinding.ItemLanguageBinding
import com.example.loanapp.domain.entity.Language

class LanguageAdapter(private val onClick: (Language) -> Unit) :
    RecyclerView.Adapter<LanguageAdapter.LanguageViewHolder>() {

    var languages = emptyList<Language>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class LanguageViewHolder(
        private val binding: ItemLanguageBinding,
        private val onClick: (Language) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Language) {
            binding.apply {
                root.setOnClickListener {
                    onClick(item)
                }

                languageName.text = item.name
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LanguageViewHolder {
        val binding =
            ItemLanguageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LanguageViewHolder(binding, onClick)
    }

    override fun onBindViewHolder(holder: LanguageViewHolder, position: Int) {
        val currentItem = languages[position]
        holder.bind(currentItem)
    }

    override fun getItemCount(): Int = languages.size

}