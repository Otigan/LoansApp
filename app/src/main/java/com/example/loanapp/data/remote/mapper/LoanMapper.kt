package com.example.loanapp.data.remote.mapper

import com.example.loanapp.data.local.db.LoanEntity
import com.example.loanapp.data.remote.model.LoanDto
import com.example.loanapp.domain.entity.Loan
import javax.inject.Inject

class LoanMapper @Inject constructor() {

    fun mapLoanDtoToLoanEntity(model: LoanDto): LoanEntity =
        LoanEntity(
            amount = model.amount,
            date = model.date,
            firstName = model.firstName,
            id = model.id,
            lastName = model.lastName,
            percent = model.percent,
            period = model.period,
            phoneNumber = model.phoneNumber,
            state = model.state
        )

    fun mapLoanDtoToLoan(model: LoanDto): Loan =
        Loan(
            amount = model.amount,
            date = model.date,
            firstName = model.firstName,
            id = model.id,
            lastName = model.lastName,
            percent = model.percent,
            period = model.period,
            phoneNumber = model.phoneNumber,
            state = model.state
        )


    fun mapLoanToLoanDto(domainModel: Loan): LoanDto =
        LoanDto(
            amount = domainModel.amount,
            date = domainModel.date,
            firstName = domainModel.firstName,
            id = domainModel.id,
            lastName = domainModel.lastName,
            percent = domainModel.percent,
            period = domainModel.period,
            phoneNumber = domainModel.phoneNumber,
            state = domainModel.state
        )

}