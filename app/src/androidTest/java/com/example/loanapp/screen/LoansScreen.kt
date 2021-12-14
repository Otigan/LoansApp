package com.example.loanapp.screen

import android.view.View
import com.example.loanapp.R
import io.github.kakaocup.kakao.recycler.KRecyclerItem
import io.github.kakaocup.kakao.recycler.KRecyclerView
import io.github.kakaocup.kakao.screen.Screen
import io.github.kakaocup.kakao.text.KTextView
import org.hamcrest.Matcher

object LoansScreen : Screen<LoansScreen>() {

    val loans = KRecyclerView(
        builder = { withId(R.id.recycler_view_loans) },
        itemTypeBuilder = { itemType(LoansScreen::LoanItem) }
    )

    class LoanItem(parent: Matcher<View>) : KRecyclerItem<LoanItem>(parent) {
        val date = KTextView(parent) { withId(R.id.txt_loan_date) }
        val amount = KTextView(parent) { withId(R.id.txt_view_amount) }
        val state = KTextView(parent) { withId(R.id.txt_loan_status) }
    }
}