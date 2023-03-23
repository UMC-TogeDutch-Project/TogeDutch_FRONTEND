package umc.mobile.project.ram.Auth.ChatRoomOfUser.ChatRoomOfUserGet


import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ChatRoomOfUserGetRetrofitInterfaces {
    @GET("chatRoom/{chatRoom_id}/users")
    fun getChatRoomOfUser (@Path("chatRoom_id") chatRoom_id: Int): Call<ChatMemberGetResponse>
}