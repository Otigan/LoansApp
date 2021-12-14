package com.example.loanapp.data.local.mapper

import com.example.loanapp.data.local.db.LoanEntity
import com.example.loanapp.domain.entity.Loan
import javax.inject.Inject

class LoanEntityMapper @Inject constructor() {

    fun mapToDomainModel(model: LoanEntity): Loan =
        Loan(
            id = model.id,
            amount = model.amount,
            date = model.date,
            firstName = model.firstName,
            lastName = model.lastName,
            percent = model.percent,
            period = model.period,
            phoneNumber = model.phoneNumber,
            state = model.state
        )

}