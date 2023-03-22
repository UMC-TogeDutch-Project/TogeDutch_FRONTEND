package umc.mobile.project.ram.Auth.ChatRoomOfUser.IsReadPut

import android.util.Log
import umc.mobile.project.getRetrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class IsReadPutService {
    private lateinit var isReadPutResult: IsReadPutResult

    fun setIsReadPutResult(isReadPutResult: IsReadPutResult){
        this.isReadPutResult = isReadPutResult
    }

    fun putIsRead(chatRoom_id : Int, chat_id : Int){
        val chatMemberGetService = getRetrofit().create(IsReadPutRetrofitInterfaces::class.java)

        chatMemberGetService.putIsRead(chatRoom_id, chat_id).enqueue(object : Callback<IsReadPutResponse> {
            override fun onResponse(call: Call<IsReadPutResponse>, response: Response<IsReadPutResponse>,) {
                Log.d("READ-PUT SUCCESS",response.toString())
                if(response.body() != null) {
                    val resp: IsReadPutResponse = response.body()!!
                    when (resp.code) {
                        1000 -> isReadPutResult.putIsReadSuccess(resp.code, resp.result!!)
                        else -> isReadPutResult.putIsReadFailure(resp.code, resp.message)
                    }
                }
                else
                    Log.d("READ-PUT FAILURE", "NULL")

            }

            override fun onFailure(call: Call<IsReadPutResponse>, t: Throwable) {
                Log.d("READ-PUT FAILURE",t.message.toString())
            }
        })
    }
}