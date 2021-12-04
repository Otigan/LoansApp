package com.example.loanapp.data.remote.mapper

import com.example.loanapp.data.remote.model.UserDto
import com.example.loanapp.domain.entity.User
import com.example.loanapp.domain.util.Mapper

class UserDtoMapper : Mapper<UserDto, User> {

    override fun mapToDomainModel(model: UserDto): User =
        User(
            name = model.name,
            role = model.role
        )

    override fun mapFromDomainModel(domainModel: User): UserDto =
        UserDto(
            name = domainModel.name,
            role = domainModel.role
        )
}