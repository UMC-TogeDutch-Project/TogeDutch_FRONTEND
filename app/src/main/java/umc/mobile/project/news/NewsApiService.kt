package umc.mobile.project.news

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import umc.mobile.project.news.upload.KeywordResponse
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

    @GET("user/{user_id}/keyword")
    fun getKeyword(
        @Path("user_id") user_id : Int
    ) : Call<KeywordResponse>



}