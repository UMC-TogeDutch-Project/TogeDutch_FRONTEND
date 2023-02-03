package umc.mobile.project.mypage.GetUser

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import umc.mobile.project.getRetrofit

class UserGetService {
    private lateinit var userGetResult: UserGetResult

    fun setUserGetResult(userGetResult: UserGetResult){
        this.userGetResult = userGetResult
    }

    fun getUser(userIdx : Int){
        val userGetService = getRetrofit().create(UserGetRetrofitInterface::class.java)

        userGetService.getUser(userIdx).enqueue(object : Callback<UserGetResponse> {
            override fun onResponse(call: Call<UserGetResponse>, response: Response<UserGetResponse>,) {
                Log.d("USER-GET SUCCESS",response.toString())
                val resp : UserGetResponse = response.body()!!
                Log.d("success code: ", resp.code.toString())
                when(resp.code) {
//                    1000 ->
                    1000 -> userGetResult.getUserSuccess(resp.code, resp.result!!)
                    else -> userGetResult.getUserFailure(resp.code, resp.message)
                }
            }

            override fun onFailure(call: Call<UserGetResponse>, t: Throwable) {
                Log.d("USER-GET FAILURE",t.message.toString())
            }
        })
    }
}