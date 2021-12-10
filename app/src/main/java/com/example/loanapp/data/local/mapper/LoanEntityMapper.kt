package com.example.loanapp.data.local.mapper

import com.example.loanapp.data.local.db.LoanEntity
import com.example.loanapp.data.remote.model.LoanDto
import com.example.loanapp.domain.entity.Loan
import com.example.loanapp.domain.util.Mapper

class LoanEntityMapper : Mapper<LoanEntity, LoanDto> {

    fun mapToLoan(model: LoanEntity): Loan =
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

    override fun mapToDomainModel(model: LoanEntity): LoanDto =
        LoanDto(
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


    override fun mapFromDomainModel(domainModel: LoanDto): LoanEntity =
        LoanEntity(
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