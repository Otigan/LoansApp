package com.example.loanapp.data.remote.mapper

import com.example.loanapp.data.remote.model.AuthTokenDto
import com.example.loanapp.domain.entity.AuthToken
import com.example.loanapp.domain.util.Mapper

class AuthTokenDtoMapper : Mapper<AuthTokenDto, AuthToken> {

    override fun mapToDomainModel(model: AuthTokenDto): AuthToken =
        AuthToken(
            token = model.token
        )

    override fun mapFromDomainModel(domainModel: AuthToken): AuthTokenDto =
        AuthTokenDto(token = domainModel.token)
}