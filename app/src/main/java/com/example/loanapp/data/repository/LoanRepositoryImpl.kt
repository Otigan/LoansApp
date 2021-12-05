package com.example.loanapp.data.repository

import android.util.Log
import com.example.loanapp.data.local.db.UserDao
import com.example.loanapp.data.remote.data_source.loan.LoanConditionDataSource
import com.example.loanapp.data.remote.data_source.loan.LoanDataSource
import com.example.loanapp.data.remote.data_source.loan.LoanRequestDataSource
import com.example.loanapp.data.remote.model.LoanDto
import com.example.loanapp.data.remote.model.LoanRequestBody
import com.example.loanapp.domain.entity.LoanCondition
import com.example.loanapp.domain.repository.LoanRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject

class LoanRepositoryImpl @Inject constructor(
    private val loanDataSource: LoanDataSource,
    private val loanConditionDataSource: LoanConditionDataSource,
    private val loanRequestDataSource: LoanRequestDataSource,
    private val userDao: UserDao
) :
    LoanRepository {

    override suspend fun getAllLoans(name: String): Flow<Response<List<LoanDto>>> =
        loanDataSource.getAllLoans(getToken(name))

    override suspend fun requestLoan(
        name: String,
        loanRequestBody: LoanRequestBody
    ): Flow<Response<LoanDto>> =
        loanRequestDataSource.requestLoan(getToken(name), loanRequestBody)

    override suspend fun getLoanCondition(name: String): Flow<Response<LoanCondition>> =
        loanConditionDataSource.getLoanCondition(getToken(name))

    private suspend fun getToken(name: String): String? {
        val user = userDao.searchByUserName(name)
        Log.i("LoanRepositoryImpl", user.name!!)
        return user.token
    }

}