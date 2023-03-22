package umc.mobile.project.ram.Auth.ChatRoomOfUser.ChatRoomOfUserGet

import android.util.Log
import umc.mobile.project.getRetrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChatRoomOfUserGetService {
    private lateinit var chatRoomOfUserGetResult: ChatRoomOfUserGetResult

    fun setMemberChatGetResult(chatRoomOfUserGetResult: ChatRoomOfUserGetResult){
        this.chatRoomOfUserGetResult = chatRoomOfUserGetResult
    }

    fun getChatRoomOfUser(chatRoom_id : Int){
        val chatMemberGetService = getRetrofit().create(ChatRoomOfUserGetRetrofitInterfaces::class.java)

        chatMemberGetService.getChatRoomOfUser(chatRoom_id).enqueue(object : Callback<ChatMemberGetResponse> {
            override fun onResponse(call: Call<ChatMemberGetResponse>, response: Response<ChatMemberGetResponse>,) {
                Log.d("CHAT-GET SUCCESS",response.toString())
                if(response.body() != null) {
                    val resp: ChatMemberGetResponse = response.body()!!
                    when (resp.code) {
                        1000 -> chatRoomOfUserGetResult.getChatMemberSuccess(resp.code, resp.result!!)
                        else -> chatRoomOfUserGetResult.getChatMemberFailure(resp.code, resp.message)
                    }
                }
                else
                    Log.d("CHAT-GET FAILURE", "NULL")

            }

            override fun onFailure(call: Call<ChatMemberGetResponse>, t: Throwable) {
                Log.d("CHAT-GET FAILURE",t.message.toString())
            }
        })
    }
}