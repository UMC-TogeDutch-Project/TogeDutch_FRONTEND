package umc.mobile.project.news

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET


interface NewsApiService {
    @GET("post/category")
    fun getPostFromCategory(
        @Body body: UpLoadRequest
    ): Call<UpLoadResponse>
}