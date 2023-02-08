package umc.mobile.project.news

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Query


interface NewsApiService {
    @GET("post/category")
    fun getPostFromCategory(
        @Query("category") category: String,
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double
    ): Call<UpLoadResponse>
}