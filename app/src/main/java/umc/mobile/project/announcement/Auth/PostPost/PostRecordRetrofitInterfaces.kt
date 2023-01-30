package umc.mobile.project.announcement.Auth.PostPost

import retrofit2.Call
import okhttp3.MultipartBody
import retrofit2.http.*

interface PostRecordRetrofitInterfaces {
    @POST("post?user={user_id}")
    fun sendPost(@Query("user_id") user_id: Int, @Body postRecord: PostRecord) : Call<PostRecordResponse>
}