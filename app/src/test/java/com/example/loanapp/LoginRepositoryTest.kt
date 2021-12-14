package com.example.loanapp

import com.example.loanapp.data.remote.model.LoginRequestBody
import com.example.loanapp.data.repository.FakeLoginDataSource
import com.example.loanapp.data.repository.FakeLoginRepository
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.Assert.assertEquals
import org.junit.Test
import retrofit2.Response

class LoginRepositoryTest {


    @Test
    fun `WHEN user not found EXPECT 404`() {
        val fakeLoginDataSource = FakeLoginDataSource()
        val fakeLoginRepository = FakeLoginRepository(fakeLoginDataSource)
        runBlocking {
            val response = fakeLoginRepository.login(LoginRequestBody("Test", "test"))
            val expected = Response.error<String>(
                404,
                ResponseBody.create(MediaType.parse("application/json"), "Пользователь не найден")
            )
            assertEquals(response.errorMessage, expected.errorBody()?.string())
        }
    }

}