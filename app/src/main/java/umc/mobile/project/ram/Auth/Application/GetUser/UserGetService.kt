package umc.mobile.project.ram.Auth.Application.GetUser

import android.util.Log
import umc.mobile.project.getRetrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserGetService {
    private lateinit var userGetResult: UserGetResult

    fun setUserGetResult(userGetResult: UserGetResult){
        this.userGetResult = userGetResult
    }

    fun getUser(user_id : Int){
        val postUploadDetailGetService = getRetrofit().create(UserGetRetrofitInterfaces::class.java)

        postUploadDetailGetService.getUser(user_id).enqueue(object : Callback<UserGetResponse> {
            override fun onResponse(call: Call<UserGetResponse>, response: Response<UserGetResponse>,) {
                Log.d("POSTUPLOAD-GET SUCCESS",response.toString())
                val resp : UserGetResponse = response.body()!!
                when(resp.code) {
//                    1000 ->
                    1000 -> userGetResult.getUserSuccess(resp.code, resp.result!!)
                    else -> userGetResult.getUserFailure(resp.code, resp.message)
                }
            }

            override fun onFailure(call: Call<UserGetResponse>, t: Throwable) {
                Log.d("POSTUPLOAD-GET FAILURE",t.message.toString())
            }
        })
    }
}