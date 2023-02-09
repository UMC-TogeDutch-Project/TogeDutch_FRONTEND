package umc.mobile.project.ram.Auth.Chat.ChatPost

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import umc.mobile.project.getRetrofit
import java.sql.Timestamp
import java.util.*

class PostChatService {

    var timestamp = Timestamp(Date().time)
    private var result : Result = Result(chatId = 1, chatRoomId = 1, userId = 1, createdAt = timestamp, content = "", writer = "", type = "")

    private lateinit var postChatResult: PostChatResult

    fun setChatResult(postChatResult: PostChatResult){
        this.postChatResult = postChatResult
    }

    fun sendChat(chatRoom_id : Int, user_id : Int, message : chatPost){
        val authService = getRetrofit().create(PostChatRetrofitInterfaces::class.java)
        authService.sendChat(chatRoom_id, user_id, message).enqueue(object: Callback<PostChatResponse> {
            override fun onResponse(call: Call<PostChatResponse>, response: Response<PostChatResponse>) {
                Log.d("SNED-ACCEPT SUCCESS",response.toString())
                if(response.body() != null) {
                    val resp: PostChatResponse = response.body()!!
                    result = resp.result!!
                    when (resp.code) {
                        1000 -> postChatResult.sendChatSuccess(result)
                        else -> postChatResult.sendChatFailure()
                    }
                }
                else{
                    Log.d("SNED-ACCEPT FAILURE","null")
                }
            }

            override fun onFailure(call: Call<PostChatResponse>, t: Throwable) {
                Log.d("SNED-ACCEPT FAILURE",t.message.toString())
            }
        })
    }
}