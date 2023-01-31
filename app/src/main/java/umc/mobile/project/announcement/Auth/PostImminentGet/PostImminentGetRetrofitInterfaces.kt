package umc.mobile.project.announcement.Auth.PostImminentGet

import retrofit2.Call
import retrofit2.http.*


interface PostImminentGetRetrofitInterfaces {
    @GET("post/?sort=imminent")
    fun getPost() : Call<PostImminentResponse>
}