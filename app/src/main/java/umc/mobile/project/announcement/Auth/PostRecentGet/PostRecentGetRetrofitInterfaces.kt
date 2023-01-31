package umc.mobile.project.announcement.Auth.PostRecentGet

import retrofit2.Call
import retrofit2.http.*


interface PostRecentGetRetrofitInterfaces {
    @GET("post")
    fun getPost(@Query("sort") type: String) : Call<PostRecentGetResponse>
}