package umc.mobile.project

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL2 = "https://maps.googleapis.com/"

fun getRetrofit2(): Retrofit {
    val retrofit = Retrofit.Builder().baseUrl(BASE_URL2)
        .addConverterFactory(GsonConverterFactory.create()).build()

    return retrofit
}