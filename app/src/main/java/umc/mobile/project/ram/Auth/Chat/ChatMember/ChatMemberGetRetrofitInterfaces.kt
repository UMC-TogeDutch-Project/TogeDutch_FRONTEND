package umc.mobile.project.ram.Auth.Chat.ChatMember


import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ChatMemberGetRetrofitInterfaces {
    @GET("/chatRoom/{chatRoom_id}/{user_id}")
    fun getChatAll (@Path("chatRoom_id") chatRoom_id: Int, @Path("user_id") user_id : Int): Call<ChatMemberGetResponse>
}