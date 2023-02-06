package umc.mobile.project.ram.Auth.Chat.ChatAllGet

import android.util.Log
import umc.mobile.project.getRetrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChatAllGetService {
    private lateinit var chatAllGetResult: ChatAllGetResult

    fun setChatAllGetResult(chatAllGetResult: ChatAllGetResult){
        this.chatAllGetResult = chatAllGetResult
    }

    fun getChatAll(chatRoom_id : Int){
        val chatAllGetService = getRetrofit().create(ChatAllGetRetrofitInterfaces::class.java)

        chatAllGetService.getChatAll(chatRoom_id).enqueue(object : Callback<ChatAllGetResponse> {
            override fun onResponse(call: Call<ChatAllGetResponse>, response: Response<ChatAllGetResponse>,) {
                Log.d("CHATALL-GET SUCCESS",response.toString())
                val resp : ChatAllGetResponse = response.body()!!
                when(resp.code) {
//                    1000 ->
                    1000 -> chatAllGetResult.getChatAllSuccess(resp.code, resp.result!!)
                    else -> chatAllGetResult.getChatAllFailure(resp.code, resp.message)
                }
            }

            override fun onFailure(call: Call<ChatAllGetResponse>, t: Throwable) {
                Log.d("CHATALL-GET FAILURE",t.message.toString())
            }
        })
    }
}