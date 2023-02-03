package umc.mobile.project.ram.Auth.Application.GetUser


import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface UserGetRetrofitInterfaces {
//    @GET("post/{post_id}/")
    @GET("user/{user_id}")
    fun getUser (@Path("user_id") user_id: Int): Call<UserGetResponse>
}