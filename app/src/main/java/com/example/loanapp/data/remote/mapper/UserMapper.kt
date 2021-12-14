package com.example.loanapp.data.remote.mapper

import com.example.loanapp.data.remote.model.UserDto
import com.example.loanapp.domain.entity.User
import javax.inject.Inject

class UserMapper @Inject constructor() {

    fun mapUserDtoToUser(userDto: UserDto): User =
        User(
            name = userDto.name,
            role = userDto.role
        )

}