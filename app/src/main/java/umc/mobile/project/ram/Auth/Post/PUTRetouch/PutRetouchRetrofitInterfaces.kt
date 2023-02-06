package umc.mobile.project.ram.Auth.Post.PUTRetouch

import retrofit2.Call
import retrofit2.http.*

interface PutRetouchRetrofitInterfaces {
    @POST("post/{post_id}")
    fun putRetouch(@Path("post_id") post_id : Int, @Query("user") user_id : Int, request : Request_put ) : Call<PutRetouchResponse>
}