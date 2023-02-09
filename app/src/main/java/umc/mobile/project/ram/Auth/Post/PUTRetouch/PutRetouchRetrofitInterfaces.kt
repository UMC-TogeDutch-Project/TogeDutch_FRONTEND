package umc.mobile.project.ram.Auth.Post.PUTRetouch

import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface PutRetouchRetrofitInterfaces {
    @Multipart
    @PUT("post/{post_id}")
    fun putRetouch(@Path("post_id") post_id : Int, @Query("user") user_id : Int, @Body request : Request_put, @Part file : MultipartBody.Part? ) : Call<PutRetouchResponse>
}