package com.example.loanapp.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Loan(
    val amount: Int = 0,
    val date: String = "",
    val firstName: String = "",
    val id: Int = 0,
    val lastName: String = "",
    val percent: Double = 0.0,
    val period: Int = 0,
    val phoneNumber: String = "",
    val state: String = "",
) : Parcelable