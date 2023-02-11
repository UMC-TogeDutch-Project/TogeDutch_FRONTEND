package umc.mobile.project.news

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import umc.mobile.project.news.upload.UpLoadRequest
import umc.mobile.project.news.upload.UpLoadResponse


interface NewsApiService {
    @POST("post/category")
    fun getPostFromCategory(
        @Body body: UpLoadRequest
//        @Query("category1") category1: String?,
//        @Query("category2") category2: String?,
//        @Query("category3") category3: String?,
//        @Query("category4") category4: String?,
//        @Query("category5") category5: String?,
//        @Query("category6") category6: String?,
//        @Query("latitude") latitude: Double,
//        @Query("longitude") longitude: Double
    ): Call<UpLoadResponse>



}