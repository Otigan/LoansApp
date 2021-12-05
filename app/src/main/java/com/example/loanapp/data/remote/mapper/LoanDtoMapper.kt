package com.example.loanapp.data.remote.mapper

import com.example.loanapp.data.remote.model.LoanDto
import com.example.loanapp.domain.entity.Loan
import com.example.loanapp.domain.util.Mapper

class LoanDtoMapper : Mapper<LoanDto, Loan> {

    override fun mapToDomainModel(model: LoanDto): Loan =
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


    override fun mapFromDomainModel(domainModel: Loan): LoanDto =
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