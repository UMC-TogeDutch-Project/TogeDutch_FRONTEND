package umc.mobile.project.ram.Auth.Chat.ChatGet


import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ChatGetRetrofitInterfaces {
    @GET("chatRoom/{chatRoom_id}/chatmessage/{chat_id}")
    fun getChatAll (@Path("chatRoom_id") chatRoom_id: Int, @Path("chat_id") chat_id : Int): Call<ChatGetResponse>
}