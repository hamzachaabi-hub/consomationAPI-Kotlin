package com.example.consomationapirec

import retrofit2.Call
import retrofit2.http.GET

interface RetrofitAPI {
    // as we are making get request
    @GET("users")
    fun getCourse(): Call<UsersDataModal?>?
}