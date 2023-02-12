package umc.mobile.project.ram.Auth.Chat.ChatMeetTimePost

import android.util.Log
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import umc.mobile.project.getRetrofit
import java.sql.Timestamp
import java.util.*

class PostChatMeetTimeService {

    var timestamp = Timestamp(Date().time)
    private var result : ChatMeetTime = ChatMeetTime(chatMeetTimeId = 1, chatRoomId = 1, userId = 1, meetTime = "", createdAt = timestamp)

    private lateinit var postChatMeetTimeResult: PostChatMeetTimeResult

    fun setPhotoResult(postChatMeetTimeResult: PostChatMeetTimeResult){
        this.postChatMeetTimeResult = postChatMeetTimeResult
    }

    fun sendChatMeetTime(chatRoom_id : Int, user_id : Int, time : String){
        val authService = getRetrofit().create(PostChatMeetTimeRetrofitInterfaces::class.java)
        authService.sendChatMeetTime(chatRoom_id, user_id, time).enqueue(object: Callback<PostChatMeetTimeResponse> {
            override fun onResponse(call: Call<PostChatMeetTimeResponse>, response: Response<PostChatMeetTimeResponse>) {
                Log.d("SNED-ChatMeetTime SUCCESS",response.toString())
                if(response.body() != null) {
                    val resp: PostChatMeetTimeResponse = response.body()!!
                    result = resp.result!!
                    when (resp.code) {
                        1000 -> postChatMeetTimeResult.sendChatMeetTimeSuccess(result)
                        else -> postChatMeetTimeResult.sendChatMeetTimeFailure()
                    }
                }
                else{
                    Log.d("SNED-ChatMeetTime FAILURE","null")
                }
            }

            override fun onFailure(call: Call<PostChatMeetTimeResponse>, t: Throwable) {
                Log.d("SNED-ChatMeetTime FAILURE",t.message.toString())
            }
        })
    }
}