package com.example.loanapp.tools.annotations

@Repeatable
@Retention(AnnotationRetention.SOURCE)
@Target(AnnotationTarget.FUNCTION)
annotation class TestCase(val name: String, val description: String = "")