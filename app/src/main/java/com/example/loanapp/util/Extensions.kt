package com.example.loanapp.util

import android.content.Context
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import com.google.android.material.textfield.TextInputLayout

object Extensions {

    public fun TextInputLayout.check() {
        val editText = this.editText!!
    }

    fun Context.displayToast(errorMsg: String) {
        Toast.makeText(this, errorMsg, Toast.LENGTH_SHORT).show()
    }

    fun ProgressBar.displayProgressBar(loading: Boolean) {
        this.visibility = if (loading) View.VISIBLE else View.GONE
    }

}