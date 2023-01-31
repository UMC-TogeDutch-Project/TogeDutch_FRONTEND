package umc.mobile.project.announcement.Auth.PostImminentGet

import retrofit2.Call
import retrofit2.http.*


interface PostImminentGetRetrofitInterfaces {
    @GET("post")
    fun getPost(@Query("sort") type: String) : Call<PostImminentResponse>
}