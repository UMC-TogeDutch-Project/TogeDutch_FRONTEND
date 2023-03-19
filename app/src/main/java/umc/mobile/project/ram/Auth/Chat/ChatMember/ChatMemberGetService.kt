package umc.mobile.project.ram.Auth.Chat.ChatMember

import android.util.Log
import umc.mobile.project.getRetrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChatMemberGetService {
    private lateinit var chatMemberGetResult: ChatMemberGetResult

    fun setMemberChatGetResult(chatMemberGetResult: ChatMemberGetResult){
        this.chatMemberGetResult = chatMemberGetResult
    }

    fun getChatMember(chatRoom_id : Int, chat_id : Int){
        val chatMemberGetService = getRetrofit().create(ChatMemberGetRetrofitInterfaces::class.java)

        chatMemberGetService.getChatAll(chatRoom_id, chat_id).enqueue(object : Callback<ChatMemberGetResponse> {
            override fun onResponse(call: Call<ChatMemberGetResponse>, response: Response<ChatMemberGetResponse>,) {
                Log.d("CHAT-GET SUCCESS",response.toString())
                if(response.body() != null) {
                    val resp: ChatMemberGetResponse = response.body()!!
                    when (resp.code) {
                        1000 -> chatMemberGetResult.getChatMemberSuccess(resp.code, resp.result!!)
                        else -> chatMemberGetResult.getChatMemberFailure(resp.code, resp.message)
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