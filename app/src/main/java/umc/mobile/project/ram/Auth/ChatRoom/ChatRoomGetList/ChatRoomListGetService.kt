package umc.mobile.project.ram.Auth.ChatRoom.ChatRoomGetList

import android.util.Log
import umc.mobile.project.getRetrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChatRoomListGetService {
    private lateinit var childRoomListGetResult: ChatRoomListGetResult

    fun setChatRoomListGetResult(childRoomListGetResult: ChatRoomListGetResult){
        this.childRoomListGetResult = childRoomListGetResult
    }

    fun getChatRoomUpload(user_id : Int){
        val chatRoomListGetService = getRetrofit().create(ChatRoomListGetRetrofitInterfaces::class.java)

        chatRoomListGetService.getChatRoomUpload(user_id).enqueue(object : Callback<ChatRoomListGetResponse> {
            override fun onResponse(call: Call<ChatRoomListGetResponse>, response: Response<ChatRoomListGetResponse>,) {
                Log.d("POSTUPLOAD-GET SUCCESS",response.toString())
                val resp : ChatRoomListGetResponse = response.body()!!
                when(resp.code) {
//                    1000 ->
                    1000 -> childRoomListGetResult.getChatRoomListSuccess(resp.code, resp.result!!)
                    else -> childRoomListGetResult.getChatRoomListFailure(resp.code, resp.message)
                }
            }

            override fun onFailure(call: Call<ChatRoomListGetResponse>, t: Throwable) {
                Log.d("POSTUPLOAD-GET FAILURE",t.message.toString())
            }
        })
    }
}