package com.diegoribeiro.taskwars.remote

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient() {

    companion object{
        private const val BASE_URL = "http://192.168.1.103:8080/"
        private lateinit var retrofitClient: Retrofit

        private val client = OkHttpClient().newBuilder()
        private fun getRetrofitInstance(): Retrofit{
            if (!::retrofitClient.isInitialized){
                retrofitClient = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofitClient
        }
    }

    fun <T> createService(serviceClass: Class<T>): T = getRetrofitInstance().create(serviceClass)

}