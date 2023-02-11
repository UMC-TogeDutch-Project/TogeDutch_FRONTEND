package umc.mobile.project.ram.Auth.Application.Join

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import umc.mobile.project.getRetrofit

class JoinApplicationGetService {
    private lateinit var joinApplicationGetResult: JoinApplicationGetResult

    fun setJoinApplicationGetResult(joinApplicationGetResult: JoinApplicationGetResult) {
        this.joinApplicationGetResult = joinApplicationGetResult
    }

    fun getJoinApplication(user_id: Int) {
        val apiGetService = getRetrofit().create(JoinApplicationGetRetrofitInterface::class.java)

        apiGetService.getJoinApplication(user_id).enqueue(object : Callback<JoinApplicationGetResponse> {
            override fun onResponse(call: Call<JoinApplicationGetResponse>, response: Response<JoinApplicationGetResponse>, ) {
                Log.d("JOINAPPLICATION-GET SUCCESS", response.toString())
                val resp: JoinApplicationGetResponse = response.body()!!
                Log.d("success code: ", resp.code.toString())
                when (resp.code) {
//                    1000 ->
                    1000 -> joinApplicationGetResult.getJoinApplicationSuccess(resp.code, resp.result!!)
                    else -> joinApplicationGetResult.getJoinApplicationFailure(resp.code, resp.message)
                }
            }

            override fun onFailure(call: Call<JoinApplicationGetResponse>, t: Throwable) {
                Log.d("JOINAPPLICATION-GET FAILURE", t.message.toString())
            }
        })
    }
}