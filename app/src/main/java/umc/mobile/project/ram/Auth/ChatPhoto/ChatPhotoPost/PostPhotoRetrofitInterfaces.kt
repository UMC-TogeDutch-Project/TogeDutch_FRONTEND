package umc.mobile.project.ram.Auth.ChatPhoto.ChatPhotoPost

import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface PostPhotoRetrofitInterfaces {
    @Multipart
    @POST("chatRoom/{chatRoom_id}/chatPhoto")
    fun sendPhoto(@Path("chatRoom_id") chatRoom_id : Int, @Query("user") user_id : Int, @Part file : MultipartBody.Part) : Call<PostPhotoResponse>
}