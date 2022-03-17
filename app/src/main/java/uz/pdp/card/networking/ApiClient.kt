package uz.pdp.card.networking

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    var BASE_URl = "https://6232aea28364d63035c18588.mockapi.io/api/v1/"

    private val retrofit = getRetrofit()


    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URl)
            .build()
    }

    fun <T> createService(service: Class<T>): T {
        return retrofit.create(service)
    }
}