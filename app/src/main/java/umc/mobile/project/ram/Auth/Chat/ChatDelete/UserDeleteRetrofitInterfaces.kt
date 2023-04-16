package umc.mobile.project.ram.Auth.Chat.ChatDelete


import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.Path

interface UserDeleteRetrofitInterfaces {
    @DELETE("chatRoom/{chatRoom_id}/user/{user_id}/leave")
    fun deleteUser (@Path("chatRoom_id") chatRoom_id: Int, @Path("user_id") user_id : Int): Call<ChatRoomDeleteResponse>
}