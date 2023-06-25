package umc.mobile.project.ram.Auth.Chat.ChatDelete

import android.util.Log
import umc.mobile.project.getRetrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserDeleteService {
    private lateinit var userDeleteResult: UserDeleteResult

    fun setUserDeleteResult(userDeleteResult: UserDeleteResult){
        this.userDeleteResult = userDeleteResult
    }

    fun deleteUser(chatRoom_id : Int, user_id : Int){
        val authService = getRetrofit().create(UserDeleteRetrofitInterfaces::class.java)

        authService.deleteUser(chatRoom_id, user_id).enqueue(object : Callback<UserDeleteResponse> {
            override fun onResponse(call: Call<UserDeleteResponse>, response: Response<UserDeleteResponse>,) {
                Log.d("CHAT-GET SUCCESS",response.toString())
                if(response.body() != null) {
                    val resp: UserDeleteResponse = response.body()!!
                    when (resp.code) {
                        1000 -> userDeleteResult.userDeleteSuccess(resp.code, resp.result!!)
                        else -> userDeleteResult.userDeleteFailure(resp.code, resp.message)
                    }
                }
                else
                    Log.d("CHAT-GET FAILURE", "NULL")

            }

            override fun onFailure(call: Call<UserDeleteResponse>, t: Throwable) {
                Log.d("CHAT-GET FAILURE",t.message.toString())
            }
        })
    }
}