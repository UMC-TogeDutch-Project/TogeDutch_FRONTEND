package umc.mobile.project

import retrofit2.Call
import retrofit2.http.GET

interface AdsRandomApiService {
    @GET("ad/random")
    fun adsRandom(): Call<AdsRandomResponse>
}