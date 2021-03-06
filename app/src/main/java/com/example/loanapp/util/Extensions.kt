package com.example.loanapp.util

import android.content.Context
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import com.example.loanapp.R
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout

object Extensions {

    fun View.displaySnackbar(errorMsg: String) {
        Snackbar.make(this, errorMsg, Snackbar.LENGTH_SHORT).show()
    }

    fun TextInputLayout.checkIfNotEmpty(): Boolean {
        val inputLayout = this.editText
        val inputText = inputLayout?.text.toString()
        return if (inputText.isBlank()) {
            this.error = this.context.getString(R.string.empty_field_error)
            false
        } else {
            this.error = ""
            true
        }
    }

    fun TextInputLayout.checkIfPasswordsAreSame(password: TextInputLayout): Boolean {
        val inputConfPassword = this.editText?.text.toString()
        val inputPassword = password.editText?.text.toString()
        return if (inputPassword != inputConfPassword) {
            this.error = this.context.getString(R.string.password_doesnt_match)
            false
        } else {
            this.error = ""
            true
        }
    }

    fun Context.displayToast(errorMsg: String) {
        Toast.makeText(this, errorMsg, Toast.LENGTH_SHORT).show()
    }

    fun ProgressBar.displayProgressBar(loading: Boolean) {
        this.visibility = if (loading) View.VISIBLE else View.GONE
    }
}