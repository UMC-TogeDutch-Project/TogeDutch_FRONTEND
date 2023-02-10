package umc.mobile.project.news

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Query


interface NewsApiService {
    @GET("post/category")
    fun getPostFromCategory(
        @Query("category1") category1: String?,
        @Query("category2") category2: String?,
        @Query("category3") category3: String?,
        @Query("category4") category4: String?,
        @Query("category5") category5: String?,
        @Query("category6") category6: String?,
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double
    ): Call<UpLoadResponse>
}