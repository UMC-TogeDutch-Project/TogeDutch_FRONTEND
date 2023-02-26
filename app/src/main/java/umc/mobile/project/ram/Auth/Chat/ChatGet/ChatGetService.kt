package umc.mobile.project.ram.Auth.Chat.ChatGet

import android.util.Log
import umc.mobile.project.getRetrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChatGetService {
    private lateinit var chatGetResult: ChatGetResult

    fun setChatGetResult(chatGetResult: ChatGetResult){
        this.chatGetResult = chatGetResult
    }

    fun getChat(chatRoom_id : Int, chat_id : Int){
        val chatGetService = getRetrofit().create(ChatGetRetrofitInterfaces::class.java)

        chatGetService.getChatAll(chatRoom_id, chat_id).enqueue(object : Callback<ChatGetResponse> {
            override fun onResponse(call: Call<ChatGetResponse>, response: Response<ChatGetResponse>,) {
                Log.d("CHAT-GET SUCCESS",response.toString())
                if(response.body() != null) {
                    val resp: ChatGetResponse = response.body()!!
                    when (resp.code) {
                        1000 -> chatGetResult.getChatSuccess(resp.code, resp.result!!)
                        else -> chatGetResult.getChatFailure(resp.code, resp.message)
                    }
                }
                else
                    Log.d("CHAT-GET FAILURE", "NULL")

            }

            override fun onFailure(call: Call<ChatGetResponse>, t: Throwable) {
                Log.d("CHAT-GET FAILURE",t.message.toString())
            }
        })
    }
}