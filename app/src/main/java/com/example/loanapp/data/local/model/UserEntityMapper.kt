package com.example.loanapp.data.local.model

import com.example.loanapp.domain.entity.User
import com.example.loanapp.domain.util.Mapper

class UserEntityMapper : Mapper<UserEntity, User> {

    override fun mapToDomainModel(model: UserEntity): User =
        User(
            user_id = model.user_id,
            name = model.name,
            role = model.role,
            token = model.token,
        )


    override fun mapFromDomainModel(domainModel: User): UserEntity =
        UserEntity(
            user_id = domainModel.user_id,
            token = domainModel.token
        )
}