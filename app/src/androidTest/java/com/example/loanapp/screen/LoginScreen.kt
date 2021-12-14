package com.example.loanapp.screen

import com.example.loanapp.R
import io.github.kakaocup.kakao.edit.KEditText
import io.github.kakaocup.kakao.screen.Screen
import io.github.kakaocup.kakao.text.KButton


object LoginScreen : Screen<LoginScreen>() {


    val loginInput = KEditText { withId(R.id.input_login) }
    val passwordInput = KEditText { withId(R.id.input_password) }
    val loginButton = KButton { withId(R.id.btn_login) }

}