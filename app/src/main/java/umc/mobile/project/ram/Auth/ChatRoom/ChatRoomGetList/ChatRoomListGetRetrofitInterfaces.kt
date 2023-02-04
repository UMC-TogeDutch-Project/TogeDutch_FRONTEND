package umc.mobile.project.ram.Auth.ChatRoom.ChatRoomGetList


import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ChatRoomListGetRetrofitInterfaces {
//    @GET("post/{post_id}/")
    @GET("user/{user_id}/chatroom")
    fun getChatRoomUpload (@Path("user_id") user_id: Int): Call<ChatRoomListGetResponse>
}