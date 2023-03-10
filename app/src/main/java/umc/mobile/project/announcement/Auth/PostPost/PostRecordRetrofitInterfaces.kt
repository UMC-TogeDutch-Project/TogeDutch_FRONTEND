package umc.mobile.project.announcement.Auth.PostPost

import retrofit2.Call
import okhttp3.MultipartBody
import retrofit2.http.*

interface PostRecordRetrofitInterfaces {
    @Multipart
    @POST("post")
    fun sendPost(@Query("user") user_id: Int, @Part("post") postRecord: PostRecord, @Part file: MultipartBody.Part?) : Call<PostRecordResponse>
}