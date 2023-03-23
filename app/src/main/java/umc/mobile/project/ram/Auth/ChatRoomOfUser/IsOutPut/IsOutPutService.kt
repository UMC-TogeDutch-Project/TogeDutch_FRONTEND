package umc.mobile.project.ram.Auth.ChatRoomOfUser.IsOutPut

import android.util.Log
import umc.mobile.project.getRetrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class IsOutPutService {
    private lateinit var isOutPutResult: IsOutPutResult

    fun setIsOutPutResult(isOutPutResult: IsOutPutResult){
        this.isOutPutResult = isOutPutResult
    }

    fun putIsOut(chatRoom_id : Int, chat_id : Int){
        val chatMemberGetService = getRetrofit().create(IsOutPutRetrofitInterfaces::class.java)

        chatMemberGetService.putIsOut(chatRoom_id, chat_id).enqueue(object : Callback<IsOutPutResponse> {
            override fun onResponse(call: Call<IsOutPutResponse>, response: Response<IsOutPutResponse>,) {
                Log.d("OUT-PUT SUCCESS",response.toString())
                if(response.body() != null) {
                    val resp: IsOutPutResponse = response.body()!!
                    when (resp.code) {
                        1000 -> isOutPutResult.putIsOutSuccess(resp.code, resp.result!!)
                        else -> isOutPutResult.putIsOutFailure(resp.code, resp.message)
                    }
                }
                else
                    Log.d("OUT-PUT FAILURE", "NULL")

            }

            override fun onFailure(call: Call<IsOutPutResponse>, t: Throwable) {
                Log.d("OUT-PUT FAILURE",t.message.toString())
            }
        })
    }
}