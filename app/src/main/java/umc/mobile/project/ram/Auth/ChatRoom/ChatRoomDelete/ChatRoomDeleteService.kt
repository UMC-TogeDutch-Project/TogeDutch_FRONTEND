package umc.mobile.project.ram.Auth.Chat.ChatDelete

import android.util.Log
import umc.mobile.project.getRetrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChatRoomDeleteService {
    private lateinit var chatRoomDeleteResult: ChatRoomDeleteResult

    fun setDeleteChatRoomResult(chatRoomDeleteResult: ChatRoomDeleteResult){
        this.chatRoomDeleteResult = chatRoomDeleteResult
    }

    fun deleteChatRoom(chatRoom_id : Int){
        val authService = getRetrofit().create(ChatRoomDeleteRetrofitInterfaces::class.java)

        authService.deleteChatRoom(chatRoom_id).enqueue(object : Callback<ChatRoomDeleteResponse> {
            override fun onResponse(call: Call<ChatRoomDeleteResponse>, response: Response<ChatRoomDeleteResponse>,) {
                Log.d("CHAT-GET SUCCESS",response.toString())
                if(response.body() != null) {
                    val resp: ChatRoomDeleteResponse = response.body()!!
                    when (resp.code) {
                        1000 -> chatRoomDeleteResult.chatRoomDeleteSuccess(resp.code, resp.result!!)
                        else -> chatRoomDeleteResult.chatRoomDeleteFailure(resp.code, resp.message)
                    }
                }
                else
                    Log.d("CHAT-GET FAILURE", "NULL")

            }

            override fun onFailure(call: Call<ChatRoomDeleteResponse>, t: Throwable) {
                Log.d("CHAT-GET FAILURE",t.message.toString())
            }
        })
    }
}