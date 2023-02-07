package umc.mobile.project.ram.Auth.Chat.ChatAllGet


import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ChatAllGetRetrofitInterfaces {
    @GET("chatRoom/{chatRoom_id}/conversation")
    fun getChatAll (@Path("chatRoom_id") chatRoom_id: Int): Call<ChatAllGetResponse>
}