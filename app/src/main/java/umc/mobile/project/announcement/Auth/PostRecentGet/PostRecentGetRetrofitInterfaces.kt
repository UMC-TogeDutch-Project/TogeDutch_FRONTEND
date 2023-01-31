package umc.mobile.project.announcement.Auth.PostRecentGet

import retrofit2.Call
import retrofit2.http.*


interface PostRecentGetRetrofitInterfaces {
    @GET("post/?sort=latest")
    fun getPost() : Call<PostRecentGetResponse>
}