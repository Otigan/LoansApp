package com.example.loanapp.data.remote.util

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthErrorHandler @Inject constructor() {

    operator fun invoke(errorCode: Int): String =
        when (errorCode) {
            401 -> {
                "Пользователь не авторизован"
            }
            403 -> {
                "Доступ запрещен"
            }
            404 -> {
                "Данный аккаунт не найден, проверьте правильность введенных данных"
            }
            500 -> {
                "Ошибка на сервере"
            }
            else -> "Неизвестная ошибка"
        }
}