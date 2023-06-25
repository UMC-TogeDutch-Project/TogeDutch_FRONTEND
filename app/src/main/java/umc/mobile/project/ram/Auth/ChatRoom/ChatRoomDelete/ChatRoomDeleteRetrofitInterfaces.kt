package umc.mobile.project.ram.Auth.Chat.ChatDelete


import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.Path

interface ChatRoomDeleteRetrofitInterfaces {
    @DELETE("chatRoom/{chatRoom_id}")
    fun deleteChatRoom (@Path("chatRoom_id") chatRoom_id: Int): Call<ChatRoomDeleteResponse>
}