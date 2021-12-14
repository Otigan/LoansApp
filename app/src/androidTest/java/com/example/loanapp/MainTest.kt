package com.example.loanapp

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.loanapp.screen.LoansScreen
import com.example.loanapp.screen.LoginScreen
import com.example.loanapp.tools.annotations.TestCase
import com.example.loanapp.ui.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainTest : KTestCase() {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    @TestCase(name = "Main Test", description = "Check login flow and loans history recycler view")
    fun checkLoginFieldDisplayed() {
        run {
            step("Pass login screen") {
                LoginScreen {
                    loginInput {
                        typeText("SampleUser")
                    }
                    passwordInput {
                        typeText("123")
                    }
                    loginButton {
                        click()
                    }
                }
            }
            step("Check size of RecyclerView") {
                LoansScreen {
                    loans {
                        check(getSize() == 3)
                    }
                }
            }
            step("Check content of loans history") {
                checkLoans(
                    Loan(date = "14.12.2021", amount = "2222 RUB", state = "На рассмотрении"),
                    Loan(date = "14.12.2021", amount = "1111 RUB", state = "Отклонено"),
                    Loan(date = "14.12.2021", amount = "2600 RUB", state = "Одобрена"),
                )
            }

        }
    }
}

data class Loan(val date: String, val amount: String, val state: String)

private fun checkLoans(vararg loans: Loan) {
    loans.forEachIndexed { index, loan ->
        LoansScreen {
            loans {
                childAt<LoansScreen.LoanItem>(index) {
                    date {
                        isDisplayed()
                        hasText(loan.date)
                    }
                    amount {
                        isDisplayed()
                        hasText(loan.amount)
                    }
                    state {
                        isDisplayed()
                        hasText(loan.state)
                    }
                }
            }
        }
    }
}