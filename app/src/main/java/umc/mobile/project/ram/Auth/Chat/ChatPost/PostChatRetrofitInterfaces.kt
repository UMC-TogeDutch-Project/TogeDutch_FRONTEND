package umc.mobile.project.ram.Auth.Chat.ChatPost

import retrofit2.Call
import retrofit2.http.*

interface PostChatRetrofitInterfaces {
    @POST("chatRoom/{chatRoom_id}/chatmessage")
    fun sendChat(@Path("chatRoom_id") chatRoom_id : Int, userId : Int, content : String) : Call<PostChatResponse>
}