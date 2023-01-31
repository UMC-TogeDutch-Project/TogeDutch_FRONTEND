package umc.mobile.project.announcement.Auth.PostGet

import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*


interface PostGetRetrofitInterfaces {
    @GET("post")
    fun getPost(@Query("sort") type: String) : Call<PostGetResponse>
}