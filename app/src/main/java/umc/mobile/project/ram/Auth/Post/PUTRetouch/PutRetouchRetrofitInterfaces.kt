package umc.mobile.project.ram.Auth.Post.PUTRetouch

import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface PutRetouchRetrofitInterfaces {
    @Multipart
    @PUT("post/{postIdx}")
    fun putRetouch(@Path("postIdx") post_id : Int, @Part("post") request : Request_put, @Query("user") user_id : Int, @Part file : MultipartBody.Part? ) : Call<PutRetouchResponse>
}