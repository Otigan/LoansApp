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

    fun mapFromDomainModel(domainModel: Loan): LoanEntity =
        LoanEntity(
            id = domainModel.id,
            amount = domainModel.amount,
            date = domainModel.date,
            firstName = domainModel.firstName,
            lastName = domainModel.lastName,
            percent = domainModel.percent,
            period = domainModel.period,
            phoneNumber = domainModel.phoneNumber,
            state = domainModel.state
        )
}