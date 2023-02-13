package umc.mobile.project.ram.Auth.Chat.ChatMeetTimePost

import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface PostChatMeetTimeRetrofitInterfaces {
    @POST("chatRoom/{chatRoom_id}/chatMeetTime")
    fun sendChatMeetTime(@Path("chatRoom_id") chatRoom_id : Int, @Query("user") user_id : Int, @Query("time") time : String) : Call<PostChatMeetTimeResponse>
}