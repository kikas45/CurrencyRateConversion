package com.example.rates_conversion.fxdatas

import com.example.rates_conversion.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface RateAPI {

    @GET("/latest?access_key=${Constants.API_KEY}")
    suspend fun getRates(
        @Query("base") base: String
    ): Response<MoneyResponse>


}